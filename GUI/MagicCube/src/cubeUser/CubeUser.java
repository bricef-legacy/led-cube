package cubeUser;

import core.AbstractCubeUser;


public class CubeUser extends AbstractCubeUser {
	public CubeUser(){
		this.setName("Random");
	}
	
	@Override
	public void loop() {
		int status=(int) (Math.random()*4);
		int x=(int) (Math.random()*this.cube.getSize());
		int y=(int) (Math.random()*this.getCube().getSize());
		int z=(int) (Math.random()*this.getCube().getSize());
		this.getCube().setLED(status, x, y, z);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
