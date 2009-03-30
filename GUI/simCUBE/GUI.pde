import controlP5.*;
import java.lang.Integer.*;
/*
 *@Author: Samuel Dove
 *@Date: 03/2009
 *@About: GUI for simcube project work
 */

ControlP5 gui;

/*
 *variables used for stuff
 *
*/

//miller indices controlling variables
final static int millerGo=100;

int ival, jval, kval;

class GUI{
  
  
 public Textfield iBox, jBox, kBox;
 
 
 
 GUI(simCUBE s){ 
   //have to draw gui on something specific
  gui = new ControlP5(s);
  
  gui.tab("Ising Model");
  gui.tab("Miller Indices");
  gui.tab("default").setLabel("Crystal");
  
  //radio buttons for crystal structure basic 
  Radio r = gui.addRadio("radio",10,40);
  r.add("Large SC", 7);
  
  r.add("Small FCC",3);
  r.add("Large FCC",4);
  
  r.add("Small BCC",5);
  r.add("Large BCC",6);
 
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
 Controller iBox, jBox, kBox, millGo;
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
void controlEvent(ControlEvent theEvent) {
  println(theEvent.controller().id());
 
  switch(theEvent.controller().id()){
   //Miller indices activated, takes input from boxes to produce correct planes
   //Amazing plane code by Chris Ryan *g*
   case millerGo:
   //reads valuues from text boxes, then sends them to miller draw method
   ival = Integer.decode(cubeGUI.iBox.getText());
   jval = Integer.decode(cubeGUI.jBox.getText());
   kval = Integer.decode(cubeGUI.kBox.getText());
   miller(mycube, 8, ival, jval, kval);
   break; 
   
  }
  
}

void radio(int theID) {
  switch(theID) {
    
    case(3):
    smallFCC(mycube, 8);
    break;
    case(4):
    largeFCC(mycube, 8);
    break;
    case(5):
    smallBCC(mycube, 8);
    break;
    case(6):
    largeBCC(mycube, 8);
    break;
    case(7):
    largeSC(mycube, 8);
    break;
  }
  
}

