//Timer based interrupt for outputing a 3x3 single-colour Multiplexed light
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

//Output LED states.
//Last 3 Bits are LED's.... DONT SET HIGHER BITS AS 1...
#include "WProgram.h"
void setup(void);
void loop(void);
byte Display[3] = {B000101, B000010, B000101};

#define NLAYERS = 3
const byte Layers[3] = {B110000,B101000,B011000};


int curlayer = 0;
byte tempout;
int clockcnt=0;

ISR(TIMER2_OVF_vect) {
 
 //SLOW DOWN CLOCK
 //if (clockcnt == 1000){
   
   //precalculate what to put on port.... 0 EXCESS BITS
   tempout = Display[curlayer] & B00000111;
   
   //Now combine with layer
   tempout = tempout | Layers[curlayer];
   
   //Write to Port
   PORTC = tempout;
   
   //Move to next layer:
   curlayer++;
   
   //Check layer is in bounds..
   if (curlayer > 2) curlayer = 0; 
   
   //reset counter
   //clockcnt = 1;
  //}
  //clockcnt++;
  RESET_TIMER2;
};



void setup(void) 
{
  DDRC = B11111111;
  
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
}


void loop(void) 
{
  Display[0] = B000101;
  Display[1] = B000010;
  Display[2] = B000101;
  delay (400);
  
  Display[0] = B000010;
  Display[1] = B000101;
  Display[2] = B000010;
  delay (400);
  
  Display[0] = B000101;
  Display[1] = B000000;
  Display[2] = B000101;
  delay (100);
  
  Display[0] = B000000;
  Display[1] = B000101;
  Display[2] = B000000;
  delay (100);
  
  Display[0] = B000100;
  Display[1] = B000000;
  Display[2] = B000001;
  delay (100);
  
  Display[0] = B000010;
  Display[1] = B000000;
  Display[2] = B000010;
  delay (100);
  
  Display[0] = B000001;
  Display[1] = B000000;
  Display[2] = B000100;
  delay (100);
  
  Display[0] = B000000;
  Display[1] = B000101;
  Display[2] = B000000;
  delay (100);
  
  Display[0] = B000100;
  Display[1] = B000000;
  Display[2] = B000000;
  delay (400);
  
  Display[0] = B000100;
  Display[1] = B000010;
  Display[2] = B000000;
  delay (400);
  
  Display[0] = B000100;
  Display[1] = B000010;
  Display[2] = B000001;
  delay (400);
  
  Display[0] = B000100;
  Display[1] = B000010;
  Display[2] = B000011;
  delay (400);
  
  Display[0] = B000100;
  Display[1] = B000010;
  Display[2] = B000111;
  delay (400);
  
  Display[0] = B000101;
  Display[1] = B000010;
  Display[2] = B000111;
  delay (400);
  
  Display[0] = B000111;
  Display[1] = B000010;
  Display[2] = B000111;
  delay (400);
  
}
int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

