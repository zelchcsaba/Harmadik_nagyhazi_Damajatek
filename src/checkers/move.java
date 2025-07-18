package checkers;

import java.awt.*;
import java.io.Serializable;

public class move implements Serializable{

	private static final long serialVersionUID = -2359339719746825333L;
	//a tábla, ahol a játék folyik
	private table t;
	//ez akkor szükséges, mikor egymás után két ütést tudunk végrehajtani
	private boolean disableSelect=false;
	
	
	 public move(table t) {
            this.t=t;
        }

    public void movePiece(square sq) {
    	
    	//leellenőrzöm, hogy a játéknak a jelenlegi körében volt-e már bábu kiválasztva, ez a kiválasztott bábu
    	if(t.getSelected() != null) {
    		
    		//ha igen, és a következő, amit kiválasztottam üres mező
    		if(sq.getType() == squareType.empty) {
    			
    			//leellenőrzöm, ha a kiválasztott bábuval tudok-e ütni
    			if(t.canBeat(t.getSelected())){
    				//ütő lépés a kiválasztott mezőre ha lehetséges
    				beatMoveTo(sq);
    				
    			//ha a kiválasztott bábuval nem tudok ütni
    			}else{
    				//sima lépés a kiválasztott mezőre ha lehetséges
    				simpleMoveTo(sq);
    					
    			}
    			
    		//ha igen, és a következő, amit kiválasztottam egy bábu
    		//leellenőrzöm, hogy az, amit kiválasztottam olyan szinű, amilyen játékos következik
    		//leellenőrzöm, ha a bábukiválasztás nincs-e tiltva	
    		}else if((t.getIsWhiteTurn() == sq.getPiece().getIsWhite()) && (!disableSelect)) {
    			//kiválasztott bábu beállitása
    			setSelectedSquare(sq);
    			
        	}
    			
    	//ha a játéknak a jelenlegi körében még nem volt bábu kiválasztva
    	//leellenőrzöm, hogy bábut választottunk-e ki
    	//leellenőrzöm, hogy a kiválasztott bábu a játékoshoz tartozik
    	}else if(sq.getType() != squareType.empty && t.getIsWhiteTurn()== sq.getPiece().getIsWhite()) {
    		//kiválasztott bábu beállitása
    		setSelectedSquare(sq);
    		
    	}
    }
    
    //ha a kiválasztott bábuval ütni lehet, és a mező, amelyet kiválasztottam üres
    //leellenőrzöm, hogy a kiválasztott üres mezőre lépés helyes ütést eredményez
    //ha igen odalépek
    public void beatMoveTo(square sq) {
    	
    	//leellenőrzöm, ha a kiválasztott üres mezőre lépés helyes a kiválasztott bábuval
		if(t.isValidMove(t.getSelected(), sq)) {
			
			//ha helyes a lépés
			//kitörlöm az ellenfél bábuját, amit ütöttem
			t.removeBeated(t.getSelected(), sq);		
			
			//leellenőrzöm, ha a lépés során elértem-e a tábla végét
			if(t.reached(sq,t.getSelected())){
				
				//ha igen a bábu királynő lesz, és a kiválasztott üres mezőre kerül
				if(t.getSelected().getPiece().getIsWhite()) {
					
					sq.setSquare(squareType.whitequeen);
					
				}else {
					
					sq.setSquare(squareType.blackqueen);
					
				}
				
				//ha nem a bábu nem változik,  és a kiválasztott üres mezőre kerül
			}else {
				
				sq.setSquare(t.getSelected().getType());
				
			}
			
			//a tábla mezőit az eredeti szinekre állitom
			t.resetColors();
			
			//a kiválasztott bábu ütés előtti helyét üres mezőnek állitom
			t.getSelected().setempty();

			
			//leellenőrzöm, ha a lépés után tudok-e még ütni a kiválasztott bábuval
			if(t.canBeat(sq)) {
				
				//frissitem a kiválasztott bábu helyét
				//ha igen, akkor megtiltom, hogy a táblán más bábut lehessen kijelölni
				//megjelenitem a lehetséges ütési lehetőségeket
				t.setSelected(sq);
				disableSelect=true;
				t.showMoves(sq);
				sq.setBackground(Color.YELLOW);
				
			}else {
				
				//ha nem, és a bábukiválasztás nincs engedélyezve, akkor ezt engedélyezem
				//beállitom, hogy ne legyen bábu kiválasztva
				//beállitom, hogy a következő játékos jöjjön
				if(disableSelect) {
					
					disableSelect=false;
					
				}
				t.setSelected(null);
				
				//leellenőrzöm, ha a bot játékos be van-e kapcsoéva
				if(t.getIsBot()) {
					
					if(!t.checkWin(t.getIsWhiteTurn())) {
						
						t.botMove(!t.getIsWhiteTurn());
						t.checkWin(!t.getIsWhiteTurn());
						
					}

				} else {
					
					if(!t.checkWin(t.getIsWhiteTurn())) {
						t.setIsWhiteTurn(!t.getIsWhiteTurn());
						
					}
				}	
			}
		}//helytelen lépés esetén nem csinálok semmit
    }
    
