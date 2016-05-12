package hillbillies.expression.bool;
import hillbillies.model.Unit;

public class True extends BooleanExpression{
	
	public True(Unit unit){
		super(unit);
	}
	
	public boolean Execute(){
		return true;
	}
}
