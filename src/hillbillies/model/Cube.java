package hillbillies.model;



import java.util.HashSet;
import java.util.Set;

import ogp.framework.util.ModelException;

public class Cube {
	
	public Cube(World World, int xPosition, int yPosition, int zPosition, CubeType type){
		this.world = World;
		this.xPosition = xPosition;
		this.yPosition = yPosition; 
		this.zPosition = zPosition;
		this.cubetype = type;
		
		this.getAdjacants();
				
	}
	
	public int getXPosition(){
		return this.xPosition;
	}
	public int getYPosition(){
		return this.yPosition;
	}
	public int getZPosition(){
		return this.zPosition;
	}
	
	public CubeType getType(){
		return this.cubetype;
	}
	
	public void setCubeType(CubeType type){
		this.cubetype = type;
	}
	
	public boolean isPassableType(){
		return this.getType().isPassable();
	}
	
	public Set<Cube> getAdjacants()throws IndexOutOfBoundsException{
		int[] xPlusAdj = {this.getXPosition()+1,this.getYPosition(),this.getZPosition()};
		int[] xMinAdj = {this.getXPosition()-1,this.getYPosition(),this.getZPosition()};
		int[] yPlusAdj = {this.getXPosition(),this.getYPosition()+1,this.getZPosition()};
		int[] yMinAdj = {this.getXPosition(),this.getYPosition()-1,this.getZPosition()};
		int[] zPlusAdj = {this.getXPosition(),this.getYPosition(),this.getZPosition()+1};
		int[] zMinAdj = {this.getXPosition(),this.getYPosition(),this.getZPosition()-1};
		int[][] AdjPos = {xPlusAdj, xMinAdj, yPlusAdj,yMinAdj,zPlusAdj,zMinAdj};
		
		for (int[] i : AdjPos) {
			try{
				this.dirAdjacant.add(this.world.getCubeAtPos(i[0], i[1], i[2]));
			} catch (IndexOutOfBoundsException exc){
				this.dirAdjacant.add(null);
			}
			
		}
		return this.dirAdjacant;
					
	}
	
	public void caveIn() throws ModelException {
		this.setCubeType(CubeType.AIR);
		double P = Math.random();
		double PLog = 0.125;
		double PBoulder = 0.125;
		if (P <= PLog){
			this.world.addLog(new int[]{this.getXPosition(), this.getYPosition(), this.getZPosition()});
		} 
		if ((P > PLog) && (P <= PBoulder)){
			this.world.addBoulder(new int[]{this.getXPosition(),  this.getYPosition(), this.getZPosition()});
		}
	}
		
	public Set<Cube> dirAdjacant = new HashSet<Cube>();
	
	private final World world;
	private final int xPosition;
	private final int yPosition;
	private final int zPosition;
	private CubeType cubetype;

}
