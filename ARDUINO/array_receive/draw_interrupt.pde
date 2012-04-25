/**
 * Handles the timer 2 overflow interrupt. 
 * @author Edward Overton
 */
 
//Some definitions

//Output port, and Data direction register.
#define PORTZ PORTC
#define DDRZ DDRC

//Clock pins for shift register and layer clocks.
#define CLKON B010000
#define LAYERCLK B100000

//arduino output pin for initial layer toggle
#define LAYERTOG 3

int cur_layer=0;
int cur_row;

 
ISR(TIMER2_OVF_vect) { //handle timer 2 overflow (draw cube)
  
  //push all rows of data for the layer onto the shift registers
  for (cur_row=0; cur_row<32; cur_row++){
    PORTZ = cubeDraw[cur_layer][cur_row];
    PORTZ |= CLKON;
  }

  //if not the last layer, set the layer initiator pin low, move to next layer
  //or we are on the last layer, so set layer initiator pin high, and set the next layer = 0
  if (cur_layer != 7){
    if (cur_layer == 0) digitalWrite(LAYERTOG, LOW);
    cur_layer++;
  }else{
    digitalWrite(LAYERTOG, HIGH);
    cur_layer = 0;
  }
  
  //update layer
  PORTZ |= LAYERCLK;
  
  //reset the timer
  RESET_TIMER2;
};
