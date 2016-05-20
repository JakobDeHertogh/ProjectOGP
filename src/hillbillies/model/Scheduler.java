package hillbillies.model;
import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
/**
 * A class of Schedulers. Every scheduler has a Faction and a schedule, to which Tasks can be added or removed.
 * Units of this Scheduler's Faction can access the Scheduler to retrieve Tasks for execution. Tasks can be removed from a
 * Scheduler on execution.
 * @author Jakob De Hertogh
 * @author Kristof Van Cappellen
 *
 */
public class Scheduler implements Iterable<Task> {
	/**
	 * @param faction The Faction for this Scheduler
	 * @effect the Faction of this Scheduler is set to the given faction.
	 * 			|setFaction(faction)
	 */
	public Scheduler(Faction faction){
		this.setFaction(faction);
	}
	/**
	 * Add the given Task to this Scheduler.
	 * @param task The Task that is to be added to this Scheduler.
	 * @effect	The task is added to this Scheduler's schedule.
	 * 			|schedule.add(task)
	 */
	public void addTask(Task task){
		if (this.schedule.isEmpty()){
			this.schedule.add(task);
		}
		for (int i=1; i<this.schedule.size(); i++){
			if (schedule.get(i).getPriority()<=task.getPriority()){
				this.schedule.add(i, task);
			}
		}
	}
	
	/**
	 * Remove the given Task from this Scheduler.
	 * @param task The Task that is to be removed from this Scheduler.
	 * @effect	The Task is removed from the schedule.
	 * 			|schedule.remove(task)
	 */
	public void removeTask(Task task){
		this.schedule.remove(task);
	}
	
	/**
	 * Set the Faction for this Scheduler.
	 * @param faction	The Faction that is to be the faction for this Schedule.
	 * @post	If this Scheduler didn't already have a Faction and the faction didn't already have a Scheduler,
	 * 			this Scheduler's faction is set to the given Faction.
	 * 			|new.faction == faction
	 * @effect	If this Scheduler didn't already have a Faction and the faction didn't already have a Scheduler,
	 * 			this Scheduler's faction is set to the given Faction.
	 * 			|faction.setScheduler(this)
	 */
	public void setFaction(Faction faction){
		if((faction.getScheduler()==null)&&(faction.getScheduler()==null)){
			this.faction = faction;
			faction.setScheduler(this);
		}
		
	}
	
	/**
	 * Return the Faction of this Scheduler.
	 */
	@Basic
	public Faction getFaction(){
		return this.faction;
	}
	
	/**
	 * Retrieve the Task with the highest priority from the schedule.
	 * @return
	 */
	public Task retrieveTask(){
		if (this.schedule.isEmpty()){
			return null;
		}
		return this.getSchedule().get(0);
	}
	
	/**
	 * Return this Scheduler's schedule.
	 */
	@Basic
	public LinkedList<Task> getSchedule(){
		return this.schedule;
	}
	
	/**
	 * Return this Schedulers iterator.
	 */
	@Override
	public Iterator<Task> iterator(){
		return this.schedule.iterator();	
	};
		
	/**
	 * Variable registering the Tasks, ordered by priority.
	 */
	private LinkedList<Task> schedule = new LinkedList<Task>();
	
	/**
	 * Variable registering the Faction this Scheduler belongs to.
	 */
	private Faction faction;
	
}
