import java.util.ArrayList;

public class Group {
	
	private Color color;
	private ArrayList<Intersection> liberties;
	private ArrayList<Stone> stones;
	
	public Group(Stone stone) {
		color = stone.getColor();
		stones = new ArrayList<Stone>();
		stones.add(stone);
		stone.setGroup(this);
	}
	
	/**
	 * Used for when two groups become one
	 * @param group
	 */
	public void addGroup(Group group) {
		for (Stone stone : group.getStones()) {
			stones.add(stone);
			stone.setGroup(this);
		}
	}
	
	/**
	 * Adds a new stone to the group
	 * @param stone
	 */
	public void addStone(Stone stone) { 
		stones.add(stone);
	}
	
	/**
	 * Checks whether a group is captured by checking whether it has at least one open liberty
	 * @return
	 */
	public Boolean isCaptured() {
		Boolean isCaptured = true;
		for (Intersection liberty : liberties) {
			if (liberty.getStone() == null) {
				isCaptured = false; // If at least one liberty is free, the group is still alive
			}
		}
		return isCaptured;
	}
	
	/**
	 * Re-finds all liberties for a group. A liberty is any intersection adjacent to a stone that isn't occupied by a stone of the same color
	 * I'm counting adjacent intersections occupied by opposing stones as valid liberties--just ones that aren't counted when determining when
	 * the group is captured
	 * @param board
	 */
	public void updateLiberties(Board board) {
		liberties = new ArrayList<Intersection>();
		
		for (Stone stone : stones) { 
			for (Intersection intersection : stone.getAdjacentIntersections(board)) {
				if ( (intersection.getStone() == null || intersection.getStone().getColor() != color) && !liberties.contains(intersection)) {
					liberties.add(intersection);
				}
			}
		}
		
	}
	
	public ArrayList<Stone> getStones() {
		return stones;
	}

}
