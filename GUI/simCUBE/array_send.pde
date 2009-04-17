import processing.serial.*;

/* BUG: Its broken, Data transfer breaks after one cube is transmitted.
 *
 * Orignal array_send by Brice.
 * Modified and intregrated by Ed.
 *
 * Since sending the data to the arduino takes longer than a frame,
 * I have created a new thread to send the data, this **should prevent death of the framerate.
 * Based on example: http://www.janeg.ca/scjp/threads/runnable.html
 * --
 * Ed
 */
 
 class array_send implements Runnable {
   
   // store the state of the thread
   // [1:Update once, then stop, 2: Keep updating untill told to stop, anything else: stop]
   int updateState = 0;
   
   //the led cube we're using, (to get bytestream from)
   LedCube updatecube;
   
   //Parent applet (serial needs this)
   PApplet parentapp;
   
   int comport; //the comport number.

  //class initilisation, take ledcube to use and updatestate data
  array_send(PApplet parent, LedCube cube, int updateState, int port) {
    parentapp = parent;
    updatecube = cube;
    this.updateState = updateState;
    comport = port;
  }
  
  void setUpdateState (int newstate){
    updateState = newstate;
  }
  
  //other variables from brices example..
  Serial myPort;  // Create object from Serial class
  int val;        // Data received from the serial port
  public static final int MAX_WAIT_MILLIS = 1000;
  int returned=0;
  int del=0;
  byte[] buffer = {12,11,10,9,8,7,6,5,4,3,2,1,0};
  byte[][] doubleBuffer = { {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0},
                            {31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0}
                           };
  //end brice-variables.
  
  int ssec, fsec, smil, fmil;

  // override run() method in interface
  public void run() {
    
    //first off we initilise the serial port, and get a data link.
    println(Serial.list());
    String portName = Serial.list()[1];
    myPort = new Serial(parentapp, portName, 115200);//must match the baud rate on the arduino.
    
    //now lets do the main loop
    //while loops continuosly, when updateState != 1,2 a return is called to exit loop
    
    while(true){
      
      //now lets operate the criteria
      if ( (updateState != 1) && (updateState != 2) ){
        println("Update State != 1,2. Exiting Aurdino update thread.");
        return;
      }
      if (updateState == 1) updateState = 0; //Exit thread on next run.
      
      //now time todo the real updating....
      
      //Get the latest states on the cube...
      doubleBuffer = updatecube.getByteStream();
      
      print("Checking for readiness: ");
      while(returned!=INIT_OK){
        print(".");
        SSend(INIT_IS_READY);
        returned=myPort.read();
      }
      println("Arduino listening.");
      writeCube();
      
    }
    
  }
  
  //Functions from brices example app
  void writeCube(){
    println("Begining cube transmission.");
  
   try{
         
         //send begin cube
          //for layer in layers:   
             //send begin layer
             //send layer bytes
             //send endlayer
             //wait for layer ack
               //if layer ACK +ve layer+=1;
               //else leyer=layer;
         //send endcube
        
        // Ed: Start Time 
         ssec = second(); smil = millis();
         
        SSend(BEGIN_CUBE);  
        for(int j=0; j<doubleBuffer.length;j++){
          println("Transmitting layer "+str(j));
          SSend(BEGIN_LAYER);
          
          //Write can handle an array of bytes..
         /* for(int i=0; i<doubleBuffer[j].length; i++){
            SSend(doubleBuffer[j][i]);
          }*/
          
          myPort.write(doubleBuffer[j]);
          
          SSend(END_LAYER);
          if(waitForLayerACK()){println("Layer "+str(j)+" acknowledged");};
        }
        SSend(END_CUBE);
        
        // Ed: Stop Time
        fsec = second(); fmil = millis();
        
        if(waitForCubeACK()){
          // Ed: Print Time
          print("Time (ms): "); println(1000*(fsec-ssec) +fmil - smil);
          println("Cube sent successfully.");
          //System.exit(0);
        };
        
   }catch(Exception e){
    System.out.println(e);
    System.exit(1);
   } 
  }
  
  boolean waitForCubeACK()  throws Exception{
    int time = millis();
    int opcode = myPort.read();
      while(opcode!=CUBE_SUCCESS){
        if(millis()-time>MAX_WAIT_MILLIS){
           throw new Exception("the arduino board does not seem to be responding (NO CUBE ACK)");
          }
          opcode=myPort.read();
      }
      return true;
  }
  
  
  boolean waitForLayerACK()  throws Exception{
    int time = millis();
    boolean success=false;
    boolean acked=false;
    int opcode = myPort.read();
    //println(opcode);
      while(!acked){
        if(millis()-time>MAX_WAIT_MILLIS){
           throw new Exception("The arduino board does not seem to be responding (NO LAYER ACK)");
          }
        if(opcode==LAYER_ACK_SUCCESS){
            acked=true;
            success= true;
        }else if(opcode==LAYER_ACK_FAIL){
            acked=true;
            success= false;
        };
        opcode=myPort.read();
      }
      return success;
  }
  
  void SSend(int toSend){
    myPort.write(toSend);
      delay(del);
  }
}
