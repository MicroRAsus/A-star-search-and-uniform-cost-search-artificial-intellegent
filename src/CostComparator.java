import java.util.Comparator;

public class CostComparator implements Comparator<GameState> {	
	public int compare(GameState a, GameState b) {
		if(a.getCost() < b.getCost()) {
			return -1;
		} else if(a.getCost() > b.getCost()) {
			return 1;
		}
		return 0;
	}
}
