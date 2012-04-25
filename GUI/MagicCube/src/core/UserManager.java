package core;
import java.util.HashMap;

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
	HashMap<String, AbstractCubeUser> users;
	
	AbstractCubeUser currentUser;
	AbstractCubeUser talker;
	String talkerName;
	String currentUserName;
	
	/**
	 * Constructor.
	 * @param cube The cube to share amongst the Threads.
	 */
	public UserManager(CoreAPI cube){
		this.cube=cube;
		this.users=new HashMap<String, AbstractCubeUser>();
	}
	
	/**
	 * Adds a user to the Manager's internal HashMap.
	 * @param cube The AbstractCubeUser to add.
	 * @param name The name to be used in the future to refer to this new user. (also the HashMap key)
	 */
	public void adduser(AbstractCubeUser user, String name){
		this.users.put(name, user);
	}
	
	/**
	 * Removes a user.
	 * @param user
	 */
	public void removeuser(AbstractCubeUser user){
		this.users.remove(user);
	}
	
	/**
	 * Change the currently executing thread. the new thread must have been registered previously and should
	 * be referenced by name.<p>
	 * Avoids duplicate threads. If this method is called twice with the same arguments, the second invocation
	 * will do nothing. If called when there are no currently running threads, will start a new one.
	 * @param name The name of the thread to start.
	 */
	public void toggleToUser(String name){
		/*Warning. hairy.*/
		if(currentUser!=this.users.get(name)){//if the current thread is NOT the one toggled to 
			System.out.println("[MANAGER]: Selected user is not Current user. Checking if there is a Current User.");
			if((currentUser!=null)){//if there is a running thread. we need to kill it first
				System.out.println("[MANAGER]: There is a current user. Killing current user.");
					currentUser.killme();
					try {
						currentUser.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}//there is no running thread, so we dont have to kill it.
			System.out.println("[MANAGER]: There is no Current User. Starting new Current user.");
			currentUser=users.get(name);
			currentUser.setCube(cube);
			currentUserName=name;
			currentUser.start();
		}else{//if the current thread is the thread toggled to
			System.out.println("[MANAGER]: Selected user already Current user. Doing nothing.");
			//pass
		}
	}
	/**
	 * Sets a background thread by name. This thread executes regardless of the status of the current thread. 
	 * The talker thread must be registered like a normal user before it can be called by this method.
	 * This method does not start the talker thread.
	 * @param talkerName
	 */
	public void setTalker(String talkerName){
		this.talkerName=talkerName;
	}
	/**
	 * Starts the background talker thread. intended to be used with a serial access thread for communicating with 
	 * the physical cube.
	 */
	public void startTalker(){
		if(talker!=null){talker.killme();};
		if(talkerName!=null){
			talker=users.get(talkerName);
			talker.setCube(this.cube);
			talker.start();
		}
	}
	/**
	 * Stops the background thread
	 */
	public void stopTalker(){
		if(talker!=null){talker.killme();};
	}
	/**
	 * Returns the currently executing thread.
	 * @return The currently executing User
	 */
	public AbstractCubeUser getCurrentUser(){
		return currentUser;
	}
	/**
	 * Method to get the name of the currently executing User.
	 * @return the name of the current user.
	 */
	public String getCurrentuserName(){
		return this.currentUserName;
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
