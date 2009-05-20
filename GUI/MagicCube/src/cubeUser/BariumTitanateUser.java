package cubeUser;

import utils.CoreAPI;
import core.AbstractCubeUser;

/**
 * Class to display the motions of Barium Titanate.
 * @author Chris Ryan
 * @author Brice Fernandes
 *
 */
public class BariumTitanateUser extends AbstractCubeUser {

	
	@Override
	public void loop() {
		int sleeper = 200;
		try{
			int stage = -1;
			this.BariumTitanate(cube, cube.getSize(), stage);
			Thread.sleep(sleeper);
			stage = -2;
			this.BariumTitanate(cube, cube.getSize(), stage);
			Thread.sleep(sleeper);
			stage = -1;
			this.BariumTitanate(cube, cube.getSize(), stage);
			Thread.sleep(sleeper);
			stage = 0;
			this.BariumTitanate(cube, cube.getSize(), stage);
			Thread.sleep(sleeper);
		}catch(InterruptedException e){
			System.out.println("[BARIUM]: Error: caughte InterruptedException while running. Comitting suicide.");
			this.killme();
		}
		
	}
	/**
	 * @author Chris Ryan (wrote main logic)
	 * @AUTHOR Brice Fernandes (added up-down movement)
	 * @param c the cube (CoreAPI)
	 * @param s the size of said cube
	 */
	void BariumTitanate(CoreAPI c, int s, int stage){
		 
		 c.clearCube();
		  
		   
		       
		  for(int z = 0; z< s; z++){
		   for(int y = 0; y<s; y++){
		    for(int x = 0; x < s; x++){
		      
		      //draws barium atoms
		      if(z==0 ||z==1 ||z==6 ||z==7){
		        if(x==0||x==1||x==6||x==7) {
		          if(y==0||y==1||y==6||y==7){
		            
		            c.setLED(2,x,y,z);
		                }}}
		   
		      //draws oxygen atoms
		            //top and bottom oxygen
		      if(z==0||z==1||z==6||z==7){
		        if(x==3||x==4){
		          if(y==3||y==4){
		            
		            c.setLED(3,x,y,z);
		          }}}
		      
		      
		      //sides of oxygen
		      
		      if(z==3||z==4){
		        
		        
		        if(x==0||x==1||x==6||x==7){
		          if(y==3||y==4){
		           
		             c.setLED(3,x,y,z);
		          }}
		          
		        if(x==3||x==4){
		          if(y==0||y==1||y==6||y==7){
		            
		            c.setLED(3,x,y,z);
		          }}
		      }
		      
		          
		          //draws titanium
		          if(z==4+stage||z==5+stage){
		            if(y==3||y==4){
		              if(x==3||x==4){
		                
		                c.setLED(1,x,y,z);
		              }}}
		            
		          
		          
		      
		    }}}
		    
		}

}
