package hillbillies.model.objects;
import hillbillies.model.world.*;

public class Path {
	
	
	
	public Path(Unit unit, Cube start, Cube end){
		this.searchPath(start, end);
		this.setStart(start);
		this.setEnd(end);
	}
	
	public double CalcCost(Cube a, Cube b){
		int[] aPos = a.getPosition();
		int[] bPos = b.getPosition();
				
		
		return 0;
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
