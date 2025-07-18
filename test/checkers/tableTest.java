package checkers;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;


class tableTest {
	private table t;
	
	@BeforeEach
	public void setTable(){
		
		t = new table();
		
	}
	//letesztelem, hogy a tábla valóban a helyes értéket adja vissza, ha a nagyságát lekérdezzük
	@Test
	void test1() {
		
		assertEquals(8,t.getSiz());
		
	}
	//letesztelem, ha beállitunk egy mezőt kiválasztottnak, akkor valóban az állitódik-e be
	@Test
	void test2() {
		
		t.setSelected(t.getSquare(0, 1));
		
		assertEquals(t.getSquare(0, 1), t.getSelected());
		
	}
	//letesztelem, ha beállitom melyik játékos következik, az tényleg beállitódik-e
	@Test
	void test3() {
		
		t.setIsWhiteTurn(false);
		
		assertFalse(t.getIsWhiteTurn());
		
	}
	//letesztelem, ha aktiválom a bot játékost, az tényleg aktiválódik-e
	@Test
	void test4() {
		
		t.setIsBot(true);
		
		assertTrue(t.getIsBot());
		
	}
	//letesztelem, ha kezdeti helyzetbe állitom a tábla szineit, akkor a beállitott szinek is kezdeti helyzetbe állnak
	@Test
	void test5() {
		
		t.getSquare(0, 0).setBackground(new Color(0,0,0));
		t.resetColors();
		
		assertEquals(new Color(200,200,200), t.getSquare(0, 0).getBackground());
		
	}
	//leteszteli, hogy a helyes lépés tesztelő tényleg jó eredményt ad vissza
	@Test
	void test6() {
		
		assertTrue(t.isValidMove(t.getSquare(5, 2), t.getSquare(4, 1)));
		assertTrue(t.isValidMove(t.getSquare(5, 2), t.getSquare(4, 3)));
		assertFalse(t.isValidMove(t.getSquare(5, 2), t.getSquare(4, 0)));
		assertFalse(t.isValidMove(t.getSquare(5, 2), t.getSquare(6, 1)));
		
	}
	
	@Test
	void test7() {
		
		assertTrue(t.isValidMove(t.getSquare(2, 1), t.getSquare(3, 0)));
		assertTrue(t.isValidMove(t.getSquare(2, 1), t.getSquare(3, 2)));
		assertFalse(t.isValidMove(t.getSquare(2, 1), t.getSquare(3, 3)));
		assertFalse(t.isValidMove(t.getSquare(2, 1), t.getSquare(1, 0)));
		
	}
	//leteszteli a helyes lépés vizsgáló helyes működését olyan lépéseknél is, amikor ütni tudunk
	@Test
	void test8() {
		
		setMove1(t);
		assertTrue(t.isValidMove(t.getSquare(5, 2), t.getSquare(3, 4)));
		
	}
	
	@Test
	void test9() {
		
		setMove2(t);
		assertTrue(t.isValidMove(t.getSquare(2, 1), t.getSquare(4, 3)));
		
	}
	//leteszteli a helyes lépéseket visszaadó függvényt egy fehér dámára
	@Test
	void test10() {
		
		setWhiteQueen(t);
		List<square> list = t.getMoves(t.getSquare(3, 2));
		
		assertEquals(t.getSquare(4, 1), list.get(0));
		assertEquals(t.getSquare(4, 3), list.get(1));
		assertEquals(t.getSquare(2, 1), list.get(2));
		assertEquals(t.getSquare(2, 3), list.get(3));
		
	}
	//leteszteli a lépés megjelenitő függvény helyes működését
	@Test
	void test11() {
		
		t.showMoves(t.getSquare(2, 1));
		
		assertEquals(new Color(11,102,35), t.getSquare(3, 0).getBackground());
		assertEquals(new Color(11,102,35), t.getSquare(3, 2).getBackground());
		
	}
	
