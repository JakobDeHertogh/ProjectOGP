package hillbillies.expression.bool;
import hillbillies.model.Unit;

public class Invert extends SingleExpression {
	
	public Invert(Unit unit, BooleanExpression expression){
		super(unit, expression);
		
	}
	
	@Override
	public boolean Execute(){
		return (!(super.getExpression().Execute()));
	}
	
	
}
