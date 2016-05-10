 package hillbillies.model;

import ogp.framework.util.ModelException;

public enum WorkTypes {
	PUTDOWNLOG(){
		public boolean check(Unit unit, Cube cube){
			boolean a = unit.isCarryingLog(); 
			boolean b = cube.isPassableType();
			return a&&b;
		}
		
		public void execute(Unit unit, Cube cube){
			unit.putDownLog(cube);
		}
	},
	PUTDOWNBOULDER(){
		public boolean check(Unit unit, Cube cube){
			boolean a = unit.isCarryingBoulder();
			boolean b = cube.isPassableType();
			return a&&b;
		}
		
		public void execute(Unit unit, Cube cube){
			unit.putDownBoulder(cube);
		}
		
	},
	WORKSHOP(){
		public boolean check(Unit unit, Cube cube){
			boolean a = cube.getType() == CubeType.WORKSHOP;
			boolean b = !cube.getBoulders().isEmpty();
			boolean c = !cube.getLogs().isEmpty();
			return a&&b&&c;
		}
		
		public void execute(Unit unit, Cube cube){
			cube.randomLog().terminate();
			cube.randomBoulder().terminate();
			unit.setWeight(unit.getWeight() + 2);
			unit.setToughness(unit.getToughness() + 2);
		}
		
	},
	PICKUPLOG(){
		public boolean check(Unit unit, Cube cube){
			boolean a = !unit.isCarryingLog();
			boolean b = !unit.isCarryingBoulder();
			boolean c = !cube.getLogs().isEmpty();
			return a&&b&&c;
		}
		
		public void execute(Unit unit, Cube cube){
			Log log = cube.randomLog();
			unit.pickUpLog(log);
		}
		
	},
	PICKUPBOULDER(){
		public boolean check(Unit unit, Cube cube){
			boolean a =	!unit.isCarryingLog();
			boolean b = !unit.isCarryingBoulder();
			boolean c = !cube.getBoulders().isEmpty();
			return a&&b&&c;
		}
		
		public void execute(Unit unit, Cube cube){
			Boulder boulder = cube.randomBoulder();
			unit.pickUpBoulder(boulder);
		}
	}, 
	CHOPWOOD(){
		public boolean check(Unit unit, Cube cube){
			boolean a = cube.getType() == CubeType.WOOD;
			return a;
		}
		
		public void execute(Unit unit, Cube cube){
			try {
				cube.caveIn();
				cube.getWorld().caveInCubes.addAll(cube.getWorld().getCTB().changeSolidToPassable(
						cube.getXPosition(), cube.getYPosition(), cube.getZPosition()));
			} catch (ModelException e) {
			}
		}
	},
	MINEROCK(){
		public boolean check(Unit unit, Cube cube){
			boolean a = cube.getType() == CubeType.ROCK;
			return a;
		}
		
		public void execute(Unit unit, Cube cube){
			try {
				cube.caveIn();
				cube.getWorld().caveInCubes.addAll(cube.getWorld().getCTB().changeSolidToPassable(
						cube.getXPosition(), cube.getYPosition(), cube.getZPosition()));
			} catch (ModelException e) {
			}
		}
		
	};
	
	private WorkTypes(){
		
	}
	
	public abstract boolean check(Unit uit, Cube cube);
	public abstract void execute(Unit unit, Cube cube);
		
	
}
