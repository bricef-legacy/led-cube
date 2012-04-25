package cubeUser;

import utils.CoreAPI;
import core.AbstractCubeUser;

public class IsingUser extends AbstractCubeUser{
	
	/**
	 * 3D Ising system
	 * @author:Michael Overington
	 */
	class IsingSystem{
		isingparticle[][][] particles = new isingparticle[ISINGSIZE][ISINGSIZE][ISINGSIZE];
		
		final static int ISINGSIZE=8;
		final static int NUMPARTICLES=512;
		
		private int currentx;
		private int currenty;
		private int currentz;
		private int xplus;
		private int xminus;
		private int yplus;
		private int yminus;
		private int zplus;
		private int zminus;

		/**
		 * Ising system constructor
		 */
		public IsingSystem(){
			for (int i=0; i<ISINGSIZE; i++){
				for (int j=0; j<ISINGSIZE; j++){
					for (int k=0; k<ISINGSIZE; k++){
						   particles[i][j][k] = new isingparticle();
					}
				}
			}
		};
		
		//getters:
		int getx(){return(currentx);} ;
		int gety(){return(currenty);} ;
		int getz(){return(currentz);}  ; 
		
		void findneighbours(){
			xminus=currentx-1;
			xplus=currentx+1;
			yminus=currenty-1;
			yplus=currenty+1;
			zminus=currentz-1;
			zplus=currentz+1;             
			if (xplus==ISINGSIZE){xplus=0;}
			if (xminus==-1){xminus=ISINGSIZE-1;}
			if (yplus==ISINGSIZE){yplus=0;}
			if (yminus==-1){yminus=ISINGSIZE-1;}
			if (zplus==ISINGSIZE){zplus=0;}
			if (zminus==-1){zminus=ISINGSIZE-1;}
		};
		 
		void choose(){
			currenty=(int)(Math.random()*ISINGSIZE);
			currentx=(int)(Math.random()*ISINGSIZE);
			currentz=(int)(Math.random()*ISINGSIZE);
		};
		  
		void perturb(){
			particles[currentx][currenty][currentz].flip();
		}
			  
		public int givespin(int a,int b,int c){
			return (particles[a][b][c].spinvalue());
		};
		   
		int getspin(){
			return (particles[currentx][currenty][currentz].spinvalue());
		};
			
		float localenergy(){
			float le;                       
			findneighbours();
			le=(particles[currentx][currenty][zplus].spinvalue())+(particles[currentx][currenty][zminus].spinvalue())+(particles[currentx][yplus][currentz].spinvalue())+(particles[currentx][yminus][currentz].spinvalue())+(particles[xplus][currenty][currentz].spinvalue())+(particles[xminus][currenty][currentz].spinvalue());
			le=-le*(particles[currentx][currenty][currentz].spinvalue());
			return(le);
		};
	   
		float totalenergy(){
			float energy=0.0f;
			for(currentx=0;currentx<ISINGSIZE;currentx++){
				for(currenty=0;currenty<ISINGSIZE;currenty++){
					for(currentz=0;currentz<ISINGSIZE;currentz++){
						energy=energy+(localenergy());
					}
				}
			}
			return(energy);
		};
		  
		float magnetisation(){
			float magn=0;
			currentx=0;
			currenty=0;
			currentz=0;
			for(currentx=0;currentx<ISINGSIZE;currentx++){
				for(currenty=0;currenty<ISINGSIZE;currenty++){
					for(currentz=0;currentz<ISINGSIZE;currentz++){
						magn=magn+particles[currentx][currenty][currentz].spinvalue();
					}
				}
			}
			return(magn/NUMPARTICLES);
		}
		   
		//draws whole isingmodel
		void drawModel(CoreAPI cube){
			for(int z = 0; z < ISINGSIZE; z++){
				for(int y = 0; y < ISINGSIZE; y++){
					for(int x = 0; x < ISINGSIZE; x++){ 
						if(particles[x][y][z].spinvalue()==1){
							cube.setLED(2, x, y, z);
						}
						if(particles[currentx][currenty][currentz].spinvalue()==-1){
							cube.setLED(1, currentx, currenty, currentz);
						}  
					}
				}
			}
		}
		   
		//draws current particle
		void drawParticle(CoreAPI c){
			if(particles[currentx][currenty][currentz].spinvalue()==1){
				c.setLED(2, currentx, currenty, currentz); 
			}
			if(particles[currentx][currenty][currentz].spinvalue()==-1){
				c.setLED(1, currentx, currenty, currentz);
			}
		}
	}

	/**
	 * Ising particle class
	 * @author: Michael Overington
	 */
	class isingparticle{
		private int spin=1;
		isingparticle(){
			spin=1;
		}
		void flip(){
			spin=-spin;
		}
		int spinvalue(){
			return(spin);
		}  
	}

	private float temp;
	private float de;
	IsingSystem sys;
	
	public IsingUser(){
		this.sys=new IsingSystem();
		temp = 0;
		de = 0;
		//draw initial configuration
		//sys.drawModel(this.cube);
	}
	
	public void setTemp(float temp){
		this.temp=temp;
		System.out.printf("[ISING]: current temperature: %f\n", temp);
	}
	
	@Override
	public void loop(){
		sys.choose();
		sys.perturb();
		//check if new config is retained
		de=-2*sys.localenergy();
		if ((de<=0)||((de>0)&&(Math.exp(-de/temp))>(Math.random()))){
			sys.perturb();
		}else{System.out.println("change");}
		sys.drawParticle(this.getCube());
	}
}
