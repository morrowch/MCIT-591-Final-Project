import java.util.ArrayList;

public class Board {
	
	private Intersection[][] intersections;
	private ArrayList<Group> groups;
	private ArrayList<Stone> stones;
	private int capturedWhiteStones;
	private int capturedBlackStones;
	private int size;
	
	public Board(int size) {
		this.size = size;
		intersections = new Intersection[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Intersection intersection = new Intersection(x, y);
				intersections[x][y] = intersection;
			}
		}
		capturedBlackStones = 0;
		capturedWhiteStones = 0;
	}
	
	public Boolean validateMove(Color color, int x, int y) {
		// Check if stone is already at that intersection
		// Check if stone has an liberties
		// Check rule of Ko
		return true;
	}
	
	/**
	 * When a stone is placed, a new stone is created, it is added to the total list of stones, and the intersection is updated
	 * updatedGroups is then called
	 * removedCapturedGroups is then called
	 * @param color
	 * @param x
	 * @param y
	 */
	public void placeStone(Color color, int x, int y) {
		Stone stone = new Stone(color, x, y); 	// Create a new stone with given color and location
		stones.add(stone);						// Update stone
		intersections[x][y].setStone(stone); 	// Update that board location
		updateGroups(stone);
		removeCapturedGroups();
	}
	
	/**
	 * When a stone is placed, the groups on the board need to be updated as follows:
	 * If the stone is not adjacent to any groups of the same color, create a new group comprising of only that stone
	 * If the stone is adjacent to exactly one group of the same color, add it to that group
	 * If the stone is adjacent to multiple groups of the same color, merge those groups into one group
	 * @param placedStone
	 */
	public void updateGroups(Stone placedStone) {
		for (Intersection intersection : placedStone.getAdjacentIntersections(this)) {
			
			if (intersection.getStone() == null) {continue;}
			Stone adjacentStone = intersection.getStone();
			
			if (adjacentStone.getColor() == placedStone.getColor()) { // If the stone is adjacent to a stone of the same color...
				
				if (placedStone.getGroup() == null) {
					adjacentStone.getGroup().addStone(placedStone); // Add the placed stone to that stone's group
					placedStone.setGroup(adjacentStone.getGroup());
				} else {
					// If the stone is already part of a group, add the other group to that same group, and remove the original group
					// (i.e. combine groups)
					placedStone.getGroup().addGroup(adjacentStone.getGroup());
					groups.remove(adjacentStone.getGroup());
				}
			}
		}
		
		// If, after checking each intersection, the stone hasn't been added to a group, then create a new group comprising only of it
		if (placedStone.getGroup() == null) {
			groups.add(new Group(placedStone));
		}
		
	}
	
	public void removeCapturedGroups() {
		// TODO
		 
	}

	public Intersection[][] getIntersections() {
		return intersections;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public int getCapturedWhiteStones() {
		return capturedWhiteStones;
	}

	public int getCapturedBlackStones() {
		return capturedBlackStones;
	}
	
	public int getSize() {
		return size;
	}

}
