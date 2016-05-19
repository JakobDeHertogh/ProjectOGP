package hillbillies.task.statement;

import java.util.Map;

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
	
	private Expression<UnitType> target;
	private boolean isBusy;
	private boolean isExecuted;
}
