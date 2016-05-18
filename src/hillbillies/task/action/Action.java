package hillbillies.task.action;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.Type;
import ogp.framework.util.ModelException;

public abstract class Action {
	
	public abstract void start() throws ModelException;
	
	public abstract boolean isBusy();
	
	public abstract boolean isExecuted();
}
