package hillbillies.task.expression;

import hillbillies.task.type.Type;

public class ValueExpression<E extends Type> extends Expression<E> {

	public ValueExpression(E value){
		this.value = value;
	}

	@Override
	public E evaluate() {
		return this.value;
	}

	public E getValue(){
		return this.value;
	}
	private final E value;
}
