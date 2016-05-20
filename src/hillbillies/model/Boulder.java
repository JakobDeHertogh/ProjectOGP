package hillbillies.model;

import ogp.framework.util.ModelException;

/**
 * A class of game Boulders. Each Boulder has a position and a weight. Boulders can move and fall.
 * @invar	The starting value for the weight of every Boulder must be valid.
 * 			| isValidWeight(weight)
 * @invar	Each Boulder belongs to a world and has a valid position in that world.
 * @invar	Each Boulder has a weight between a given upper and lower limit. 
 * 
 * @version 0.51
 * @author Kristof Van Cappellen
 * @author Jakob De Hertogh
 *
 */
public class Boulder {
	
	/**
	 * @post	The boulder has a starting position within the game world. 
	 * @post 	The boulder has a valid weight, randomly chosen between the upper
	 * 			and lower limit. 
	 * @post	The boulder is not carried by a Unit. 
	 * 
	 * @param world 
	 * 			The game world in which the Boulder is situated
	 * @param startPosition
	 * 			The position at which the Boulder spawns
	 * @throws ModelException If the given weight is not valid.
	 */
	public Boulder( World world, int[] startPosition) throws ModelException{
		this.world = world;
		
		int weight = (int)((Math.random()*this.weightRange)+this.minWeight);
		this.setWeight(weight);
		
		double[] pos = new double[]{startPosition[0]+0.5, startPosition[1]+0.5, startPosition[2]};
		this.setPosition(pos);
		this.fallingTo = this.getzPosition();
		this.isTerminated = false;
	}
	/**
	 * Set the coordinate of the Cube this Boulder is Situated in.
	 * @post the CubeCoordinate is set to the rounded down coordinates of the Boulder.
	 */
	public void setCubeCoordinate(){
		int[] cubecoordinate = new int[]{(int)Math.floor(this.position[0]),(int)Math.floor(this.position[1]),
				(int)Math.floor(this.position[2])};
		this.CubeCoordinate = cubecoordinate;
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
	
	/**
	 * Returns the Cube which is occupied by this boulder.
	 */
	public Cube occupiesCube(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	/**
	 * Returns the cube below the occupied cube. 
	 * @return	returns the Cube at the z-position under this Boulder.
	 * 			|result == world.getCubeAtPos(getCubeCoordinate()[0], getCubeCoordinate()[1], getCubeCoordinate()[2])
	 */
	public Cube getCubeUnder(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]-1);
	}
	
	/**
	 * Returns the World in which the boulder is situated.
	 */
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Checks whether the given position is a valid position for a Boulder.
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
	 * Returns the position of this Boulder.
	 */
	public double[] getPosition(){
		return this.position;
	}


	/**
	 * Sets the position of the Boulder to the given position.
	 * @param	newPosition	The position the Boulder is to be set on.
	 * @post	This Boulder's position is set to the given position
	 * 			|new.position == newPosition
	 */
	public void setPosition(double[] newPosition){
		double[] pos = new double[]{newPosition[0], newPosition[1], newPosition[2]};
		this.position = pos;
	}
	/**
	 * Returns the z coordinate of the boulder
	 */
	public double getzPosition() {
		return this.position[2];
	}
	
	/**
	 * Returns the weight of this boulder
	 */
	public int getWeight() {
		return this.weight;
	}
	/**
	 * sets the weight to the given value.
	 * 
	 *  @param weight
	 *  		An integer value to which we want to set the Boulder's weight.
	 *  @post The weight of the Boulder is set to the given value.
	 *  		|this.weight = weight.
	 */
	public void setWeight(int weight) throws ModelException{
		this.weight = weight;
		
	}
	
	/**
	 * Adapts the Boulder's current position on whether it is falling or being carried.
	 * @param dt The time step the Scheduler is to be advanced with.
	 * @effect If this Boulder is being carried by a Unit, this Boulder's position will be adjusted to that Unit's position.
	 * @post If this Boulder has reached its falling destination and its occupying Cube is not a valid Cube, this Boulder will fall to the Boulder below.
	 * @post If the z-distance between the Boulder's position and its falling destination is smaller than dt, the Boulder's position is set to 
	 * 		 its falling destination.
	 * @post If the z-distance between the Boulder's position and its falling destination is greater than dt, the Boulder's z-coordinate is set to 
	 * 			the current z-coordinate increased with the product of this Boulder's falling speed and the time step dt.
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
	 * Removes this Boulder from the game world.
	 * 
	 * @effect This Boulder will be removed from the game world.
	 * 			|world.removeBoulder(this);
	 * @post This Boulder will be terminated.
	 * 			|this.isTerminated = true;
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
	
	/**
	 * Variable registering the World this Boulder is in.
	 */
	private World world;
	/**
	 * Variable registering this Boulder's current position.
	 */
	private double[] position;
	/**
	 * Variable registering the coordinates of the Cube this Boulder is situated in.
	 */
	private int[] CubeCoordinate;
	/**
	 * Variable registering this Boulder's weight.
	 */
	private int weight;
	/**
	 * Position this Boulder is falling to.
	 */
	private double fallingTo;
	/**
	 * Constant registering the z-component of this Boulder's falling speed.
	 */
	private static int fallSpeed = -3;
	/**
	 * Constant registering the minimum weight of a Boulder.
	 */
	private final int minWeight = 10;
	/**
	 * Constant registering the maximum weight of a Boulder.
	 */
	private final int maxWeight = 50;
	/**
	 * Constant registering the range for a Boulder's weight.
	 */
	private final int weightRange = this.maxWeight - this.minWeight;
	
	/**
	 * Variable registering the Unit that is carrying this Boulder.
	 */
	public Unit isCarriedBy = null;
	/**
	 * Variable registering whether or not this Boulder is terminated.
	 */
	private boolean isTerminated = false;
}
