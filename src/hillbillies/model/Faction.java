package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import ogp.framework.util.ModelException;
/**
 * A class of Factions. Each Faction has a World it is part of, and a Scheduler. Factions have Units as members,
 * and if empty they are removed from their World. 
 * @author Jakob De Hertogh
 * @author Kristof Van Cappellen
 *
 */
public class Faction {
	/**
	 * @param world The world this Faction is set in.
	 * @post this Faction's World is set to the given World.
	 * @effect the Scheduler of this Faction is set to a newly created Scheduler.
	 */
	public Faction(World world){
		this.world = world;
		this.setScheduler(new Scheduler(this));
	}
	
	/**
	 * Return the number of Units that are a member of this Faction.
	 * @return the size of the set of members of this Faction.
	 * 			|result == members.size()
	 */
	public int getNbMembers(){
		return this.members.size();
	}
	
	/**
	 * Return the members (Units) of this Faction.
	 */
	@Basic
	public Set<Unit> getMembers() {
		return this.members;
	}
	
	/**
	 * Return the World of this Faction.
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Return the Scheduler of this Faction.
	 */
	@Basic
	public Scheduler getScheduler(){
		return this.scheduler;
	}
	
	/**
	 * Set the given Scheduler to this Faction.
	 * @param scheduler The Scheduler we wish to set for this Faction.
	 * @post This Faction's Scheduler is set to the given Scheduler.
	 */
	public void setScheduler(Scheduler scheduler){
		this.scheduler = scheduler;
	}
	
	/**
	 * Add the given Unit to this Faction.
	 * @param unit The Unit that is to be added to this Faction.
	 * @post if the given Unit didn't already havd  a Faction, this Unit will be added to this Faction.
	 * @effect if the given Unit didn't already havd  a Faction, this Faction will be set to its faction.
	 */
	public void addUnit(Unit unit){
		//if (unit.isTerminated())
		//	throw new ModelException("This unit is terminated");
		if (unit.getFaction() == null)
			unit.setFaction(this);
			this.members.add(unit);
	}
	
	/**
	 * Remove the given Unit from this Faction.
	 * @param unit The Unit that is to be removed from this Faction.
	 * @post The unit is removed from this Faction's members.
	 * @effect If the faction is empty after removing the unit, this Faction will be removed from its World
	 */
	public void removeUnit(Unit unit){
		this.members.remove(unit);
		if (this.members.size() == 0){
			this.world.removeFaction(this);
		}
	}
	
	/**
	 * Variable registering the members of this Faction.
	 */
	private Set<Unit> members = new HashSet<Unit>();
	
	/**
	 * Variable registering this Faction's Scheduler.
	 */
	private Scheduler scheduler;
	
	/**
	 * Constant registering the World this Faction is a part of.
	 */
	private final World world;
}
