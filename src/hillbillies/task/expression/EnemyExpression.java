package hillbillies.task.expression;

import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.*;

public class EnemyExpression extends Expression<UnitType> {

	@Override
	public UnitType evaluate(Map<String, Type> globalVars, Unit thisUnit) {
		// TODO Auto-generated method stub
		return new UnitType(thisUnit.getNearestEnemy());
	}

}
