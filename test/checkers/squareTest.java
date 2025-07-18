package checkers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class squareTest {

	private square sq;
	
	@BeforeEach
	public void setSquare(){
		
		sq = new square(1,1);
		
	}
	//leteszteli, hogy kezdetlegesen tényleg üres mező keletkezik
	@Test
	void test1() {
		
		assertEquals(squareType.empty, sq.getType());
		
	}
	//leteszteli, hogy a megadott sor és oszlop szerint jön létre a mező
	@Test
	void test2() {
		
		assertEquals(1, sq.getRow());
		
	}
	@Test
	void test3() {
		
		assertEquals(1, sq.getCol());
		
	}
	//leteszteli, hpgy valóban nincsen bábu a létrehozott mezőn
	@Test
	void test4() {
		
		assertNull(sq.getPiece());
		
	}
	@Test
	//ha empty-re változtatjuk a tipusát valóban arra változik
	void test5() {
		
		sq.setSquare(squareType.empty);
		assertEquals(squareType.empty, sq.getType());
		
	}
	//ha blackpiece-re változtatjuk a tipusát valóban arra változik
	@Test
	void test6() {
		
		sq.setSquare(squareType.blackpiece);
		assertEquals(squareType.blackpiece, sq.getType());
		
	}
	//ha whitepiece-re változtatjuk a tipusát valóban arra változik
	@Test
	void test7() {
		
		sq.setSquare(squareType.whitepiece);
		assertEquals(squareType.whitepiece, sq.getType());
		
	}
	//ha blackqueen-re változtatjuk a tipusát valóban arra változik
	@Test
	void test8() {
		
		sq.setSquare(squareType.blackqueen);
		assertEquals(squareType.blackqueen, sq.getType());
		
	}
	//ha whitequeen-re változtatjuk a tipusát valóban arra változik
	@Test
	void test9() {
		
		sq.setSquare(squareType.whitequeen);
		assertEquals(squareType.whitequeen, sq.getType());
		
	}
}
