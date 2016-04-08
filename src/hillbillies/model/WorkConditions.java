package hillbillies.model;

public enum WorkConditions {
	PUTDOWNLOG(){
		public boolean check(Unit unit, Cube cube){
			boolean a = unit.isCarryingLog(); 
			boolean b = cube.isPassableType();
			return a&&b;
		}
	},
	PUTDOWNBOULDER(){
		public boolean check(Unit unit, Cube cube){
			boolean a = unit.isCarryingBoulder();
			boolean b = cube.isPassableType();
			return a&&b;
		}
		
	},
	WORKSHOP(){
		public boolean check(Unit unit, Cube cube){
			boolean a = cube.getType() == CubeType.WORKSHOP;
			boolean b = !cube.isOccupiedByBoulders().isEmpty();
			boolean c = !cube.isOccupiedByLogs().isEmpty();
			return a&&b&&c;
		}
		
		
		
	},
	PICKUPLOG(){
		public boolean check(Unit unit, Cube cube){
			boolean a = !unit.isCarryingLog();
			boolean b = !unit.isCarryingBoulder();
			boolean c = !cube.isOccupiedByLogs().isEmpty();
			return a&&b&&c;
		}
		
	},
	PICKUPBOULDER(){
		public boolean check(Unit unit, Cube cube){
			boolean a =	!unit.isCarryingLog();
			boolean b = !unit.isCarryingBoulder();
			boolean c = !cube.isOccupiedByBoulders().isEmpty();
			return a&&b&&c;
		}
	}, 
	CHOPWOOD(){
		public boolean check(Unit unit, Cube cube){
			boolean a = cube.getType() == CubeType.WOOD;
			return a;
		}
		
	},
	MINEROCK(){
		public boolean check(Unit unit, Cube cube){
			boolean a = cube.getType() == CubeType.ROCK;
			return a;
		}
		
	};
	
	private WorkConditions(){
		
	}
	
	public abstract boolean check(Unit uit, Cube cube);
		
	
}
