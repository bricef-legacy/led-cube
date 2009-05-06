

import processing.opengl.*;
import javax.media.opengl.*;
import core.CoreAPI;
import core.CubeUserInterface;
import core.MagicCubeData;
import core.NoSuchUserException;
import core.UserManager;
import processing.core.PApplet;
import cubeUsers.CubeUser;
import cubeUsers.SerialTalk;


public class CubeSimulation extends PApplet implements CubeUserInterface{
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
	private float a = 0;
	private float b = 0;
	private float mousespeed = 0.02f; //the mouse movement
	UserManager manager;
	GL gl;        //OpenGL class (used to assist rendering)
	final static String FONT_LOCATION="D:/FILES/PHY343/repo/CLIENT/MagicCube/src/CourierNew36.vlw";
	
	static public void main(String args[]) {
		   PApplet.main(new String[] { "CubeSimulation" });
	}
	
	public CubeSimulation(){
		
	}
	
	public void setup() {
		size(800,600, OPENGL);
		//hint(ENABLE_OPENGL_4X_SMOOTH); //enable beautiful antialiasing
		noStroke();
	    background(0);
	    lights();
		translate(width/2, height/2);
		textFont(loadFont(FONT_LOCATION)); 
	    
		this.cubestates = new int[CUBESIZE][CUBESIZE][CUBESIZE];
		this.theCube=new MagicCubeData(8);
		this.P5Controller =new P5GUI(this);
		this.manager=new UserManager(this.theCube);
		this.manager.adduser(new CubeUser(), "Random");
		this.manager.adduser(new SerialTalk(this, "COM3"), "Serial");
		
		this.manager.toggleToUser("Random");
		this.manager.startParallelUser("Serial");
		
		}
	  
	public void draw() {
		background(0);
		noStroke();
		lights();
		
		pushMatrix();
			translate(100, height-100); //this function translates the drawing to the bottom left
			rotateY(a);
			rotateZ(b/2);
			noFill();
			smooth();
			strokeWeight((float) 1.5);
			drawAxis((float)75.0,(float)10.0);
		popMatrix();
		
		pushMatrix();
		noStroke();
			translate((float)(width/(1.7)),(float)(height/1.9)); //this function translates the drawing to the centre of the window
			rotateY(a);
			rotateZ(b/2);
			drawcube();
		popMatrix();
		
		P5Controller.getGUI().draw();
	}
	private void drawcube(){
		//System.out.println("frame rate:"+this.frameRate);
		cubestates = this.theCube.readCube();
		pushMatrix();
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
	
	public void mouseDragged() {
		  if (pmouseX < mouseX) a+=mousespeed; 
		  else if (pmouseX > mouseX) a-=mousespeed;
		  if (pmouseY < mouseY) b+=mousespeed;
		  else if (pmouseY > mouseY) b-=mousespeed;
	}
	
	void drawAxis(float axissize, float arrowszie){
		  
		  translate(-axissize/2, axissize/2, -axissize/2);
		  stroke(255, 0, 0);
		  
		    beginShape(); //X-Axis
		      vertex(0, 0, 0);
		      vertex(axissize, 0, 0);
		      vertex(axissize-arrowszie, arrowszie, 0);
		      vertex(axissize, 0, 0);
		      vertex(axissize-arrowszie, -arrowszie, 0);
		    endShape();
		    
		    fill(255,0,0);
		    pushMatrix();
		    translate(axissize/4,0,0);
		    text("X", 15, 30);
		    popMatrix();
		    noFill();
		    
		    stroke(0, 255, 0);
		    beginShape(); //Y-Axis
		      vertex(0, 0, 0);
		      vertex(0, 0, axissize);
		      vertex(0, arrowszie, axissize-arrowszie);
		      vertex(0, 0, axissize);
		      vertex(0, -arrowszie, axissize-arrowszie);
		    endShape();
		    
		    fill(0,255,0);
		    pushMatrix();
		    translate(0,0,axissize);
		    rotateY(PI/2);
		    text("Y", 15, 30);
		    popMatrix();
		    noFill();
		    
		    stroke(0, 0, 255);
		    beginShape(); //Z-Axis
		      vertex(0, 0, 0);
		      vertex(0, -axissize, 0);
		      vertex(arrowszie, -axissize+arrowszie, 0);
		      vertex(0, -axissize, 0);
		      vertex(-arrowszie, -axissize+arrowszie, 0);
		    endShape();
		    
		    fill(0,0,255);
		    pushMatrix();
		    translate(0,-axissize,0);
		    //rotateY(PI/2);
		    text("Z", 15, 30);
		    popMatrix();
		    noFill();
		}
	
}

