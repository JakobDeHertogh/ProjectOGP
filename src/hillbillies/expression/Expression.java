package hillbillies.expression;
import hillbillies.model.Unit;

public abstract class Expression{
	
	public Expression(Unit unit){
		this.setUnit(unit);
	}
	
	public void setUnit(Unit unit){
		if(isValidUnit(unit)){
			this.unit = unit;
		}
	}
	
	public boolean isValidUnit(Unit unit){
		return true;
	}
	
	public Unit getUnit(){
		return this.unit;
	}
	
	
	private Unit unit;
}
