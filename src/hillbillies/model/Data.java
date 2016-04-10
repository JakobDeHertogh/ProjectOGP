package hillbillies.model;

import java.util.*;

/**
 * 
 * @author Kristof Van Cappellen
 * @author Jakob De Hertogh
 * A class of cube Data. Each Data is created for a specific cube, and has a position, and a cost.
 * @invar The cost-trait of the Data is always greater or equal to 0
 * 			|isValidCost(cost);
 *
 */
public class Data {
	public Data(Cube cube, int cost){
		this.position[0] = cube.getXPosition();
		this.position[1] = cube.getYPosition();
		this.position[2] = cube.getZPosition();
		this.setCost(cost);
		this.cube = cube;
	}
	
	public Cube getCube(){
		return this.cube;
	}
	
	public int[] getPosition(){
		return this.position;
	}
	public void setCost(int cost){
		this.cost = cost;
	}
	public boolean isValidCost(int cost){
		return cost>=0;
	}
	public int getCost(){
		return this.cost;
	}
	
private int[] position;
private int cost;
private Cube cube;
}
