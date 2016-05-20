package hillbillies.task.statement;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;
import ogp.framework.util.ModelException;

public class AttackStatement extends ActionStatement{

	public AttackStatement(Expression<UnitType> target) {
		super(target);

		this.target = target;
	}

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {
		if (thisUnit.getActivity() == null){
			if (! this.isBusy){
				try{
					thisUnit.fight(target.evaluate(globalVars, thisUnit).getValue());
					this.isBusy = true;
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
					return AttackStatement.this;
				}
				else
					return AttackStatement.this;
			}
			
			private boolean isIterated;
		};
	}
	private Expression<UnitType> target;
	private boolean isBusy;
	private boolean isExecuted;
}
