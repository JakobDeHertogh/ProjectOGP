package hillbillies.model;
import java.util.*;

import hillbillies.exceptions.ExecutionErrorException;
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
	
	public void assignTo(Unit unit){
		this.thisUnit = unit;
	}
	
	// Iterator
	public void setIterator(Iterator<Statement> iterator){
		this.iterator = iterator;
	}
	
	public Iterator<Statement> getIterator(){
		return this.iterator;
	}
	
	/* Executing Task: 
	 * 
	 * execute for time specified in advanceTime of the executing unit
	 * iterate task as long as time and iterator allow. 
	 * execute all statements before iterating further. 
	 * 
	 */
	public void execute(double dt){
		double remainingTime = dt;
		while (remainingTime > 0){
			
			try{
				this.getIterator().next().execute(globalVars, thisUnit);
			} catch (NoSuchElementException ex){
				this.isCompleted = true;
				thisUnit.assignTask(null);
				this.removeTask();
				return;
			} catch (ExecutionErrorException ex){
				this.reset();
			}
			
			remainingTime -= 0.001;
		}
	}
	
	public void reset(){
		// create new iterator to start task from the top again
		this.setIterator(this.getTaskBody().iterator());
		// clear global variables
		this.globalVars.clear();
		// reduce priority (set to 0)
		this.setPriority(0);
	}
	
	
	
	
	
	private Unit thisUnit = null;
	private String name;
	private int priority;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	private Map<String, Type> globalVars;
	private boolean isCompleted = false;
	private Iterator<Statement> iterator;
	private Statement taskBody;
}
