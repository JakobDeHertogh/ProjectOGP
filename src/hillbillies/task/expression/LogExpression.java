package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class LogExpression extends Expression<PosType> {

	@Override
	public PosType evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		// TODO Auto-generated method stub
		return new PosType(thisUnit.getNearestLog().getCubeCoordinate());
	}

}
