/* 
 *  File containing the LED Class, contains instruictions specific to the Individual LED's, such as setting position, state, and a draw command
 */

final static int LEDSIZE = 10;

class LED{
  
  /**
   * The XYZ location of the LED, state, and colour inferred from this state
   */
  
  int state;
  float x,y,z;
  color colour;
  
  
  /**
   * Initilization, setting all variables
   */
   
  LED(int _state, float _x, float _y, float _z){
   
    state = _state;
    this.x = _x;
    this.y = _y;
    this.z = _z;
    
   updateColor();
   this.drawLED();
  }
  
  LED(){
    state = 0;
    this.x = 0;
    this.y = 0;
    this.z = 0;
    updateColor();
  }
  
  void setXYZ(float _x, float _y, float _z){
    this.x = _x;
    this.y = _y;
    this.z = _z;
  }
  
  /**
   * Function for changing the state of the LED
   */
  
  void changeState(int s){
   state = s;
   updateColor();
  }
  
  /**
   * Function to update the colour of the LED, called when state changes.
   */
  
  void updateColor(){
    if (state == 0) colour = color(50,50,50);
    if (state == 1) colour = color(255,0,0);
    if (state == 2) colour = color(0,255,0);
    if (state == 3) colour = color(255,255,0);
  }
  
  /**
   * Function to draw the LED each frame
   */
  
  void drawLED(){
    pushMatrix();
    translate(x, y, z);
    fill(colour);
    box(LEDSIZE); 
    popMatrix();
  }

  int getState()
  {
    return state;
  }
}
