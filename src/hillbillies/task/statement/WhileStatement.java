package hillbillies.task.statement;

import java.util.*;

import hillbillies.exceptions.BreakException;
import hillbillies.exceptions.ExecutionErrorException;
import hillbillies.model.Unit;
import hillbillies.task.expression.Expression;
import hillbillies.task.type.*;

public class WhileStatement extends Statement{

	public WhileStatement(Expression<BoolType> condition, Statement whileBody){
		this.condition = condition;
		this.whileBody = whileBody;
	}
	
	public Expression<BoolType> getCondition(){
		return this.condition;
	}
	
	public Statement getWhileBody(){
		return this.whileBody;
	}
		
	public List<Statement> getSubStatements(){
		List<Statement> subStatements = new ArrayList<Statement>();
		subStatements.add(this.getWhileBody());
		return subStatements;
	}
	
	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) throws ExecutionErrorException {

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
			
			private void undoIteration(){
				this.isIterated = false;
			}
			
			private void breakWhile(){
				this.isBroken = true;
			}
			
			private boolean isBroken(){
				return this.isBroken;
			}
			
			private Iterator<Statement> getBodyIterator(){
				return this.bodyIterator;
			}
			
			private void setBodyIterator(Iterator<Statement> newIterator){
				this.bodyIterator = newIterator;
			}
						
			@Override
			public boolean hasNext() {
				return ! ((this.isIterated() && !expressionValue) || this.isBroken());
			}

			@Override
			public Statement next() {
				if (! this.hasNext())
					throw new NoSuchElementException();
				if (! this.isIterated()){
					this.iterate();
					return WhileStatement.this;
				}
				try{
					return this.bodyIterator.next();
				} catch (BreakException ex){
					this.breakWhile();
					return ex.getOrigin();
				} catch (NoSuchElementException exc){
					// einde while lus: voorwaarde opnieuw checken en body opnieuw itereren
					this.undoIteration();
					this.setBodyIterator(getWhileBody().iterator());
					return this.next();
				}
			}
			
			private boolean isIterated;
			private Iterator<Statement> bodyIterator = getWhileBody().iterator();
			private boolean isBroken;
		};
	}
	
	private Expression<BoolType> condition;
	private Statement whileBody;
	private boolean expressionValue; 
}
