package hillbillies.task.statement;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;

public class IfStatement extends Statement{

	public IfStatement(Expression<BoolType> condition, Statement bodyIf, Statement bodyElse){
		this.condition = condition;
		this.bodyIf = bodyIf;
		this.bodyElse = bodyElse;
	}
	
	public Expression<BoolType> getCondition(){
		return this.condition;
	}
	
	public Statement getBodyIf(){
		return this.bodyIf;
	}
	
	public Statement getBodyElse(){
		return this.bodyElse;
	}

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) {
		if (this.condition.evaluate(globalVars, thisUnit).getValue() == true){
			this.getBodyIf().execute(globalVars, thisUnit);
		}
		else 
			this.getBodyElse().execute(globalVars, thisUnit);
	}
	
	private final Expression<BoolType> condition;
	private final Statement bodyIf;
	private final Statement bodyElse;
}
