package cubeUser;

import core.AbstractCubeUser;
import java.awt.FileDialog;
import java.awt.Frame;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;



public class SaveUser extends AbstractCubeUser {
	PApplet papp;
	
	public SaveUser() {
		papp = new PApplet();
		
	}
	
	
	//Method From: http://www.rgagnon.com/javadetails/java-0247.html
	public String saveFile(String fileType) {
		FileDialog fd = new FileDialog(new Frame(), "Save as", FileDialog.SAVE);
		fd.setFile(fileType);
		fd.setLocation(50, 50);
		fd.setVisible(true);
		if (fd.getDirectory()==null || fd.getDirectory() == null) return null;
		return (fd.getDirectory() + fd.getFile());
  }

	
	public void loop() {
		//save File
		saveFile();
		killme();
	}
	
	void saveFile(){
		String saveme  = saveFile(".png");
		if (saveme !=null){
		  System.out.println("[SAVEUSR]: Saving to file: " + saveme);
		  int a,b,c;
		  PImage myimage = papp.createGraphics(64, 8, PGraphics.P2D);
		  int cubestates [][][] = new int[8][8][8];
		  cubestates = this.getCube().readCube();
		  
		  for (a=0; a<8; a++) for (b=0; b<8; b++) for (c=0; c<8; c++)
		  {
		    if (cubestates[a][b][c] == 0) myimage.set(8*a + b, c, papp.color(0, 0, 0));
		    if (cubestates[a][b][c] == 1) myimage.set(8*a + b, c, papp.color(255, 0, 0));
		    if (cubestates[a][b][c] == 2) myimage.set(8*a + b, c, papp.color(0, 255, 0));
		    if (cubestates[a][b][c] == 3) myimage.set(8*a + b, c, papp.color(255, 255, 0));
		  }
		  myimage.save(saveme);
		}
		}
	
}