

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Radio;
import controlP5.Textfield;
import controlP5.Toggle;

public class P5GUI {
	//The gui
	ControlP5 gui;
	
	//the miller indices controller and fields
	Controller millGo;
	Textfield iBox, jBox, kBox;
	int ival, jval, kval;
	
	//Ising controller
	Controller isingGo;
	Controller isingSlider;
	
	
	
	//The event refs
	final static int MILLER_START_ID=100;
	final static int ISING_START_ID =50;
	final static int ISING_SLIDER_ID=51;
	final static int UTILS_START_ID=60;
	
	//radio refs
	final static int CRYSTAL_SC = 1;
	final static int CRYSTAL_FCC = 2;
	final static int CRYSTAL_FCC_XL = 3;
	final static int CRYSTAL_BCC = 4;
	final static int CRYSTAL_BCC_XL = 5;
	
	//options refs
	final static int WRITE_SERIAL = 11;
	 
	 public P5GUI(CubeSimulation coreApplet){ 
	  //have to draw gui on something specific
	  gui = new ControlP5(coreApplet);
	  gui.tab("default").setLabel("Home");
	  gui.tab("Crystal");
	  gui.tab("Ising Model");
	  gui.tab("Miller Planes");
	  gui.tab("Utils");
	  gui.tab("Options");
	  
	  /*---Default gui----*/
	  String welcomeText="3D Cube Simulator \n";
	  welcomeText+="=============================\n";
	  welcomeText+="The University of Sheffield.\n";
	  welcomeText+="\n";
	  welcomeText+="PHY343 project for Spring Semester 2009.\n";
	  welcomeText+="\n";
	  welcomeText+="Credits:\n";
	  welcomeText+="    Samuel Dove\n";
	  welcomeText+="    Brice Fernandes\n";
	  welcomeText+="    Tomas Loussert\n";
	  welcomeText+="    Michael Overington\n";
	  welcomeText+="    Edward Overton\n";
	  welcomeText+="    Chris Ryan\n";
	  welcomeText+="\n";
	  welcomeText+="Supervisor: Dr Martin Grell\n";
	  welcomeText+="\n";
	  welcomeText+="See http://ledcube.googlecode.com for the latest info and releases.\n";
	  welcomeText+="\n";
	  gui.addTextarea("Welcome", welcomeText, 10, 40, 170, 300);
	  
	  /*----Utils contoller----*/
	  Controller utils;
	  utils=gui.addButton("Load pattern", 10, 10, 40, 102, 20);
	  utils.setId(UTILS_START_ID);
	  utils.setTab("Utils");
	  
	  /*----Options contoller----*/
	  Toggle serialWrite = gui.addToggle("Write to serial?", false, 10, 40, 10, 10);
	  serialWrite.setId(WRITE_SERIAL);
	  serialWrite.setTab("Options");
	  //idea for options: led size, led separation
	  
	  
	  /*----Crystal sturcture gui elements----*/
	  Radio r = gui.addRadio("radio",10,40);
	  r.addItem("Large SC", CRYSTAL_SC);
	  r.addItem("Small FCC",CRYSTAL_FCC);
	  r.addItem("Large FCC",CRYSTAL_FCC_XL);
	  r.addItem("Small BCC",CRYSTAL_BCC);
	  r.addItem("Large BCC",CRYSTAL_BCC_XL);
	  r.setTab("Crystal");
	  
	  
	  
	 /*----Ising model Gui elements----*/
	 isingGo = gui.addButton("Start Ising Simulation",10, 10, 40, 102, 20);
	 isingGo.setId(ISING_START_ID);
	 isingGo.setTab("Ising Model"); 
	 
	 isingSlider= gui.addSlider("Temperature", 0, 50, 0, 10, 70, 10, 400);//addSlider(theName, theMin, theMax, theDefaultValue, theX, theY, theW, theH);
	 isingSlider.setId(ISING_SLIDER_ID);
	 isingSlider.setTab("Ising Model");
	 
	 
	 /*----Miller indices Gui elements----*/
	 
	 ival=0;
	 jval=0;
	 kval=0; 
	 iBox = gui.addTextfield("i", 10, 40, 100, 20);
	 jBox = gui.addTextfield("j", 10, 80, 100, 20);
	 kBox = gui.addTextfield("k", 10, 120, 100, 20);
	 iBox.setTab("Miller Planes");
	 jBox.setTab("Miller Planes");
	 kBox.setTab("Miller Planes");
	 millGo = gui.addButton("Draw Miller Indices", 10, 10, 160, 100, 20);
	 millGo.setId(MILLER_START_ID);
	 millGo.setTab("Miller Planes");
	  
	  
	 }
	  
	  public ControlP5 getGUI(){
	   return gui; 
	  }
	  
	  void controlEvent(ControlEvent Event){
		  System.out.println("event from controller id: "+Event.controller().id());
		  switch(Event.controller().id()){
			  //Miller indices activated, takes input from boxes to produce correct planes
			  //Amazing plane code by Chris Ryan *g*
	
			  case MILLER_START_ID:
				  //reads valuues from text boxes, then sends them to miller draw method
				  ival = Integer.decode(this.iBox.getText());
				  jval = Integer.decode(this.jBox.getText());
				  kval = Integer.decode(this.kBox.getText());
				  //start miller thread with i,j,k
				  //miller(mycube, 8, ival, jval, kval);
				  break; 
	
			  case ISING_START_ID:
				  //model = new IsingModel(mycube, temperature);
				  //model.run();
				  break;
	
			  case ISING_SLIDER_ID:
				  //model.setTemp(temperature);
				  //println("Temperature: " +temperature);
				  break;
		  }
	  }

void radio(int theID) {
  switch(theID) {
    case(CRYSTAL_SC):
	    //TODO
	    break;
    case(CRYSTAL_FCC):
	    //TODO
	    break;
    case(CRYSTAL_FCC_XL):
	    //TODO
	    break;
    case(CRYSTAL_BCC):
	    //TODO
	    break;
    case(CRYSTAL_BCC_XL):
	    //TODO
	    break;
  }
  
}
	  
}
