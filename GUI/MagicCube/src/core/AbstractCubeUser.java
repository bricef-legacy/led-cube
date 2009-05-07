package core;


public abstract class AbstractCubeUser extends Thread implements CubeUserInterface{
	CoreAPI cube;
	
	private boolean killme=false;
	
	public CoreAPI getCube() {
		return cube;
	}
	
	@Override
	public void setCube(CoreAPI mcube) {
		this.cube=mcube;
	}

	@Override
	public abstract void run();

	@Override
	public void killme() {
		this.setKillme(true);
	}

	public void setKillme(boolean killme) {
		this.killme = killme;
	}

	public boolean isKillme() {
		return killme;
	}
}
