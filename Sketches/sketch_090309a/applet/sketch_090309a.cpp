//#include < avr / interrupt.h >
//#include < avr / io.h >

#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = INIT_TIMER_COUNT

#include "WProgram.h"
void setup();
void loop();
unsigned char SetupTimer2(float timeoutFrequency);
void setup(void);
void loop(void);
int ledPin = 13;
int int_counter = 0;
volatile int second = 0;
int oldSecond = 0;
long starttime = 0;

// Aruino runs at 16 Mhz, so we have 1000 Overflows per second...
// 1/ ((16000000 / 64) / 256) = 1 / 1000
ISR(TIMER2_OVF_vect) {
  RESET_TIMER2;
  int_counter += 1;
  if (int_counter == 1000) {
    second+=1;
    int_counter = 0;
  } 
};

void setup() {
  Serial.begin(9600);
  Serial.println("Initializing timerinterrupt");
  //Timer2 Settings: Timer Prescaler /64, 
  TCCR2A |= (1<<CS22);    
  TCCR2A &= ~((1<<CS21) | (1<<CS20));     
  // Use normal mode
  TCCR2A &= ~((1<<WGM21) | (1<<WGM20));  
  // Use internal clock - external clock not used in Arduino
  ASSR |= (0<<AS2);
  //Timer2 Overflow Interrupt Enable
  TIMSK2 |= (1<<TOIE2) | (0<<OCIE2A);  
  RESET_TIMER2;               
  sei();
  starttime = millis();
}

void loop() {
  if (oldSecond != second) {
    Serial.print(second);
    Serial.print(". ->");
    Serial.print(millis() - starttime);
    Serial.println(".");
    digitalWrite(ledPin, HIGH);
    delay(100);
    digitalWrite(ledPin, LOW);
    oldSecond = second;
  }
}

#define TOGGLE_IO        9  //Arduino pin to toggle in timer ISR

unsigned int latency;
unsigned int latencySum;
unsigned int sampleCount;
unsigned char timerLoadValue;

#define TIMER_CLOCK_FREQ 2000000.0 //2MHz for /8 prescale from 16MHz

//Setup Timer2.
//Configures the ATMegay168 8-Bit Timer2 to generate an interrupt at the specified frequency.
//Returns the time load value which must be loaded into TCNT2 inside your ISR routine.
//See the example usage below.
unsigned char SetupTimer2(float timeoutFrequency){
  unsigned char result; //The value to load into the timer to control the timeout interval.

  //Calculate the timer load value
  result=(int)((257.0-(TIMER_CLOCK_FREQ/timeoutFrequency))+0.5); //the 0.5 is for rounding;
  //The 257 really should be 256 but I get better results with 257, dont know why.

  //Timer2 Settings: Timer Prescaler /8, mode 0
  //Timmer clock = 16MHz/8 = 2Mhz or 0.5us
  //The /8 prescale gives us a good range to work with so we just hard code this for now.
  TCCR2A = 0;
  TCCR2B = 0<<CS22 | 1<<CS21 | 0<<CS20; 

  //Timer2 Overflow Interrupt Enable   
  TIMSK2 = 1<<TOIE2;

  //load the timer for its first cycle
  TCNT2=result; 
  
  return(result);
}

//Timer2 overflow interrupt vector handler
ISR(TIMER2_OVF_vect) {

  //Toggle the IO pin to the other state.
  digitalWrite(TOGGLE_IO,!digitalRead(TOGGLE_IO));
   
  //Capture the current timer value. This is how much error we have
  //due to interrupt latency and the work in this function
  latency=TCNT2;

  //Reload the timer and correct for latency.  //Reload the timer and correct for latency.  //Reload the timer and correct for latency.
  TCNT2=latency+timerLoadValue; 
}

void setup(void) {
  //Set the pin we want the ISR to toggle for output.
  pinMode(TOGGLE_IO,OUTPUT);

  //Start up the serial port
  Serial.begin(9600);
  
  //Signal the program start
  Serial.println("Timer2 Test");
  
  //Start the timer and get the timer reload value.
  timerLoadValue=SetupTimer2(44100);
  
  //Output the timer reload value
  Serial.print("Timer2 Load:");
  Serial.println(timerLoadValue,HEX);
}

void loop(void) {
  //Accumulate ISR latency every 10ms.
  delay(10);

  //Accumulate the current latency value form the ISR and increment the sample counter
  latencySum+=latency;
  sampleCount++;
  
  //Once we have 100 samples, calculate and output the measurements
  if(sampleCount>99) {
    float latencyAverage;
    float loadPercent;
    
    //Calculate the average latency
    latencyAverage=latencySum/100.0;

    //zero the accumulator values
    sampleCount=0;
    latencySum=0;

    //Calculate the Percentage processor load estimate
    loadPercent=latencyAverage/(float)(256.0-timerLoadValue);
    loadPercent*=100; //Scale up from ratio to percentage;
    
    //Output the average Latency
    Serial.print("Latency Average:");
    Serial.print((int)latencyAverage);
    Serial.print(".");
    latencyAverage-=(int)latencyAverage;
    Serial.print((int)(latencyAverage*100));

    //Output the load percentage estimate
    Serial.print("  Load:");
    Serial.print((int)loadPercent);
    Serial.println("%");
  }
}

int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

