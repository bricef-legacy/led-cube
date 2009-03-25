//Timer based interrupt for outputing a 2x2 Bi-colour Multiplexed light
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

#define PORTZ PORTC
#define DDRZ DDRC

//Output LED layers. RRGG (pins 0-4, PORTC)
byte Display[2] = {B00000111, B00001110};

//layer definition array: (pins 5-6, PORTC) remenber LOW is ON.
const byte Layers[2] = {B00100000,B00010000};
#define NLAYERS = 2

//int to hold current layer...
int curlayer=0;

//int to delay loop by counting timer ticks...
int clockcnt=0;

//tempory byte for layer buffering
byte temp;


//the timer.... called regulalrly to draw the layers.. NO ANIMATION DONE HERE!
ISR(TIMER2_OVF_vect) {
  
  //delay by waiting for so many ticks...
  //if (clockcnt == 1000){
  
    //lets buffer the display... first off, lets ZERO any bytes that are going to interfere with our layers... (Using BITOPS)
    temp = B00001111 & Display[curlayer];
    
    //now lets addatively combine the layer, and layer data to the output
    PORTZ = temp | Layers[curlayer];
    
    //now lets move to the next layer...
    curlayer++;
    
    //check if layer is still available! if not, back to square 0
    if (curlayer >= 2) curlayer = 0;
    
  //clockcnt = 1;
  //}
  //clockcnt++;
  RESET_TIMER2;
  
};



void setup(void) 
{
  DDRZ = B11111111;
  
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
  Display[0] = B1101;
  Display[1] = B0001;
  delay(200);
  
  Display[0] = B0100;
  Display[1] = B0111;
  delay(200);

  Display[0] = B0010;
  Display[1] = B1110;
  delay(200);
  
  Display[0] = B1011;
  Display[1] = B1000;
  delay(200);
  
  
  
}
