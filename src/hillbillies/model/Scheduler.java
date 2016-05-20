package hillbillies.model;
import java.util.*;

public class Scheduler implements Iterable<Task> {
	public Scheduler(Faction faction){
		this.setFaction(faction);
	}
	
	public void addTask(Task task){
		this.schedule.add(task);
	}
	
	public void removeTask(Task task){
		this.schedule.remove(task);
	}
	
	public void setFaction(Faction faction){
		this.faction = faction;
		faction.setScheduler(this);
	}
	
	public Faction getFaction(){
		return this.faction;
	}
	
	public Task retrieveTask(){
		return this.getSchedule().poll();
	}
	
	public PriorityQueue<Task> getSchedule(){
		return this.schedule;
	}
	
	@Override
	public Iterator<Task> iterator(){
		return new Iterator<Task>(){

			@Override
			public boolean hasNext() {
				return ! schedule.isEmpty();
			}

			@Override
			public Task next() {
				if (! this.hasNext())
					throw new NoSuchElementException();
				return schedule.poll();
			}
			
		};
		
	}
	
	private PriorityQueue<Task> schedule = new PriorityQueue<Task>();
	private Faction faction;
	
}
