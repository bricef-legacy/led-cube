

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Radio;
import controlP5.Tab;
import controlP5.Textfield;
import controlP5.Toggle;

public class P5GUI implements EventCodes{
	//the calling applet
	CubeSimulation parent;
	
	//The gui
	ControlP5 gui;
	
	//the miller indices controller and fields
	Controller millGo;
	Textfield iBox, jBox, kBox;
	
	//Ising controller
	Controller isingGo;
	Controller isingSlider;
	
	Controller demoToggle;
	
	 public P5GUI(CubeSimulation coreApplet, EventListener listener){ 
	  
		 this.parent=coreApplet;
		 
		 //have to draw gui on something specific
	  gui = new ControlP5(coreApplet);
	  gui.tab("default").setLabel("Home");
	  gui.tab("Crystal");
	  gui.tab("Ising Model");
	  gui.tab("Miller Planes");
	  gui.tab("Utils");
	  gui.tab("Options");
	  
	  /*---Default gui----*/
	  demoToggle=gui.addButton("Run Demo", 10, 50, 250, 50, 20);
	  demoToggle.setId(DEMO_TOGGLE);
	  demoToggle.addListener(listener);
	  demoToggle.setTab("default");
	  
	  
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
	  gui.addTextarea("Welcome", welcomeText, 10, 40, 170, 300).setTab("default");	  
	  
	  
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
	 isingGo.addListener(listener);
	 isingGo.setTab("Ising Model"); 
	 isingSlider= gui.addSlider("temperature", 0, 50, 0, 10, 70, 10, 350);//addSlider(theName, theMin, theMax, theDefaultValue, theX, theY, theW, theH);
	 isingSlider.setId(ISING_SLIDER_ID);
	 isingSlider.setLabel("Temperature");
	 isingSlider.addListener(listener);
	 isingSlider.setTab("Ising Model");
	 
	 /*----Miller indices Gui elements----*/
	 iBox = gui.addTextfield("i", 10, 40, 100, 20);
	 jBox = gui.addTextfield("j", 10, 80, 100, 20);
	 kBox = gui.addTextfield("k", 10, 120, 100, 20);
	 iBox.setId(IBOX_ID);
	 jBox.setId(JBOX_ID);
	 kBox.setId(KBOX_ID);
	 iBox.setTab("Miller Planes");
	 jBox.setTab("Miller Planes");
	 kBox.setTab("Miller Planes");
	 iBox.addListener(listener);
	 jBox.addListener(listener);
	 kBox.addListener(listener);
	 millGo = gui.addButton("Draw Miller Planes", 10, 10, 160, 100, 20);
	 millGo.setId(MILLER_START_ID);
	 millGo.addListener(listener);
	 millGo.setTab("Miller Planes");
	  
	  
	 }
	  void temperature(float t){
		  System.out.println(t);
	  }
	  public ControlP5 getGUI(){
		  return gui; 
	  }
	public Textfield getIBox() {
		return iBox;
	}
	public Textfield getJBox() {
		return jBox;
	}
	public Textfield getKBox() {
		return kBox;
	}
}
