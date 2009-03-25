//Timer based interrupt for outputing a 3x3 single-colour Multiplexed light
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

//Output LED states.
//Last 3 Bits are LED's.... DONT SET HIGHER BITS AS 1...
byte Display[3] = {B000111, B000111, B000111};

#define NLAYERS = 3
const byte Layers[3] = {B110000,B101000,B011000};


int curlayer = 0;
byte tempout;
int clockcnt=0;

ISR(TIMER2_OVF_vect) {
  
 if (clockcnt == 1000){
    PORTC = B110111;
    
   
    clockcnt = 1;
  }
  clockcnt++;
  RESET_TIMER2;
};



void setup(void) 
{
  DDRC = B11111111;
}


void loop(void) 
{
  //PORTC = B110111;
  delay (1000);
}
