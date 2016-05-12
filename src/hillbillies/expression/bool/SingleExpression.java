package hillbillies.expression.bool;
import hillbillies.model.Unit;

public abstract class SingleExpression extends BooleanExpression {
	
	
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
	
	@Override
	public void setUnit(Unit unit){
		super.setUnit(unit);
		
		getExpression().setUnit(unit);
	}
	
	@Override
	public abstract boolean Execute();
	
	private BooleanExpression expression;
	private Unit unit;

}
