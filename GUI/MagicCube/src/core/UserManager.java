package core;
import utils.CoreAPI;


/**
 * The UserManager class is a thread starting/stopping utility that manages all threads that have access to the virtual
 * representation of the LED cube. It centralises Error handling and thread management and guarantees that only one 
 * thread at a time is accessing the cube. It also allows for a special <code>Talker</code> thread to access the cube, regardless 
 * of who else currently has access. The Talker cube is created to enable Serial communication to happen in the background.
 * @author Brice
 *
 */
public class UserManager{
	CoreAPI cube;
	
	AbstractCubeUser currentUser;
	AbstractCubeUser talker;
	
	/**
	 * Constructor.
	 * @param cube The cube to share amongst the Threads.
	 */
	public UserManager(CoreAPI cube){
		this.cube=cube;
	}
	
	
	public void toggleToUser(AbstractCubeUser user){
			if(this.currentUser!=null){
				this.currentUser.killme();
				try {
					this.currentUser.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("[MANAGER]: Starting new user -> "+user.getName());
			
			this.currentUser=user;
			this.currentUser.setCube(cube);
			this.currentUser.start();	
	}
	
	
	/**
	 * Starts the background talker thread. intended to be used with a serial access thread for communicating with 
	 * the physical cube.
	 */
	public void startTalker(AbstractCubeUser talker){
		System.out.println("[MANAGER]: Starting talker: "+talker.getName());
		if(this.talker!=null && !this.talker.getState().equals(Thread.State.TERMINATED)){
			this.talker.killme();
			try {
				this.talker.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.talker=talker;
		this.talker.setCube(cube);
		this.talker.start();
	}
	/**
	 * Stops the background thread
	 */
	public void stopTalker(){
		System.out.println("[MANAGER]: Killing talker.");
		if(talker!=null && !talker.getState().equals(Thread.State.TERMINATED)){
			talker.killme();
			try {
				talker.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			talker=null;
		};
	}
	/**
	 * Returns the currently executing thread.
	 * @return The currently executing User
	 */
	public AbstractCubeUser getCurrentUser(){
		return currentUser;
	}
	
	/**
	 * kills currently running threads that have access to the cube.
	 * Stops threads hanging back after the main applet is down and taking system resources.
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize(){
		currentUser.killme();
		talker.killme();
	}
}
