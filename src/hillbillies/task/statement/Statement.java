package hillbillies.task.statement;

import java.util.*;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public abstract class Statement implements Iterable<Statement>{

	public abstract void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException;
	
	@Override
	public Iterator<Statement> iterator(){
		return new Iterator<Statement>(){

			private boolean isIterated(){
				return this.isIterated;
			}
			
			private void iterate(){
				this.isIterated = true;
			}
			
			@Override
			public boolean hasNext() {
				return ! this.isIterated();
			}

			@Override
			public Statement next() {
				if (this.isIterated())
					throw new NoSuchElementException();
				else{
					this.iterate();
					return Statement.this;
				}
			}
			
			private boolean isIterated;
		};
	}
	
	//default lege arraylist, override in composed statements, maar niet in single statements. 
	public List<Statement> getSubStatements(){
		return new ArrayList<Statement>();
	}
	
	public boolean hasIllegalBreak(){
		if (this instanceof BreakStatement)
			return true;
		
		if (! (this instanceof WhileStatement)){
			for (Statement s : this.getSubStatements()){
				if (s instanceof BreakStatement)
					return true;
			}
		}
		
		return false;
	}
}
	
