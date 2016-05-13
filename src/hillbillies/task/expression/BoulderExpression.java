package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class BoulderExpression extends Expression<PosType> {

	@Override
	public PosType evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		// TODO Auto-generated method stub
		return new PosType(thisUnit.getNearestBoulder().getCubeCoordinate());
	}

}
