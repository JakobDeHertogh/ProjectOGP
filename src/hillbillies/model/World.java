package hillbillies.model;


import java.util.HashSet;
import java.util.Set;

import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

public class World {
	
	public World(int[][][] terraintypes, TerrainChangeListener tcl){
		// for pos
		// 		make new cube(x, y, z, type)
		//	! change value to type 
		this.nbXCubes = terraintypes[0].length; 
		this.nbYCubes = terraintypes[1].length;
		this.nbZCubes = terraintypes[2].length;
		
		this.cubes = new Cube[this.getNbCubesX()][this.getNbCubesY()][this.getNbCubesZ()];
		for (int i = 0 ; i < terraintypes[0].length ; i++){
			for (int j = 0 ; j < terraintypes[0].length  ; j++){
				for (int k = 0 ; k< terraintypes[0].length ; k++){
					CubeType type = CubeType.getCubeTypeOfValue(terraintypes[i][j][k]);
					cubes[i][j][k] = new Cube(this, i,j,k, type);
				}
			}
		}
	}
	
	public Cube getCubeAtPos(int x, int y, int z){
		return cubes[x][y][z];
	}
	
	public int getCubeTypeOf(int x,int y,int z){
		return this.getCubeAtPos(x, y, z).getType().getValue();
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
			if (result == null || result.getNbMembers() < i.getNbMembers())
				result = i;
		}
		return result;
	}
	
	public void addUnit(Unit unit) throws ModelException{
		if (unit.getFaction() != null || this.getActiveUnits().size() == activeUnitsLimit)
			throw new ModelException();
		if (this.activeFactions.size() < this.activeFactionslimit){
			Faction newFaction = new Faction(this);
			newFaction.addUnit(unit);
			this.activeFactions.add(newFaction);
		}
		else 
			this.getSmallestFaction().addUnit(unit);
		
	}
	
	private Set<Faction> activeFactions = new HashSet<Faction>();
	
	private Cube[][][] cubes;
	
	private final int nbXCubes;
	private final int nbYCubes; 
	private final int nbZCubes;
	
	private final int activeFactionslimit = 5;
	private final int activeUnitsLimit = 100;
}
