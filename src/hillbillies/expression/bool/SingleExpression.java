package hillbillies.expression.bool;
import hillbillies.model.Unit;

public class SingleExpression extends BooleanExpression {
	
	
	public SingleExpression(Unit unit, BooleanExpression booleanexpression){
		super(unit);
		this.setExpression(booleanexpression);
	}
	
	public void setExpression(BooleanExpression booleanexpression){
		if (isValidExpression(booleanexpression)){
			this.expression = booleanexpression;
		}
		
	}
	
	public BooleanExpression getExpression(){
		return this.expression;
	}
	
	public boolean isValidExpression(BooleanExpression expression){
		return (expression!=null);
	}

	
	
	private BooleanExpression expression;
}
