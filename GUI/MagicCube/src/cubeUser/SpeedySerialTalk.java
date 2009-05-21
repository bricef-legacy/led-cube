package cubeUser;

import processing.core.PApplet;
import processing.serial.Serial;
import utils.ColorCodes;
import utils.OpCodes;
import utils.Statistics;
import core.AbstractCubeUser;


public class SpeedySerialTalk extends AbstractCubeUser implements OpCodes, ColorCodes{
	public static final long MAX_WAIT_MILLIS = 1000;
	int comport;
	Serial myPort;  // Create object from Serial class
	PApplet parentapp;
	int val;        // Data received from the serial port
	int returned=0;
	byte[][] doubleBuffer;
	Statistics stats=new Statistics();
	long before;
	long time;
	boolean acked;
	int[][][] mycube;
    byte temp;
	
	public SpeedySerialTalk(String portName, PApplet parent){
		this.setName("Serial");
		myPort = new Serial(parent, portName, 115200);//must match the baud rate on the arduino.
		doubleBuffer=new byte[8][32];
	}
	
		
	@Override
	public void loop(){
		acked=false;
		mycube=this.cube.readCube();
		
	    while(returned!=INIT_OK){
	    	myPort.write(INIT_IS_READY);
	        returned=myPort.read();
	    }
	    returned = 0;
		try{
			
		    
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
		        doubleBuffer[z][7-x] = temp;


		        //repeating abouve for red
		        temp = 0x00;
		        for (y=3; y<8; y++)
		          if ( (mycube[x][y][z]== RED) || (mycube[x][y][z] == ORANGE)){ //this time we are setting the RED led.
		            temp |= ( 0x01 << (y-4) );
		        }
		        doubleBuffer[z][8+7-x] = temp; // we need to manipulate the array 8 elements further along.
		        
		        //now we shall do the first half (y:0-4)
		        temp = 0x00;
		        for (y=0; y<4; y++)
		          if ( (mycube[x][y][z] == GREEN) || (mycube[x][y][z] == ORANGE)){
		            temp |= ( 0x01 << (y) ); //no need to subtract 4 now, scince y ranges from 0-3
		          }
		        doubleBuffer[z][16+7-x] = temp; //we now need to write into the array between 15-23
		        
		        //now doing the first red half.
		        temp = 0x00;
		        for (y=0; y<4; y++)
		          if ( (mycube[x][y][z] == RED) || (mycube[x][y][z] == ORANGE)){
		            temp |= ( 0x01 << (y) );
		          }
		        doubleBuffer[z][24+7-x] = temp;
		      }
		    }
			
			
			
			
			myPort.write(BEGIN_CUBE);  
			for(int j=0; j<doubleBuffer.length;j++){
		        myPort.write(BEGIN_LAYER);
		        myPort.write(doubleBuffer[j]);
		        myPort.write(END_LAYER);
		        int opcode = myPort.read();
		        time = System.currentTimeMillis();
		        while(!acked){
					if(System.currentTimeMillis()-time>MAX_WAIT_MILLIS){
						throw new Exception("[SERIAL]: The arduino board does not seem to be responding (NO LAYER ACK)");
					}
					if(opcode==LAYER_ACK_SUCCESS){
						acked=true;
					}
					opcode=myPort.read();
				}
	        }
	        myPort.write(END_CUBE);
	        
	        
	        time = System.currentTimeMillis();
			while(myPort.read()!=CUBE_SUCCESS){
			    if(System.currentTimeMillis()-time>MAX_WAIT_MILLIS){
			    	throw new Exception("[SERIAL]: the arduino board does not seem to be responding (NO CUBE ACK)");
			    }
		    }
	   }catch(Exception e){
	   } 
	}
}
