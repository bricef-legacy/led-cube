/*
 *LED Cube simulator
 * Edd Overton 03/09
 * Simulates the led cube and allows drawings and changes of mode
 */

PFont font;
float a = 0.0, b=0.0, mousespeed=0.02; //amount of rotation

//some classes used
LedCube      mycube;    //the LEDCube class (this is what well be playing with)
GL           gl;        //OpenGL class (used to assist rendering)
array_send   updateMe;  //update cube thread class.
GUI          cubeGUI;   //sams GUI stuff

//Drawing libs, DONT EVEN THINK ABOUT TOUCHING THESE!
import processing.opengl.*;
import javax.media.opengl.*;



//the setup function runs once when the programs ran, the draw function below is looped through continuosly when the programs running
void setup() 
{
  //here we setup the display window, colours and lighting.
  size(800, 600, OPENGL);
  hint(ENABLE_OPENGL_4X_SMOOTH); //enable beautiful antialiasing
  noStroke();
  background(0);
  lights();
  translate(width/2, height/2);
  
  font = loadFont("CourierNew36.vlw");
  textFont(font); 

  // now we create our ledcube. its a class so we need to use the new LedCube();
  mycube = new LedCube();
  
  //initialise the GUI
  cubeGUI = new GUI(this);
 
  /*
  *SAM
  *call drawing methods from drawingclass, passing arguments of mycube and size (8)
  *This first method is 'splash screen' as such
  */
  largeSC(mycube, 8);
  
  /**
   * ED: load / save functions. [Uncomment to use]
   * Important: DONT USE TIF, processing doesent like opened files.
   */
   
/*   
 // Cube Bytestream, print to console (for testing) un /* to use.
       byte mystream[][] = new byte[8][32];
       mystream = mycube.getByteStream();
      
      //now lets write out the stream
      for (int i=0; i<8; i++){
        print ("Current Layer: ");
        println (i);
        for (int j=0; j<32; j++){
          print(j);
          print(": ");
          println (binary(mystream[i][j],8));
        }
      }
    
    //Limited layer drawing, used for debugging bytestream 
      mycube.setOnly(1); //draws only selected layer
*/

/* 
 // EXPERIMENTAL: Writing cube to arduino using a new thread
    // Initilisation of class (this, cube were using, UpdateState, Com Port)
    // UpdateState: [1:Update once, then stop, 2: Keep updating untill told to stop, anything else: stop]
    // BUG: mode 2 is broken, my arduino refuses to take commands after 1 cube is sent.
    updateMe = new array_send(this, mycube, 2, 1);
    
    //create and start thread
    new Thread(updateMe).start();
    
    //change update state: can be used to stop thread, in a nicer way?
    //updateMe.setUpdateState(1);
*/
  
  mycube.drawCube();
}

//the draw funcion is continuosly looped through by the program
void draw() {
  //these functions clear the window giving us something to draw a new cube to
  background(0);
  noStroke();
  lights();
  
  // rotate me a little
  /* NOW USE MOUSE!
  a += 0.01/2;
  if(a > TWO_PI) { 
    a = 0.0; 
  }*/
  
  //Draw Axis
  pushMatrix();
  translate(100, height-100); //this function translates the drawing to the centre of the window
  rotateY(a);
  rotateZ(b/2);
  noFill();
  smooth();
  strokeWeight(1.5);
  drawAxis(75.0,10.0);
  popMatrix();
  
  
  //Draw LED Cube.
  pushMatrix();
  translate(width/(1.7), height/1.9); //this function translates the drawing to the centre of the window
  rotateY(a);
  rotateZ(b/2);
  //we now draw the new cube on our screen
  mycube.drawCube();

  popMatrix();
  
  gl=((PGraphicsOpenGL)g).beginGL();
  gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
  ((PGraphicsOpenGL)g).endGL();
  cubeGUI.getGUI().draw();
}

void mouseDragged() {
  if (pmouseX < mouseX) a+=mousespeed; 
  else if (pmouseX > mouseX) a-=mousespeed;
  if (pmouseY < mouseY) b+=mousespeed;
  else if (pmouseY > mouseY) b-=mousespeed;
}
