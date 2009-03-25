//some vatiables used later in the program

float a = 0.0; //ammount of rotation

LedCube mycube; // the LEDCube class (this is what well be playing with)

int cubestates[][][]; //an array of states, which we can then write directly to the class

//the setup function runs once when the programs ran, the draw function below is looped through continuosly when the programs running
void setup() 
{
  //here we setup the display window, colours and lighting.
  size(800, 600, P3D);
  noStroke();
  background(0);
  lights();
  translate(width/2, height/2);
  
  //we now create the cubestates array so we can use it (notice the new).
  //note we have 3 lots of []. this makes it a 3 dimensional array, or an array of an array of an array.
  cubestates = new int[8][8][8];
  
  //the cubestates array takes the form: cubestates[X][Y][Z] = state;
  //below we set some states on the array.
  
  //set Z green
  cubestates[0][0][1] = 2;
  cubestates[0][0][2] = 2;
  cubestates[0][0][3] = 2;
  cubestates[0][0][4] = 2;
  cubestates[0][0][5] = 2;
  cubestates[0][0][6] = 2;
  cubestates[0][0][7] = 2;
  
  //set y yellow
  cubestates[0][1][0] = 3;
  cubestates[0][2][0] = 3;
  cubestates[0][3][0] = 3;
  cubestates[0][4][0] = 3;
  cubestates[0][5][0] = 3;
  cubestates[0][6][0] = 3;
  cubestates[0][7][0] = 3;
  
  //set x red
  cubestates[1][0][0] = 1;
  cubestates[2][0][0] = 1;
  cubestates[3][0][0] = 1;
  cubestates[4][0][0] = 1;
  cubestates[5][0][0] = 1;
  cubestates[6][0][0] = 1;
  cubestates[7][0][0] = 1;
  
  // now we create our ledcube. its a class so we need to use the new LedCube();
  mycube = new LedCube();
  
  // we now clear the cube, this isnt nesseseiry, since the cube is clear initially
  mycube.clearCube();
  
  // now we write our states defined above onto the cube, this is done by calling the cubes setCube function.
  // the setCube function takes one argument which is an array of all the LED states.
  mycube.setCube(cubestates);
  
  //now we use an additional command to the ones outlined. This is specific to the simulation case, and instructs the cube to draw itself in the window.
  mycube.drawCube();
}

//the draw funcion is continuosly looped through by the program
void draw() {
  
  //these functions clear the window giving us something to draw a new cube to
  background(0);
  noStroke();
  lights();
  translate(width/2, height/2); //this function translates the drawing to the centre of the window
  
  //this controlls the value of a (variable used to controll rotation) , we slowly increase from 0 to 2PI, and once we get to 2PI goback to zero,
  a += 0.01;
  if(a > TWO_PI) { 
    a = 0.0; 
  }
  
  // Here we use some conditional statements based on a, to set the colour of the 0,0,0 LED.
  // we acsess the cube class in the same way as before, but we call the setLED function, with arguments (state,x,y,z)
  if (a>0.5) { mycube.setLED(3,0,0,0); }
  if (a>1.0) { mycube.setLED(2,0,0,0); }
  if (a>1.5) { mycube.setLED(1,0,0,0); }
  
  //these two functions rotate the drawing in the Y,Z axes. We rotate before drawing, this tells the program to draw our cube rotated.
  rotateY(a);
  rotateZ(0.1);

  //we now draw the new cube on our screen
  mycube.drawCube();
}
