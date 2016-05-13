package hillbillies.task.expression;

import java.util.Map;
import java.util.function.Function;

import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class UnaryExpression<A extends Type,E extends Type> extends Expression<E> {
	
	public UnaryExpression(Function<A,E> operator, Expression<A> expression){
		this.operator = operator;
		this.expression = expression;
	}
	
	@Override
	public E evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		return this.getOperator().apply(this.getExpression().evaluate(globalVars, thisUnit));
	}

	public Function<A,E> getOperator(){
		return this.operator;
	}
	
	public Expression<A> getExpression(){
		return this.expression;
	}
	
	private final Function<A,E> operator;
	private final Expression<A> expression;
}
