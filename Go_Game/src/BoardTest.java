import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	final void testPlaceStone() {
		Board board = new Board(5);
		board.placeStone(Color.BLACK, 0, 0);
		board.placeStone(Color.WHITE, 0, 1);
		board.placeStone(Color.BLACK, 3, 3);
		board.placeStone(Color.WHITE, 1, 0);
	}

	

}
