package hillbillies.task.expression;

import java.util.function.Function;

import hillbillies.task.type.Type;

public class UnaryExpression<A extends Type,E extends Type> extends Expression<E> {
	
	public UnaryExpression(Function<A,E> operator, Expression<A> expression){
		this.operator = operator;
		this.expression = expression;
	}
	
	@Override
	public E evaluate() {
		return this.getOperator().apply(this.getExpression().evaluate());
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
