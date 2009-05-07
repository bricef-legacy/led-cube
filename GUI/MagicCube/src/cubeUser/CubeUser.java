package cubeUser;


import core.AbstractCubeUser;


public class CubeUser extends AbstractCubeUser {
		@Override
	public void run() {
		while(!isKillme()){
			int status=(int) (Math.random()*4);
			int x=(int) (Math.random()*8);
			int y=(int) (Math.random()*8);
			int z=(int) (Math.random()*8);
			this.getCube().setLED(status, x, y, z);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
