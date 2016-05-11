package hillbillies.model;
import java.util.*;

import hillbillies.part3.programs.Statement;

public class Task {
	public Task(String name, int priority, int[] position, Statement activity){
		this.setName(name);
		this.setPriority(priority);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public int getPriority(){
		return this.priority;
	}
	
	public void addSchedulers(Scheduler schedule){
		this.schedulers.add(schedule);
		schedule.add(this);
	}
	
	public Set<Scheduler> getSchedulers(){
		return this.schedulers;		
	}
	
	public void replaceTask(Task replacant){
		for(Scheduler i: this.schedulers){
			i.removeTask(this);
			i.addTask(replacant);
		}
	}
	
	public void removeTask(){
		for (Scheduler i:this.schedulers){
			i.removeTask(this);
		}
	}
	
	private String name;
	private int priority;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
}
