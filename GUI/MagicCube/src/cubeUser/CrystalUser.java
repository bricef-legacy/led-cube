package cubeUser;


import utils.CoreAPI;
import utils.EventCodes;
import core.AbstractCubeUser;

public class CrystalUser extends AbstractCubeUser implements EventCodes{

	public int currentState;
	
	
	/*
	 * Crystal User is implemented to draw crystal structures onto the cube
	 * @author: Samuel Dove
	 * @date: May 2009
	 */
	public CrystalUser(){
		
	}

	public void setState(int s){
		currentState = s;
	}
	
	
	@Override
	public void loop() {
		System.out.printf("Current State: %d \n", currentState);
		
		switch(currentState){
		
		case CRYSTAL_SC:
			simpleCubic(this.getCube());
			break;
		
		case CRYSTAL_FCC:
			smallFCC(this.getCube());
			break;
			
		case CRYSTAL_FCC_XL:
			largeFCC(this.getCube());
			break;
			
		case CRYSTAL_BCC:
			smallBCC(this.getCube());
			break;
			
		case CRYSTAL_BCC_XL:
			largeBCC(this.getCube());
			break;
		}
		}
	
	void simpleCubic(CoreAPI c){
		c.clearCube();
		int s = c.getSize();
		  
		   
	       
		  for(int z = 0; z< s; z++){
		   for(int y = 0; y<s; y++){
		    for(int x = 0; x < s; x++){
		      
		          //draws axis
		  
		/*[AXIS]     
		  if(y==0 && z ==0){
		       c.setLED(1, x, y, z);}
		  if(y==0 && x ==0){
		       c.setLED(1, x, y, z);}  
		  if(x==0 && z ==0){
		       c.setLED(1, x, y, z);}
		*/

		//draws lattice
		     
		     if( z==1 || z == 2 || z == 6 || z == 7){
		        if( x==1 || x == 2 || x == 6 || x == 7){
		          if(y==1 || y == 2 || y == 6 || y == 7){
		            c.setLED(2, x,y, z);
		          }}}
		     
		       }}}
	}

	void smallFCC(CoreAPI c){
		/*
		  * FCC structure method by Samuel Dove 03/2009
		  * for use with LedCube and LED classes by Edd Overton 03/2009
		  */
		 
		  c.clearCube();
		  int s = c.getSize();
		 
		 
		  for(int z=0; z<s; z++){
		   for(int y=0; y<s; y++){
		    for(int x=0; x<s; x++){
		  
		      //odd layer
		    if(z%2 != 0){
		    if(y%2 == 0){
		     if(x%2 == 0){c.setLED(3, x, y, z);}
		    }else{
		     if(x%2 != 0){c.setLED(3, x, y, z);}
		    }
		    }
		    //even layer
		    if(z%2 == 0){
		      if(y%2 ==0){
		       if(x%2 !=0){c.setLED(3, x, y, z);}
		      }else{
		        if(x%2 == 0){c.setLED(3, x, y, z);}
		      }

		    }}}}
		  /*
		  * END FCC Structure Code
		  */
		}

	void smallBCC(CoreAPI c){
		 
		 c.clearCube();
		 int s = c.getSize();
		 
		  for(int z = 0; z< s; z++){
		   for(int y = 0; y<s; y++){
		    for(int x = 0; x < s; x++){
		     
		      //odd layer
		      if(z%2 !=0){
		        if(y%2 !=0){
		           if(x%2 !=0){ 
		       c.setLED(3, x, y, z);
		       }}}else{
		        if(y%2 == 0){ 
		         if(x%2 == 0){
		          c.setLED(3, x, y, z); 
		         }}} 
		    }}}   
		}

	void largeBCC(CoreAPI c){
		    c.clearCube();
		    int s = c.getSize();
		    
		    for(int z = 0; z< s; z++){
		     for(int y = 0; y<s; y++){
		      for(int x = 0; x < s; x++){
		      
		      if( z==1 || z == 2 || z == 6 || z == 7){
		        if( x==1 || x == 2 || x == 6 || x == 7){
		          if(y==1 || y == 2 || y == 6 || y == 7){
		            c.setLED(3, x,y, z);
		          }
		        }
		      }
		      
		      if( z == 3 || z == 4 || z ==5){
		         if( x == 3 || x == 4 || x ==5){
		           if( y == 3 || y == 4 || y ==5){
		              c.setLED(3, x, y, z);
		      
		      
		        
		      }}}
	
		    }}}

		}

	void largeFCC(CoreAPI c){
		int s = c.getSize();
		   c.clearCube();
		    
		    for(int z = 0; z< s; z++){
		     for(int y = 0; y<s; y++){
		      for(int x = 0; x < s; x++){
		      
		          //draws axis
		        
		        //corner code
		      if( z==1 || z == 2 || z == 6 || z == 7){
		        if( x==1 || x == 2 || x == 6 || x == 7){
		          if(y==1 || y == 2 || y == 6 || y == 7){
		            c.setLED(3, x,y, z);
		          }}}
		      
		      //face code
		      int fc = 2;
		      //bottom
		      if ( z == 1 || z == 2){
		       if (x == 3 || x==4 || x==5){
		        if ( y ==3 || y==4 || y ==5){
		         c.setLED(fc, x, y, z); 
		        }}}
		        //top
		        if ( z == 6 || z == 7){
		       if (x == 3 || x==4 || x==5){
		        if ( y ==3 || y==4 || y ==5){
		         c.setLED(fc, x, y, z); 
		        }}}
		      //side
		      if ( x == 1 || x == 2){
		       if (z == 3 || z==4 || z==5){
		        if ( y ==3 || y==4 || y ==5){
		         c.setLED(fc, x, y, z); 
		        }}}
		      
		      if ( x == 6 || x == 7){
		       if (z == 3 || z==4 || z==5){
		        if ( y ==3 || y==4 || y ==5){
		         c.setLED(fc, x, y, z); 
		        }}}
		        
		        if ( y == 1 || y == 2){
		       if (z == 3 || z==4 || z==5){
		        if ( x ==3 || x==4 || x ==5){
		         c.setLED(fc, x, y, z); 
		        }}}
		        
		        if ( y == 6 || y == 7){
		       if (z == 3 || z==4 || z==5){
		        if ( x ==3 || x==4 || x ==5){
		         c.setLED(fc, x, y, z); 
		        }}}
		      
		      
		      
		      }}}
		  
		}
	
}

	



