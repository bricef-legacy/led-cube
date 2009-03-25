/**
 * Arduino DCF77 decoder v0.2.1
 * Copyright (C) 2006 Mathias Dalheimer (md@gonium.net)
 * Contributions from Lasse Lambrecht
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 * Where is the DCF receiver connected?
 */
#define DCF77PIN 2
/**
 * Where is the LED connected?
 */
#define BLINKPIN 13
/**
 * Turn debugging on or off
 */
//#define DCF_DEBUG 1

/**
 * Uncomment if you use an Arduino with ATMEGA168
 */
//#define ATMEGA168

/**
 * Number of milliseconds to elapse before we assume a "1",
 * if we receive a falling flank before - its a 0.
 */
#define DCF_split_millis 140
/**
 * There is no signal in second 59 - detect the beginning of 
 * a new minute.
 */
#define DCF_sync_millis 1200
/**
 * Definitions for the timer interrupt 2 handler:
 * The Arduino runs at 16 Mhz, we use a prescaler of 64 -> We need to 
 * initialize the counter with 6. This way, we have 1000 interrupts per second.
 * We use tick_counter to count the interrupts.
 */
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = INIT_TIMER_COUNT
int tick_counter = 0;
/**
 * DCF time format struct
 */
struct DCF77Buffer {
  unsigned long long prefix	:21;
  unsigned long long Min	:7;	// minutes
  unsigned long long P1		:1;	// parity minutes
  unsigned long long Hour	:6;	// hours
  unsigned long long P2		:1;	// parity hours
  unsigned long long Day	:6;	// day
  unsigned long long Weekday	:3;	// day of week
  unsigned long long Month	:5;	// month
  unsigned long long Year	:8;	// year (5 -> 2005)
  unsigned long long P3		:1;	// parity
};
struct {
	unsigned char parity_flag	:1;
	unsigned char parity_min		:1;
	unsigned char parity_hour	:1;
	unsigned char parity_date	:1;
} flags;
/**
 * Clock variables 
 */
volatile unsigned char DCFSignalState = 0;  
unsigned char previousSignalState;
int previousFlankTime;
int bufferPosition;
unsigned long long dcf_rx_buffer;
/**
 * time vars: the time is stored here!
 */
volatile unsigned char ss;
volatile unsigned char mm;
volatile unsigned char hh;
volatile unsigned char day;
volatile unsigned char mon;
volatile unsigned int year;


/**
 * used in main loop: detect a new second...
 */
unsigned char previousSecond;

/**
 * Initialize the DCF77 routines: initialize the variables,
 * configure the interrupt behaviour.
 */
void DCF77Init() {
  previousSignalState=0;
  previousFlankTime=0;
  bufferPosition=0;
  dcf_rx_buffer=0;
  ss=mm=hh=day=mon=year=0;
#ifdef DCF_DEBUG 
  Serial.println("Initializing DCF77 routines");
  Serial.print("Using DCF77 pin #");
  Serial.println(DCF77PIN);
  pinMode(BLINKPIN, OUTPUT);
  pinMode(DCF77PIN, INPUT);
#endif
  pinMode(DCF77PIN, INPUT);
#ifdef DCF_DEBUG
  Serial.println("Initializing timerinterrupt");
#endif
  //Timer2 Settings: Timer Prescaler /64, 
#ifdef ATMEGA168
  TCCR2B |= (1<<CS22);    // turn on CS22 bit
  TCCR2B &= ~((1<<CS21) | (1<<CS20));    // turn off CS21 and CS20 bits   
#else
  TCCR2 |= (1<<CS22);    // turn on CS22 bit
  TCCR2 &= ~((1<<CS21) | (1<<CS20));    // turn off CS21 and CS20 bits   
#endif
  // Use normal mode
#ifdef ATMEGA168
  TCCR2A &= ~((1<<WGM21) | (1<<WGM20));   // turn off WGM21 and WGM20 bits 
  TCCR2B &= ~(1<<WGM22);                  // turn off WGM22
#else
  TCCR2 &= ~((1<<WGM21) | (1<<WGM20));   // turn off WGM21 and WGM20 bits 
#endif
  // Use internal clock - external clock not used in Arduino
  ASSR |= (0<<AS2);
#ifdef ATMEGA168
  TIMSK2 |= (1<<TOIE2) | (0<<OCIE2A);        //Timer2 Overflow Interrupt Enable  
#else
  TIMSK |= (1<<TOIE2) | (0<<OCIE2);        //Timer2 Overflow Interrupt Enable  
#endif
  RESET_TIMER2;
#ifdef DCF_DEBUG
  Serial.println("Initializing DCF77 signal listener interrupt");
#endif  
  attachInterrupt(0, int0handler, CHANGE);
}

/**
 * Append a signal to the dcf_rx_buffer. Argument can be 1 or 0. An internal
 * counter shifts the writing position within the buffer. If position > 59,
 * a new minute begins -> time to call finalizeBuffer().
 */
