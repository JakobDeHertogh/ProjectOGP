package hillbillies.task.action;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;
import ogp.framework.util.ModelException;

public class MoveAction extends Action{


	public MoveAction(Expression<PosType> target, Map<String, Type> globalVars, Unit thisUnit){
		this.thisUnit = thisUnit;
		this.target = target;
		this.globalVars = globalVars;
	} 

	@Override
	public boolean isBusy(){
		return this.isBusy;
	}
	
	@Override
	public void start() throws ModelException{
		this.thisUnit.moveTo(this.target.evaluate(this.globalVars, this.thisUnit).getValue());
		this.isBusy = true;
	}
	@Override
	public boolean isExecuted() {
		
	}

	private final Unit thisUnit;
	private final Expression<PosType> target;
	private Map<String, Type> globalVars;
	private boolean isBusy;
}
