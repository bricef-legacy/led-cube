package cubeUser;

import core.AbstractCubeUser;
import java.awt.FileDialog;
import java.awt.Frame;
import processing.core.PApplet;
import processing.core.PImage;
import utils.ColorCodes;

public class LoadUser extends AbstractCubeUser implements ColorCodes{
	
	PApplet papp;
	
	int C_BLACK, C_RED, C_ORANGE, C_GREEN;
	
	public LoadUser(){
		this.setName("Load");
		papp = new PApplet();
		
		C_BLACK = papp.color(0, 0, 0);
		C_RED = papp.color(255, 0, 0);
		C_GREEN = papp.color(0, 255, 0);
		C_ORANGE = papp.color(255, 255, 0);
	}
	
	public String loadFile(String fileType) {
		FileDialog fd = new FileDialog(new Frame(), "Load...", FileDialog.LOAD);
		fd.setFile(fileType);
		fd.setLocation(50, 50);
		fd.setVisible(true);
		if (fd.getDirectory()==null || fd.getDirectory() == null) return null;
		return (fd.getDirectory() + fd.getFile());
  }
	
	void loadFile(){
		String loadme  = loadFile(".png");
		
		if (loadme !=null){
			  System.out.println("[LOADUSR]: Loading file: " + loadme);
			  
			  int a,b,c;
			  int tempcol;
			  PImage myimage = papp.loadImage(loadme);
			  
			  int cubestates [][][] = new int[8][8][8];
			  
			  for (a=0; a<8; a++) for (b=0; b<8; b++) for (c=0; c<8; c++){
				  tempcol = myimage.get(8*a + b, c);
				  
				  if(tempcol == C_RED) cubestates[a][b][c] = RED;
				  else if (tempcol == C_GREEN) cubestates[a][b][c] = GREEN;
				  else if (tempcol == C_ORANGE) cubestates[a][b][c] = ORANGE;
				  else cubestates[a][b][c] = OFF;  
			  }
			  
			  this.getCube().setCube(cubestates);
			  
		}
				  
			  

	}

	@Override
	public void loop() {
		loadFile();
		killme();
	}

}
