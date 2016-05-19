package hillbillies.task.statement;

import java.util.Map;

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
	
	private Expression<PosType> target;
	private boolean isExecuted = false;
}
