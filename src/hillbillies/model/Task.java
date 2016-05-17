package hillbillies.model;
import java.util.*;

import hillbillies.task.statement.Statement;
import hillbillies.task.type.Type;

public class Task {
	public Task(String name, int priority, Statement taskBody, Map<String, Type> globalVars){
		this.setName(name);
		this.setPriority(priority);
		this.setIterator(taskBody.iterator());
		this.globalVars = globalVars;
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
		schedule.addTask(this);
	}
	
	public Statement getTaskBody(){
		return this.taskBody;
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
	// Iterator
	public void setIterator(Iterator<Statement> iterator){
		this.iterator = iterator;
	}
	
	public Iterator<Statement> getIterator(){
		return this.iterator;
	}
	
	// Executing program:
	
	public void execute(double dt){
		double remainingTime = dt;
		while (this.getIterator().hasNext()){
			
		}
	}
	
	public void resetVariables(){
		this.globalVars.clear();
	}
	
	
	
	
	
	private Unit thisUnit;
	private String name;
	private int priority;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	private Map<String, Type> globalVars;
	private Iterator<Statement> iterator;
	private Statement taskBody;
}
