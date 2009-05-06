package cubeUsers;
import core.CoreAPI;
import core.CubeUserInterface;


public class IsingUser extends Thread implements CubeUserInterface {
	CoreAPI cube;
	boolean killme=false;
	CoreAPI tempcube;
	
	@Override
	public void setCube(CoreAPI mcube) {
		this.cube=mcube;
	}

	@Override
	public void run() {
		while(!killme){
			//this is where the main loop resides,
			//make sure that you use tempCube=this.cube.readCube();
			//and then modify tempCube, before doing this.cube.setCube(tempCube);
		}
	}

	@Override
	public void killme() {
		this.killme=true;
	}

}
