/**
 * Core LED Cube API.
 * @author Michael Overington 
 * @author Edward Overton
 * @author Brice Fernandes
 * @author Thomas Loussert
 * @author Chris Ryan
 * @author Samuel Dove
 */
public interface CoreAPI{
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
	 * The Clear Cube method clears the internal representation of 
	 * the LED cube, as well as switching off all the LEDs.
	 */
	public void clearCube();
	
	/**
	 * The setLED method sets a given LED to a particular state
	 * @param status The status of the LED (OFF|RED|GREEN|ORANGE)
	 * @param x The x coordinate (0-7)
	 * @param y The y coordinate (0-7)
	 * @param z The z coordinate (0-7)
	 * @see status
	 */
	public void setLED(int status, int x, int y, int z);
	
	/**
	 * Sets the cube to a particular state.
	 * @param status[][][] a three dimensional int array using status ints
	 * @see status
	 */
	public void setCube(int[][][] status);
	
	/**
	 * sets an XY layer to the required states using a two dimensional 
	 * int array.
	 * @param status The two dimensional int array keeping the new plane state
	 * @param layer The layer to be changed
	 * @see status
	 */
	public void setPlaneXY(int[][] status, int layer );
	
	/**
	 * sets an XZ layer to the required states using a two dimensional 
	 * int array.
	 * @param status The two dimensional int array keeping the new plane state
	 * @param layer The layer to be changed
	 * @see status
	 */
	public void setPlaneXZ(int[][] status, int layer );
	
	/**
	 * sets an YZ layer to the required states using a two dimensional 
	 * int array.
	 * @param status The two dimensional int array keeping the new plane state
	 * @param layer The layer to be changed
	 * @see status
	 */
	public void setPlaneYZ(int[][] status, int layer );
	
	/**
	 * The readCube() method returns a representation of the cube as a three
	 * dimensional int array using the built-in status codes.
	 * @return status[][][] The representative array of the cube
	 * @see status
	 */
	public int[][][] readCube();
}
