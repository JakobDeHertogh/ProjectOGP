package hillbillies.model;
import java.util.*;

public class Path {
	
	
	/**
	 * 
	 * @param start
	 * @param end
	 */
	public Path(Cube start, Cube end){
		
		
		// HET VINDEN VAN HET OMGEKEERDE PAD (Opzoeken in came_from geeft de vorige cube in de gevolgde weg)
		LinkedList<Data> frontier = new LinkedList<Data>();
		Data startData = new Data(start, 0);
		frontier.add(startData);
		HashMap<Cube, Cube>  came_from= new HashMap<Cube, Cube>();
		HashMap<Cube, Double> Cost_so_far = new HashMap<Cube, Double>();
		Stack<Cube> Path = new Stack<Cube>();
		came_from.put(start, null);
		Cost_so_far.put(start, 0.0);
		
		
		
		while (!frontier.isEmpty()){
			Cube current = frontier.remove().getCube();
			
			if (current == end) {
				this.came_from = came_from;
				this.Cost_so_far = Cost_so_far;
				break;
			}
			
			for (Cube next: current.getSurroundingCubes()){
				try {
					double new_cost = Cost_so_far.get(current)+ Math.abs(next.getXPosition()-current.getXPosition()) +Math.abs(next.getZPosition()-current.getZPosition()) + Math.abs(next.getYPosition()-current.getYPosition());
					if (((!Cost_so_far.containsKey(next))|| (new_cost < Cost_so_far.get(next))) && (next.isValidCube())){
						Cost_so_far.put(next, new_cost);
						double priority = new_cost + CalcCost(end, next);
						Data nextData = new Data(next, priority);
						frontier.add(nextData);
						came_from.put(next, current);
						
					}
				} catch (NullPointerException e) {
					continue;
				}
			}
		}
		// HET OPBOUWEN VAN HET PAD IN DE JUISTE RICHTING
		Cube latestCube = end;
		while(came_from.get(latestCube) != null){
			Path.push(latestCube);
			latestCube = came_from.get(latestCube);
		}
		this.path = Path;
		
		
		this.setStart(start);
		this.setEnd(end);
	}
	/**
	 * 
	 * @param a The starting Cube, from which the cost will be calculated to the end Cube.
	 * @param b The end Cube to which the cost will be calculated from the starting Cube.
	 * @return
	 */
	public double CalcCost(Cube a, Cube b){
		int[] aPos = a.getPosition();
		int[] bPos = b.getPosition();
		return Math.sqrt(Math.pow((aPos[0]-bPos[0]),2) + Math.pow((aPos[1]-bPos[1]),2) + Math.pow((aPos[2]-bPos[2]),2));
	}
	/**
	 * Returns the path.
	 * @return
	 */
	public Stack<Cube> getRoute(){
		return this.path;
	}

	/**
	 * Returns the starting cube of the path.
	 * @return
	 */
	public Cube getStart(){
		return this.start;
	}
	
	/**
	 * Sets the starting cube of the path.
	 * @param start
	 */
	public void setStart(Cube start){
		this.start = start;
	}
	
	/**
	 * Returns the end Cube of the path/
	 * @return
	 */
	public Cube getEnd(){
		return this.end;
	}
	
	/**
	 * Sets the end Cube of the path.
	 * @param end
	 */
	public void setEnd(Cube end){
		this.end = end;
	}
	
	
	private Cube start;
	private Cube end;
	private HashMap<Cube, Cube>  came_from;
	private HashMap<Cube, Double> Cost_so_far;
	private Stack<Cube> path;
}
