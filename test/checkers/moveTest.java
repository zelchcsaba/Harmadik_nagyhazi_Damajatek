package checkers;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class moveTest {

	private table t;
	private move mv;
	
	@BeforeEach
	public void setMove(){
		
		t = new table();
		mv = new move(t);
		
	}
	//letesztelem, azt a függvényt, amely a dáma kijelöléséért felel, megnézem, hogy helyesen változtatja-e meg a mezők szineit
	@Test
	void test1() {
		
		t.setIsWhiteTurn(true);
		t.setIsBot(false);
		mv.setSelectedSquare(t.getSquare(5, 2));
		
		assertEquals(Color.YELLOW, t.getSquare(5, 2).getBackground());
		assertEquals(new Color(11,102,35), t.getSquare(4, 1).getBackground());
		assertEquals(new Color(11,102,35), t.getSquare(4, 3).getBackground());
		
	}
	//letesztelem, hogy helyesen változnak-e a mezők szinei a táblán, mikor olyan bábut választok, amivel ütni lehet
	@Test
	void test2() {
		
		t.setIsWhiteTurn(false);
		t.setIsBot(false);
		setMove1(t);
		mv.setSelectedSquare(t.getSquare(5, 2));
		
		assertEquals(Color.YELLOW, t.getSquare(5, 2).getBackground());
		assertEquals(new Color(194,27,4), t.getSquare(3, 4).getBackground());
		
	}
	//letesztelem, hogy a sima lépést végrehajtja-e helyesen
	@Test
	void test3() {
		
		t.setIsWhiteTurn(true);
		t.setIsBot(false);
		t.setSelected(t.getSquare(5, 2));
		mv.simpleMoveTo(t.getSquare(4, 1));
		
		assertEquals(squareType.empty, t.getSquare(5, 2).getType());
		assertEquals(squareType.whitepiece, t.getSquare(4, 1).getType());
		
	}
	//letesztelem, hogy ha eléri a bábu a tábla végét dáma lesz-e, illetve, hogy miután léptünk a következő játékos jön-e
	@Test
	void test4() {
		
		t.setIsWhiteTurn(false);
		t.setIsBot(false);
		setMove2(t);
		t.setSelected(t.getSquare(6, 1));
		mv.simpleMoveTo(t.getSquare(7, 0));
		
		assertEquals(squareType.empty, t.getSquare(6, 1).getType());
		assertEquals(squareType.blackqueen, t.getSquare(7, 0).getType());
		assertEquals(true, t.getIsWhiteTurn());
		
	}
	//letesztelem, hogy ütő lépés esetén helyesen hajtódik-e végre a lépés
	//a leütött bábu eltűnik a tábláról
	//ebben az esetben kétszer üthetünk, igy az ütés után újra ugyan az a bábu lesz kijelölve, és újra mi jövünk
	@Test
	void test5() {
		
		t.setIsWhiteTurn(true);
		t.setIsBot(false);
		setMove4(t);
		t.setSelected(t.getSquare(5, 4));
		mv.beatMoveTo(t.getSquare(3, 2));
		
		assertEquals(squareType.empty, t.getSquare(5, 4).getType());
		assertEquals(squareType.empty, t.getSquare(4, 3).getType());
		assertEquals(squareType.whitepiece, t.getSquare(3, 2).getType());
		assertEquals(Color.YELLOW, t.getSquare(3, 2).getBackground());
		assertEquals(new Color(194,27,4), t.getSquare(1, 0).getBackground());
		assertEquals(true, t.getIsWhiteTurn());
		
	}
	//leellenőrzöm azt az esetet, ha ütés után a bábu elérte a tábla végét, akkor valóban dáma lesz-e
	//ezután pedig ha újra tud ütni, akkor újra ugyanaz a bábu lesz kiválasztva és ugyan az a játékos jön
	@Test
	void test6() {
		
		t.setIsWhiteTurn(false);
		t.setIsBot(false);
		setMove5(t);
		t.setSelected(t.getSquare(5, 2));
		mv.beatMoveTo(t.getSquare(7, 4));
		
		assertEquals(squareType.empty, t.getSquare(5, 2).getType());
		assertEquals(squareType.empty, t.getSquare(6, 3).getType());
		assertEquals(squareType.blackqueen, t.getSquare(7, 4).getType());
		assertEquals(Color.YELLOW, t.getSquare(7, 4).getBackground());
		assertEquals(new Color(194,27,4), t.getSquare(5, 6).getBackground());
		assertEquals(false, t.getIsWhiteTurn());
		
	}
	//megnézem ha a movePiece függvénnyel történő egyszerű lépés is sima eredményt ad
	@Test
	void test7() {
		
		t.setIsWhiteTurn(true);
		t.setIsBot(false);
		t.setSelected(t.getSquare(5, 2));
		mv.movePiece(t.getSquare(4, 1));
		
		assertEquals(squareType.empty, t.getSquare(5, 2).getType());
		assertEquals(squareType.whitepiece, t.getSquare(4, 1).getType());
		
	}
	//letesztelem, hogy a helytelen lépés valóban nem hajtódik végre
	@Test
	void test8() {
		
		t.setIsWhiteTurn(true);
		t.setIsBot(false);
		t.setSelected(t.getSquare(5, 2));
		mv.movePiece(t.getSquare(4, 4));
		
		assertEquals(squareType.whitepiece, t.getSquare(5, 2).getType());
		assertEquals(squareType.empty, t.getSquare(4, 3).getType());
		
	}
	//ha a játékos nem a saját bábui közül választ, akkor nem jelölődik ki bábu
	@Test
	void test9() {
		
		t.setIsWhiteTurn(false);
		t.setIsBot(false);
		mv.movePiece(t.getSquare(5, 2));
		
		assertNull(t.getSelected());
		
	}
	//ha a játékos a saját bábui közül választ, akkor kijelölődik bábu
	@Test
	void test10() {
		
		t.setIsWhiteTurn(false);
		t.setIsBot(false);
		mv.movePiece(t.getSquare(2, 1));
		
		assertEquals(t.getSquare(2, 1),t.getSelected());
		
	}
	
	//letesztelem, hogy ütő lépés esetén helyesen hajtódik-e végre a lépés, ha a movePiece függvényt hivom
	@Test
	void test11() {
		
		t.setIsWhiteTurn(true);
		t.setIsBot(false);
		setMove4(t);
		t.setSelected(t.getSquare(5, 4));
		mv.movePiece(t.getSquare(3, 2));
		
		assertEquals(squareType.empty, t.getSquare(5, 4).getType());
		assertEquals(squareType.empty, t.getSquare(4, 3).getType());
		assertEquals(squareType.whitepiece, t.getSquare(3, 2).getType());	
		
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
	//egyetlen fekete bábut helyez a tábláre
	private void setMove2(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(6, 1).setSquare(squareType.blackpiece);
		
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
		
	//fekete bábu ütéshelyzetben, három fehér bábut tud ütni
	private void setMove5(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(6, 3).setSquare(squareType.whitepiece);
		t.getSquare(6, 5).setSquare(squareType.whitequeen);
		t.getSquare(5, 2).setSquare(squareType.blackpiece);
		
	}
}
