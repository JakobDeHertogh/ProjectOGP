package hillbillies.model;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.ModelException;

public class World {
	
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
	
	public void advanceTime(double dt) throws ModelException{
		// caveIn alle cubes die moeten instorten. Onmiddellijk => max 5s delay?
		for (int[] i : caveInCubes){
			caveInCube(i[0], i[1], i[2]);
		}
		
		for (Unit unit : this.getActiveUnits()){
			unit.advanceTime(dt);
		}
	}
	
	public boolean isPassable(int[] pos){
		return this.isPassableCube(pos[0], pos[1], pos[2]);
	}
	
	public ConnectedToBorder getCTB(){
		return this.ctb;
	}
	
	public boolean isSolidConnectedToBorder(int x, int y, int z){
		return this.ctb.isSolidConnectedToBorder(x,y,z);
	}
	
	public void caveInCube(int x, int y, int z) throws ModelException{
		getCubeAtPos(x,y,z).caveIn();
	}
	
	public Cube getCubeAtPos(int x, int y, int z){
		return cubes[x][y][z];
	}
	
	public Cube getCubeAtPos(int[] pos){
		return cubes[pos[0]][pos[1]][pos[2]];
	}
	
	public int getCubeTypeOf(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).getType().getValue();
	}
	
	public void setCubeTypeOf(int x, int y, int z, int value){
		CubeType type = CubeType.getCubeTypeOfValue(value);
		this.getCubeAtPos(x, y, z).setCubeType(type);
	}
	
	public Set<Object> isOccupiedBy(Cube cube){
		Set<Object> result = new HashSet<Object>();
		result.addAll(cube.getBoulders());
		result.addAll(cube.getLogs());
		result.addAll(cube.isOccupiedByUnits());
		return result;
	}
	
	
	public boolean isPassableCube(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).isPassableType();
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
	
	public void addBoulder(Boulder newBoulder){
		this.boulders.add(newBoulder);
		int[] position = new int[]{(int)Math.floor(newBoulder.getPosition()[0]),
				(int)Math.floor(newBoulder.getPosition()[1]),
				(int)Math.floor(newBoulder.getPosition()[2])};
		this.getCubeAtPos(position[0], position[1], position[2]).addBoulder(newBoulder);
	}
	
	public void removeBoulder(Boulder boulder){
		this.boulders.remove(boulder);
	}
	
	public void addLog(Log newLog){
		this.logs.add(newLog);
		int[] position = new int[]{(int)Math.floor(newLog.getPosition()[0]), (int)Math.floor(newLog.getPosition()[1]),
				(int)Math.floor(newLog.getPosition()[2])};
		this.getCubeAtPos(position[0], position[1], position[2]).addLog(newLog);
	}
	
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
	
	public Set<Log> getLogs(){
		return this.logs;
	}
	
	public Set<Boulder> getBoulders(){
		return this.boulders;
	}
	
	public Set<Faction> getActiveFactions(){
		return this.activeFactions;
	}
	
	public void removeFaction(Faction faction){
		this.activeFactions.remove(faction);
	}
	
	
	public Set<Unit> getActiveUnits(){
		Set<Unit> result = new HashSet<Unit>();
		for (Faction i : this.activeFactions){
			result.addAll(i.getMembers());
		}
		return result;
	}
	
	public TerrainChangeListener getTCL(){
		return this.tcl;
	}
	
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
	
	
	
	private Set<Faction> activeFactions = new HashSet<Faction>();
	private Set<Boulder> boulders = new HashSet<Boulder>();
	private Set<Log> logs = new HashSet<Log>();
	Set<int[]> caveInCubes = new HashSet<int[]>();
	public Set<Cube> viableSpawnCubes = new HashSet<Cube>();
	private Cube[][][] cubes;
	private final TerrainChangeListener tcl;
	
	private ConnectedToBorder ctb;
	private final int nbXCubes;
	private final int nbYCubes; 
	private final int nbZCubes;
	
	private final int activeFactionslimit = 5;
	private final int activeUnitsLimit = 100;
}
