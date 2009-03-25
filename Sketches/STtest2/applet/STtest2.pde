#define PORTZ PORTC
#define DDRZ DDRC
#define CLKON B010000;
#define LAYERCLK B100000
#define DMASK B11001111

#define LAYERTOG 3

//okay, so pins 0-3 data, pin 4 CLK, pin 5 layer CLK

//here im going to code for 4 SR writes simultaneously. The End Bit is the one I care aobut

byte Display[2][8] = {{B1, B0, B1, B0, B1, B0 ,B1 ,B0 },{B0, B1, B0, B1, B0 ,B1 ,B0, B1}};
byte temp;

//int to hold current layer...
int curlayer=0;
int i;

void setup(void) 
{
  DDRZ = B11111111;
  pinMode(LAYERTOG, OUTPUT);
  
}

void loop(void) 
{
  for (i=0; i<8; i++){
    PORTZ = Display[curlayer][i]; //& DMASK;
    PORTZ |= CLKON;
    //delay(100);
  }
  
  if (curlayer==1) digitalWrite(LAYERTOG, HIGH);
  
  PORTZ |= LAYERCLK;
  
  digitalWrite(LAYERTOG, LOW);
  curlayer++;
  
  if(curlayer>1) curlayer = 0;
  
  delayMicroseconds (200);
}
  

