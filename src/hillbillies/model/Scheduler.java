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
	 * 
	 * @param faction The Faction for this Scheduler
	 */
	public Scheduler(Faction faction){
		if(faction.getScheduler()==null){this.setFaction(faction);}
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
	 * @post	This Scheduler's faction is set to the given Faction.
	 * 			|new.faction == faction
	 * @effect	The given Faction's Scheduler is set to this Scheduler.
	 * 			|faction.setScheduler(this)
	 */
	public void setFaction(Faction faction){
		this.faction = faction;
		faction.setScheduler(this);
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
	
	
	@Override
	public Iterator<Task> iterator(){
		return this.schedule.iterator();	
	};
		
	
	private LinkedList<Task> schedule = new LinkedList<Task>();
	private Faction faction;
	
}
