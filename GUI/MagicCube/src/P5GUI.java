import utils.EventCodes;
import controlP5.ControlP5;
import controlP5.Controller;

import controlP5.Textfield;
import controlP5.Toggle;

/**
 * The main Gui for the project. Implements EventCode for convenience.
 * Creates the gui elements. Serves as a wrapper around controlP5. Has
 * a few ad-hoc methods to access text fields from outside.
 * @author Brice
 */
public class P5GUI implements EventCodes{
	
	//The gui
	ControlP5 gui;
	
	//the miller indices controller and fields
	Controller millGo;
	Textfield iBox, jBox, kBox;
	
	//Ising controller
	Controller isingGo;
	Controller isingSlider;
	Controller rad;
	
	//crystal stuff
	Controller sc;
	Controller sFcc;
	Controller lFcc;
	Controller sBcc;
	Controller lBcc;
	Controller barium;
	
	Controller demoToggle;
	
	/**
	 * The Gui constructor. Creates the gui elements.
	 * @param coreApplet The parent caller. Used only to initialise the ControlP5 object.
	 * @param listener The event listener where all gui actions are specified.
	 * @author Brice
	 * @author Sam
	 */
	 public P5GUI(CubeSimulation coreApplet, EventListener listener){ 
		 
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
	  welcomeText+="    Christopher Ryan\n";
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
	  sc = gui.addButton("Simple Cubic",10, 10, 40, 102, 20);
		 sc.setId(CRYSTAL_SC);
		 sc.addListener(listener);
		 sc.setTab("Crystal");
	  lFcc = gui.addButton("Large FCC",10, 10, 70, 102, 20);
		 lFcc.setId(CRYSTAL_FCC_XL);
		 lFcc.addListener(listener);
		 lFcc.setTab("Crystal");
	sFcc = gui.addButton("Small FCC",10, 10, 100, 102, 20);
		 sFcc.setId(CRYSTAL_FCC);
		 sFcc.addListener(listener);
		 sFcc.setTab("Crystal");
	lBcc = gui.addButton("Large BCC",10, 10, 130, 102, 20);
		 lBcc.setId(CRYSTAL_BCC_XL);
		 lBcc.addListener(listener);
		 lBcc.setTab("Crystal");
	sBcc = gui.addButton("Small BCC",10, 10, 160, 102, 20);
		 sBcc.setId(CRYSTAL_BCC);
		 sBcc.addListener(listener);
		 sBcc.setTab("Crystal");
		 barium = gui.addButton("Barium Titanate", 10, 10, 190, 102, 20);
		 barium.setId(CRYSTAL_BARIUM);
		 barium.addListener(listener);
		 barium.setTab("Crystal");
		 
	  
	  
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
	 
	/**
	 * The gui accessor is used by CubeSimulation to call the draw method of the undrlying ControlP5 class.
	 * @return The controlP5 gui used to draw the gui.
	 * @author Brice
	 */
	public ControlP5 getGUI(){
		return gui; 
	}
	/**
	 * Textfield accessor used by the eventListener.
	 * @return the controlP5.TextField used for displaying miller planes
	 * @see EventListener
	 * @author Brice
	 */
	public Textfield getIBox() {
		return iBox;
	}
	/**
	 * Textfield accessor used by the eventListener.
	 * @return the controlP5.TextField used for displaying miller planes
	 * @see EventListener
	 * @author Brice
	 */
	public Textfield getJBox() {
		return jBox;
	}
	/**
	 * Textfield accessor used by the eventListener.
	 * @return the controlP5.TextField used for displaying miller planes
	 * @see EventListener
	 * @author Brice
	 */
	public Textfield getKBox() {
		return kBox;
	}
}
