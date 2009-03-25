// Fading LED 
// by BARRAGAN <http://people.interaction-ivrea.it/h.barragan> 

#define DELAYD 30

int value = 0; 
int value2;                          // variable to keep the actual value 
int R1 = 5;                         // light connected to digital pin 9
int R2 = 9;
int R3 = 10;
int Bl1 = 3;
int Bl2 = 6;
int Bl3 = 11;
 
void setup() 
{ 
  // nothing for setup 
} 
 
void loop() 
{ 
  //LHSIDE RED FADE ON
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    analogWrite(R1, value);           // sets the value (range from 0 to 255) 
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
  //FADE RED RIGHT AND FADE PREV TO BLUE
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    analogWrite(R1, value2);           // sets the value (range from 0 to 255)
    analogWrite(R2, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
  //INVERT COLS and FADE NEXT RED
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    analogWrite(R1, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value);           // sets the value (range from 0 to 255)
    analogWrite(R2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value2);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
  //Fade next...
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    analogWrite(R1, value2);           // sets the value (range from 0 to 255)
    analogWrite(R3, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value);           // sets the value (range from 0 to 255)
    analogWrite(R2, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
  //Start Fading out..
  for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    //analogWrite(R1, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value2);           // sets the value (range from 0 to 255)
    analogWrite(R2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl1, value2);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
    for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    //analogWrite(R1, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value2);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value);           // sets the value (range from 0 to 255)
    //analogWrite(R2, value);           // sets the value (range from 0 to 255)
    //analogWrite(Bl1, value);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
    for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    //analogWrite(R1, value);           // sets the value (range from 0 to 255)
    //analogWrite(R3, value);           // sets the value (range from 0 to 255)
    //analogWrite(Bl2, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value2);           // sets the value (range from 0 to 255)
    //analogWrite(R2, value);           // sets the value (range from 0 to 255)
    //analogWrite(Bl1, value);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
  /*    for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    analogWrite(R1, value);           // sets the value (range from 0 to 255)
    analogWrite(R3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl2, value);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
        for(value = 0 ; value <= 255; value+=5) // fade in (from min to max) 
  { 
    value2 = 255-value;
    analogWrite(Bl1, value);           // sets the value (range from 0 to 255)
    analogWrite(Bl3, value);           // sets the value (range from 0 to 255)
    analogWrite(Bll2, value);           // sets the value (range from 0 to 255)
    delay(DELAYD);                            // waits for 30 milli seconds to see the dimming effect 
  }
  
  
  */
  
} 