void appendSignal(unsigned char signal) {
#ifdef DCF_DEBUG
  Serial.print(", appending value ");
  Serial.print(signal, DEC);
  Serial.print(" at position ");
  Serial.println(bufferPosition);
#endif
  dcf_rx_buffer = dcf_rx_buffer | ((unsigned long long) signal << bufferPosition);
  // Update the parity bits. First: Reset when minute, hour or date starts.
  if (bufferPosition ==  21 || bufferPosition ==  29 || bufferPosition ==  36) {
	flags.parity_flag = 0;
  }
  // save the parity when the corresponding segment ends
  if (bufferPosition ==  28) {flags.parity_min = flags.parity_flag;};
  if (bufferPosition ==  35) {flags.parity_hour = flags.parity_flag;};
  if (bufferPosition ==  58) {flags.parity_date = flags.parity_flag;};
  // When we received a 1, toggle the parity flag
  if (signal == 1) {
    flags.parity_flag = flags.parity_flag ^ 1;
  }
  bufferPosition++;
  if (bufferPosition > 59) {
    finalizeBuffer();
  }
}

/**
 * Evaluates the information stored in the buffer. This is where the DCF77
 * signal is decoded and the internal clock is updated.
 */
void finalizeBuffer(void) {
  if (bufferPosition == 59) {
#ifdef DCF_DEBUG
    Serial.println("Finalizing Buffer");
#endif
    struct DCF77Buffer *rx_buffer;
    rx_buffer = (struct DCF77Buffer *)(unsigned long long)&dcf_rx_buffer;
    if (flags.parity_min == rx_buffer->P1  &&
        flags.parity_hour == rx_buffer->P2  &&
        flags.parity_date == rx_buffer->P3) 
    { 
#ifdef DCF_DEBUG
      Serial.println("Parity check OK - updating time.");
#endif
      //convert the received bits from BCD
      mm = rx_buffer->Min-((rx_buffer->Min/16)*6);
      hh = rx_buffer->Hour-((rx_buffer->Hour/16)*6);
      day= rx_buffer->Day-((rx_buffer->Day/16)*6); 
      mon= rx_buffer->Month-((rx_buffer->Month/16)*6);
      year= 2000 + rx_buffer->Year-((rx_buffer->Year/16)*6);
    }
#ifdef DCF_DEBUG
      else {
        Serial.println("Parity check NOK - running on internal clock.");
    }
#endif
  } 
  // reset stuff
  ss = 0;
  bufferPosition = 0;
  dcf_rx_buffer=0;
}

/**
 * Dump the time to the serial line.
 */
void serialDumpTime(void){
  Serial.print("Time: ");
  Serial.print(hh, DEC);
  Serial.print(":");
  Serial.print(mm, DEC);
  Serial.print(":");
  Serial.print(ss, DEC);
  Serial.print(" Date: ");
  Serial.print(day, DEC);
  Serial.print(".");
  Serial.print(mon, DEC);
  Serial.print(".");
  Serial.println(year, DEC);
}


/**
 * Evaluates the signal as it is received. Decides whether we received
 * a "1" or a "0" based on the 
 */
void scanSignal(void){ 
    if (DCFSignalState == 1) {
      int thisFlankTime=millis();
      if (thisFlankTime - previousFlankTime > DCF_sync_millis) {
#ifdef DCF_DEBUG
        Serial.println("####");
        Serial.println("#### Begin of new Minute!!!");
        Serial.println("####");
#endif
        finalizeBuffer();
      }
      previousFlankTime=thisFlankTime;
#ifdef DCF_DEBUG
      Serial.print(previousFlankTime);
      Serial.print(": DCF77 Signal detected, ");
#endif
    } 
    else {
      /* or a falling flank */
      int difference=millis() - previousFlankTime;
#ifdef DCF_DEBUG
      Serial.print("duration: ");
      Serial.print(difference);
#endif
      if (difference < DCF_split_millis) {
        appendSignal(0);
      } 
      else {
        appendSignal(1);
      }
    }
}

/**
 * The interrupt routine for counting seconds - increment hh:mm:ss.
 */
ISR(TIMER2_OVF_vect) {
  RESET_TIMER2;
  tick_counter += 1;
  if (tick_counter == 1000) {
    ss++;
    if (ss==60) {
      ss=0;
      mm++;
      if (mm==60) {
        mm=0;
        hh++;
        if (hh==24) 
          hh=0;
      }
    }
    tick_counter = 0;
  }
};

/**
 * Interrupthandler for INT0 - called when the signal on Pin 2 changes.
 */
void int0handler() {
  // check the value again - since it takes some time to
  // activate the interrupt routine, we get a clear signal.
  DCFSignalState = digitalRead(DCF77PIN);
}


/**
 * Standard Arduino methods below.
 */

void setup(void) {
  // We need to start serial here again, 
  // for Arduino 007 (new serial code)
  Serial.begin(9600);
  DCF77Init();
}

void loop(void) {
  if (ss != previousSecond) {
    serialDumpTime();
    previousSecond = ss;
  }
  if (DCFSignalState != previousSignalState) {
    scanSignal();
    if (DCFSignalState) {
      digitalWrite(BLINKPIN, HIGH);
    } else {
      digitalWrite(BLINKPIN, LOW);
    }
    previousSignalState = DCFSignalState;
  }
    //delay(20);
}
