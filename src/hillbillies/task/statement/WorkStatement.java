package hillbillies.task.statement;

import java.util.Map;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;
import ogp.framework.util.ModelException;

public class WorkStatement extends ActionStatement {

	public WorkStatement(Expression<PosType> target) {
		super(target);
		
		this.target = target;
	}
	
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException{
		if (thisUnit.getActivity() == null){
			if (this.isBusy){
				this.isBusy = false;
				this.isExecuted = true;
			}
			else{
				try{
					int[] targetPos = target.evaluate(globalVars, thisUnit).getValue();
					thisUnit.workAt(targetPos[0], targetPos[1], targetPos[2]);
					this.isBusy = true;
				} catch (ModelException ex){
					throw new ExecutionErrorException();
				}
			}
		}
	}
	

	private Expression<PosType> target;
	private boolean isExecuted = false;
	private boolean isBusy = false;

}