	@Test
	void test12() {
		
		t.showMoves(t.getSquare(5, 2));
		
		assertEquals(new Color(11,102,35), t.getSquare(4, 1).getBackground());
		assertEquals(new Color(11,102,35), t.getSquare(4, 3).getBackground());
		
	}
	//megnézi, ha a bábu ütés helyzetben van, akkor is jól működik-e
	@Test
	void test13() {
		
		setMove1(t);
		t.showMoves(t.getSquare(5, 2));
		
		assertEquals(new Color(194,27,4), t.getSquare(3, 4).getBackground());
		
	}
	
	@Test
	void test14() {
		
		setMove2(t);
		t.showMoves(t.getSquare(2, 1));
		
		assertEquals(new Color(194,27,4), t.getSquare(4, 3).getBackground());
		
	}
	//fehér dáma esetén is letesztelem a lépés megjelenitő függvényt
	@Test
	void test15() {
		
		setWhiteQueen(t);
		t.showMoves(t.getSquare(3, 2));
		
		assertEquals(new Color(11,102,35),t.getSquare(4, 1).getBackground());
		assertEquals(new Color(11,102,35),t.getSquare(4, 3).getBackground());
		assertEquals(new Color(11,102,35),t.getSquare(2, 1).getBackground());
		assertEquals(new Color(11,102,35),t.getSquare(2, 3).getBackground());
		
	}
	//letesztelem, azt a függvényt, amelyik igazat ad akkor, ha a játékos ütni tud
	@Test
	void test16() {
		
		assertFalse(t.findBeat(true));
		
	}
	
	@Test
	void test17() {
		
		assertFalse(t.findBeat(false));
		
	}
	//megnézem akkor is, ha a játékos tényleg ütni tud
	@Test
	void test18() {
		
		setMove1(t);
		
		assertTrue(t.findBeat(true));
		
	}
	
	@Test
	void test19() {
		
		setMove2(t);
		
		assertTrue(t.findBeat(false));
		
	}
	//leellenőrzöm azt a függvényt, amelyik visszaadja a játékos első olyan bábuját, amellyel ütni tud
	@Test
	void test20() {
		
		setMove2(t);
		
		assertEquals(t.getSquare(3, 2), t.getBeatPiece(true));
		
	}
	
	@Test
	void test21() {
		
		setMove2(t);
		
		assertEquals(t.getSquare(2, 1), t.getBeatPiece(false));
		
	}
	//leellenőrzöm, hogy ez a függvény null értéket ad-e vissza, ha nincs mivel ütni
	@Test
	void test22() {
		
		assertNull(t.getBeatPiece(true));
		
	}
	//leellenőrzöm annak a függvénynek a működését, amelyik visszaadja az adott játékos összes olyan bábuját, amellyel lépni tud
	@Test
	void test23() {
		
		setMove3(t);
		List<square> list = t.getMovablePieces(false);
		
		assertEquals(3, list.size());
		assertEquals(t.getSquare(2, 1), list.get(0));
		assertEquals(t.getSquare(2, 3), list.get(1));
		assertEquals(t.getSquare(2, 5), list.get(2));
		
	}
	
