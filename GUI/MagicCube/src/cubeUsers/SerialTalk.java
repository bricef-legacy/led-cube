package cubeUsers;
import core.ColorCodes;
import core.CoreAPI;
import core.CubeUserInterface;
import core.OpCodes;
import core.Statistics;
import processing.core.PApplet;
import processing.serial.Serial;


public class SerialTalk extends Thread implements CubeUserInterface, OpCodes, ColorCodes{
	CoreAPI cube;
	boolean killme=false;
	public static final long MAX_WAIT_MILLIS = 1000;
	int comport;
	Serial myPort;  // Create object from Serial class
	PApplet parentapp;
	int val;        // Data received from the serial port
	int returned=0;
	int del=0;
	byte[][] doubleBuffer;
	Statistics stats=new Statistics();
	
	public SerialTalk(PApplet parentapp, String portName){
		myPort = new Serial(parentapp, portName, 115200);//must match the baud rate on the arduino.
	}
	
	@Override
	public void setCube(CoreAPI mcube) {
		this.cube=mcube;
	}
	
	@Override
	public void run(){
		while(!this.killme){
			 writeCube();
		}
	}

	public byte[][] getByteStream(){ 
	    
	    /*
	     * @Authors: Samuel Dove, Edward Overton.
	     * Initial Work done by Sam.
	     * tweaked and incoperated into LEDCUBE class done by Edward.
	     *
	     *  @About: Trying to solve byte array problem.
	     *          Converting the data stored in the computer to draw the cube, to a ByteStream which can be written onto the cube
	     */
	    
	    byte[][] stream = new byte[8][32];
	    int[][][] mycube=this.cube.readCube();
	    byte temp;
	    
	    //lets loop through z
	    int x,y;
	    for (int z=0; z<8; z++){
	      
	      //loop through all x'es. inside the loop the 4 (2nd half Green, Red, 1st half Greem, Red) different 'y' bytes will be calculated (for a given x)
	      //and each written to the correct part of the array.
	      for (x=7; x>=0; x--){
	        
	        temp = 0x00;//temp is the byte were manipulating, initilised each time.
	        //looping through the bottom block of y's (these need to be written first)
	        for (y=3; y<8; y++){
	        
	          //If green LED needs to be on
	          if ( (mycube[x][y][z] == GREEN) || (mycube[x][y][z] == ORANGE)){
	            //turn the corresponding bit on, done by shifting 00000001 to the correct bit, then using an OR opertation.
	            //remenber 8 > y > 3, so to put the correct bit in the 0-4 range we need to subtract4.
	            temp |= ( 0x01 << (y-4) );
	          }
	        }  
	        //okay, now lets write the bits to the array... remenber the 0th element needs to be written first.
	        stream[z][7-x] = temp;


	        //repeating abouve for red
	        temp = 0x00;
	        for (y=3; y<8; y++)
	          if ( (mycube[x][y][z]== RED) || (mycube[x][y][z] == ORANGE)){ //this time we are setting the RED led.
	            temp |= ( 0x01 << (y-4) );
	        }
	        stream[z][8+7-x] = temp; // we need to manipulate the array 8 elements further along.
	        
	        //now we shall do the first half (y:0-4)
	        temp = 0x00;
	        for (y=0; y<4; y++)
	          if ( (mycube[x][y][z] == GREEN) || (mycube[x][y][z] == ORANGE)){
	            temp |= ( 0x01 << (y) ); //no need to subtract 4 now, scince y ranges from 0-3
	          }
	        stream[z][16+7-x] = temp; //we now need to write into the array between 15-23
	        
	        //now doing the first red half.
	        temp = 0x00;
	        for (y=0; y<4; y++)
	          if ( (mycube[x][y][z] == RED) || (mycube[x][y][z] == ORANGE)){
	            temp |= ( 0x01 << (y) );
	          }
	        stream[z][24+7-x] = temp;
	      }
	    }
	    return stream;
	  }
		  
	
	public void writeCube(){
		long before=System.currentTimeMillis();
		System.out.print("Checking for readiness: ");
	      while(returned!=INIT_OK){
	    	  System.out.print(".");
	        SSend(INIT_IS_READY);
	        returned=myPort.read();
	        //System.out.println(returned);
	      }
	      returned = 0;
	      System.out.println("Arduino listening.");
		try{
	  System.out.println("Begining cube transmission.");
	  doubleBuffer=this.getByteStream();
	   
	         
	         //send begin cube
	          //for layer in layers:   
	             //send begin layer
	             //send layer bytes
	             //send endlayer
	             //wait for layer ack
	               //if layer ACK +ve layer+=1;
	               //else leyer=layer;
	         //send endcube
	        
	     
	        SSend(BEGIN_CUBE);  
	        //System.out.println(myPort.read());
	        for(int j=0; j<doubleBuffer.length;j++){
	          //System.out.printf("Transmitting layer %d\n",j);
	          
	          SSend(BEGIN_LAYER);
	          myPort.write(doubleBuffer[j]);
	          SSend(END_LAYER);
	          
	   	   
	          if(waitForLayerACK()){
	        	  //System.out.printf("Layer %d acknowledged\n", j);
	        	  };
	        }
	        SSend(END_CUBE);
	        
	        if(waitForCubeACK()){
	          long after = System.currentTimeMillis();
	          long foo=after-before;
	          stats.add(foo);
	          System.out.printf("Cube sent successfully in: %dms (%dms avg)\n\n", foo, stats.average());
	        };
	        
	   }catch(Exception e){
		e.printStackTrace();
	    //System.exit(1);
	   } 
	   
	  }
		  public boolean waitForCubeACK()  throws Exception{
		    long time = System.currentTimeMillis();
		    int opcode = myPort.read();
		      while(opcode!=CUBE_SUCCESS){
		        if(System.currentTimeMillis()-time>MAX_WAIT_MILLIS){
		           throw new Exception("the arduino board does not seem to be responding (NO CUBE ACK)");
		          }
		          opcode=myPort.read();
		      }
		      return true;
		  }
		  
		  
		  public boolean waitForLayerACK()  throws Exception{
		    long time = System.currentTimeMillis();
		    boolean success=false;
		    boolean acked=false;
		    int opcode = myPort.read();
		    //println(opcode);
		      while(!acked){
		        if(System.currentTimeMillis()-time>MAX_WAIT_MILLIS){
		           throw new Exception("The arduino board does not seem to be responding (NO LAYER ACK)");
		          }
		        if(opcode==LAYER_ACK_SUCCESS){
		            acked=true;
		            success= true;
		        }else if(opcode==LAYER_ACK_FAIL){
		            acked=true;
		            success= false;
		            System.out.println("LAYER_ACK_FAIL");
		        };
		        opcode=myPort.read();
		      }
		      return success;
		  }
		  
		  public void SSend(int toSend){
		    myPort.write(toSend);
		    //println(myPort.read());
		      try {
				Thread.sleep(del);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }

		@Override
		public void killme() {
			this.killme=true;
			
		}
		}
