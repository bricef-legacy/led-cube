
int layer1 = 3;
int layer2 = 4;
int layer3 = 5;

//Stuff for the interrupt
int DELAY=1;  //in microseconds
int SHORT_DELAY=1;
int LONG_DELAY=250;

long interval = 5000;
long previousMillis=0;


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
		
                //Toggle between SHORT_DELAY and LONG_DELAY
                if (DELAY == SHORT_DELAY){
			DELAY = LONG_DELAY;
		}else{
			DELAY = SHORT_DELAY;
                        
		}
	}
        doleds(DELAY);
}

void doleds(int newdel){
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
	delay(newdel);

	//do the other color
	digitalWrite(layer1, LOW);
	digitalWrite(6, HIGH);
	digitalWrite(7, HIGH);
	digitalWrite(8, HIGH);
	delay(newdel);

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
	delay(newdel);
	digitalWrite(7, LOW);

	//do the other color
	digitalWrite(layer2, LOW);
	digitalWrite(6, HIGH);
	digitalWrite(7, LOW);
	digitalWrite(8, HIGH);
	delay(newdel);

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
	delay(newdel);
	//do the other color
	digitalWrite(layer3, LOW);
	digitalWrite(6, HIGH);
	digitalWrite(7, HIGH);
	digitalWrite(8, HIGH);
	delay(newdel);
}
