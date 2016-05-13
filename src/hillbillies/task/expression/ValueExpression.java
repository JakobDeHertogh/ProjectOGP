package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class ValueExpression<E extends Type> extends Expression<E> {

	public ValueExpression(E value){
		this.value = value;
	}

	@Override
	public E evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		return this.value;
	}

	public E getValue(){
		return this.value;
	}
	private final E value;
}
