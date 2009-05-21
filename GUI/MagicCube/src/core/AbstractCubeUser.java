package core;

import utils.CoreAPI;

/**
 * The AbstractCubeUser defines all required methods for a cube user.
 * New cube users should be created by extending this class. Child classes can 
 * access their cube object directly or  by calling their own getCube() method.
 * new Cube Users should only override the abstract loop() method. 
 * @author Brice
 */
public abstract class AbstractCubeUser extends Thread{
	protected CoreAPI cube;
	private boolean killme=false;
	/**
	 * Accessor for this class's cube
	 * @return The stored cube implementor of CoreAPI
	 */
	public CoreAPI getCube() {
		return cube;
	}
	
	/**
	 * Cube mutator. Notably used by the UserManager to automatically assign new Users the
	 * appropriate cube.
	 * @param mcube
	 */
	public void setCube(CoreAPI mcube) {
		this.cube=mcube;
	}

	@Override 
	public void run(){
		while(!killme){
				loop();
		}
	}
	
	/**
	 * The main thread loop. This should be overridden by all child classes.<p>
	 * Child classes do not have to do any thread management or have to worry about accessing the cube.
	 * Access to cube is carried out by calling <code>cube.readCube()</code> which returns a three dimensional integer array.
	 * This should be stored as a local variable, mutated, and then sent back to the cube using <code>cube.setcCube()</code>
	 * Accessing the cube using any other methods is discouraged, as it will block the shared object for longer than necessary.
	 */
	public abstract void loop();
	
	/**
	 * Used to kill this thread. Usually followed by a join() call to wait for thread death.
	 */
	public void killme() {
		this.killme=true;
		//System.out.println(this.getName()+" now going to lala land.");
	}
}
