/**
 * LED CUBE Implementation, has all functions drscribed in the CORE API. + some extra for drawing...
 */


/**
 * Core LED Cube API.
 * @author Michael Overington 
 * @author Edward Overton
 * @author Brice Fernandes
 * @author Thomas Loussert
 * @author Chris Ryan
 * @author Samuel Dove
 */
class LedCube{
	/**
	 * @status
	 * The status code for the LEDs
	 * OFF|RED|GREEN|ORANGE
	 */
	final static int OFF = 0;
	final static int RED = 1;
	final static int GREEN = 2;
	final static int ORANGE = 3;

        
        /**
         * Here we define our 3d Cube
         */
         
         int cubeState[][][]; // this is the variable used to export the cube, in the ReadCube function
         final static int CUBESIZE = 8; //size of cube
         final static float LEDSPACE = 40; //led spacing
         final static float startX = -CUBESIZE*40/2; //XYZ starting locations. eg. location of first LED (X,Y,Z)
         final static float startY = -CUBESIZE*40/2;
         final static float startZ = -CUBESIZE*40/2;
         
         LED[][][] mycube = new LED[CUBESIZE][CUBESIZE][CUBESIZE]; //our LED's used in the cube
         
        /*
         * Initilization of the cube...
         */
         
         LedCube(){
           for (int x = 0; x<CUBESIZE; x++){
             for (int y = 0; y<CUBESIZE; y++){
               for (int z = 0; z<CUBESIZE; z++){
                 //here we create each LED, and set its XYZ location, the confusing (Y,Z,X) is to make sure the LED's
                 mycube[x][y][CUBESIZE-z-1] = new LED(0,startY + float(y)*LEDSPACE ,startZ + float(z)*LEDSPACE , startX + float(x)*LEDSPACE);
               }
             }
           }
         }
         
         /*
          * Added function to draw the cube...
          */
         
	void drawCube(){
          for (int x = 0; x<CUBESIZE; x++){
             for (int y = 0; y<CUBESIZE; y++){
               for (int z = 0; z<CUBESIZE; z++){
                 mycube[x][y][z].drawLED();
                 //mycube[x][y][z].setXYZ(startX + float(x)*LEDSPACE, startY + float(y)*LEDSPACE, startZ + float(z)*LEDSPACE);
               }
             }
           }
         }
	/**
	 * The Clear Cube method clears the internal representation of 
	 * the LED cube, as well as switching off all the LEDs.
	 */
	public void clearCube(){
          for (int x = 0; x<CUBESIZE; x++){
             for (int y = 0; y<CUBESIZE; y++){
               for (int z = 0; z<CUBESIZE; z++){
                 mycube[x][y][z].changeState(0);
                 //mycube[x][y][z].setXYZ(startX + float(x)*LEDSPACE, startY + float(y)*LEDSPACE, startZ + float(z)*LEDSPACE);
               }
             }
           }
         }    
	
	/**
	 * The setLED method sets a given LED to a particular state
	 * @param status The status of the LED (OFF|RED|GREEN|ORANGE)
	 * @param x The x coordinate (0-7)
	 * @param y The y coordinate (0-7)
	 * @param z The z coordinate (0-7)
	 * @see status
	 */
	public void setLED(int status, int x, int y, int z){
          mycube[x][y][z].changeState(status);
        }
	
	/**
	 * Sets the cube to a particular state.
	 * @param status[][][] a three dimensional int array using status ints
	 * @see status
	 */
	public void setCube(int[][][] status){
        for (int x = 0; x<CUBESIZE; x++){
             for (int y = 0; y<CUBESIZE; y++){
               for (int z = 0; z<CUBESIZE; z++){
                 mycube[x][y][z].changeState(status[x][y][z]);
               }
             }
           }
        }
	
	/**
	 * sets an XY layer to the required states using a two dimensional 
	 * int array.
	 * @param status The two dimensional int array keeping the new plane state
	 * @param layer The layer to be changed
	 * @see status
	 */
	public void setPlaneXY(int[][] status, int layer ){
          for (int x = 0; x<CUBESIZE; x++){
            for (int y = 0; y<CUBESIZE; y++){
              mycube[x][y][layer].changeState(status[x][y]);
            }
          }
        }
	
	/**
	 * sets an XZ layer to the required states using a two dimensional 
	 * int array.
	 * @param status The two dimensional int array keeping the new plane state
	 * @param layer The layer to be changed
	 * @see status
	 */
	public void setPlaneXZ(int[][] status, int layer ){
        for (int x = 0; x<CUBESIZE; x++){
            for (int z = 0; z<CUBESIZE; z++){
              mycube[x][layer][z].changeState(status[x][z]);
            }
          }
        }
	
	/**
	 * sets an YZ layer to the required states using a two dimensional 
	 * int array.
	 * @param status The two dimensional int array keeping the new plane state
	 * @param layer The layer to be changed
	 * @see status
	 */
	public void setPlaneYZ(int[][] status, int layer ){
          for (int y = 0; y<CUBESIZE; y++){
            for (int z = 0; z<CUBESIZE; z++){
              mycube[layer][y][z].changeState(status[y][z]);
            }
          }
        }
	
	/**
	 * The readCube() method returns a representation of the cube as a three
	 * dimensional int array using the built-in status codes.
	 * @return status[][][] The representative array of the cube
	 * @see status
	 */
	public int[][][] readCube(){
          for (int x = 0; x<CUBESIZE; x++){
             for (int y = 0; y<CUBESIZE; y++){
               for (int z = 0; z<CUBESIZE; z++){
                 cubeState[x][y][z] = mycube[x][y][z].getState();
               }
             }
           }
        
        return cubeState;
       }
	
}
