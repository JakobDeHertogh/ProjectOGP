package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import ogp.framework.util.ModelException;

public class Faction {
	
	public Faction(World world){
		this.world = world;
	}
	
	public int getNbMembers(){
		return this.members.size();
	}
	
	public Set<Unit> getMembers() {
		return this.members;
	}

	public World getWorld() {
		return this.world;
	}
	
	public void addUnit(Unit unit){
		//if (unit.isTerminated())
		//	throw new ModelException("This unit is terminated");
		if (unit.getFaction() == null)
			unit.setFaction(this);
			this.members.add(unit);
	}
	
	public void removeUnit(Unit unit){
		this.members.remove(unit);
		if (this.members.size() == 0){
			this.world.removeFaction(this);
		}
	}

	private Set<Unit> members = new HashSet<Unit>();
	
	private final World world;
}
