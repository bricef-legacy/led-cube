package core;

public class MagicCubeData implements CoreAPI{
	private int[][][] cubeRep;
	private int cubesize;
	
	public MagicCubeData(int cubesize){
		this.cubeRep =new int[cubesize][cubesize][cubesize];
		this.cubesize=cubesize;
		this.clearCube();
	}

	@Override
	public synchronized void clearCube() {
		
		
		
		
		for(int x=0; x<this.cubesize;x++){
			for(int y=0; y<this.cubesize;y++){
				for(int z=0; z<this.cubesize;z++){
					this.cubeRep[x][y][z]=0;
				}
			}
		}
		
	}

	@Override
	public synchronized int[][][] readCube() {
		return cubeRep;
	}

	@Override
	public synchronized void setCube(int[][][] status) {
		this.cubeRep=status;
		
	}

	@Override
	public synchronized void setLED(int status, int x, int y, int z) {
		this.cubeRep[x][y][z]=status;
		
	}

	@Override
	public synchronized void setPlaneXY(int[][] status, int layer) {
	for (int x = 0; x<this.cubesize; x++){
		  for (int y = 0; y<this.cubesize; y++){
			  this.cubeRep[x][y][layer]=status[x][y];
		  }
	    }
	}

	@Override
	public synchronized void setPlaneXZ(int[][] status, int layer) {
		for (int x = 0; x<this.cubesize; x++){
		      for (int z = 0; z<this.cubesize; z++){
		    	  this.cubeRep[x][layer][z]=status[x][z];
		      }
		    }
		
	}

	@Override
	public synchronized void setPlaneYZ(int[][] status, int layer) {
		for (int y = 0; y<this.cubesize; y++){
		      for (int z = 0; z<this.cubesize; z++){
		        this.cubeRep[layer][y][z]=status[y][z];
		      }
		    }
		
	}

	@Override
	public int getSize() {
		return this.cubesize;
	}
	
	
	
	
}
