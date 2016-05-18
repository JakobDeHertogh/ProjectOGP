package hillbillies.task.statement;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import hillbillies.exceptions.BreakException;
import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class BreakStatement extends Statement{
	
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
			public boolean hasNext(){
				return (! this.isIterated());
			}
			
			@Override
			public Statement next(){
				if (! this.hasNext())
					throw new NoSuchElementException();
				else{
					this.iterate();
					throw new BreakException(BreakStatement.this);
				}
			}
			
			private boolean isIterated;
		};

	}

	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {}

	
}
