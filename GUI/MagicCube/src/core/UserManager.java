package core;
import java.util.HashMap;
import java.util.Iterator;



public class UserManager{
	CoreAPI cube;
	HashMap<String, AbstractCubeUser> users;
	
	AbstractCubeUser currentUser;
	AbstractCubeUser talker;
	String talkerName;
	String currentUserName;
	
	public UserManager(CoreAPI cube){
		this.cube=cube;
		this.users=new HashMap<String, AbstractCubeUser>();
	}
	
	public void adduser(AbstractCubeUser user, String name){
		this.users.put(name, user);
	}
	
	public void removeuser(CubeUserInterface user){
		this.users.remove(user);
	}
	
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
	public void setTalker(String talkerName){
		this.talkerName=talkerName;
	}
	
	public void startTalker(){
		if(talker!=null){talker.killme();};
		if(talkerName!=null){
			talker=users.get(talkerName);
			talker.setCube(this.cube);
			talker.start();
		}
	}
	
	public void stopTalker(){
		if(talker!=null){talker.killme();};
	}
	
	public Iterator<AbstractCubeUser> getIterator(){
		return users.values().iterator();
	}
	public CubeUserInterface getCurrentUser(){
		return currentUser;
	}
	public String getCurrentuserName(){
		return this.currentUserName;
	}
	
	@Override
	protected void finalize(){
		currentUser.killme();
	}
}
