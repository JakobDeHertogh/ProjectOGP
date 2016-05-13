package hillbillies.task.type;

public class PosType extends Type{

	public PosType(int[] position){
		this.value = position;
	}
	
	public int[] getValue(){
		return this.value;
	}
	
	private final int[] value;
}
