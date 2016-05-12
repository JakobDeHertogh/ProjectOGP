package hillbillies.expression.bool;
import hillbillies.model.Unit;


public class False extends BooleanExpression {
	
	public False(Unit unit){
		super(unit);
	}
	
	public boolean Execute(){
		return false;
	}
}
