package hillbillies.task.expression;
import hillbillies.task.type.Type;

public abstract class Expression<E extends Type>{
	
	public abstract E evaluate();
}
