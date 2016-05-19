package hillbillies.task.statement;

import java.util.*;
import java.util.Map;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;

public class ActionStatement extends Statement{

	public ActionStatement(Expression<? extends Type> target){
		this.target = target;
	}
	
	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {
		
	}
	
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
				if (! this.hasNext())
					throw new NoSuchElementException();				
				// as long as action is not complete, this statement is 
				if (isExecuted){
					this.iterate();
					return ActionStatement.this;
				}
				else
					return ActionStatement.this;
			}
			
			private boolean isIterated;
		};
	}

	private boolean isExecuted = false;
	private Expression<? extends Type> target;
}
