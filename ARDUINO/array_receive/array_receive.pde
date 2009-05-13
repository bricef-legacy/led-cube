/**
 * FSM for the Arduino.
 * 
 * @date 27-March-2009
 * @author Brice Fernandes
 */
#include "opcodes.h"

/*---Timer interrupt stuff---*/
#define RESET_TIMER2 TCNT2 = 0

/*---different waits for timeouts---*/
#define MAX_WAIT_MILLIS 500
#define MAX_LAYER_WAIT 200
#define ERROR_DURATION 250

/*---State machine states---*/
#define INIT_STATE 0
#define WAIT_BEGIN_CUBE 1
#define WAIT_BEGIN_LAYER 2
#define RECEIVING_LAYER 3
#define WAIT_LAYER_END 4
#define ERROR_STATE 5
#define WAIT_END_CUBE 6

//Output port, and Data direction register.
#define PORTZ PORTC
#define DDRZ DDRC

//Clock pins for shift register and layer clocks.
#define CLKON B010000
#define LAYERCLK B100000

//arduino output pin for initial layer toggle
#define LAYERTOG 3


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
	
        //DRAWING PINS + DDR
        pinMode(LAYERTOG, OUTPUT);
        DDRZ = B00111111;

	/**
	 * Begin serial communications
	 */
	Serial.begin(9600);
        pinMode(12, OUTPUT);
        pinMode(13, OUTPUT);        

}

int i=0;
int val=0;

byte doubleBuffer[8][32];
int numbytes = 31;
boolean checking = true;
byte status=INIT_STATE;
int currentLayer =0;



void loop(void){
switch(status){
	case INIT_STATE:
		SMClean();
		if(waitForOpcode(INIT_IS_READY)){
			SSend(INIT_OK);
			status=WAIT_BEGIN_CUBE;
		}else{
			status=ERROR_STATE;
		};
		break;
		
	case WAIT_BEGIN_CUBE:
		if(waitForOpcode(BEGIN_CUBE)){
			status=WAIT_BEGIN_LAYER;
		}else{
			status=ERROR_STATE;
		};
		break;
		
	case WAIT_BEGIN_LAYER:
		if(waitForOpcode(BEGIN_LAYER)){
			status=RECEIVING_LAYER;
		}else{
                        //NOT GET HERE
			status=ERROR_STATE;
		};
		break;
		
	case RECEIVING_LAYER:
		if(getLayer(currentLayer)){
			status=WAIT_LAYER_END;
		}else{
			SSend(LAYER_ACK_FAIL);
			status=WAIT_BEGIN_LAYER;
                }
		break;
		
	case WAIT_LAYER_END: 
		if(waitForOpcode(END_LAYER)){
                        SSend(LAYER_ACK_SUCCESS);
			nextLayer();
			if(currentLayer==8){
				status=WAIT_END_CUBE;
			}else{
				status=WAIT_BEGIN_LAYER;
                        };
		}else{
			status=ERROR_STATE;
		};
		break;
		
	case ERROR_STATE:
		sendErrorSignal();
		status=INIT_STATE;
		break;
		
	case WAIT_END_CUBE:
		if(waitForOpcode(END_CUBE)){
                        SSend(CUBE_SUCCESS);
			status=INIT_STATE;
		}else{
			status=ERROR_STATE;
		};
		break;
  }
}


void sendErrorSignal(){
    digitalWrite(12,HIGH);
	unsigned long time = millis();
	while(millis()-time<ERROR_DURATION){
		Serial.print(ERROR, BYTE);
	}
  digitalWrite(12,LOW);
}

void SMClean(void){
	currentLayer=0;
}

void nextLayer(void){
	currentLayer++;
}

void SSend(byte opcode){
	Serial.print(opcode, BYTE);
}

boolean getLayer(int layer){
	unsigned long time = millis();
	for(int i=0; i<numbytes; i++){
          while(!Serial.available())
          {
            
          }
		if (Serial.available()){
			val = Serial.read();
                        digitalWrite(13, HIGH);
			doubleBuffer[layer][i]=val;
			if(val==END_LAYER){
				return false;
			}
		}
		if(millis()-time>MAX_LAYER_WAIT){
			return false;
		}
	}
	return true;
}



boolean waitForOpcode(byte opcode){
  unsigned long time = millis();
    while(Serial.read()!=opcode){
      if(millis()-time>MAX_WAIT_MILLIS){
          return false;
        }
    }
    return true;
}

void displayCube(){
	//do nothing
}
