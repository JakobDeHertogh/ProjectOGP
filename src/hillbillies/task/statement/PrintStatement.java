package hillbillies.task.statement;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.Type;

public class PrintStatement extends Statement{

	public PrintStatement(Expression<? extends Type> value){
		this.value = value;
	}
	
	public Expression<? extends Type> getValue(){
		return this.value;
	}
	
	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit){
		System.out.println(this.getValue().evaluate(globalVars, thisUnit));
	}
	
	private final Expression<? extends Type> value;
}
