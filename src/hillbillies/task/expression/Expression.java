package hillbillies.task.expression;
import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public abstract class Expression<E extends Type>{
	
	public abstract E evaluate(Map<String, Type> globalVars, Unit thisUnit);
}
