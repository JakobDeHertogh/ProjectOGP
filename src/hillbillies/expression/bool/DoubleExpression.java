package hillbillies.expression.bool;

import hillbillies.model.Unit;

public abstract class DoubleExpression extends BooleanExpression{
	
	public DoubleExpression(Unit unit, BooleanExpression left, BooleanExpression right){
		super(unit);
		this.setLeft(left);
		this.setRight(right);
	}
	
	public void setLeft(BooleanExpression left){
		this.left = left;
	}
	
	public void setRight(BooleanExpression right){
		this.right = right;
	}
	
	public abstract boolean

	
	
	private BooleanExpression left;
	private BooleanExpression right;
}
