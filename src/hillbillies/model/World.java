package hillbillies.model;


import hillbillies.part2.listener.TerrainChangeListener;

public class World {
	
	public World(int[][][] terraintypes, TerrainChangeListener tcl){
		// for pos
		// 		make new cube(x, y, z, type)
		//	! change value to type 
		this.nbXCubes = terraintypes[0].length; 
		this.nbYCubes = terraintypes[1].length;
		this.nbZCubes = terraintypes[2].length;
		
		this.cubes = new Cube[this.getNbCubesX()][this.getNbCubesY()][this.getNbCubesZ()];
		for (int i = 0 ; i < terraintypes[0].length ; i++){
			for (int j = 0 ; j < terraintypes[0].length  ; j++){
				for (int k = 0 ; k< terraintypes[0].length ; k++){
					CubeType type = CubeType.getCubeTypeOfValue(terraintypes[i][j][k]);
					cubes[i][j][k] = new Cube(this, i,j,k, type);
				}
			}
		}
	}
	
	public Cube getCubeAtPos(int x, int y, int z){
		return cubes[x][y][z];
	}
	
	public int getCubeTypeOf(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).getType().getValue();
	}

	
	public int getNbCubesX(){
		return this.nbXCubes;
	}
	
	public int getNbCubesY(){
		return this.nbYCubes;
	}
	
	public int getNbCubesZ(){
		return this.nbZCubes;
	}
	private Cube[][][] cubes;
	
	private final int nbXCubes;
	private final int nbYCubes; 
	private final int nbZCubes;
}
