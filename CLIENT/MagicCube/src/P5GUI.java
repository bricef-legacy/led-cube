import controlP5.ControlP5;
import controlP5.Radio;
import controlP5.Textfield;
import controlP5.*;

public class P5GUI {
	public Textfield iBox, jBox, kBox;
	ControlP5 gui;
	int ival, jval, kval;
	final static int millerGo=100;
	final static int iGo =50;
	final static int iSlid=51;
	Controller millGo;
	 
	 public P5GUI(CoreApplet s){ 
	   //have to draw gui on something specific
	  gui = new ControlP5(s);
	  
	  gui.tab("Ising Model");
	  gui.tab("Miller Indices");
	  gui.tab("default").setLabel("Crystal");
	  
	  //radio buttons for crystal structure basic 
	  Radio r = gui.addRadio("radio",10,40);
	  r.addItem("Large SC", 7);
	  
	  r.addItem("Small FCC",3);
	  r.addItem("Large FCC",4);
	  
	  r.addItem("Small BCC",5);
	  r.addItem("Large BCC",6);
	 
	  
	 //Ising model control
	 Controller isingGo;
	 int iGo = 50;
	 isingGo = gui.addButton("Start Ising Simulation",10, 10, 40, 102, 20);
	 isingGo.setId(iGo);
	 isingGo.setTab("Ising Model"); 
	 
	  
	 //Miller indices control tab
	  ival=0;
	  jval=0;
	  kval=0; 
	 
	 //numberBox(name, initial value, x, y, width, height)


	   this.iBox = gui.addTextfield("i", 10, 40, 100, 20);
	   this.jBox = gui.addTextfield("j", 10, 80, 100, 20);
	   this.kBox = gui.addTextfield("k", 10, 120, 100, 20);
	   
	   
	  
	   millGo = gui.addButton("Draw Miller Indices", 10, 10, 160, 100, 20);
	   
	   millGo.setId(millerGo);
	   this.iBox.setTab("Miller Indices");
	   this.iBox.setId(millerGo+1);
	   this.jBox.setTab("Miller Indices");
	   this.kBox.setTab("Miller Indices");
	  
	  millGo.setTab("Miller Indices");
	  
	  
	 }
	  
	  ControlP5 getGUI(){
	   return gui; 
	  }
	  
}
