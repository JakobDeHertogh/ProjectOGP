package hillbillies.task.statement;

import java.util.Map;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class AssignVarStatement extends Statement{
	
	public AssignVarStatement(String variableName, Type newType){
		this.variableName = variableName;
		this.newType = newType;
	}
	
	public String getVariableName(){
		return this.variableName;
	}
	
	public Type getNewType(){
		return this.newType;
	}

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {
		
		if (globalVars.get(this.getVariableName()).getClass() != this.getNewType().getClass())
			throw new ExecutionErrorException();
		
		globalVars.put(this.getVariableName(), this.getNewType());
	}
	
	private final String variableName;
	private final Type newType;

}
