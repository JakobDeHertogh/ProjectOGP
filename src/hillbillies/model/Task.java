package hillbillies.model;
import java.util.*;

import hillbillies.task.statement.Statement;
import hillbillies.task.type.Type;

public class Task {
	public Task(String name, int priority, Statement taskBody){
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
	
	// Task iterator
	
	public StatementIterator<Statement> getTaskIterator(){
		if (this.taskIterator == null)
			this.taskIterator = this.getTaskBody().iterator();
		return this.taskIterator;
	}
	
	
	// Executing program:
	
	
	public void execute(double dt){
		double remainingTime = dt;
		while (remainingTime > 0){
			if (! thisUnit.isAlive)
				break;
			if(this.getTaskIterator().hasNext()){
				// execute program
				Statement nextStatement = getTaskIterator().next();
				if (nextStatement != null)
					remainingTime -= 0.001;
			}
			else {
				resetVariables();
				getTaskIterator().restart();
			}
		}
	}
	
	public void resetVariables(){
		this.globalVars.clear();
	}
	
	
	
	
	
	private Unit thisUnit;
	private String name;
	private int priority;
	private Set<Scheduler> schedulers = new HashSet<Scheduler>();
	private Map<String, Type> globalVars = new HashMap<String, Type>();
	private StatementIterator<Statement> taskIterator = null;
	private Statement taskBody;
}
