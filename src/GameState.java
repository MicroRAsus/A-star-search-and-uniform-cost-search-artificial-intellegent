public class GameState {
	private double cost; //accumulating
	private double heuristic;
	private GameState parent;
	private float x;
	private float y;

	GameState(double cost, GameState parent, float x, float y) {
		this.cost = cost;
		this.heuristic = 0.0;
		this.parent = parent;
		this.x = x;
		this.y = y;
	}
	
	GameState(double cost, double heuristic, GameState parent, float x, float y) {
		this.cost = cost;
		this.heuristic = heuristic;
		this.parent = parent;
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public double getHeuristicCost() {
		return this.cost + this.heuristic;
	}
	
	public void setCost(double newCost) {
		this.cost = newCost;
	}
	
	public GameState getParent() {
		return this.parent;
	}
	
	public void setParent(GameState newParent) {
		this.parent = newParent;
	}
}
