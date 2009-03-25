



/*
 * PHY343 proto x/z drive
 */

int ledPin = 13;                  // LED connected to digital pin 13
int layer1 = 3;
int layer2 = 4;
int layer3 = 5;
int DELAY=1000;  //in microseconds
int interval = 5000;
int SHORT_DELAY=1000;
int LONG_DELAY=10000000;
int previousMillis;


void setup()                    // run once, when the sketch starts
{
  pinMode(layer1, OUTPUT);      // sets the digital pin as output
  pinMode(layer2, OUTPUT); 
  pinMode(layer3, OUTPUT); 
 
  pinMode(6, OUTPUT); 
  pinMode(7, OUTPUT); 
  pinMode(8, OUTPUT); 
   
}

void loop(){

	if (millis() - previousMillis > interval) {
		previousMillis = millis();   // remember the last time we blinked the LED
		if (DELAY == SHORT_DELAY){
			DELAY = LONG_DELAY;
		}else{
			DELAY = SHORT_DELAY;
		}
	}
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
	delay(DELAY);

	//do the other color
	digitalWrite(layer1, LOW);
	digitalWrite(6, HIGH);
	digitalWrite(7, HIGH);
	digitalWrite(8, HIGH);
	delay(DELAY);

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
	delay(DELAY);
	digitalWrite(7, LOW);

	//do the other color
	digitalWrite(layer2, LOW);
	digitalWrite(6, HIGH);
	digitalWrite(7, LOW);
	digitalWrite(8, HIGH);
	delay(DELAY);

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
	delay(DELAY);
	//do the other color
	digitalWrite(layer3, LOW);
	digitalWrite(6, HIGH);
	digitalWrite(7, HIGH);
	digitalWrite(8, HIGH);
	delay(DELAY);
}

