package hillbillies.task.type;

import hillbillies.model.Unit;

public class UnitType extends Type{
	
	public UnitType(Unit unit){
		this.value = unit;
	}
	
	public Unit getValue(){
		return this.value;
	}
	
	private final Unit value;
}
