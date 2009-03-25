// Fading LED 
// by BARRAGAN <http://people.interaction-ivrea.it/h.barragan> 

int value = 0; 
int value2;                          // variable to keep the actual value 
int ledpin = 11;                         // light connected to digital pin 9
int ledpin2 = 9;
int ledpin3 = 6;
int ledpin4 = 5;
 
void setup() 
{ 
  // nothing for setup 
} 
 
void loop() 
{ 
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 225-value;
    analogWrite(ledpin, value);           // sets the value (range from 0 to 255) 
    analogWrite(ledpin2, value);           // sets the value (range from 0 to 255)
    analogWrite(ledpin3, value2);           // sets the value (range from 0 to 255)
    analogWrite(ledpin4, value2);           // sets the value (range from 0 to 255)
    delay(30);                            // waits for 30 milli seconds to see the dimming effect 
  } 
  for(value = 255; value >=0; value-=5)   // fade out (from max to min) 
  { 
    value2 = 225-value;
    analogWrite(ledpin, value);
    analogWrite(ledpin2, value);           // sets the value (range from 0 to 255)
    analogWrite(ledpin3, value2);           // sets the value (range from 0 to 255)
    analogWrite(ledpin4, value2);           // sets the value (range from 0 to 255)
    delay(30); 
  }  
} 
