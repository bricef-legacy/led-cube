import processing.core.PApplet;
import processing.serial.Serial;


public class CoreApplet extends PApplet implements CubeUserInterface{
	private static final long serialVersionUID = 1L;
	boolean killme;
	private CoreAPI theCube;
	P5GUI P5Controller;
	//Drawing Parameters
	final static int CUBESIZE = 8; //size of cube
	final static float LEDSPACE = 40; //led spacing
	final static float LEDSIZE = 10; //led size
	final static float startX = -LEDSPACE*CUBESIZE/2; //XYZ starting locations. eg. location of first LED (X,Y,Z)
	final static float startY = -LEDSPACE*CUBESIZE/2;
	final static float startZ = -LEDSPACE*CUBESIZE/2;
	int[][][] cubestates;
	
	CubeUserInterface user;
	CubeUserInterface talker;
	
	static public void main(String args[]) {
		   PApplet.main(new String[] { "CoreApplet" });
	}
	
	public CoreApplet(){
		
	}
	
	public void setup() {
		size(800,600, OPENGL);
	    background(0);
		this.cubestates = new int[CUBESIZE][CUBESIZE][CUBESIZE];
		this.theCube=new MagicCubeData(8);
		this.P5Controller =new P5GUI(this);
		
		
		user=new CubeUser();
		user.setCube(theCube);
		user.start();
		try{
		talker=new SerialTalk(this, "COM3");
		talker.setCube(theCube);
		talker.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	  
	public void draw() {
		background(0);
		P5Controller.getGUI().draw();
		lights();
		drawcube();
	}
	private void drawcube(){
		//System.out.println("frame rate:"+this.frameRate);
		cubestates = this.theCube.readCube();
		pushMatrix();
		translate(400, 300);
		for (int x=0; x<CUBESIZE; x++ ){
			for (int y=0; y<CUBESIZE; y++){
				for (int z=0; z<CUBESIZE; z++){
					pushMatrix();
					translate(startY + PApplet.parseFloat(y)*LEDSPACE ,startZ + PApplet.parseFloat(z)*LEDSPACE , startX + PApplet.parseFloat(x)*LEDSPACE);
					switch (cubestates[x][y][z]){
						case 0: fill(50);
								break;
						case 1: fill(color(255,0,0));
								break;
						case 2: fill(color(0,255,0));
								break;		
						case 3: fill(color(255,255,0));
								break;	
						default: fill(color(0,0,255));
								break;
					}
					box(LEDSIZE);
					popMatrix();
				}
			}
		}
		popMatrix();
	}

	@Override
	public void setCube(CoreAPI mcube) {
		this.theCube=mcube;
	}

	@Override
	public void killme() {
		this.killme=true;
	}
}

