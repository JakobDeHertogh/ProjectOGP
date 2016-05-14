package hillbillies.task.expression;

import java.util.Map;
import java.util.function.*;

import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class UnaryUnitExpression<A extends Type, E extends Type> extends Expression<E> {
	
	public UnaryUnitExpression(BiFunction<A,UnitType,E> operator, Expression<A> expression){
		this.operator = operator;
		this.expression = expression;
	}
	
	@Override
	public E evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		return this.getOperator().apply(expression.evaluate(globalVars, thisUnit),  new UnitType(thisUnit));
	}

	public BiFunction<A,UnitType,E> getOperator(){
		return this.operator;
	}
	
	public Expression<A> getExpression(){
		return this.expression;
	}
	
	private final BiFunction<A,UnitType,E> operator;
	private final Expression<A> expression;
}
