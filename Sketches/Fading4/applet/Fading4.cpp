// Fading LED 
// by BARRAGAN <http://people.interaction-ivrea.it/h.barragan> 

#define DELAYD 80

#include "WProgram.h"
void setup();
void loop();
int value1 = 0;// variable to keep the actual value goes between 0-512, 0-256 = blue, 256-512 = red
int value2 = 0;
int value3 = 0;
int temp;
int R1 = 5;                         // light connected to digital pin 9
int R2 = 9;
int R3 = 10;
int Bl1 = 3;
int Bl2 = 6;
int Bl3 = 11;

int rwait = 0;
 
void setup() 
{ 
  // nothing for setup 
} 
 
void loop() 
{ 
  if (value1 > 256)
  {
    temp = value1 - 256;
    analogWrite (R1, temp);
    analogWrite (Bl1, 256-temp);
  }else{
    analogWrite (Bl1, value1);
    analogWrite (R1, 0);
  }
  
  if (value2 > 256)
  {
    temp = value2 - 256;
    analogWrite (R2, temp);
    analogWrite (Bl2, 256-temp);
  }else{
    analogWrite (Bl2, value2);
    analogWrite (R2, 0);
  }
  
  if (value3 > 256)
  {
    temp = value3 - 256;
    analogWrite (R3, temp);
    analogWrite (Bl3, 256-temp);
  }else{
    analogWrite (Bl3, value3);
    analogWrite (R3, 0);
  }
  
  if (value1 > 0) value1 = (float) value1/1.4;
  if (value2 > 0) value2 = (float) value2/1.4;
  if (value3 > 0) value3  =(float) value3/1.4;
  
  if (rwait >20){
    value1 += random(500);
    value2 += random(500);
    value3 += random(500);
    rwait = 0;
  }
  
  rwait++;
  
  delay (50);
  
} 

int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

