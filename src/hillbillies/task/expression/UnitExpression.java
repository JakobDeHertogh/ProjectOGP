package hillbillies.task.expression;

import java.util.Map;
import java.util.function.*;
import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class UnitExpression<E extends Type> extends Expression<E> {

	public UnitExpression(Function<UnitType,E> operator){
		this.operator = operator;
	}
	
	@Override
	public E evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		return this.getOperator().apply(new UnitType(thisUnit));
	}
	
	public Function<UnitType,E> getOperator(){
		return this.operator;
	}
	
	private final Function<UnitType,E> operator;

}
