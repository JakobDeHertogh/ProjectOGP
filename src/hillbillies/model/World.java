package hillbillies.model;


import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.ModelException;

public class World {
	/**
	 * 
	 * @param terraintypes
	 * @param tcl	The given TerrainChangeListener for the new World.
	 * @post	The number of x, y and z Cubes is set to the dimensions of the given terraintypes.
	 * @post	The TerrainChangeListener for this world is set to the given TerrainChangeListener.
	 * 
	 */
	public World(int[][][] terraintypes, TerrainChangeListener tcl){
		
		this.nbXCubes = terraintypes.length; 
		this.nbYCubes = terraintypes[0].length;
		this.nbZCubes = terraintypes[0][0].length;

		//INITIALISE CONNECTEDTOBORDER 
		ConnectedToBorder ctb = new ConnectedToBorder(this.nbXCubes, this.nbYCubes, this.nbZCubes);
		this.ctb = ctb;

		this.cubes = new Cube[this.getNbCubesX()][this.getNbCubesY()][this.getNbCubesZ()];
		for (int i = 0 ; i < terraintypes.length ; i++){
			for (int j = 0 ; j < terraintypes[0].length  ; j++){
				for (int k = 0 ; k< terraintypes[0][0].length ; k++){
					CubeType type = CubeType.getCubeTypeOfValue(terraintypes[i][j][k]);
					cubes[i][j][k] = new Cube(this, i,j,k, type);
					if (cubes[i][j][k].getType() == CubeType.WORKSHOP){
						workshops.add(cubes[i][j][k]);
					}
					
					//	UPDATE CONNECTEDTOBORDER
					if (type.isPassable()){
						caveInCubes.addAll(ctb.changeSolidToPassable(i, j, k));
						if (cubes[i][j][k].isValidCube())
							this.viableSpawnCubes.add(cubes[i][j][k]);
					}
				}
			}
		}
		this.tcl = tcl;
	}
	
	/**
	 * Make this World advance with a given time step.
	 * @param dt The time step this World advances with.
	 * @throws ModelException
	 * @effect	For every Unit in this World the time will advance with the given time step.
	 */
	public void advanceTime(double dt) throws ModelException{
		// caveIn alle cubes die moeten instorten. Onmiddellijk => max 5s delay?
		for (int[] i : caveInCubes){
			caveInCube(i[0], i[1], i[2]);
		}
		
		for (Unit unit : this.getActiveUnits()){
			unit.advanceTime(dt);
		}
	}
	
	/**
	 * Return whether a given position is passable or not.
	 * @param pos	The position that is to be checked.
	 * @return	true if and only if the Cube at this position is passable.
	 */
	public boolean isPassable(int[] pos){
		return this.isPassableCube(pos[0], pos[1], pos[2]);
	}
	
	/**
	 * Return the ConnectedToBorder of this World.
	 */
	@Basic
	public ConnectedToBorder getCTB(){
		return this.ctb;
	}
	
	/*
	 * Check whether the given coordinates are solid connected to the World border.
	 * @return	True if and only if this position is solidly connected to the World border.
	 */
	public boolean isSolidConnectedToBorder(int x, int y, int z){
		return this.ctb.isSolidConnectedToBorder(x,y,z);
	}
	
	/**
	 * Make a Cube at given coordinates cave in.
	 * @param x	The x coordinate of the Cube that has to cave in.
	 * @param y The y coordinate of the Cube that has to cave in.
	 * @param z The z coordinate of the Cube that has to cave in.
	 * @throws ModelException
	 */
	public void caveInCube(int x, int y, int z) throws ModelException{
		getCubeAtPos(x,y,z).caveIn();
	}
	
