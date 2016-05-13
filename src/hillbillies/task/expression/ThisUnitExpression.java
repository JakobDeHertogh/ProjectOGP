package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class ThisUnitExpression extends Expression<UnitType> {

	public ThisUnitExpression(){
		//Volgens mij is dit niet nodig. 
	}
	
	@Override
	public UnitType evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		// TODO Auto-generated method stub
		return new UnitType(thisUnit);
	}

}
