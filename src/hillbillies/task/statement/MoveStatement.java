package hillbillies.task.statement;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Activity;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;
import ogp.framework.util.ModelException;

public class MoveStatement extends ActionStatement{

	public MoveStatement(Expression<PosType> target) {
		super(target);
		
		this.target = target;
	}

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException{
		
		if (thisUnit.getActivity() == null){
			// activity is either not initiated or completed
			if (thisUnit.getCubeCoordinate() == this.target.evaluate(globalVars, thisUnit).getValue())
				this.isExecuted = true;
			
			else{
				try{
					thisUnit.moveTo(target.evaluate(globalVars, thisUnit).getValue());
				} catch (ModelException ex){
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
					return MoveStatement.this;
				}
				else
					return MoveStatement.this;
			}
			
			private boolean isIterated;
		};
	}
	private Expression<PosType> target;
	private boolean isExecuted = false;
}
