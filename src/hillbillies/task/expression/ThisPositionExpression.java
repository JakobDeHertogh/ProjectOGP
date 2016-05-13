package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class ThisPositionExpression extends Expression<PosType> {

	public ThisPositionExpression(){
		// Moet hier iets? Is deze eigenlijk zelfs nodig? 
	}
	
	@Override
	public PosType evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		// TODO Auto-generated method stub
		return new PosType(thisUnit.getCubeCoordinate());
	}
	
}
