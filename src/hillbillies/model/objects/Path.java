package hillbillies.model.objects;
import java.util.HashMap;
import java.util.PriorityQueue;

import hillbillies.model.world.*;
import hillbillies.model.Data;

public class Path {
	
	
	
	public Path(Unit unit, Cube start, Cube end){
		this.searchPath(start, end);
		this.setStart(start);
		this.setEnd(end);
	}
	
	public double CalcCost(Cube a, Cube b){
		int[] aPos = a.getPosition();
		int[] bPos = b.getPosition();
		return Math.abs(aPos[0]-bPos[0]) + Math.abs(aPos[1]-bPos[1]) + Math.abs(aPos[2]-bPos[2]);
	}
	
	public void searchPath(Cube start, Cube end){
		PriorityQueue<Data> frontier = new PriorityQueue<Data>();
		Data startData = new Data(start, 0);
		frontier.add(startData);
		HashMap<Cube, Cube>  came_from= new HashMap<Cube, Cube>();
		HashMap<Cube, Double> Cost_so_far = new HashMap<Cube, Double>();
		came_from.put(start, null);
		Cost_so_far.put(start, 0.0);
		
		
		
		while (!frontier.isEmpty()){
			Cube current = frontier.peek().getCube();
			
			if (current == end) {
				break;
			}
			
			for (Cube next: current.getSurroundingCubes()){
				double new_cost = Cost_so_far.get(current)+ 1;
				if ((!Cost_so_far.containsKey(next))|| (new_cost < Cost_so_far.get(next))){
					Cost_so_far.put(next, new_cost);
					double priority = new_cost + CalcCost(end, next);
					Data nextData = new Data(next, priority);
					frontier.add(nextData);
					came_from.put(next, current);
					
				}
			}
		}
		this.came_from = came_from;
		this.Cost_so_far = Cost_so_far;
		
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
}
