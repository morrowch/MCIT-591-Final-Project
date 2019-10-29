import java.util.ArrayList;

public class Board {
	
	private Intersection[][] intersections;
	private ArrayList<Group> groups;
	private ArrayList<Stone> stones;
	private int capturedWhiteStones;
	private int capturedBlackStones;
	private int size;
	private ArrayList<String> boardPositions; // Keeps a record of the game position after each move 
	
	public Board(int size) {
		this.size = size;
		groups = new ArrayList<Group>();
		stones = new ArrayList<Stone>();
		boardPositions = new ArrayList<String>();

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
	
	/**
	 * When a stone is placed, a new stone is created, it is added to the total list of stones, and the intersection is updated
	 * updatedGroups is then called
	 * removedCapturedGroups is then called
	 * @param color
	 * @param x
	 * @param y
	 */
	public void placeStone(Color color, int x, int y) {
		if (intersections[x][y].getStone() != null) {
			// Throw invalid move exception (can't place a stone on top of another stone)
			System.out.println("Stone is already at that location");
		}
			
		Stone stone = new Stone(color, x, y, this); 	// Create a new stone with given color and location
		stones.add(stone);						// Update stone
		intersections[x][y].setStone(stone); 	// Update that board location
		updateGroups(stone);
		updateBoardPositions();

	}
	
	/**
	 * When a stone is placed, the groups on the board need to be updated as follows:
	 * If the stone is not adjacent to any groups of the same color, create a new group comprising of only that stone
	 * If the stone is adjacent to exactly one group of the same color, add it to that group
	 * If the stone is adjacent to multiple groups of the same color, merge those groups into one group
	 * 
	 * Finally, remove any captured groups of the opposing color, and update the liberties of the new stone's group
	 * 
	 * NOTE: I think all of the updates in this method should be done with a "temporary" board, so that at the end, if the move
	 * is invalid due to Ko or due to the placed stone being dead, an exception can be thrown, and the original state of the board
	 * will be preserved.
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
			else { // Else, if the adjacent stone is of the opposite color, check if that group is captured
				if (adjacentStone.getGroup().isCaptured()) {
					
					for (Stone capturedStone : adjacentStone.getGroup().getStones()) {
						capturedStone.getIntersection().setStone(null);
						stones.remove(capturedStone);
						System.out.println(capturedStone.getColor() + " stone has been captured at " + capturedStone.getIntersection().getxPosition() + "," + capturedStone.getIntersection().getyPosition());
					}
					
					groups.remove(adjacentStone.getGroup());
					
					if (adjacentStone.getColor() == Color.WHITE) {
						capturedWhiteStones += adjacentStone.getGroup().getStones().size();
					} else {
						capturedBlackStones += adjacentStone.getGroup().getStones().size();
					}
					
				}
			}
		}
		
		// If, after checking each intersection, the stone hasn't been added to a group, then create a new group comprising only of it
		if (placedStone.getGroup() == null) {
			groups.add(new Group(placedStone));
		}
		
		placedStone.getGroup().updateLiberties(this);
		
		if (placedStone.getGroup().isCaptured()) {
			// Throw invalid move exception (can't place a stone that immediately dies)
			System.out.println("Placed stone immediately dies");
		}
		
	}
	
	public void updateBoardPositions() {
		String boardPosition = "";
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (intersections[x][y].getStone() == null) {
					boardPosition += Integer.toString(x) + Integer.toString(y) + ","; 
				} else if (intersections[x][y].getStone().getColor() == Color.WHITE) {
					boardPosition += "w" + Integer.toString(x) + Integer.toString(y) + ","; 
				} else if (intersections[x][y].getStone().getColor() == Color.BLACK) {
					boardPosition += "b" + Integer.toString(x) + Integer.toString(y) + ",";
				}
			}
		}
		
		if ((boardPositions.size() > 1) && (boardPosition.equals(boardPositions.get(boardPositions.size() - 2)))) {
			// Throw invalid move exception (Ko)
			System.out.println("Move violates rule of Ko");
		}
		
		boardPositions.add(boardPosition);
		System.out.println(boardPosition);
	}

	public Intersection[][] getIntersections() {
		return intersections;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}
	
	public ArrayList<Stone> getStones() {
		return stones;
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
