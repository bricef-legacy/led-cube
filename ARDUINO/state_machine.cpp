
#define MAX_LAYER_WAIT 25
#define ERROR_DURATION 250

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
	unsigned long time = millis();
	while(millis()-time<ERROR_DURATION){
		Serial.print(ERROR, BYTE);
	}
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
		if (Serial.available()){
			val = Serial.read();
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

