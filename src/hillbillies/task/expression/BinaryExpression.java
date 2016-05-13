package hillbillies.task.expression;

import java.util.Map;
import java.util.function.BiFunction;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.Type;

public class BinaryExpression<A extends Type,B extends Type,E extends Type> extends Expression<E> {
	
	public BinaryExpression(BiFunction<A,B,E> operator, Expression<A> leftExpression, Expression<B> rightExpression){
		
		this.operator = operator;
		this.leftExpression = leftExpression; 
		this.rightExpression = rightExpression;
		
	}
	
	@Override
	public E evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		return this.getOperator().apply(this.getLeftExpression().evaluate(globalVars, thisUnit), 
				this.getRightExpression().evaluate(globalVars, thisUnit));
	}
		
	public BiFunction<A,B,E> getOperator(){
		return this.operator;
	}
	
	public Expression<A> getLeftExpression(){
		return this.leftExpression;
	}
	
	public Expression<B> getRightExpression(){
		return this.rightExpression;
	}
	
	private final BiFunction<A,B,E> operator;
	private final Expression<A> leftExpression;
	private final Expression<B> rightExpression;
	
}
