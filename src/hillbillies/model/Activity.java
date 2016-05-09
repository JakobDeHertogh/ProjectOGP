package hillbillies.model;

import java.util.Arrays;

import ogp.framework.util.ModelException;

public enum Activity {
	WORK(){
		public void defaultAction(Unit unit) throws ModelException{
			Cube unitCube = unit.occupiesCube();
			for (Cube c : unitCube.getSurroundingCubes()){
				try{
					unit.workAt(c.getXPosition(), c.getYPosition(), c.getZPosition());
				} catch (NullPointerException ex){
					continue;
				}
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
//			double[] randomPos = new double[]{unit.getWorld().getNbCubesX()+1,0,0};
//			int[] target = new int[3];
//			while (! unit.isValidPosition(randomPos)){
//				System.out.println(Arrays.toString(randomPos));
//				Cube a = unit.getWorld().getRandomSpawnCube();
//				target = a.getPosition();
//			}
			for (Cube c : unit.getWorld().viableSpawnCubes){
				try{
					unit.moveTo(c.getPosition());
					break;
				} catch (ModelException ex){
					continue;
				}
			}
		}
	},
	FIGHT(){
		public void defaultAction(Unit unit) throws ModelException{
			for (Unit u : unit.getWorld().getActiveUnits()){
				try{
					unit.fight(u);
					break;
				} catch (ModelException ex){
					continue;
				}
			}				
		
		}
	};
	
	public abstract void defaultAction(Unit unit) throws ModelException;
}
