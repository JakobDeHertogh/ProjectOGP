package hillbillies.model.objects;
import java.util.*;

import hillbillies.model.world.*;
import hillbillies.model.Data;

public class Path {
	
	
	
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
					double new_cost = Cost_so_far.get(current)+ 1;
					if (((!Cost_so_far.containsKey(next))|| (new_cost < Cost_so_far.get(next))) && (next.isValidCube())){
						System.out.println("current " + Arrays.toString(current.getPosition()));
						System.out.println("next " + Arrays.toString(next.getPosition()));
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
	
	public double CalcCost(Cube a, Cube b){
		int[] aPos = a.getPosition();
		int[] bPos = b.getPosition();
		return Math.abs(aPos[0]-bPos[0]) + Math.abs(aPos[1]-bPos[1]) + Math.abs(aPos[2]-bPos[2]);
	}
	
	public Stack<Cube> getRoute(){
		return this.path;
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
	private HashMap<Cube, Cube>  came_from;
	private HashMap<Cube, Double> Cost_so_far;
	private Stack<Cube> path;
}
