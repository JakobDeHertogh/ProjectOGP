package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
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
	/**
	 * @param world	The World this Log is set in.
	 * @param startPosition	The position this Log starts on.
	 */
	public Log (World world, int[] startPosition) throws ModelException{
		this.world = world;
		
		int weight = (int)((Math.random()*this.weightRange)+this.minWeight);
		this.setWeight(weight);
		
		double[] pos = new double[]{startPosition[0]+0.5, startPosition[1]+0.5, startPosition[2]};
		this.setPosition(pos);
		this.fallingTo = this.getzPosition();
		
		this.setCubeCoordinate();
	}
	
	/**
	 * Set the coordinate of the Cube this Log is Situated in.
	 * @post the CubeCoordinate is set to the rounded down coordinates of the Log.
	 */
	public void setCubeCoordinate(){
		int[] cubecoordinate = new int[]{(int)Math.floor(this.position[0]),(int)Math.floor(this.position[1]),
				(int)Math.floor(this.position[2])};
		this.CubeCoordinate = cubecoordinate;
	}
	
	
	/**
	 * Returns the coordinate of the Cube which is occupied by this Log.
	 */
	@Basic
	public int[] getCubeCoordinate(){
		return this.CubeCoordinate;
	}
	
	/**
	 * Returns the Cube which is occupied by this Log.
	 */
	public Cube occupiesCube(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	
	/**
	 * Returns the cube below the occupied cube. 
	 * @return	returns the Cube at the z-position under this Log.
	 * 			|result == world.getCubeAtPos(getCubeCoordinate()[0], getCubeCoordinate()[1], getCubeCoordinate()[2])
	 */
	public Cube getCubeUnder(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]-1);
	}
	
	/**
	 * Returns the World in which the Log is situated.
	 */
	@Basic
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
	 * Return the position of this Log.
	 */
	@Basic
	public double[] getPosition(){
		return this.position;
	}

	/**
	 * Sets the position of the Log to the given position.
	 * @param	newPosition	The position the Log is to be set on.
	 * @post	This Log's position is set to the given position
	 * 			|new.position == newPosition
	 */
	public void setPosition(double[] newPosition){
		this.position = newPosition;
	}
	
	/**
	 * 
	 * Returns the z-value of the Log's position.
	 */
	public double getzPosition() {
		return this.position[2];
	}

	/**
	 * Return the weight of this Log.
	 */
	@Basic
	public int getWeight() {
		return this.weight;
	}
	/**
	 * Sets the weight of this Log to the given weight.
	 * @param weight
	 * @throws ModelException if the given weight is not a valid weight for a Log.
	 */
	public void setWeight(int weight) throws ModelException{
		this.weight = weight;
		
	}
	/**
	 * Adapts the Log's current position on whether it is falling or being carried.
	 * @param dt The time step the Scheduler is to be advanced with.
	 * @effect If this Log is being carried by a Unit, this Log's position will be adjusted to that Unit's position.
	 * @post If this Log has reached its falling destination and its occupying Cube is not a valid Cube, this Log will fall to the Log below.
	 * @post If the z-distance between the Log's position and its falling destination is smaller than dt, the Log's position is set to 
	 * 		 its falling destination.
	 * @post If the z-distance between the Log's position and its falling destination is greater than dt, the Log's z-coordinate is set to 
	 * 			the current z-coordinate increased with the product of this Log's falling speed and the time step dt.
	 */
	public void advanceTime(double dt){
		if (this.isCarriedBy != null)
			this.setPosition(this.isCarriedBy.getPosition());
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
	 * @post	This Log is terminated.
	 * @effect	This Log is removed from its World.
	 */
	public void terminate() {
		this.isTerminated = true;
		this.world.removeLog(this);
	}
	
	/**
	 * Checks whether this Log is terminated.
	 */
	@Basic
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Variable registering the World this Log is in.
	 */
	private World world;
	/**
	 * Variable registering this Log's current position.
	 */
	private double[] position;
	/**
	 * Variable registering the coordinates of the Cube this Log is situated in.
	 */
	private int[] CubeCoordinate;
	/**
	 * Variable registering this Log's weight.
	 */
	private int weight;
	/**
	 * Position this Log is falling to.
	 */
	private double fallingTo;
	/**
	 * Constant registering the z-component of this Log's falling speed.
	 */
	private static int fallSpeed = -3;
	/**
	 * Constant registering the minimum weight of a Log.
	 */
	private final int minWeight = 10;
	/**
	 * Constant registering the maximum weight of a Log.
	 */
	private final int maxWeight = 50;
	/**
	 * Constant registering the range for a Log's weight.
	 */
	private final int weightRange = this.maxWeight - this.minWeight;
	
	/**
	 * Variable registering the Unit that is carrying this Log.
	 */
	public Unit isCarriedBy = null;
	/**
	 * Variable registering whether or not this Log is terminated.
	 */
	private boolean isTerminated = false;
}
