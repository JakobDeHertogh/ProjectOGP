package hillbillies.model.objects;
import hillbillies.model.world.*;

public class Pathfinding {
	
	
	
	public Pathfinding(Unit unit, Cube start, Cube end){
		this.searchPath(start, end);
		this.setStart(start);
		this.setEnd(end);
	}
	
	public void searchPath(Cube start, Cube end){
		
	}
	
	public Cube getStart(){
		return this.start;
	}
	
	public void setStart(Cube start){
		this.start = start;
	}
	
	public Cube getEnd(){
		return this.end;
	}
	
	public void setEnd(Cube end){
		this.end = end;
	}
	
	
	private Cube start;
	private Cube end;	
}
