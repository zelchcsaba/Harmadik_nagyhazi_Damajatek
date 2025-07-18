package checkers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class pieceTest {
	//ha blackpiece, akkor a szine valóban feketére állitódik
	@Test
	void test1() {
		
		piece p = new piece(squareType.blackpiece);
		
		assertFalse(p.getIsWhite());
		
	}
	//ha whitepiece, akkor a szine valóban fehérre állitódik
	@Test
	void test2() {
		
		piece p = new piece(squareType.whitepiece);
		
		assertTrue(p.getIsWhite());
		
	}
	//ha blackqueen, akkor a szine valóban feketére állitódik
	@Test
	void test3() {
		
		piece p = new piece(squareType.blackqueen);
		
		assertFalse(p.getIsWhite());
		
	}
	//ha whitepiece, akkor a szine valóban fehérre állitódik
	@Test
	void test4() {
		
		piece p = new piece(squareType.whitequeen);
		
		assertTrue(p.getIsWhite());
		
	}


}
