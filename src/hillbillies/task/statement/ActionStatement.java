package hillbillies.task.statement;

import java.util.Iterator;
import java.util.Map;

import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class ActionStatement extends Statement{

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {
		// check if action complete
	}
	
	@Override 
	public Iterator<Statement> iterator(){
		return new Iterator<Statement>(){

			private boolean isIterated(){
				return this.isIterated;
			}
			
			private void iterate(){
				this.isIterated = true;
			}
			
			@Override
			public boolean hasNext() {
				return ! this.isIterated();
			}

			@Override
			public Statement next() {
				// as long as action is not complete, this statement is 
				return null;
			}
			
			private boolean isIterated;
		};
	}

	
}
