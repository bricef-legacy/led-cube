//Timer based interrupt for outputing a 2x2 Bi-colour Multiplexed light
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

//Output LED states.
byte Display[2] = {B0111, B1110};
#define NLAYERS = 2;
#define CLKMAX 1000

//Define LED-GRND PINS
int layerPins[2] = {8,9};

int curlayer = 0;
int clockcnt=0;


ISR(TIMER2_OVF_vect) {
  if (clockcnt == 1000){
  digitalWrite(layerPins[curlayer], HIGH);
  curlayer += 1;
  if (curlayer==2) curlayer = 0;
  PORTC = Display[curlayer];
  digitalWrite(layerPins[curlayer], LOW);
  clockcnt = 1;
  }
  clockcnt++;
  RESET_TIMER2;
  
};



void setup(void) 
{
  //set pins up for output
  pinMode(layerPins[0], OUTPUT);
  pinMode(layerPins[1], OUTPUT);
  pinMode(13, OUTPUT);
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
  digitalWrite(13, LOW);
  delay (1000);
  digitalWrite(13, HIGH);
  delay (1000);
  //Display[0] = B0011;
}
