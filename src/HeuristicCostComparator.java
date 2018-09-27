import java.util.Comparator;

public class HeuristicCostComparator implements Comparator<GameState> {	
	public int compare(GameState a, GameState b) {
		if(a.getHeuristicCost() < b.getHeuristicCost()) {
			return -1;
		} else if(a.getHeuristicCost() > b.getHeuristicCost()) {
			return 1;
		}
		return 0;
	}
}
