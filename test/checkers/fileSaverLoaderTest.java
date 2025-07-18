package checkers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class fileSaverLoaderTest {
	//letesztelem a tábla fájlba kimentését, és az onnan visszaolvasásának helyességét
	@Test
	void test() {
		
		window w = new window();
		setMove(w.getShowedTable());
		
		fileSaver to_File = new fileSaver(w);
		to_File.saveTableToFile("test.txt");
		
		fileLoader from_File = new fileLoader(w);
		from_File.loadTableFromFile("test.txt");
		
		assertNotNull(from_File.getLoadedTable());
		assertEquals(squareType.blackqueen, from_File.getLoadedTable().getSquare(6, 1).getType());
		
	}
	//egyetlen királynőt helyez a táblára
	private void setMove(table t) {
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				t.getSquare(i, j).setSquare(squareType.empty);
				
			}
		}
		
		t.getSquare(6, 1).setSquare(squareType.blackqueen);
		
	}
}