	/**
	 * Return the Cube at the given coordinates.
	 * @param x The x coordinate of the Cube that has to be returned.
	 * @param y	The x coordinate of the Cube that has to be returned.
	 * @param z	The x coordinate of the Cube that has to be returned.
	 */
	@Basic
	public Cube getCubeAtPos(int x, int y, int z){
		return cubes[x][y][z];
	}
	/**
	 * Return the Cube at the given position.
	 * @param pos The position of which the Cube must be returned/
	 */
	@Basic
	public Cube getCubeAtPos(int[] pos){
		return cubes[pos[0]][pos[1]][pos[2]];
	}
	
	
	public int getCubeTypeOf(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).getType().getValue();
	}
	
	/**
	 * Set the CubeType of the Cube at the given coordinates to the given value.
	 * @param x	The x coordinate of the Cube which' type is to be set.
	 * @param y	The y coordinate of the Cube which' type is to be set.
	 * @param z	The z coordinate of the Cube which' type is to be set.
	 * @param value The type value to which the Cube's type is to be set.
	 * @effect The Cube at the given coordinates is given the proper CubeType.
	 */
	public void setCubeTypeOf(int x, int y, int z, int value){
		CubeType type = CubeType.getCubeTypeOfValue(value);
		this.getCubeAtPos(x, y, z).setCubeType(type);
	}
	
	/**
	 * Return all game entities (Logs, boulders, Units) that occupy the given Cube.
	 * @param cube The Cube of which all occupying entities (Logs, Boulder and Units) are to determined.
	 * @return	A set of game Objects, that consists of all Boulders, Logs and Units in this World.
	 */
	public Set<Object> isOccupiedBy(Cube cube){
		Set<Object> result = new HashSet<Object>();
		result.addAll(cube.getBoulders());
		result.addAll(cube.getLogs());
		result.addAll(cube.isOccupiedByUnits());
		return result;
	}
	
	/**
	 * Check whether a Cube is passable.
	 * @param x	The x coordinate of the position that needs to be checked.
	 * @param y	The y coordinate of the position that needs to be checked.
	 * @param z	The z coordinate of the position that needs to be checked.
	 * @return True if and only if the Cube at the given coordinates is of z passable CubeType.
	 */
	public boolean isPassableCube(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).isPassableType();
	}
	
	/**
	 * Return the number of Cubes in this World along the X-axis.
	 */
	@Basic
	public int getNbCubesX(){
		return this.nbXCubes;
	}
	
	/**
	 * Return the number of Cubes in this World along the Y-axis.
	 */
	@Basic
	public int getNbCubesY(){
		return this.nbYCubes;
	}
	
	/**
	 * Return the number of Cubes in this World along the z-axis.
	 * @return
	 */
	@Basic
	public int getNbCubesZ(){
		return this.nbZCubes;
	}
	
	/**
	 * Add a given Boulder to this World.
	 * @param newBoulder The Boulder that is to be added.
	 * @effect	The given Boulder is added to this World.
	 * @effect	The given Boulder is added to the Cube that contains its position.
	 */
	public void addBoulder(Boulder newBoulder){
		this.boulders.add(newBoulder);
		int[] position = new int[]{(int)Math.floor(newBoulder.getPosition()[0]),
				(int)Math.floor(newBoulder.getPosition()[1]),
				(int)Math.floor(newBoulder.getPosition()[2])};
		this.getCubeAtPos(position[0], position[1], position[2]).addBoulder(newBoulder);
	}
	
	/**
	 * Remove the given Boulder from this World
	 * @param boulder The Boulder that is to be removed.
	 * @post	The Boulder is removed from the world.
	 */
	public void removeBoulder(Boulder boulder){
		this.boulders.remove(boulder);
	}
	
	/**
	 * Add a given Log to this World.
	 * @param newLog The Log that is to be added.
	 * @effect	The given Log is added to this World.
	 * @effect	The given Log is added to the Cube that contains its position.
	 */
	public void addLog(Log newLog){
		this.logs.add(newLog);
		int[] position = new int[]{(int)Math.floor(newLog.getPosition()[0]), (int)Math.floor(newLog.getPosition()[1]),
				(int)Math.floor(newLog.getPosition()[2])};
		this.getCubeAtPos(position[0], position[1], position[2]).addLog(newLog);
	}
	
	/**
	 * Remove the given Log from this World.
	 * @param log The log that is to be removed.
	 * @effect	The given Log is removed from this World.
	 */
	public void removeLog(Log log){
		this.logs.remove(log);
	}
	
	public void addUnit(Unit unit) throws ModelException{
		if (this.getActiveUnits().size() == activeUnitsLimit)
			throw new ModelException("Maximum number of units reached!");

		if (this.getActiveFactions().size() < this.activeFactionslimit){
			Faction newFaction = new Faction(this);
			newFaction.addUnit(unit);
			this.activeFactions.add(newFaction);
		}
		else {
			this.getSmallestFaction().addUnit(unit);
		}
		// some originally valid positions may have caved in. Find another viable spot.
		while (! unit.isValidPosition(unit.getPosition())){
			try{
				Cube a = this.getRandomSpawnCube();
				unit.setPosition(a.getCubeCenter());
			} catch (ModelException ex){
				continue;
			}
		}
	}
	
	/**
	 * Return a random Cube that is spawnable.
	 * @return
	 */
	public Cube getRandomSpawnCube(){
		int size = this.viableSpawnCubes.size();
		int random = new Random().nextInt(size);
		int i = 0;
		for (Cube cube : viableSpawnCubes){
			if (i == random)
				return cube;
			i += 1;
		}
		return null; // kan eigenlijk niet voorkomen
	}
	
	/**
	 * Return the set of Logs that this World contains.
	 */
	@Basic
	public Set<Log> getLogs(){
		return this.logs;
	}
	
	/**
	 * Return the set of Boulders that this World contains.
	 */
	@Basic
	public Set<Boulder> getBoulders(){
		return this.boulders;
	}
	
	/**
	 * Return a list of Cubes that are workshops in this World.
	 */
	@Basic
	public ArrayList<Cube> getWorkshops(){
		return this.workshops;
	}
	
	/**
	 * Return the active Factions of this World.
	 * @return
	 */
	@Basic
	public Set<Faction> getActiveFactions(){
		return this.activeFactions;
	}
	
	/**
	 * Remove the given Faction from this World's active Factions.
	 * @param faction The Faction that is to be removed from this world.
	 */
	public void removeFaction(Faction faction){
		this.activeFactions.remove(faction);
	}
	
	/**
	 * Return the active Units of this World.
	 */
	@Basic
	public Set<Unit> getActiveUnits(){
		Set<Unit> result = new HashSet<Unit>();
		for (Faction i : this.activeFactions){
			result.addAll(i.getMembers());
		}
		return result;
	}
	
	/**
	 * Return the TerrainChangeListener of this World.
	 */
	@Basic
	public TerrainChangeListener getTCL(){
		return this.tcl;
	}
	
	/**
	 * Return the smallest Faction of this World.
	 * @return	The smallest Faction. All other active Factions in this World have more or as much members.
	 */
	private Faction getSmallestFaction(){
		Faction result = null;
		for (Faction i : this.activeFactions){
			if (result == null)
				result = i;
			
			else if (result.getNbMembers() > i.getNbMembers())
				result = i;
		}
		return result;
	}
	
	
	/**
	 * Variable registering all active Factions of this World.
	 */
	private Set<Faction> activeFactions = new HashSet<Faction>();
	
	/**
	 * Variable registering all the Boulders in this World.
	 */
	private Set<Boulder> boulders = new HashSet<Boulder>();
	private Set<Log> logs = new HashSet<Log>();
	Set<int[]> caveInCubes = new HashSet<int[]>();
	public Set<Cube> viableSpawnCubes = new HashSet<Cube>();
	private Cube[][][] cubes;
	private ArrayList<Cube> workshops = new ArrayList<Cube>();
	private final TerrainChangeListener tcl;
	
	private ConnectedToBorder ctb;
	private final int nbXCubes;
	private final int nbYCubes; 
	private final int nbZCubes;
	
	private final int activeFactionslimit = 5;
	private final int activeUnitsLimit = 100;
}
