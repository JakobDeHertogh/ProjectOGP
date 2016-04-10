package hillbillies.model;



import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ogp.framework.util.ModelException;

public class Cube {
	
	public Cube(World World, int xPosition, int yPosition, int zPosition, CubeType type){
		this.world = World;
		this.xPosition = xPosition;
		this.yPosition = yPosition; 
		this.zPosition = zPosition;
		this.cubetype = type;
		int[] pos = new int[]{this.xPosition, this.yPosition, this.zPosition};
		double[]cubeCenter = new double[]{this.xPosition + 0.5, this.yPosition + 0.5, this.zPosition + 0.5};
		this.Position = pos ;
		this.cubeCenter = cubeCenter;
						
	}
	
	public Set<Log> isOccupiedByLogs(){
		Set <Log> result = new HashSet<Log>();
		for (Log i : this.world.getLogs()){
			if (i.getPosition() == new double[]{this.getXPosition(), this.getYPosition(), this.getZPosition()})
				result.add(i);
		}
		return result;
	}
	public Log randomLog(){
		int size = this.isOccupiedByLogs().size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (Log log : this.isOccupiedByLogs()){
			if (i == item)
				return log;
			i+=1;
		}
		return null;
	}
	
	public Boulder randomBoulder(){
		int size = this.isOccupiedByBoulders().size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (Boulder boulder : this.isOccupiedByBoulders()){
			if (i == item)
				return boulder;
			i+=1;
		}
		return null;
	}
	
	public Set<Boulder> isOccupiedByBoulders(){
		Set <Boulder> result = new HashSet<Boulder>();
		for (Boulder i : this.world.getBoulders()){
			if (i.getPosition() == new double[]{this.getXPosition(), this.getYPosition(), this.getZPosition()})
				result.add(i);
		}
		return result;
	}
	
	public Set<Unit> isOccupiedByUnits(){
		Set <Unit> result = new HashSet<Unit>();
		for (Unit i : this.world.getActiveUnits()){
			if (i.getPosition() == new double[]{this.getXPosition(), this.getYPosition(), this.getZPosition()})
				result.add(i);
		}
		return result;
	}
	
	public boolean isValidCube(){
		if ((this.isPassableType())&&(this.hasSolidNeighbor()))
			return true;
		return false;
	}
	
	public int getXPosition(){
		return this.xPosition;
	}
	public int getYPosition(){
		return this.yPosition;
	}
	public int getZPosition(){
		return this.zPosition;
	}
	public int[] getPosition(){
		return this.Position;
	}
	public double[] getCubeCenter(){
		return this.cubeCenter;
	}
	
	public CubeType getType(){
		return this.cubetype;
	}
	
	public void setCubeType(CubeType type){
		this.cubetype = type;
	}
	
	public boolean isPassableType(){
		return this.getType().isPassable();
	}
	
	
	public Set<Cube> getSurroundingCubes() throws IndexOutOfBoundsException{
		Set<Cube> result = new HashSet<Cube>();
		for (int i = 0 ; i <= 2 ; i++){
			for (int j = 0; j <= 2; j++){
				for (int k = 0 ; k <= 2 ; k++){
					try {
						result.add(this.world.getCubeAtPos(this.getXPosition()+(i-1), this.getYPosition()+(j-1), this.getZPosition()+(k-1)));
					} catch (IndexOutOfBoundsException e) {
						result.addAll(null);
					}
				}
			}
		}
		result.remove(this);
		return result;
	}
	
	public boolean hasSolidNeighbor(){
		try {
			for (Cube cube : this.getSurroundingCubes()){
				if (!cube.isPassableType())
					return true;
			}
			return false;
		} catch (NullPointerException e) {
			return true;
		}
	}
	
	
	public void caveIn() throws ModelException {
		CubeType prevCubeType = this.getType();
		this.setCubeType(CubeType.AIR);
		double P = Math.random();
		double PLogBoulder = 0.25;
		if ((P <= PLogBoulder)&&(prevCubeType == CubeType.WOOD)){
			this.world.addLog(new int[]{this.getXPosition(), this.getYPosition(), this.getZPosition()});
		} 
		if ((P <= PLogBoulder) && (prevCubeType == CubeType.ROCK)){
			this.world.addBoulder(new int[]{this.getXPosition(),  this.getYPosition(), this.getZPosition()});
		}
		if (this.isValidCube())
			this.world.viableSpawnCubes.add(this);
	}
			
	private final World world;
	private final int xPosition;
	private final int yPosition;
	private final int zPosition;
	private final int[] Position;
	private final double[] cubeCenter;
	private CubeType cubetype;

}
