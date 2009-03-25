



/*
 * PHY343 proto x/z drive
 */

#include "WProgram.h"
void setup();
void loop();
int ledPin = 13;                  // LED connected to digital pin 13
int layer1 = 3;
int layer2 = 4;
int layer3 = 5;
int DELAY=50;  //in microseconds


void setup()                    // run once, when the sketch starts
{
  pinMode(layer1, OUTPUT);      // sets the digital pin as output
  pinMode(layer2, OUTPUT); 
  pinMode(layer3, OUTPUT); 
 
  pinMode(6, OUTPUT); 
  pinMode(7, OUTPUT); 
  pinMode(8, OUTPUT); 
   
}

void loop()                     // run over and over again
{
  digitalWrite(ledPin, HIGH);   // sets the LED on
  
  
  //setlayer 1 out
  //set layer 2,3 high inpedence
  //write on to pins 6,7,8
  pinMode(layer1, OUTPUT); 
  digitalWrite(layer1, HIGH);
  pinMode(layer2, INPUT);
  pinMode(layer3, INPUT);
  digitalWrite(6, LOW);
  digitalWrite(7, LOW);
  digitalWrite(8, LOW);
  delayMicroseconds(DELAY);
  
  //do the other color
  digitalWrite(layer1, LOW);
  digitalWrite(6, HIGH);
  digitalWrite(7, HIGH);
  digitalWrite(8, HIGH);
  delayMicroseconds(DELAY);
  
  //set layer 1, 3 high impedence
  //set layer 2 out
  //write on to pin 6,7,8
  pinMode(layer1, INPUT); 
  pinMode(layer2, OUTPUT);
    digitalWrite(layer2, HIGH);
  pinMode(layer3, INPUT);
  digitalWrite(6, LOW);
  digitalWrite(7, HIGH);
  digitalWrite(8, LOW);
  delayMicroseconds(DELAY);
  
  digitalWrite(7, LOW);
  
  //do the other color
  digitalWrite(layer2, LOW);
  digitalWrite(6, HIGH);
  digitalWrite(7, LOW);
  digitalWrite(8, HIGH);
  delayMicroseconds(DELAY);
  
  
  
  
  //set layer 1, 2 high impedence
  //set layer 3 out
  //write on to pin 6,7,8
  pinMode(layer1, INPUT); 
  pinMode(layer2, INPUT);
  pinMode(layer3, OUTPUT);
   digitalWrite(layer3, HIGH);
  digitalWrite(6, LOW);
  digitalWrite(7, LOW);
  digitalWrite(8, LOW);
  delayMicroseconds(DELAY);
  
  //do the other color
  digitalWrite(layer3, LOW);
  digitalWrite(6, HIGH);
  digitalWrite(7, HIGH);
  digitalWrite(8, HIGH);
  delayMicroseconds(DELAY);
}


int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

