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
	
	public int getXPosition(){
		return this.xPosition;
	}
	public int getYPosition(){
		return this.yPosition;
	}
	public int getZPosition(){
		return this.zPosition;
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
					result.add(this.world.getCubeAtPos(this.getXPosition()+(i-1), this.getYPosition()+(j-1), this.getZPosition()+(k-1)));
				}
			}
		}
		result.remove(this);
		return result;
	}
	
	public boolean hasSolidNeighbor(){
		for (Cube cube : this.getSurroundingCubes()){
			if (!cube.isPassableType())
				return true;
		}
		return false;
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
	}
			
	private final World world;
	private final int xPosition;
	private final int yPosition;
	private final int zPosition;
	private CubeType cubetype;

}
