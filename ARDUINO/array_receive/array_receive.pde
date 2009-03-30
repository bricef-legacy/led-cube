/**
 * Interrupt/draw loop alpha version
 * 
 * @date 27-March-2009
 * @author Brice Fernandes
 */
#include "opcodes.h"

#define MAX_WAIT_MILLIS 1000

#define RESET_TIMER2 TCNT2 = 0

byte cubeDraw[8][32];

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
	Serial.begin(115200);
	
	/**
	 * Initialise the Bytestream buffer to null
	 */
	//TODO
       
	
}

int i=0;
int val=0;
byte buffer[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
byte doubleBuffer[8][32];
boolean checking = true;
void loop(void){
  
        /*
         * INIT
         * CHECKED @ 115200 baud winVista
         */
         if(waitForOpcode(INIT_IS_READY)){
             Serial.print((byte)INIT_OK,BYTE);
         }else{
          for(;;){
           Serial.print(ERROR,BYTE);
          } 
         };
        
        
       //wait for begincube
         //for layer in layers
           //wait for begin layer
           // -> when beginlayer write bytes to layer array until byte is endlayer. or array is full.
           //check layer for parity,
           //send ACK. if ACK -ve, layer stays same. if ACK +ve, layer +=1;
         //when byte is endcube send cubeACK, wait for begincube
        
        int numlayers=8;
        int numbytes=32;
        
        if(waitForOpcode(BEGIN_CUBE)){digitalWrite(13,HIGH);};
        for(int j=0; j<numlayers;j++){
          if(waitForOpcode(BEGIN_LAYER)){digitalWrite(12,HIGH);};
            for(int i=0; i<numbytes; i++){
                   if (Serial.available()){
	    	         val = Serial.read();
                        doubleBuffer[j][i]=val;
                        Serial.print(val,BYTE);
                     }
            }
            if(waitForOpcode(END_LAYER)){digitalWrite(11,HIGH);};
            Serial.print(LAYER_ACK_SUCCESS,BYTE);
        }
        if(waitForOpcode(END_CUBE)){digitalWrite(10, HIGH);};
        Serial.print(CUBE_SUCCESS,BYTE);
          
}

boolean waitForOpcode(byte opcode){
  unsigned long time = millis();
    while(Serial.read()!=opcode){
      if(millis()-time>MAX_WAIT_MILLIS){
          for(;;){Serial.print(ERROR, BYTE);}
          return false;
        }
    }
    return true;
}

void displayCube(){
	//do nothing
}