    //ha a kiválasztott bábuval nem lehet ütni, és a mező, amelyet kiválasztottam üres
    //leellenőrzöm, hogy a kiválasztott üres mezőre lépés helyes lépést eredményez
    //ha igen odalépek
    public void simpleMoveTo(square sq) {
    	
    	//leellenőrzöm, ha a kiválasztott üres mezőre lépés helyes a kiválasztott bábuval
		if(t.isValidMove(t.getSelected(), sq)) {
		
		//leellenőrzöm, ha a lépés során elértem-e a tábla végét
		if(t.reached(sq,t.getSelected())){
			
			//ha igen a bábu királynő lesz, és a kiválasztott üres mezőre kerül
			if(t.getSelected().getPiece().getIsWhite()) {
				
				sq.setSquare(squareType.whitequeen);
				
			}else {
				
				sq.setSquare(squareType.blackqueen);
				
			}
			
			//ha nem a bábu nem változik,  és a kiválasztott üres mezőre kerül
		}else {
			
			sq.setSquare(t.getSelected().getType());
			
		}
		
		//a tábla mezőit az eredeti szinekre állitom
		//a kiválasztott bábu ütés előtti helyét üres mezőnek állitom
		//beállitom, hogy ne legyen bábu kiválasztva
		//beállitom, hogy a következő játékos jöjjön
		t.resetColors();
		t.getSelected().setempty();
		t.setSelected(null);
		
		//leellenőrzöm, ha a bot játékos be van-e kapcsoéva
		if(t.getIsBot()) {
			
			if(!t.checkWin(t.getIsWhiteTurn())) {
				
				t.botMove(!t.getIsWhiteTurn());
				t.checkWin(!t.getIsWhiteTurn());
				
			}
			
			}else {
				
				t.setIsWhiteTurn(!t.getIsWhiteTurn());
				
			}
		}//helytelen lépés esetén nem csinálok semmit
    }
    
    //a kattintott bábut beállitja a táblán kiválasztottnak
    public void setSelectedSquare(square sq) {
    	
    	//leellenőrzöm, ha van olyan bábu, amelyikkel a játékos tud ütni
		if(t.findBeat(t.getIsWhiteTurn())) {
			
			//ha a kiválasztott bábuval tudok ütni, akkor ez lesz az új kiválasztott bábu
			//a tábla mezőit az eredeti szinekre állitom
			//megjelenitem a lehetséges ütési lehetőségeket
			if (t.canBeat(sq)){
				
    			t.resetColors();
				t.setSelected(sq);
				t.showMoves(sq);
				sq.setBackground(Color.YELLOW);
			}
			
		//ha nincs olyan bábu, amivel lehet ütni
		}else {
			
			//a tábla mezőit az eredeti szinekre állitom
			//a kiválasztott bábu a jelenleg kiválasztott bábu lesz
			//megjelenitem a lehetséges lépéseket
			t.resetColors();
			t.setSelected(sq);
			t.showMoves(sq);
			sq.setBackground(Color.YELLOW);
		}
    }
}
