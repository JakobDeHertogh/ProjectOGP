package hillbillies.model;

public enum CubeType {
	
	AIR(0, true),
	ROCK(1, false),
	WOOD(2, false),
	WORKSHOP(3, true);
	
	private CubeType(int value, boolean isPassable){
		this.value = value;
		this.isPassable = isPassable;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public boolean isPassable(){
		return isPassable;
	}
	
	private int value;
	private boolean isPassable;
	
	public static CubeType getCubeTypeOfValue(int value){
		for (CubeType ct: CubeType.values())
			if (ct.value == value)
				return ct;
		return null;
	}
}
