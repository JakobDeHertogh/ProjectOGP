package hillbillies.model;



import java.util.HashSet;
import java.util.Set;

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
	
	public boolean isSolidConnectedToBorder(){
		if (this.isPassableType())
			return false;
		
		else {
			for (Cube i : this.dirAdjacant){
				if (i == null)
					return true;
				else 
					return i.isSolidConnectedToBorder();
			}
			return false;
		}
			
	}
	
	public Set<Cube> dirAdjacant = new HashSet<Cube>();
	
	private final World world;
	private final int xPosition;
	private final int yPosition;
	private final int zPosition;
	private CubeType cubetype;
}
