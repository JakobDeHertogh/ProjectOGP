package hillbillies.model.objects;

import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import ogp.framework.util.ModelException;

/**
 * A class of game Boulders. Each Boulder has a position and a weight. Boulders can move and fall.
 * @invar	The starting value for the weight of every Boulder must be valid.
 * 			| isValidWeight(weight)
 * 
 * @version 0.51
 * @author Kristof Van Cappellen
 * @author Jakob De Herthogh
 *
 */
public class Boulder {
	
	/**
	 * 
	 * @param world
	 * @param startPosition
	 * @throws ModelException If the given weight is not valid.
	 */
	public Boulder( World world, int[] startPosition) throws ModelException{
		this.world = world;
		
		int weight = (int)((Math.random()*this.weightRange)+this.minWeight);
		this.setWeight(weight);
		
		double[] pos = new double[startPosition.length];
		for (int i = 0;i<startPosition.length; i++){
			pos[i] = startPosition[i];
		}
		this.setPosition(pos);
		this.fallingTo = this.getzPosition();
		this.isTerminated = false;
	}
	
	/**
	 * Returns the coordinate of the Cube which is occupied by this Boulder.
	 */
	public int[] getCubeCoordinate (){
		int[] cubecoordinate = new int[3];
		cubecoordinate[0] = (int)Math.floor(this.position[0]);
		cubecoordinate[1] = (int)Math.floor(this.position[1]);
		cubecoordinate[2] = (int)Math.floor(this.position[2]);
		return cubecoordinate;
	}
	
	
	public Cube occupiesCube(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	public Cube getCubeUnder(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]-1);
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public boolean isValidPosition(int [] position){
		if ((this.occupiesCube().isPassableType())&&( ! this.getCubeUnder().isPassableType()))
			return true;
		return false;
	}
	
	public double[] getPosition(){
		return this.position;
	}


	public void setPosition(double[] newPosition){
		this.position = newPosition;
	}
	
	public double getzPosition() {
		return this.position[2];
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public void setWeight(int weight) throws ModelException{
		this.weight = weight;
		
	}
	
	/**
	 * Adapts the Boulder's current position on whether it is falling or being carried.
	 * @param dt
	 */
	public void advanceTime(double dt){
		if (this.isCarriedBy != null)
			this.position = this.isCarriedBy.getPosition();
		else if (this.fallingTo == this.getzPosition()){
			if (! this.isValidPosition(this.getCubeCoordinate()))
				this.fallingTo = this.getzPosition() -1;
		}
		else {
			if (this.fallingTo - this.getzPosition() <= dt*this.fallSpeed)
				this.position[2] = this.fallingTo;
			else 
				this.position[2] += dt*this.fallSpeed;
		}
	}
	
	/**
	 * Removes this Log from the game world.
	 */
	public void terminate() {
		this.isTerminated = true;
		this.world.removeBoulder(this);
	}
	/**
	 * Checks whether this Boulder is terminated.
	 */
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	private World world;
	private double[] position;

	private int weight;
	private double fallingTo;
	private int fallSpeed = -3;
	private boolean isTerminated;

	private final int minWeight = 10;
	private final int maxWeight = 50;
	private final int weightRange = this.maxWeight - this.minWeight;
	
	Unit isCarriedBy = null;
}
