package core;
import java.util.HashMap;
import java.util.Iterator;



public class UserManager{
	CoreAPI cube;
	HashMap<String, CubeUserInterface> users;
	
	CubeUserInterface currentUser;
	CubeUserInterface talker;
	
	public UserManager(CoreAPI cube){
		this.cube=cube;
		this.users=new HashMap<String, CubeUserInterface>();
	}
	
	public void adduser(CubeUserInterface user, String name){
		user.setCube(cube);
		this.users.put(name, user);
	}
	
	public void removeuser(CubeUserInterface user){
		this.users.remove(user);
	}
	
	public void toggleToUser(String name){
			if(currentUser!=null){currentUser.killme();};
			currentUser=users.get(name);
			currentUser.start();
	}
	
	public void startParallelUser(String name){
			users.get(name).start();
	}
	
	public Iterator<CubeUserInterface> getIterator(){
		return users.values().iterator();
	}
	
	@Override
	protected void finalize(){
		currentUser.killme();
	}
}
