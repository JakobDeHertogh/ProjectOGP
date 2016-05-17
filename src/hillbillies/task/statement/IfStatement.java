package hillbillies.task.statement;

import java.util.*;
import java.util.Map;

import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;

public class IfStatement extends Statement{

	public IfStatement(Expression<? extends Type> condition, Statement ifBody, Statement elseBody){
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
	}
	
	public Expression<? extends Type> getCondition(){
		return this.condition;
	}
	
	public Statement getIfBody(){
		return this.ifBody;
	}
	
	public Statement getElseBody(){
		return this.elseBody;
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
				if ()
			}
			
			private final Iterator<Statement> ifBodyIterator = getIfBody().iterator();
			private final Iterator<Statement> elseBodyIterator = getElseBody().iterator();
			private boolean isIterated;
		};
	}

	
	private final Expression<? extends Type> condition;
	private boolean expressionValue;
	private final Statement ifBody;
	private final Statement elseBody;
}
