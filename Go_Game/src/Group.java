import java.util.ArrayList;

public class Group {
	
	private Color color;
	private ArrayList<Intersection> liberties;
	private ArrayList<Stone> stones;
	
	public Group(Stone stone) {
		color = stone.getColor();
		stones.add(stone);
	}
	
	/**
	 * Used for when two groups become one
	 * @param group
	 */
	public void addGroup(Group group) {
		for (Stone stone : group.getStones()) {
			stones.add(stone);
		}
	}
	
	public void addStone(Stone stone) { 
		stones.add(stone);
	}
	
	public Boolean isCaptured() {
		Boolean isCaptured = true;
		
		// TODO
		
		return isCaptured;
	}
	
	public ArrayList<Stone> getStones() {
		return stones;
	}

}
