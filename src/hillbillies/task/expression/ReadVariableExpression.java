package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class ReadVariableExpression<E extends Type> extends Expression<E> {

	public ReadVariableExpression(String variableName){
		this.variableName = variableName;
	}
	@Override
	public E evaluate(Map<String,Type> globalVars, Unit thisUnit) {
		return (E) globalVars.get(this.variableName);
	}
	
	private final String variableName;

}
