//Timer based interrupt for outputing a 2x2 Bi-colour Multiplexed light
#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = 0

#define PORTZ PORTC
#define DDRZ DDRC
#define CLKON B010000;
#define LAYERCLK B100000
#define DMASK B11001111

#define LAYERTOG 3

//okay, so pins 0-3 data, pin 4 CLK, pin 5 layer CLK, and output pin 3 is the layer init. (on different port)

byte Display[2][16] = {
  {
    B1, B0, B1, B0, B1, B0 ,B1 ,B0, B1, B0, B1, B0, B1, B0 ,B1 ,B0     }
  ,{
    B0, B1, B0, B1, B0 ,B1 ,B0, B1, B0, B1, B0, B1, B0 ,B1 ,B0, B1    }
};
byte temp;

//int to hold current layer...
int curlayer=0;
int i;
int j;
int n;
int k;

int clockcnt=0;


ISR(TIMER2_OVF_vect) {
  if (clockcnt == 2){
    for (i=0; i<16; i++){
      PORTZ = Display[curlayer][i]; //& DMASK;
      PORTZ |= CLKON;
      //delay(100);
    }

    if (curlayer==1) digitalWrite(LAYERTOG, HIGH);

    PORTZ |= LAYERCLK;

    digitalWrite(LAYERTOG, LOW);
    curlayer++;

    if(curlayer>1) curlayer = 0;
    clockcnt = 1;
  }
  clockcnt++;
  RESET_TIMER2;

};



void setup(void) 
{
  //set pins up for output
  DDRZ = B11111111;
  pinMode(LAYERTOG, OUTPUT);
  
  //lets zero our matrix....
  for (j=0; j<2; j++){
    for (k=0; k<16; k++){
      Display[j][k] = B0;
    }
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
  for (j=0; j<2; j++){
    for (k=0; k<16; k++){
      Display[j][k] = B1;
      delay(50);
    }
  }
  
  for (j=0; j<4; j++){
    Display[1][j+12] |= B10;
  }
  
  for (j=0; j<2; j++){
    for (k=0; k<16; k++){
      Display[j][k] &= B10;
      delay(50);
    }
  }
  
  
  for (j=0; j<4; j++){
    Display[1][j+12] = B0;
  }
  for (j=0; j<5; j++){
    for (k=0; k<8; k++){
      Display[0][k] = B1;
      Display[0][15-k] = B1;
      Display[1][k] = B1;
      Display[1][15-k] = B1;
      delay(100);
    }
    for (k=0; k<8; k++){
      Display[0][k] = B0;
      Display[0][15-k] = B0;
      Display[1][k] = B0;
      Display[1][15-k] = B0;
      delay(100);
    }
  }
}
