package cubeUser;

import utils.CoreAPI;
import core.AbstractCubeUser;

public class MillerUser extends AbstractCubeUser {
	int i, j, k;
	public MillerUser(int tempi, int tempj, int tempk) {
		this.setName("Miller");
		this.i=tempi;
		this.j=tempj;
		this.k=tempk;
	}

	@Override
	public void loop() {
		miller(this.getCube(),this.getCube().getSize(), j, i, k);
		killme();
	}

	/*
	  * Miller method by Chris Ryan 03/2009
	  * for use with LedCube and LED classes by Edd Overton 03/2009
	  */
	void miller(CoreAPI c, int s, int h, int k, int l){
	  
	  c.clearCube();
	  
	  for(int z = 0; z< s; z++){
	        for(int y = 0; y<s; y++){
	         for(int x = 0; x < s; x++){
	           for(int d = -10; d<10; d++){
	             
	/*[AXIS]             //axis
	             if(y==0 && z ==0){
	       c.setLED(1, x, y, z);}
	  if(y==0 && x ==0){
	       c.setLED(1, x, y, z);}  
	  if(x==0 && z ==0){
	       c.setLED(1, x, y, z);}
	       
	       //end axis
	*/       
	       //draw plane..
	         if(l!=0){  
	    if(z==((-1*x*h/l)-(y*k/l)+(d*7/l)))
	       {
	         c.setLED(3,x,y,z);
	       }
	         }
	       //end of draw plane
	       if(l==0 && h!=0){
	    if(x==((-1*y*k/h)+(d*7)))
	       {
	         c.setLED(3,x,y,z);
	       }
	       }
	       if(l==0 && h==0){
	    if(y==(d*7/k))
	       {
	         c.setLED(3,x,y,z);
	       }
	       }
	      }
	     }
	    }
	   }
	}
}
