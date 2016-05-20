package hillbillies.task.statement;

import java.util.*;
import java.util.Map;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;

public abstract class ActionStatement extends Statement{

	public ActionStatement(Expression<? extends Type> target){
		this.target = target;
	}
	
	@Override
	public abstract void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException;

	private Expression<? extends Type> target;
}
