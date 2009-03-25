//Timer based interrupt for outputing a 2x2 Bi-colour Multiplexed light


//Output LED states.
byte Display[2] = {B00001111, B11110000};
define NLAYERS = 2

//Define LED-GRND PINS
#define lay1 8
#define lay2 9

int curlayer = 0


ISR(TIMER2_OVF_vect) {
  
  if (int_counter == 1000) {
    second+=1;
    int_counter = 0;
  } 
  RESET_TIMER2;
};



void setup(void) 
{
  pinMode(lay1, OUTPUT);
  pinMode(lay2, OUTPUT);
}


void loop(void) 
{
}
