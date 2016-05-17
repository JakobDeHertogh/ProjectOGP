package hillbillies.task.statement;

import java.util.*;

import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public abstract class Statement implements Iterable<Statement>{

	public Statement(){
		
	}

	@Override
	public Iterator<Statement> iterator() {
		return new Iterator<Statement>(){
			
			@Override
			public boolean hasNext(){
				return false;
				
			}
			
			@Override
			public Statement next(){
				return null;
			}
		};
	}
	
	List<Statement> getSubStatements(){
		return new ArrayList<Statement>();
	}
	
	public abstract void execute(Map<String, Type> globalVars, Unit thisUnit);
	
}
	
