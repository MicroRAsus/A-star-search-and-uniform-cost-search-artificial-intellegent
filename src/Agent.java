import java.util.ArrayList;
import java.util.PriorityQueue;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;

class Agent {
	private MouseEvent mostRecentMouseEvent;
	private PathFinding pathFinding;
	private ArrayList<GameState> path;
	
	Agent(Model m) {
		this.pathFinding = new PathFinding(m);
		this.path = this.pathFinding.getPath();
	}
	
	void drawPlan(Graphics g, Model m) {
		if(this.mostRecentMouseEvent == null) {
			return;
		}
		
		if(!this.isGoal(this.mostRecentMouseEvent.getX(), this.mostRecentMouseEvent.getY(), m.getX(), m.getY())) { //draw line and frontier if not goal
			this.drawPath(g);
			if(this.mostRecentMouseEvent.getButton() == MouseEvent.BUTTON1) { //left click, UCS
				this.drawFrontier(g, false);
			} else { //right click, a*
				this.drawFrontier(g, true);
			}
		}
	}
	
	private void drawPath(Graphics g) { //draw line from start to goal.
		g.setColor(Color.red);
		for(int i = 0; i < this.path.size() - 1; i++) {
			g.drawLine(this.stabilizePoint(this.path.get(i).getX()), this.stabilizePoint(this.path.get(i).getY()), this.stabilizePoint(this.path.get(i+1).getX()), this.stabilizePoint(this.path.get(i+1).getY()));
		}
	}
	
	private int stabilizePoint(float x) { //stabilize the float point
		return (int)(x / 10) * 10 + 5;
	}
	
	private void drawFrontier(Graphics g, boolean aStar) {
		g.setColor(Color.orange);
		PriorityQueue<GameState> frontier;
		if(aStar) {
			frontier = this.pathFinding.getHeuristicFrontier();
		} else {
			frontier = this.pathFinding.getFrontier();
		}
		
		for(GameState state : frontier) {
			g.fillOval(this.stabilizePoint(state.getX()), this.stabilizePoint(state.getY()), 8, 8);
		}
	}
	
	private boolean isGoal(int ax, int ay, float bx, float by) {
		return (int)(ax / 10) == (int)(bx / 10) && (int)(ay / 10) == (int)(by / 10);
	}

	void update(Model m)
	{
		Controller c = m.getController();
		MouseEvent e;
		while((e = c.nextMouseEvent()) != null) // for consuming all the mouse event, only set destination to most recent click. e.getx y is the goal state here.
		{
			this.mostRecentMouseEvent = e;
		}

		if(this.mostRecentMouseEvent == null) {
			return;
		}
		
		if(!this.isGoal(this.mostRecentMouseEvent.getX(), this.mostRecentMouseEvent.getY(), m.getX(), m.getY())) { //search path if not goal
			if(this.mostRecentMouseEvent.getButton() == MouseEvent.BUTTON1) { //left click use UCS
				this.pathFinding.uniformCostSearch(m.getX(), m.getY(), this.mostRecentMouseEvent.getX(), this.mostRecentMouseEvent.getY());
				m.setDestination(path.get(1).getX(), path.get(1).getY());
			} else { //any other mouse click use a*
				this.pathFinding.aStarSearch(m.getX(), m.getY(), this.mostRecentMouseEvent.getX(), this.mostRecentMouseEvent.getY());
				m.setDestination(path.get(1).getX(), path.get(1).getY());
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		Controller.playGame();
	}
}
