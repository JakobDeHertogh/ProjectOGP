package hillbillies.model;
import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
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
	
	/**
	 * Set the name of this Task to the given name.
	 * @param name The name that is to be given to this Task.
	 * @post The name of this Task is set to the given name.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Return the name of this Task.
	 */
	@Basic
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the priority of this Task to the given value.
	 * @param priority The Value this Task's priority is to be set on.
	 * @post	This Task's priority is set to the given value.
	 */
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	/**
	 * Return the priority of this Task.
	 */
	@Basic
	public int getPriority(){
		return this.priority;
	}
	
	/**
	 * Add this Task to the given Scheduler, and add this Scheduler to this Task.
	 * @param schedule The Scheduler this Task is to be added to.
	 * @effect the given Scheduler is added to this Task's schedulers.
	 * @effect thit Task is added to the given Scheduler.
	 */
	public void addSchedulers(Scheduler schedule){
		this.schedulers.add(schedule);
		schedule.addTask(this);
	}
	
	/**
	 * Returns the body of this Task.
	 */
	@Basic
	public Statement getTaskBody(){
		return this.taskBody;
	}
	
	/**
	 * Return the Schedulers this tasks has been added to.
	 */
	@Basic
	public Set<Scheduler> getSchedulers(){
		return this.schedulers;		
	}
	
	/**
	 * Replace this Task with the given replacant, in the given Scheduler.
	 * @param scheduler The Scheduler this Task is to be replaced.
	 * @param replacant	The replacant for this Task.
	 * @effect	This Task is removed from the given Scheduler.
	 * @effect	The replacant is added to the given Scheduler.
	 */
	public void replaceTask(Scheduler scheduler, Task replacant){
		scheduler.removeTask(this);
		scheduler.addTask(replacant);
	}
	
	/**
	 * Remove this Task.
	 * @effect	this Task is removed from all its Schedulers.
	 * @post	this Tasks has no schedulers.
	 */
	public void removeTask(){
		for (Scheduler i:this.schedulers){
			i.removeTask(this);
		}
		this.schedulers.clear();
	}
	
	/**
	 * Assign this Task to the given Unit.
	 * @param	unit The Unit that is to be executing this Task.
	 * @post	this Task's executer is set to the given Unit.
	 */
	public void assignTo(Unit unit){
		this.thisUnit = unit;
	}
	
	/**
	 * Return the Unit this Task is assigned to.
	 */
	@Basic
	public Unit getAssignedUnit(){
		return this.thisUnit;
	}
	
	/**
	 * Check whether this Task is well formed.
	 * @return true if and only if this Tasks body does not have an illegal break.
	 */
	public boolean isWellFormed(){
		/*
		 * Types will be generically checked. 
		 * Honestly no idea how to check for ReadVariableExpressions when no
		 * AssignVarStatement is used. 
		 */
		return (! this.getTaskBody().hasIllegalBreak());
		
	}
	
	/**
	 * Set the iterator of this Task to the given iterator.
	 * @param iterator The iterator this Task's iterator is to be set to.
	 */
	public void setIterator(Iterator<Statement> iterator){
		this.iterator = iterator;
	}
	
	/**
	 * Return this Task's Iterator.
	 */
	@Basic
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
	
	/**
	 * Reset this Task.
	 * @effect The iterator of this Task is set to its body's iterator.
	 * @effect The globalvars of this Task are cleared.
	 * @effect The priority of this Task is be set to 0.
	 */
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
