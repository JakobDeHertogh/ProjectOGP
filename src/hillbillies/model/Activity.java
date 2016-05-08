package hillbillies.model;

public enum Activity {
	WORK(){
		public void defaultAction(Unit unit){
			Cube unitCube = unit.occupiesCube();
			int x = unitCube.getSurroundingCubes().size();
			int i = 0;
			for (Cube c : unitCube.getSurroundingCubes()){
				if (i == x)
					unit.workAt(c.getXPosition(), c.getYPosition(), c.getZPosition());
				i++;
			}
				
		}
	},
	REST(),
	MOVE(),
	FIGHT();
	
	public abstract void defaultAction(Unit unit);
}
