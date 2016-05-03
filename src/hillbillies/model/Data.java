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
	public Data(Cube cube, double priority){
		
		this.position = new int[]{cube.getXPosition(), cube.getYPosition(), cube.getZPosition()};
		this.setPriority(priority);
		this.cube = cube;
	}
	/**
	 * 
	 * @return
	 */
	public Cube getCube(){
		return this.cube;
	}
	/**
	 * 
	 * @return
	 */
	public int[] getPosition(){
		return this.position;
	}
	/**
	 * 
	 * @param cost
	 */
	public void setPriority(double priority){
		this.priority = priority;
	}
	/**
	 * Checks whether a given cost is a valid value.
	 * @return True if and only if the the cost is greater than or equal to 0.
	 * 			Else returns False.
	 */
	public boolean isValidCost(double priority){
		return priority>=0;
	}
	/**
	 * Returns the current cost trait of the Data
	 */
	public double getPriority(){
		return this.priority;
	}
	
	private int[] position;
	private double priority;
	private Cube cube;
}
