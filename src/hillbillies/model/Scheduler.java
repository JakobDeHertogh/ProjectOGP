package hillbillies.model;
import java.util.*;

public class Scheduler {
	public Scheduler(){
	}
	
	public void addTask(Task task){
		this.schedule.put(task.getPriority(), task);
	}
	
	public void setFaction(Faction faction){
		this.faction = faction;
		faction.setScheduler(this);
		
	}
	
	public Faction getFaction(){
		return this.faction;
	}
	
	private Set<Integer> priorities = new HashSet<Integer>();
	private HashMap<Integer, Task> schedule = new HashMap<Integer, Task>();
	private Faction faction;
	
}
