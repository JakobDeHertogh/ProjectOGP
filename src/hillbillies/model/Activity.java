package hillbillies.model;

import ogp.framework.util.ModelException;

public enum Activity {
	WORK(){
		public void defaultAction(Unit unit) throws ModelException{
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
	REST(){
		public void defaultAction(Unit unit){
			unit.rest();
		}
	},
	MOVE(){
		public void defaultAction(Unit unit) throws ModelException{
			double[] randomPos = new double[]{unit.getWorld().getNbCubesX()+1,0,0};
			int[] target = new int[3];
			while (! unit.isValidPosition(randomPos)){
				Cube a = unit.getWorld().getRandomSpawnCube();
				target = a.getPosition();
			}
			unit.moveTo(target);
		}
	},
	FIGHT(){
		public void defaultAction(Unit unit) throws ModelException{
			outerloop:
			for (Cube c : unit.occupiesCube().getSurroundingCubes()){
				if (c.isOccupiedByUnits().isEmpty())
					continue;
				for (Unit u : c.isOccupiedByUnits())
					try{
						unit.attack(u);
						break outerloop;
					} catch (ModelException e){
						continue;
					}		
			}
		}
	};
	
	public abstract void defaultAction(Unit unit) throws ModelException;
}
