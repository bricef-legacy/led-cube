
public class CubeUser extends Thread implements CubeUserInterface {
	CoreAPI cube;
	boolean killme=false;
	
	@Override
	public void setCube(CoreAPI mcube) {
		this.cube=mcube;
	}

	@Override
	public void run() {
		while(!killme){
			int status=(int) (Math.random()*4);
			int x=(int) (Math.random()*8);
			int y=(int) (Math.random()*8);
			int z=(int) (Math.random()*8);
			cube.setLED(status, x, y, z);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void killme() {
		this.killme=true;
	}

}
