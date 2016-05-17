package hillbillies.task.statement;

import java.util.*;
import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;

public class IfStatement extends Statement{

	public IfStatement(Expression<BoolType> condition, Statement ifBody, Statement elseBody){
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression<BoolType> getCondition(){
		return this.condition;
	}
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	public Statement getElseBody(){
		return this.elseBody;
	}
	
	@Override
	public List<Statement> getSubStatements(){
		List<Statement> substatements = new ArrayList<>();
		substatements.add(getIfBody());
		substatements.add(getElseBody());
		return substatements;
	}
	
	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) {
		this.expressionValue = this.getCondition().evaluate(globalVars, thisUnit).getValue();
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
			
			private Iterator<Statement> getIfBodyIterator(){
				return this.ifBodyIterator;
			}
			
			private Iterator<Statement> getElseBodyIterator(){
				return this.elseBodyIterator;
			}
			
			@Override
			public boolean hasNext(){
				if (! this.isIterated())
					return true;
				if (expressionValue)
					return this.getIfBodyIterator().hasNext();
				else 
					return this.getElseBodyIterator().hasNext();
			}
			
			@Override
			public Statement next(){
				if (! this.hasNext())
					throw new NoSuchElementException();
				if (! isIterated()){
					this.iterate();
					return IfStatement.this;
				}
				if (expressionValue)
					return this.getIfBodyIterator().next();
				else 
					return this.getElseBodyIterator().next();
			}
			
			private final Iterator<Statement> ifBodyIterator = getIfBody().iterator();
			private final Iterator<Statement> elseBodyIterator = getElseBody().iterator();
			private boolean isIterated;
		};
	}

	
	private final Expression<BoolType> condition;
	private boolean expressionValue;
	private final Statement ifBody;
	private final Statement elseBody;
}