	@Test
	void test24() {
		
		setMove3(t);
		List<square> list = t.getMovablePieces(true);
		
		assertEquals(3, list.size());
		assertEquals(t.getSquare(3, 2), list.get(0));
		assertEquals(t.getSquare(5, 2), list.get(1));
		assertEquals(t.getSquare(5, 4), list.get(2));
		
	}
	//megnézem, hogy a robot játékos hogyan viselkedik ütéshelyzetben
	@Test
	void test25() {
		
		setMove4(t);
		t.botMove(true);
		
		assertEquals(squareType.empty, t.getSquare(5, 4).getType());
		assertEquals(squareType.whitepiece, t.getSquare(1, 0).getType());
		
	}
	//letesztelem a robotjátékos működését egy bábuval a tábla széléről, mivel random működik nem nagyon lehet teszteket késziteni neki
	@Test
	void test26() {
		
		setMove5(t);
		t.botMove(false);
		
		assertEquals(squareType.empty, t.getSquare(0, 7).getType());
		assertEquals(squareType.blackpiece, t.getSquare(1, 6).getType());
		
	}
	//letesztelem, hogy a robotjátékos ha a tábla végére ér a bábuval dámává változik-e a bábu és tud ezután visszafele is ütni
	@Test
	void test27() {
		
		setMove6(t);
		t.botMove(false);
		
		assertEquals(squareType.empty, t.getSquare(3, 2).getType());
		assertEquals(squareType.blackqueen, t.getSquare(5, 6).getType());
		assertEquals(squareType.empty, t.getSquare(4, 1).getType());
		assertEquals(squareType.empty, t.getSquare(6, 3).getType());
		assertEquals(squareType.empty, t.getSquare(6, 5).getType());
		
	}
	//leellenőrzöm azt a függvényt, amelyik törli a leütött bábut
	@Test
	void test28() {
		
		setMove1(t);
		t.removeBeated(t.getSquare(5, 2), t.getSquare(3, 4));
		
		assertEquals(squareType.empty, t.getSquare(4, 3).getType());
		
	}
	//leellenőrzöm annak a függvények a működését, amelyik leellenőrzi, hogy az adott játékos nyert-e abban az esetben, mikor
	//a másik játékosnak még van bábuja a táblán, de vele mozogni nem tud
	@Test
	void test29() {
		
		setMove7(t);
		
		assertTrue(t.isWin(true));
		
	}

	//fehér és fekete bábu üteshelyzetbe állitása
	private void setMove1(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(5, 2).setSquare(squareType.whitepiece);
		t.getSquare(4, 3).setSquare(squareType.blackpiece);
		
	}
	
	private void setMove2(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(2, 1).setSquare(squareType.blackpiece);
		t.getSquare(3, 2).setSquare(squareType.whitepiece);
		
	}
	
	private void setMove3(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(2, 1).setSquare(squareType.blackpiece);
		t.getSquare(2, 3).setSquare(squareType.blackpiece);
		t.getSquare(2, 5).setSquare(squareType.blackqueen);
		t.getSquare(1, 4).setSquare(squareType.blackpiece);
		t.getSquare(3, 2).setSquare(squareType.whitepiece);
		t.getSquare(5, 2).setSquare(squareType.whitepiece);
		t.getSquare(5, 4).setSquare(squareType.whitequeen);
		t.getSquare(6, 3).setSquare(squareType.whitepiece);
		
	}
	//a fehér bábu ütéshelyzetben, két feketét tud ütni
	private void setMove4(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(2, 1).setSquare(squareType.blackpiece);
		t.getSquare(4, 3).setSquare(squareType.blackpiece);
		t.getSquare(5, 4).setSquare(squareType.whitepiece);
		
	}
	//egy fekete bábu a tábla sarkában
	private void setMove5(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(0, 7).setSquare(squareType.blackpiece);
		
	}
	//fekete bábu ütéshelyzetben, harom fehér bábut tud ütni
	private void setMove6(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(6, 3).setSquare(squareType.whitepiece);
		t.getSquare(6, 5).setSquare(squareType.whitequeen);
		t.getSquare(4, 1).setSquare(squareType.whitepiece);
		t.getSquare(3, 0).setSquare(squareType.blackpiece);
		
	}
	//fekete bábu mozgásképtelen
	private void setMove7(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(5, 6).setSquare(squareType.whitepiece);
		t.getSquare(6, 4).setSquare(squareType.whitequeen);
		
	}
	//egy fehér dáma a táblán
	private void setWhiteQueen(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(3, 2).setSquare(squareType.blackqueen);
		
	}
}
