package hillbillies.model;
import java.util.*;

public class Scheduler {
	public Scheduler(){
	}
	
	public void addTask(Task task){
		this.schedule.put(task, task.getPriority());
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
	
	private Set<Integer> priorities = new HashSet<Integer>();
	private HashMap<Task, Integer> schedule = new HashMap<Task, Integer>();
	private Faction faction;
	
}
