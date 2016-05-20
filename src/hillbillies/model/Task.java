package hillbillies.model;
import java.util.*;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.task.statement.Statement;
import hillbillies.task.type.Type;

public class Task implements Comparable<Task>{
	public Task(String name, int priority, Statement taskBody, Object selectedCube){
		this.setName(name);
		this.setPriority(priority);
		this.setIterator(taskBody.iterator());
		this.globalVars = new HashMap<String, Type>();
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
	
	public void replaceTask(Scheduler scheduler, Task replacant){
		scheduler.removeTask(this);
		scheduler.addTask(replacant);
	}
	
	public void removeTask(){
		for (Scheduler i:this.schedulers){
			i.removeTask(this);
		}
		this.schedulers.clear();
	}
	
	public void assignTo(Unit unit){
		this.thisUnit = unit;
	}
	
	public Unit getAssignedUnit(){
		return this.thisUnit;
	}
	
	public boolean isWellFormed(){
		/*
		 * Types will be generically checked. 
		 * Honestly no idea how to check for ReadVariableExpressions when no
		 * AssignVarStatement is used. 
		 */
		return (! this.getTaskBody().hasIllegalBreak());
		
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
			} catch (ExecutionErrorException ex){
				this.reset();	
				return;
			} catch (NoSuchElementException ex){
				System.out.println("pifpoefpaf de taak is af!");
				this.isCompleted = true;
				thisUnit.assignTask(null);
				this.removeTask();
				return;
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
	
	/*
	 * PriorityQueue from scheduler orders from small -> big 
	 * => revert order. 
	 * 
	 */
	@Override
	public int compareTo(Task other) {
		if (this.getPriority() > other.getPriority())
			return -1;
		if (this.getPriority() == other.getPriority())
			return 0;
		return 1;
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
