package hillbillies.task.statement;

import java.util.Map;

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
	

	private Expression<UnitType> target;
	private boolean isExecuted = false;
}
