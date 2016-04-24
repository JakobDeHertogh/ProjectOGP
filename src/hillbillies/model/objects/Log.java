package hillbillies.model.objects;

import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import ogp.framework.util.ModelException;
/**
 * A class of game Logs. Each Log has a position and a weight. Logs can move and fall.
 * @invar	The starting value for the weight of every Log must be valid.
 * 			| isValidWeight(weight)
 * 
 * @version 0.51
 * @author Kristof Van Cappellen
 * @author Jakob De Herthogh
 *
 */
public class Log {
	
	public Log (World world, int[] startPosition) throws ModelException{
		this.world = world;
		
		int weight = (int)((Math.random()*this.weightRange)+this.minWeight);
		this.setWeight(weight);
		
		double[] pos = new double[startPosition.length];
		for (int i = 0;i<startPosition.length; i++){
			pos[i] = startPosition[i];
		}
		this.setPosition(pos);
		this.fallingTo = this.getzPosition();
	}
	
	/**
	 * Returns the coordinate of the Cube which is occupied by this Log.
	 */
	public int[] getCubeCoordinate (){
		int[] cubecoordinate = new int[3];
		cubecoordinate[0] = (int)Math.floor(this.position[0]);
		cubecoordinate[1] = (int)Math.floor(this.position[1]);
		cubecoordinate[2] = (int)Math.floor(this.position[2]);
		return cubecoordinate;
	}
	
	/**
	 * Returns the Cube which is occupied by this Log.
	 */
	public Cube occupiesCube(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	
	/**
	 * Returns the Cube on top of which this Log is sitting.
	 */
	public Cube getCubeUnder(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]-1);
	}
	
	/**
	 * Returns the World which the Log is situated in.
	 */
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Checks whether the given position is a valid position for a Log.
	 * @param position
	 * @return true if and only if the Cube at the given position is of a passable type, 
	 * 			and the Cube under that Cube is of unpassable type.
	 * 			Else returns false. 
	 */
	public boolean isValidPosition(int [] position){
		if ((this.occupiesCube().isPassableType())&&( ! this.getCubeUnder().isPassableType()))
			return true;
		return false;
	}
	
	/**
	 * 
	 * @return The position of this Log.
	 */
	public double[] getPosition(){
		return this.position;
	}

	/**
	 * Sets the position of the Log to the given position.
	 * @param newPosition
	 *
	 */
	public void setPosition(double[] newPosition){
		this.position = newPosition;
	}
	
	/**
	 * 
	 * @return The z-value of the Log's position.
	 */
	public double getzPosition() {
		return this.position[2];
	}
	
	/**
	 * Checks whether the given weight is a valid option for a Log's weight.
	 * @param weight
	 * @return true if the given weight lies between the given lowerlimit and upperlimit.
	 * @return false if the given weight does not lie between the given lowerlimit and upperlimit.
	 */
	public boolean isValidWeight(int weight){
		return ((weight>lowerLimit)&&(weight<upperLimit));
	}
	/**
	 * 
	 * @return The weight of this Log.
	 */
	public int getWeight() {
		return this.weight;
	}
	/**
	 * Sets the weight of this Log to the given weight.
	 * @param weight
	 * @throws ModelException if the given weight is not a valid weight for a Log.
	 */
	public void setWeight(int weight) throws ModelException{
		if (!isValidWeight(weight)){
			throw new ModelException("Invalid weight");
		}
		this.weight = weight;
		
	}
	/**
	 * Adapts the Log's current position on whether it is falling or being carried.
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
	 * Removes the Log from the gameworld.
	 */
	public void terminate() {
		this.isTerminated = true;
		this.world.removeLog(this);
	}
	
	/**
	 * Checks whether this Log is terminated.
	 */
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	
	private World world;
	private double[] position;
	

	private int weight;
	private int lowerLimit = 10;
	private int upperLimit = 50;
	private double fallingTo;
	private int fallSpeed = -3;
	
	private final int minWeight = 10;
	private final int maxWeight = 50;
	private final int weightRange = this.maxWeight - this.minWeight;
	
	Unit isCarriedBy = null;
	
	private boolean isTerminated = false;
}
