import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	final void test1() {
		Board board = new Board(5);
		board.placeStone(Color.BLACK, 0, 0);
		board.placeStone(Color.WHITE, 0, 1);
		board.placeStone(Color.BLACK, 3, 3);
		board.placeStone(Color.WHITE, 1, 0);
		board.placeStone(Color.BLACK, 3, 4);
	}
	
	
	@Test
	public void noLibertiesException() {
		Board board = new Board(5);

		board.placeStone(Color.BLACK, 1, 0);
		board.placeStone(Color.WHITE, 1, 1);
		board.placeStone(Color.BLACK, 0, 1);
		
		try {
			board.placeStone(Color.WHITE, 0, 0);
			fail("No liberties exception was not thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), board.NO_LIBERTIES_MESSAGE);
		}
		
		assertEquals(board.getCapturedBlackStones(), 0);
		assertEquals(board.getCapturedWhiteStones(), 0);
		assertEquals(board.getGroups().size(), 3);
		assertEquals(board.getStones().size(), 3);
	}
	
	@Test
	public void koException() {
		Board board = new Board(5);
		
		board.placeStone(Color.BLACK, 0, 0);
		board.placeStone(Color.WHITE, 1, 0);
		board.placeStone(Color.BLACK, 1, 1);
		board.placeStone(Color.WHITE, 0, 1);
		board.placeStone(Color.BLACK, 0, 2);
		board.placeStone(Color.WHITE, 3, 3);
		board.placeStone(Color.BLACK, 0, 0);
		
		try {
			board.placeStone(Color.WHITE, 0, 1);
			fail("Ko exception was not thrown");
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), board.KO_MESSAGE);
		}
		
		assertEquals(board.getCapturedBlackStones(), 1);
		assertEquals(board.getCapturedWhiteStones(), 1);
		assertEquals(board.getGroups().size(), 5);
		assertEquals(board.getStones().size(), 5);

	}
	
	@Test
	public void simpleGame() {
		Board board = new Board(5);
		
		board.placeStone(Color.BLACK, 3, 3);
		board.placeStone(Color.WHITE, 1, 1);
		board.placeStone(Color.BLACK, 1, 3);
		board.placeStone(Color.WHITE, 3, 1);
		board.placeStone(Color.BLACK, 1, 2);
		board.placeStone(Color.WHITE, 2, 1);
		board.placeStone(Color.BLACK, 2, 2);
		board.placeStone(Color.WHITE, 3, 2);
		board.placeStone(Color.BLACK, 4, 2);
		board.placeStone(Color.WHITE, 2, 3);
		board.placeStone(Color.BLACK, 2, 4); // Captures a white stone
		board.placeStone(Color.WHITE, 4, 1);
		board.placeStone(Color.BLACK, 4, 3);
		board.placeStone(Color.WHITE, 0, 2);
		board.placeStone(Color.BLACK, 0, 3);
		board.placeStone(Color.WHITE, 0, 1);
		
		assertEquals(board.getCapturedBlackStones(), 0);
		assertEquals(board.getCapturedWhiteStones(), 1);
		assertEquals(board.getGroups().size(), 4);
		assertEquals(board.getStones().size(), 15);
		
	}



}
