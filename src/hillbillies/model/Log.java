package hillbillies.model;

import ogp.framework.util.ModelException;

public class Log {
	
	public Log (World world, int[] startPosition) throws ModelException{
		this.world = world;
		
		int weight = (int)((Math.random()*this.weightRange)+this.minWeight);
		this.setWeight(weight);
		
		double[] pos = new double[startPosition.length];
		for (int i = 0;i<startPosition.length; i++){
			pos[i] = startPosition[i];
		}
		this.setPosition(pos);
		this.fallingTo = this.getzPosition();
	}
	
	public int[] getCubeCoordinate (){
		int[] cubecoordinate = new int[3];
		cubecoordinate[0] = (int)Math.floor(this.position[0]);
		cubecoordinate[1] = (int)Math.floor(this.position[1]);
		cubecoordinate[2] = (int)Math.floor(this.position[2]);
		return cubecoordinate;
	}
	
	
	public Cube occupiesCube(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	public Cube getCubeUnder(){
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]-1);
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public boolean isValidPosition(int [] position){
		if ((this.occupiesCube().isPassableType())&&( ! this.getCubeUnder().isPassableType()))
			return true;
		return false;
	}
	
	public double[] getPosition(){
		return this.position;
	}


	public void setPosition(double[] newPosition){
		this.position = newPosition;
	}
	
	public double getzPosition() {
		return this.position[2];
	}
		
	public boolean isValidWeight(int weight){
		return ((weight>lowerLimit)&&(weight<upperLimit));
	}
	
	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) throws ModelException{
		if (!isValidWeight(weight)){
			throw new ModelException("Invalid weight");
		}
		this.weight = weight;
		
	}
	
	public void advanceTime(double dt){
		if (this.fallingTo == this.getzPosition()){
			if (! this.isValidPosition(this.getCubeCoordinate()))
				this.fallingTo = this.getzPosition() -1;
		}
		else {
			if (this.fallingTo - this.getzPosition() <= dt*this.fallSpeed)
				this.position[2] = this.fallingTo;
			else 
				this.position[2] += dt*this.fallSpeed;
		}
	}
	
	
	private World world;
	private double[] position;
	

	private int weight;
	private int lowerLimit = 10;
	private int upperLimit = 50;
	private double fallingTo;
	private int fallSpeed = -3;
	
	private final int minWeight = 10;
	private final int maxWeight = 50;
	private final int weightRange = this.maxWeight - this.minWeight;
}
