// Fading LED 
// by BARRAGAN <http://people.interaction-ivrea.it/h.barragan> 

int value = 0; 
int value2;                          // variable to keep the actual value 
int R1 = 9;                         // light connected to digital pin 9
int R2 = 5;
int R3 = 10;
int Bl1 = 6;
int Bl2 = 3;
int Bl3 = 11;
 
void setup() 
{ 
  // nothing for setup 
} 
 
void loop() 
{ 
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    analogWrite(R1, value);           // sets the value (range from 0 to 255) 
    analogWrite(R2, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value2);           // sets the value (range from 0 to 255)
    delay(30);                            // waits for 30 milli seconds to see the dimming effect 
  } 
  for(value = 255; value >=0; value-=5)   // fade out (from max to min) 
  { 
    value2 = 255-value;
    analogWrite(R1, value);           // sets the value (range from 0 to 255) 
    analogWrite(R2, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value2);           // sets the value (range from 0 to 255)
    delay(30); 
  }
  
  for(value = 255; value >=0; value-=10)   // fade out (from max to min) 
  {
    analogWrite(Bl1, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value);           // sets the value (range from 0 to 255)
  }
  
  
  for(value = 255; value >=0; value-=5)   // fade out (from max to min) 
  { 
    value2 = 255-value;
    analogWrite(R1, value);           // sets the value (range from 0 to 255) 
    analogWrite(Bl2, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value2);           // sets the value (range from 0 to 255)
    analogWrite(R2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value2);           // sets the value (range from 0 to 255)
    delay(30); 
  }  
} 
