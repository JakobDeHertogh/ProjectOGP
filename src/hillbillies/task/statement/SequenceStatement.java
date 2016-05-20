package hillbillies.task.statement;

import java.util.*;

import hillbillies.model.Unit;
import hillbillies.task.type.Type;

public class SequenceStatement extends Statement{

	public SequenceStatement(List<Statement> statements){
		this.subStatements = statements;
	}
	
	@Override
	public List<Statement> getSubStatements(){
		return this.subStatements;
	}
	@Override
	public void execute(Map<String, Type> globalVars, Unit thisUnit) {
		// doet niks
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
						
			// Iterator over huidig substatement
			private void setCurrStatementIterator(Iterator<Statement> iterator){
				this.currStatementIterator = iterator;
			}
			
			private Iterator<Statement> getCurrStatementIterator(){
				return this.currStatementIterator;
			}
			
			// Iterator over alle substatements
			private Iterator<Statement> getStatementIterator(){
				return this.statementIterator;
			}
			
			@Override
			public boolean hasNext(){
				//Enkel geen next als laatste substatement volledig geïtereerd is. 
				return ((! this.isIterated()) || (this.currStatementIterator.hasNext()) || 
						(this.statementIterator.hasNext()));
			}
			
			public Statement next(){
				if (! this.hasNext())
					throw new NoSuchElementException();
				if (! this.isIterated()){ //huidig statement nog niet uitgevoerd => uitvoeren
					this.iterate();
					// => volgende substatement iterator instellen
					this.setCurrStatementIterator(this.getStatementIterator().next().iterator());
					return SequenceStatement.this;
				}
				else{
					try{ //itereer verder over huidig statement;
						return this.getCurrStatementIterator().next();
					} catch (NoSuchElementException ex){ // of ga naar volgend substatement. 
						this.setCurrStatementIterator(this.getCurrStatementIterator().next().iterator());
						return this.next();
					}
				}
			}
			
			private boolean isIterated;
			private Iterator<Statement> currStatementIterator;
			private Iterator<Statement> statementIterator;
		};
	}


	private final List<Statement> subStatements;
}
