package cubeUser;

import processing.core.PApplet;
import processing.serial.Serial;
import utils.ColorCodes;
import utils.OpCodes;
import utils.Statistics;
import core.AbstractCubeUser;


public class SerialTalk extends AbstractCubeUser implements OpCodes, ColorCodes{
	public static final long MAX_WAIT_MILLIS = 1000;
	int comport;
	Serial myPort;  // Create object from Serial class
	PApplet parentapp;
	int val;        // Data received from the serial port
	int returned=0;
	byte[][] doubleBuffer;
	Statistics stats=new Statistics();
	
	public SerialTalk(String portName, PApplet parent){
		this.setName("Serial");
		System.out.println(Serial.list()[0]);
		try{
		myPort = new Serial(parent, portName, 9600);//must match the baud rate on the arduino.
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
		
	@Override
	public void loop(){
				writeCube();
	}
	
	/**
	 * Converting the data stored in the computer to draw the cube, to a ByteStream which can be written onto the cube.
	 * Takes care of the hardware splicing between colours and halves, as well as conversion into the appropriate byte format.
	 * Lowest level method in entire project. depends completely on hardware used, and would have to be rewritten for the smallest
	 * hardware change.
	 * @author Sam
	 * @author Ed
	 * @return A two dimensional byte array ready to be send as-is to the serial port. 
	 */
	public byte[][] getByteStream(){ 
	    
	    byte[][] stream = new byte[8][32];
	    int[][][] mycube=this.getCube().readCube();
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
	    
	    /*
	    for (int i=0; i<8; i++){
	        System.out.print ("Current Layer: ");
	        System.out.println (i);
	        for (int j=0; j<32; j++){
	        	System.out.print(j);
	        	System.out.print(": ");
	        	System.out.println (processing.core.PApplet.binary(stream[i][j],8));
	        }
	      }
	    */
	    
	    return stream;
	  }
		  
	/**
	 * general writeCube method.<p>
	 * AWAITING REWRITE AS STATE MACHINE
	 * @author Brice
	 */
	public void writeCube(){
		long before=System.currentTimeMillis();
		//System.out.println("[SERIAL]: Checking for readiness: ");
	      while(returned!=INIT_OK){
	    	  SSend(INIT_IS_READY);
	        returned=myPort.read();
	        //System.out.println(returned);
	      }
	      returned = 0;
	      //System.out.println("[SERIAL]: Arduino listening.");
		try{
	  //System.out.println("[SERIAL]: Begining cube transmission.");
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
	          //myPort.write(doubleBuffer[j]);
	          for (int i=0; i<32; i++){
	        	  SSend(doubleBuffer[j][i]);
	          }
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
	          System.out.printf("[SERIAL]: Cube sent successfully in: %dms (%dms avg)\n\n", foo, stats.average());
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
		    	throw new Exception("[SERIAL]: the arduino board does not seem to be responding (NO CUBE ACK)");
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
				throw new Exception("[SERIAL]: The arduino board does not seem to be responding (NO LAYER ACK)");
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
		//System.out.println(processing.core.PApplet.binary(toSend,8));
		try{ //TODO Fix ifs...
			//if(myPort.available()==1){
				myPort.write(toSend);
			//}else{
				//throw new Exception("port not available");
			//}
		}catch(Exception e2){
			System.out.println("[SERIAL]: Exception caught. (Arduino not plugged in?). Aborting Serial write attempt. Commiting suicide.");
			e2.printStackTrace();
			this.killme();
			try{
				join();
			}catch (InterruptedException e3) {
				System.out.println("[SERIAL]: Thread interrupted while trying to commit suicide.");
				System.exit(1);
			}
		}
	}
}
