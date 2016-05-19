package hillbillies.task.statement;

import java.util.Map;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.Type;

public class AssignVarStatement extends Statement{
	
	public AssignVarStatement(String variableName, Expression newValue){
		this.variableName = variableName;
		this.newValue = newValue;
	}
	
	public String getVariableName(){
		return this.variableName;
	}
	
	public Expression getNewValue(){
		return this.newValue;
	}

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {
		
		if (globalVars.get(this.getVariableName()).getClass() != this.getNewValue().evaluate(globalVars, thisUnit).getClass())
			throw new ExecutionErrorException();
		
		globalVars.put(this.getVariableName(), this.getNewValue().evaluate(globalVars, thisUnit));
	}
	
	private final String variableName;
	private final Expression newValue;

}
