package hillbillies.model;



import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;
/**
 * A Class of Cubes, of which the game world is built. Every Cube has x, y and z coordinates, a CubeType and a World.
 * @author Jakob De Hertogh
 * @author Kristof Van Cappellen
 *
 */
public class Cube {
	/**
	 * 
	 * @param World This Cube's World
	 * @param xPosition	The x-coordinate of this Cube
	 * @param yPosition	The y-coordinate of this Cube
	 * @param zPosition	The z-coordinate of this Cube
	 * @param type The CubeType for this Cube
	 * @post	This Cube's World is set to the given World.
	 * @post	This Cube's x, y and z coordinates are set to the given coordinates, as well as combined in its position array.
	 * @post	This Cube's Type is set to the given CubeType
	 * @post	This Cube's center position is set.
	 */
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
	/**
	 * Return this Cube's world.
	 */
	@Basic
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Add the given Log to this Cube.
	 * @param	log The Log that is to be added to this Cube.
	 * @post	This Cube contains the given Log.
	 */
	public void addLog(Log log){
		this.Logs.add(log);
	}
	
	/**
	 * Return all the logs that occupy this Cube.
	 */
	@Basic
	public Set<Log> getLogs(){
		return this.Logs;
	}
	
	/**
	 * Remove the given Log from this Cube.
	 * @param	log The Log that is to be removed from this Cube.
	 * @post	The given Log is no longer in this Cube
	 */
	public void removeLog(Log log){
		this.Logs.remove(log);
	}
	

	
	/**
	 * Return a random Log that occupies this Cube.
	 * @return a random Log from this Cube's Logs.
	 */
	public Log randomLog(){
		int size = this.getLogs().size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (Log log : this.getLogs()){
			if (i == item)
				return log;
			i+=1;
		}
		return null;
	}
	
	/**
	 * Return a random Boulder that occupies this Cube.
	 * @return a random Boulder from this Cube's Boudlers.
	 */
	public Boulder randomBoulder(){
		int size = this.Boulders.size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (Boulder boulder : this.getBoulders()){
			if (i == item)
				return boulder;
			i+=1;
		}
		return null;
	}
	
	/**
	 * Add the given Boulder to this Cube.
	 * @param	boulder The Boulder that is to be added to this Cube.
	 * @post	This Cube contains the given Boulder.
	 */
	public void addBoulder(Boulder boulder){
		this.Boulders.add(boulder);
	}
	/**
	 * Return all the Boulders that occupy this Cube.
	 */
	@Basic
	public Set<Boulder> getBoulders(){
		return this.Boulders;
	}
	/**
	 * Remove the given Boulder from this Cube.
	 * @param	boulder The Boulderg that is to be removed from this Cube.
	 * @post	The given Boulder is no longer in this Cube
	 */
	public void removeBoulder(Boulder boulder){
		this.Boulders.remove(boulder);
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Unit> isOccupiedByUnits(){
		Set <Unit> result = new HashSet<Unit>();
		for (Unit i : this.world.getActiveUnits()){
			if (((int)Math.floor(i.getXPosition())==this.getXPosition()) 
					&& ((int)Math.floor(i.getYPosition())==this.getYPosition()) 
					&&((int)Math.floor(i.getZPosition())==this.getZPosition())){
				result.add(i);
			}
		}
		return result;
	}
	
	/**
	 * Check whether this Cube is a valid Cube for a Unit to occupy.
	 * @return true if and only if this Cube is of a passable type and has a neighbouring Cube is of a solid type.
	 */
	public boolean isValidCube(){
		if ((this.isPassableType())&&(this.hasSolidNeighbor()))
			return true;
		return false;
	}
	
	/**
	 * Return this Cube's x coordinate.
	 */
	@Basic
	public int getXPosition(){
		return this.xPosition;
	}
	/**
	 * Return this Cube's y coordinate.
	 */
	@Basic
	public int getYPosition(){
		return this.yPosition;
	}
	/**
	 * Return this Cube's z coordinate.
	 */
	@Basic
	public int getZPosition(){
		return this.zPosition;
	}
	/**
	 * Return this Cube's position.
	 */
	@Basic
	public int[] getPosition(){
		return this.Position;
	}
	/**
	 * Return the coordinates of this Cube's center.
	 */
	@Basic
	public double[] getCubeCenter(){
		return this.cubeCenter;
	}
	
	/**
	 * Return this Cube's CubeType.
	 */
	@Basic
	public CubeType getType(){
		return this.cubetype;
	}
	
	/**
	 * Set this Cube's CubeType to the given CubeType.
	 * @post	This Cube's CubeType is set to the given type.
	 * @effect	This Cube's World's TerrainChangeListener checks this Cube for a change in CubeType.
	 */
	public void setCubeType(CubeType type){
		this.cubetype = type;
		this.world.getTCL().notifyTerrainChanged(this.getXPosition(), this.getYPosition(), this.getZPosition());
	}
	/**
	 * Return whether this Cube is of passable terrain.
	 * @return true if and only if this Cube's CubeType is passable.
	 */
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
						result.add(null);
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
		double PLogBoulder = 1.00;
		if ((P <= PLogBoulder)&&(prevCubeType == CubeType.WOOD)){
			int[] logpos = new int[]{this.getXPosition(), this.getYPosition(), this.getZPosition()};
			Log newLog = new Log(this.world, logpos);
			this.world.addLog(newLog);
		} 
		if ((P <= PLogBoulder) && (prevCubeType == CubeType.ROCK)){
			int[] boulderpos = new int[]{this.getXPosition(), this.getYPosition(), this.getZPosition()};
			Boulder newBoulder = new Boulder(this.world, boulderpos);
			this.world.addBoulder(newBoulder);
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
	private Set<Log> Logs= new HashSet<Log>();
	private Set<Boulder> Boulders = new HashSet<Boulder>();

}
