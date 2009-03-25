/**
 * Utility API for the 3D LED cube. 
 * @author Michael Overington 
 * @author Edward Overton
 * @author Brice Fernandes
 * @author Thomas Loussert
 * @author Chris Ryan
 * @author Samuel Dove
 */
public interface UtilityAPI{
	/**
	 * This method pushes the cube states in the Z direction. it preserves
	 * the states that should come outside of the cube by using them to fill the 
	 * empty states left by the shift.
	 * @param amount The number of shifts (can be negative)
	 */
	public void pushPreserveZ(int amount);
	
	/**
	 * This method pushes the cube states in the Y direction. it preserves
	 * the states that should come outside of the cube by using them to fill the 
	 * empty states left by the shift.
	 * @param amount The number of shifts (can be negative)
	 */
	public void pushPreserveY(int amount);
	
	/**
	 * This method pushes the cube states in the X direction. it preserves
	 * the states that should come outside of the cube by using them to fill the 
	 * empty states left by the shift.
	 * @param amount The number of shifts (can be negative)
	 */
	public void pushPreserveX(int amount);
	
	/**
	 * This method shifts the states of the cube in the X direction. The 
	 * Empty states left behind are <em>not</em> filled. They are left empty.
	 * Eight unit shifts will clear the cube.
	 * @param amount the number of shifts. (can be negative)
	 */
	public void pushDestructX(int amount);
	
	/**
	 * This method shifts the states of the cube in the Y direction. The 
	 * Empty states left behind are <em>not</em> filled. They are left empty.
	 * Eight unit shifts will clear the cube.
	 * @param amount the number of shifts. (can be negative)
	 */
	public void pushDestructY(int amount);
	
	/**
	 * This method shifts the states of the cube in the Z direction. The 
	 * Empty states left behind are <em>not</em> filled. They are left empty.
	 * Eight unit shifts will clear the cube.
	 * @param amount the number of shifts. (can be negative)
	 */
	public void pushDestructZ(int amount);
}
