package hillbillies.expression.bool;
import hillbillies.expression.Expression;
import hillbillies.model.Unit;

public abstract class BooleanExpression extends Expression{
	
	public BooleanExpression(Unit unit){
		super(unit);
	}
	
	public abstract boolean Execute();
}
