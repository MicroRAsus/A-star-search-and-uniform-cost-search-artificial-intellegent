import java.util.Comparator;

public class StateComparator implements Comparator<GameState> {
	private int ax = 0;
	private int ay = 0;
	private int bx = 0;
	private int by = 0;
	private double aDistance = 0.0;
	private double bDistance = 0.0;
	
	public int compare(GameState a, GameState b) {
		this.ax = (int)(a.getX() / 10);
		this.ay = (int)(a.getY() / 10);
		this.bx = (int)(b.getX() / 10);
		this.by = (int)(b.getY() / 10);
		
		if(this.ax == this.bx && this.ay == this.by) {
			return 0;
		}
		
		this.aDistance = Math.sqrt(this.ax * this.ax + this.ay * this.ay);
		this.bDistance = Math.sqrt(this.bx * this.bx + this.by * this.by);
		
		if(this.aDistance < this.bDistance) {
			return -1;
		}
		return 1;
	}
}
