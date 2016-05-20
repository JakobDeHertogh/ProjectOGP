package hillbillies.model;
import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class of Paths, each with a start, end, 
 * @author Kristof Van Cappellen & Jakob De Hertogh
 *
 */
public class Path {
	
	
	/**
	 * 
	 * @param start The Cube to set this new Path's start on.
	 * @param end	The Cube to set this new Path's end on.
	 * @effect	The start of this new Path is set to the given Cube.
	 * @effect	The end of this new Path is set to the given Cube.
	 */
	public Path(Cube start, Cube end){
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
					double new_cost = Cost_so_far.get(current)+ Math.abs(next.getXPosition()-current.getXPosition()) 
					+Math.abs(next.getZPosition()-current.getZPosition()) + Math.abs(next.getYPosition()-current.getYPosition());
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
	 * Calculate the cost for going from Cube a to Cube b.
	 * @param a The starting Cube, from which the cost will be calculated to the end Cube.
	 * @param b The end Cube to which the cost will be calculated from the starting Cube.
	 * @return	The cost of going from a to b, via the formula for the length of a vector from a to b.
	 * 			|result == Math.sqrt(Math.pow((aPos[0]-bPos[0]),2) + Math.pow((aPos[1]-bPos[1]),2) + Math.pow((aPos[2]-bPos[2]),2))
	 */
	private double CalcCost(Cube a, Cube b){
		int[] aPos = a.getPosition();
		int[] bPos = b.getPosition();
		return Math.sqrt(Math.pow((aPos[0]-bPos[0]),2) + Math.pow((aPos[1]-bPos[1]),2) + Math.pow((aPos[2]-bPos[2]),2));
	}
	/**
	 * Returns the route of this Path.
	 */
	@Basic
	public Stack<Cube> getRoute(){
		return this.path;
	}

	/**
	 * Returns the starting cube of this path.
	 * @return
	 */
	@Basic
	public Cube getStart(){
		return this.start;
	}
	
	/**
	 * Sets the starting cube of the path.
	 * @param start The Cube we wish to set the start of this Path on.
	 * @post	This Path's start is set on the given Cube
	 * 			|new.start = start
	 */
	public void setStart(Cube start){
		this.start = start;
	}
	
	/**
	 * Returns the end Cube of the path
	 */
	@Basic
	public Cube getEnd(){
		return this.end;
	}
	
	/**
	 * Sets the end Cube of the path.
	 * @param end	The Cube we wish to set the start of this Path on.
	 * @post 	The end of this Path is set to the given Cube.
	 * 			|new.end == end
	 */
	public void setEnd(Cube end){
		this.end = end;
	}
	
	/**
	 * Returns the amount of steps taken in this Path's route. A step is counted for every Cube that is part of this route.
	 * @return	The size (the amount of Cubes) in the Route of this Path.
	 * 			|result == getRoute().size()
	 * 			
	 */
	public int countStepsinRoute(){
		return (this.getRoute().size());
	}
	/**
	 * Variable registering the start of this Path.
	 */
	private Cube start;
	/**
	 * Variable registering the end of this Path.
	 */
	private Cube end;
	/**
	 * A map where every key is a Cube, with a value also being a Cube. Every value is the Cube from where the Path went to the key Cube.
	 */
	private HashMap<Cube, Cube>  came_from;
	/**
	 * A map with its keys being Cubes, where the corresponding values represent the cost of that cube in the Path.
	 */
	private HashMap<Cube, Double> Cost_so_far;
	/**
	 * A
	 */
	private Stack<Cube> path;
}
