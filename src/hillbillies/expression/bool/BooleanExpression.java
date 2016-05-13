package hillbillies.expression.bool;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;

public abstract class BooleanExpression extends Expression{
	
	public BooleanExpression(Unit unit){
		super(unit);
	}
	
	public abstract boolean Execute();
}
