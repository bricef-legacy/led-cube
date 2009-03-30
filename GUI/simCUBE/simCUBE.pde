/*
 *LED Cube simulator
 * Edd Overton 03/09
 * Simulates the led cube and allows drawings and changes of mode
 */
 
float a = 0.0; //amount of rotation
LedCube mycube; // the LEDCube class (this is what well be playing with)

//Drawing libs, DONT EVEN THINK ABOUT TOUCHING THESE!
import processing.opengl.*;
import javax.media.opengl.*;

// Sam's gui stuff
GUI cubeGUI;

//the setup function runs once when the programs ran, the draw function below is looped through continuosly when the programs running
void setup() 
{
  //here we setup the display window, colours and lighting.
  size(800, 600, OPENGL);
  noStroke();
  background(0);
  lights();
  translate(width/2, height/2);

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
   
  //saveCube(mycube, "test.png");
  //loadCube(mycube, "test.png");
  
  //draw cube
  mycube.drawCube();
}

//the draw funcion is continuosly looped through by the program
void draw() {
  
  //these functions clear the window giving us something to draw a new cube to
  background(0);
  noStroke();
  lights();
  pushMatrix();
  translate(width/2, height/2); //this function translates the drawing to the centre of the window
 // ROTATION code
  a += 0.01/2;
  if(a > TWO_PI) { 
    a = 0.0; 
  }
  rotateY(a);
  rotateZ(0.01/2);

  //we now draw the new cube on our screen
  mycube.drawCube();

  popMatrix();
  
  GL gl=((PGraphicsOpenGL)g).beginGL();
  gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
  ((PGraphicsOpenGL)g).endGL();
  cubeGUI.getGUI().draw();
}



