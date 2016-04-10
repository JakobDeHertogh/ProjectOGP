package hillbillies.model;

import java.util.*;

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
	public int getCost(){
		return this.cost;
	}
	
private int[] position;
private int cost;
private Cube cube;
}
