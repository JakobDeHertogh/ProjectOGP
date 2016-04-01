package hillbillies.model;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.ModelException;

public class World {
	
	public World(int[][][] terraintypes, TerrainChangeListener tcl){
		// for pos
		// 		make new cube(x, y, z, type)
		//	! change value to type 
		
		this.nbXCubes = terraintypes.length; 
		this.nbYCubes = terraintypes[0].length;
		this.nbZCubes = terraintypes[0][0].length;

		//INITIALISE CONNECTEDTOBORDER 
		ConnectedToBorder ctb = new ConnectedToBorder(this.nbXCubes, this.nbYCubes, this.nbZCubes);

		this.cubes = new Cube[this.getNbCubesX()][this.getNbCubesY()][this.getNbCubesZ()];
		for (int i = 0 ; i < terraintypes.length ; i++){
			for (int j = 0 ; j < terraintypes[0].length  ; j++){
				for (int k = 0 ; k< terraintypes[0][0].length ; k++){
					CubeType type = CubeType.getCubeTypeOfValue(terraintypes[i][j][k]);
					cubes[i][j][k] = new Cube(this, i,j,k, type);
					
					//	UPDATE CONNECTEDTOBORDER
					if (type.isPassable())
						caveInCubes.addAll(ctb.changeSolidToPassable(i, j, k));
					
				}
			}
		}
		
	}
	
	public void caveInCube(int x, int y, int z) throws ModelException{
		getCubeAtPos(x,y,z).caveIn();
	}
	
	public Cube getCubeAtPos(int x, int y, int z){
		return cubes[x][y][z];
	}
	
	public int getCubeTypeOf(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).getType().getValue();
	}
	
	public void setCubeTypeOf(int x, int y, int z, int value){
		CubeType type = CubeType.getCubeTypeOfValue(value);
		this.getCubeAtPos(x, y, z).setCubeType(type);
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
	
	public void addBoulder(int[] position) throws ModelException{
		Boulder newBoulder = new Boulder(this, position);
		this.boulders.add(newBoulder);
	}
	
	public void addLog(int[] position) throws ModelException{
		Log newLog = new Log(this, position);
		this.logs.add(newLog);
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
	
	
	public Set<Unit> getActiveUnits(){
		Set<Unit> result = new HashSet<Unit>();
		for (Faction i : this.activeFactions){
			result.addAll(i.getMembers());
		}
		return result;
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
	private Set<int[]> caveInCubes = new HashSet<int[]>();
	private Cube[][][] cubes;
	
	private final int nbXCubes;
	private final int nbYCubes; 
	private final int nbZCubes;
	
	private final int activeFactionslimit = 5;
	private final int activeUnitsLimit = 100;
}
