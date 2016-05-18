package hillbillies.exceptions;

import hillbillies.task.statement.BreakStatement;

public class BreakException extends RuntimeException{
	
	public BreakException(BreakStatement origin){
		this.origin = origin;
	}

	public static final long serialVersionUID = 45621384L;

	public BreakStatement getOrigin() {
		return this.origin;
	}
	
	private BreakStatement origin;
}
