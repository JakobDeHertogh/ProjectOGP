package hillbillies.task.statement;

import java.util.*;

public class Statement implements Iterable<Statement>{

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
}
	
