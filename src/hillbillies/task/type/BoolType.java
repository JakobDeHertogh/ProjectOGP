package hillbillies.task.type;

public class BoolType extends Type{
	
	public BoolType(boolean value){
		this.value = value;
	}
	
	public boolean getValue(){
		return this.value;
	}
	
	private final boolean value;
}
