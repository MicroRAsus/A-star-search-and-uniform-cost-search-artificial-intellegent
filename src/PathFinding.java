import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class PathFinding {
	private static final int[] POSSIBLE_ACTIONS = new int[] {10, 10, 10, -10, -10, 10, -10, -10, 10, 0, 0, 10, -10, 0, 0, -10};
	private static final int ACTION_COUNT = 16;
	
	private PriorityQueue<GameState> frontier;
	private PriorityQueue<GameState> heuristicFrontier;
	private TreeSet<GameState> beenThere;
	private ArrayList<GameState> path;
	private Model m;
	
	PathFinding(Model m) {
		this.frontier = new PriorityQueue<GameState>(new CostComparator());
		this.heuristicFrontier = new PriorityQueue<GameState>(new HeuristicCostComparator());
		this.beenThere = new TreeSet<>(new StateComparator());
		this.path = new ArrayList<GameState>();
		this.m = m;
	}
	
	private void constructPath(GameState goal) {
		this.path.clear();
		this.path.add(goal);
		while(goal.getParent() != null) {
			goal = goal.getParent();
			this.path.add(0, goal);
		}
	}
	
	public PriorityQueue<GameState> getFrontier() {
		return this.frontier;
	}
	
	public ArrayList<GameState> getPath() {
		return this.path;
	}
	
	public void uniformCostSearch(float startX, float startY, int goalX, int goalY) {
		GameState startState = new GameState(0.0, null, startX, startY);
	    this.frontier.clear();
	    this.beenThere.clear();
	    this.beenThere.add(startState);
	    this.frontier.add(startState);
	    
	    while(frontier.size() > 0) {
	    	GameState s = this.frontier.poll(); // get lowest-cost state
	    	if((int)(s.getX() / 10) == goalX / 10 && (int)(s.getY() / 10) == goalY / 10) { //if goal
	    		this.constructPath(s);
	    		return;
	    	}
	    	
	    	for(int i = 0; i < PathFinding.ACTION_COUNT; i += 2) {
	    		float newX = s.getX() + PathFinding.POSSIBLE_ACTIONS[i];
	    		float newY = s.getY() + PathFinding.POSSIBLE_ACTIONS[i+1];
	    		if(newX < 0.0f || newX > Model.XMAX || newY < 0.0f || newY > Model.YMAX) { //skip out of bound states
	    			continue;
	    		}
	    		
	    		double actionCost = 0.0;
	    		if(i < PathFinding.ACTION_COUNT / 2) { //first half is diagonal moves
	    			actionCost = Math.sqrt(2) / this.m.getTravelSpeed(s.getX(), s.getY());
	    		} else {
	    			actionCost = 1.0 / this.m.getTravelSpeed(s.getX(), s.getY());
	    		}
	    		GameState child = new GameState(s.getCost() + actionCost, s, newX, newY);
	    		
	    		if(this.beenThere.contains(child)) {
	    			GameState oldChild = this.beenThere.floor(child);
	    			if(child.getCost() < oldChild.getCost()) {
	    				oldChild.setCost(child.getCost());
	    				oldChild.setParent(s);
	    			}
	    		} else {
	    			this.frontier.add(child);
	    			this.beenThere.add(child);
	    		}
	    	}
	    }
	    throw new RuntimeException("There is no path to the goal");
	}
	
	private double getDistanceToDestination(float ax, float ay, int bx, int by) {
		int axx = (int)(ax / 10);
		int ayy = (int)(ay / 10);
		int bxx = bx / 10;
		int byy = by / 10;
		
		return Math.sqrt((axx - bxx) * (axx - bxx) + (ayy - byy) * (ayy - byy));
	}
	
	public PriorityQueue<GameState> getHeuristicFrontier() {
		return this.heuristicFrontier;
	}
	
	public void aStarSearch(float startX, float startY, int goalX, int goalY) {
		GameState startState = new GameState(0.0, null, startX, startY);
	    this.heuristicFrontier.clear();
	    this.beenThere.clear();
	    this.beenThere.add(startState);
	    this.heuristicFrontier.add(startState);
	    
	    while(heuristicFrontier.size() > 0) {
	    	GameState s = this.heuristicFrontier.poll(); // get lowest-cost state
	    	if((int)(s.getX() / 10) == goalX / 10 && (int)(s.getY() / 10) == goalY / 10) { //if goal
	    		this.constructPath(s);
	    		return;
	    	}
	    	
	    	for(int i = 0; i < PathFinding.ACTION_COUNT; i += 2) {
	    		float newX = s.getX() + PathFinding.POSSIBLE_ACTIONS[i];
	    		float newY = s.getY() + PathFinding.POSSIBLE_ACTIONS[i+1];
	    		if(newX < 0.0f || newX > Model.XMAX || newY < 0.0f || newY > Model.YMAX) { //skip out of bound states
	    			continue;
	    		}
	    		
	    		double actionCost = 0.0;
	    		if(i < PathFinding.ACTION_COUNT / 2) {
	    			actionCost = Math.sqrt(2) / this.m.getTravelSpeed(s.getX(), s.getY());
	    		} else {
	    			actionCost = 1.0 / this.m.getTravelSpeed(s.getX(), s.getY());
	    		}
	    		GameState child = new GameState(s.getCost() + actionCost, this.m.getLowestCost() * this.getDistanceToDestination(newX, newY, goalX, goalY), s, newX, newY);
	    		
	    		if(this.beenThere.contains(child)) {
	    			GameState oldChild = this.beenThere.floor(child);
	    			if(child.getCost() < oldChild.getCost()) { //heuristic is the same for both child and oldChild
	    				oldChild.setCost(child.getCost());
	    				oldChild.setParent(s);
	    			}
	    		} else {
	    			this.heuristicFrontier.add(child);
	    			this.beenThere.add(child);
	    		}
	    	}
	    }
	    throw new RuntimeException("There is no path to the goal");
	}
}
