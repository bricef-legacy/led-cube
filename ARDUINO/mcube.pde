#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

#define PORTZ PORTC
#define DDRZ DDRC
#define CLKON B010000
#define LAYERCLK B100000
#define DMASK B11001111

#define LAYERTOG 3

//okay, so pins 0-3 data, pin 4 CLK, pin 5 layer CLK, and output pin 3 is the layer init. (on different port)

byte Display[8][32];

//int to hold current layer...
int curlayer=0;
int currow;
int j;
int k;

ISR(TIMER2_OVF_vect) {

  for (currow=0; currow<32; currow++){
    PORTZ = Display[curlayer][currow];
    PORTZ |= CLKON;
  }

  if (curlayer != 7){
    digitalWrite(LAYERTOG, LOW);
    curlayer++;
  }
  else{
    digitalWrite(LAYERTOG, HIGH);
    curlayer = 0;
  }

  PORTZ |= LAYERCLK;

  RESET_TIMER2;

};



void setup(void) 
{
  //set pins up for output
  DDRZ = B11111111;
  pinMode(LAYERTOG, OUTPUT);

  //lets zero our matrix....
  for (j=0; j<2; j++){
    for (k=0; k<32; k++){
      Display[j][k] = B0;
    }
  }
  
  for (k=0; k<32; k++){
      Display[0][k] = B1;
   }

  k=0;

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
}
