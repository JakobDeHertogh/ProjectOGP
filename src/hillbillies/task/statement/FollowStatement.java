package hillbillies.task.statement;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;
import ogp.framework.util.ModelException;

public class FollowStatement extends ActionStatement {

	public FollowStatement(Expression<UnitType> target) {
		super(target);
		
		this.target = target;
	}
	
	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException{
		if (thisUnit.getActivity() == null){
			if (thisUnit.isNextTo(target.evaluate(globalVars, thisUnit).getValue()))
				this.isExecuted = true;
			else{
				try {
					thisUnit.moveTo(target.evaluate(globalVars, thisUnit).getValue().getCubeCoordinate());
				} catch (ModelException e) {
					throw new ExecutionErrorException();
				}				
			}
		}
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
					return FollowStatement.this;
				}
				else
					return FollowStatement.this;
			}
			
			private boolean isIterated;
		};
	}

	private Expression<UnitType> target;
	private boolean isExecuted = false;
}
