/**
 * Interrupt/draw loop alpha version
 * 
 * @date 27-March-2009
 * @author Brice Fernandes
 */

#define RESET_TIMER2 TCNT2 = 0

byte cubeDraw[8][32];
byte incomingBuffer[256];

void setup(void) {
	/**
	 * Interrupt setup magic.
	 * Credit:Mathias Dalheimer (gonium.net)
	 */
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
	sei();
	
	/**
	 * Begin serial communications
	 */
	Serial.begin(9600);
	
	/**
	 * Initialise the Bytestream buffer to null
	 */
	//TODO
	
}

int i=0;
int val=0;
byte buffer[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
boolean checking = true;
void loop(void){
	while(checking){
            if(Serial.read()==31){
              Serial.print(32, BYTE);
            checking=false;  
          }
        }
        while(i<13){
	    if (Serial.available()){
	    	 val = Serial.read();
                    buffer[i]=val;
                 Serial.print(val,BYTE);
                 i++;
            }
	}
        if(buffer[0]==12 && buffer[12]==0){
          digitalWrite(13, HIGH);
          delay(500);
          i=0;
        }
}


/**
 * Handles the timer 2 overflow interrupt. 
 */
ISR(TIMER2_OVF_vect) {//handle timer 2 overflow (draw cube)
	//draw the cube
	RESET_TIMER2;//reset the timer
};

void displayCube(){
	//do nothing
}
