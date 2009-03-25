//Timer based interrupt for outputing a 3x3 single-colour Multiplexed light
//Use PORTC, Pins 0,1,2 as anode (with resistor) and link common cathodes onto Pins 3,4,5
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

//Output LED states.
//Last 3 Bits are LED's.... DONT SET HIGHER BITS AS 1...
byte Display[3] = {B000101, B000010, B000101};

#define NLAYERS = 3

//Defines states for output pins B00XXX000, the X bits correspond to pins 3,4,5, which we set
const byte Layers[3] = {B110000,B101000,B011000};


int curlayer = 0;  //current layer
byte tempout;      //tempory output storage before writing to port
int clockcnt=0;    //used for slowing down output.

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
  //Just a cool animation written to the Display array over time...
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
