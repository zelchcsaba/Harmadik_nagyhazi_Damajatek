package checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.io.Serializable;
import java.util.Random;
import javax.swing.*;


public class table implements Serializable{
	
	static final long serialVersionUID = -6752234367120497733L;
	//a tábla mezői
	private square[][] board;
	//a tábla nagysága
	private final int SIZE = 8;
	//a kiválasztott mező
	private square selectedSq;
	//igaz, ha épp a fehér játékos jön
	private boolean isWhite;
	//igaz, ha a bot játékos be van kapcsolva
	private boolean isBot;
	//ez az osztály felel a bábukkal való lépésekért
	private move mv;
	//random szám generálás
	private Random random;
	 
	public table() {
		//tábla kezdeti helyzetbe állitása
		initTable();
		setActionListener();
		mv = new move(this);
		random = new Random();
		
	}
	
	 
	//létrehozza a táblát, és feltölti bábukkal, kezdeti fázisba helyezve a játékot
	public void initTable() {
		
		board = new square[SIZE][SIZE];
		//kezdetben nincs kijelölt mező
		selectedSq = null;
		//mindig a fehér kezd
		isWhite = true;
		//kikapcsolom a robot játékost
		isBot = false;
		
		for(int i=0; i<SIZE; i++) {
			
			for(int j=0; j<SIZE; j++) {
				//létrehozza a mezőt
				square sq = new square(i,j);
				
                if ((i + j) % 2 == 0) {
                	
                    sq.setBackground(new Color(200,200,200));
                    
                } else {
                	
                    sq.setBackground(new Color(40,40,40));
                    //a megfelelő mezőkre bábukat helyez
                    if (i < 3) {
                    	
                        sq.setblackpiece();
                        
                    } else if (i > 4) {
                    	
                    	sq.setwhitepiece();
                    	
                    }
                }
				
                //hozzáadja a mezőt a táblához
                board[i][j]=sq;
                 
			}
		}
	}
	
	//beállitja az actionListenereket a tábla minden mezőjére
	public void setActionListener() {
		
		for(int i=0; i<SIZE; i++) {
			
			for(int j=0; j<SIZE; j++) {
				
				board[i][j].addActionListener(new checkForMove(board[i][j]));
				
			}
		}
	}
	
	//visszaadja az adott pozicióban levő mezőt
	public square getSquare(int i, int j) {
		
		return board[i][j];
		
	}
	
	//visszaadja a tábla sorainak és oszlopainaka a számát
	public int getSiz() {
		
		return SIZE;
		
	}
	
	//beállitja a kiválasztott mezőt
	public void setSelected(square sq) {
		
		selectedSq = sq;
		
	}
	
	//visszaadja a kiválasztott mezőt
	public square getSelected() {
		
		return selectedSq;
		
	}
	
	//igazat ad vissza, ha a fehér játékos van soron, ellenkező esetben hamisat
	public boolean getIsWhiteTurn() {
		
		return isWhite;
		
	}
	
	//beállitja, hogy ki van soron
	public void setIsWhiteTurn(boolean isWhite) {
		
		this.isWhite = isWhite;
		
	}
	
	//igazat ad vissza, ha aktiválva van a bot funkció
	public boolean getIsBot() {
		
		return isBot;
		
	}
	
	//engedélyezi vagy letiltja a botot
	public void setIsBot(boolean bot) {
		
		this.isBot = bot;
		
	}
	
	//alaphelyzetbe állitja a tábla szineit
	public void resetColors() {
		
		for(int i=0; i<SIZE; i++) {
			
			for(int j=0; j<SIZE; j++) {
				
				if ((i + j) % 2 == 0) {
					
					board[i][j].setBackground(new Color(200,200,200));
					
                }else{
                	
                	board[i][j].setBackground(new Color(40,40,40));
                	
                }
			}
		}
	}
	
	//megnézi, ha a lépés helyes-e
	public boolean isValidMove(square sq1, square sq2) {
		//előállitja a lehetséges lépéseket
		List<square> validMoves = getMoves(sq1);
	    
		if(validMoves.contains(sq2)) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	//visszaadja a bábunak az összes lehetséges lépését
	public List<square> getMoves(square sq){
		
		List<square> validMoves = new ArrayList<>();
		
		//ha ütni lehet vele
		if(canBeat(sq)) {
			//ha egy dáma
			if(sq.getType()==squareType.blackqueen || sq.getType()==squareType.whitequeen) {
				
				validMoves.addAll(getBeatMovesBlack(sq));
				validMoves.addAll(getBeatMovesWhite(sq));
			//ha fekete bábu	
			}else if(sq.getType()==squareType.blackpiece) {
				
				validMoves.addAll(getBeatMovesBlack(sq));
			//ha fehér bábu	
			}else if(sq.getType()==squareType.whitepiece) {
				
				validMoves.addAll(getBeatMovesWhite(sq));
				
			}
			
		//ha nem lehet ütni vele
		}else {
			//ha egy dáma
			if(sq.getType()==squareType.blackqueen || sq.getType()==squareType.whitequeen) {
				
				validMoves.addAll(getMovesBlack(sq));
				validMoves.addAll(getMovesWhite(sq));
			//ha fekete bábu
			}else if(sq.getType()==squareType.blackpiece) {
				
				validMoves.addAll(getMovesBlack(sq));
			//ha fehér bábu	
			}else if(sq.getType()==squareType.whitepiece) {
				
				validMoves.addAll(getMovesWhite(sq));
				
			}
		}
		
		return validMoves;
		
	}
	
	//visszaadja hova tud lépni az a bábu, amelyik fekete és lehet ütni vele
	public List<square> getBeatMovesBlack(square sq){
		
		List<square> validMoves = new ArrayList<>();
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row+2 < SIZE && col-2 >= 0) {
			
			if(board[row+2][col-2].getType() == squareType.empty) {
				
				if((board[row+1][col-1].getType() != squareType.empty) && (board[row+1][col-1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					validMoves.add(board[row+2][col-2]);
					
				}
			}
		}
		
		if(row+2 < SIZE && col+2 < SIZE) {
			
			if(board[row+2][col+2].getType() == squareType.empty) {
				
				if((board[row+1][col+1].getType() != squareType.empty) && (board[row+1][col+1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					validMoves.add(board[row+2][col+2]);
					
				}
			}
		}
		
		return validMoves;
		
	}
	
	//visszaadja hova tud lépni az a bábu, amelyik fehér és lehet ütni vele
	public List<square> getBeatMovesWhite(square sq){
		
		List<square> validMoves = new ArrayList<>();
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row-2 >= 0 && col-2 >= 0) {
			
			if(board[row-2][col-2].getType() == squareType.empty) {
				
				if((board[row-1][col-1].getType() != squareType.empty) && (board[row-1][col-1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					validMoves.add(board[row-2][col-2]);
					
				}
			}
		}
		
		if(row-2 >= 0 && col+2 < SIZE) {
			if(board[row-2][col+2].getType()==squareType.empty) {
				if((board[row-1][col+1].getType() != squareType.empty) && (board[row-1][col+1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					validMoves.add(board[row-2][col+2]);
				}
			}
		}
		
		return validMoves;
		
	}
	
	//visszaadja hova tud lépni az a bábu, amelyik fekete és nem lehet vele ütni
	public List<square> getMovesBlack(square sq){
		
		List<square> validMoves = new ArrayList<>();
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row+1 < SIZE && col-1 >= 0) {
			
			if(board[row+1][col-1].getType() == squareType.empty) {
				
				validMoves.add(board[row+1][col-1]);
				
			}
		}
		
		if(row+1 < SIZE && col+1 < SIZE) {
			
			if(board[row+1][col+1].getType() == squareType.empty) {
				
				validMoves.add(board[row+1][col+1]);
				
			}
		}
		
		return validMoves;
	}
	
	//visszaadja hova tud lépni az a bábu, amelyik fehér és nem lehet vele ütni
	public List<square> getMovesWhite(square sq){
		
		List<square> validMoves = new ArrayList<>();
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row-1 >= 0 && col-1 >= 0){
			
			if(board[row-1][col-1].getType()==squareType.empty) {
				
				validMoves.add(board[row-1][col-1]);
				
			}
		}
		
		if(row-1 >= 0 && col+1 < SIZE) {
			
			if(board[row-1][col+1].getType() == squareType.empty) {
				
				validMoves.add(board[row-1][col+1]);
				
			}
		}
		
		return validMoves;
	}
	
	//megjeleniti a táblán, hogy az adott bábu hova tud lépni
	public void showMoves(square sq) {
		//ha a bábu tud ütni
		if(canBeat(sq)) {
			//ha dáma
			if(sq.getType( )== squareType.blackqueen || sq.getType() == squareType.whitequeen) {
				
				showBeatMovesBlack(sq);
				showBeatMovesWhite(sq);
			//ha fekete bábu
			}else if(sq.getType() == squareType.blackpiece) {
				
				showBeatMovesBlack(sq);
			//ha fehér bábu	
			}else if(sq.getType() == squareType.whitepiece) {
				
				showBeatMovesWhite(sq);
				
			}
		//ha nem tud ütni
		}else {
			//ha dáma
			if(sq.getType()==squareType.blackqueen || sq.getType()==squareType.whitequeen) {
				
				showMovesBlack(sq);
				showMovesWhite(sq);
			//ha fekete bábu
			}else if(sq.getType() == squareType.blackpiece) {
				
				showMovesBlack(sq);
			//ha fehér bábu
			}else if(sq.getType() == squareType.whitepiece) {
				
				showMovesWhite(sq);
				
			}
		}
	}
	
	//megjeleniti a bábu lépési lehetőségeit, ha fekete és ütni tud
	public void showBeatMovesBlack(square sq){
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		
		if(row+2 < SIZE && col-2 >= 0) {
			
			if(board[row+2][col-2].getType() == squareType.empty) {
				
				if((board[row+1][col-1].getType() != squareType.empty) && (board[row+1][col-1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					board[row+2][col-2].setBackground(new Color(194,27,4));
					sq.setBackground(new Color(0,49,82));
					
				}
			}
		}
		
		if(row+2 < SIZE && col+2 < SIZE) {
			
			if(board[row+2][col+2].getType() == squareType.empty) {
				
				if((board[row+1][col+1].getType() != squareType.empty) && (board[row+1][col+1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					board[row+2][col+2].setBackground(new Color(194,27,4));
					sq.setBackground(new Color(0,49,82));
					
				}
			}
		}
	}
	
	//megjeleniti a bábu lépési lehetőségeit, ha fehér és ütni tud
	public void showBeatMovesWhite(square sq){
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row-2 >= 0 && col-2 >= 0) {
			
			if(board[row-2][col-2].getType() == squareType.empty) {
				
				if((board[row-1][col-1].getType() != squareType.empty) && (board[row-1][col-1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					board[row-2][col-2].setBackground(new Color(194,27,4));
					sq.setBackground(new Color(0,49,82));
					
				}
			}
		}
		
		if(row-2 >= 0 && col+2 < SIZE) {
			
			if(board[row-2][col+2].getType() == squareType.empty)  {
				
				if((board[row-1][col+1].getType() != squareType.empty) && (board[row-1][col+1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					board[row-2][col+2].setBackground(new Color(194,27,4));
					sq.setBackground(new Color(0,49,82));
					
				}
			}
		}
	}
	
	//megjeleniti a bábu lépési lehetőségeit, ha fekete és nem tud ütni
	public void showMovesBlack(square sq) {
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row+1 < SIZE && col-1 >= 0) {
			
			if(board[row+1][col-1].getType() == squareType.empty) {
				
				board[row+1][col-1].setBackground(new Color(11,102,35));
				
			}
		}
		
		if(row+1 < SIZE && col+1 < SIZE) {
			
			if(board[row+1][col+1].getType() == squareType.empty) {
				
				board[row+1][col+1].setBackground(new Color(11,102,35));
				
			}
		}
	}
	
	//megjeleniti a bábu lépési lehetőségeit, ha fehér és nem tud ütni
	public void showMovesWhite(square sq) {
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row-1 >= 0 && col-1 >= 0) {
			
			if(board[row-1][col-1].getType() == squareType.empty) {
				
				board[row-1][col-1].setBackground(new Color(11,102,35));
				
			}
		}
		
		if(row-1 >= 0 && col+1 < SIZE) {
			
			if(board[row-1][col+1].getType() == squareType.empty)  {
				
				board[row-1][col+1].setBackground(new Color(11,102,35));
				
			}
		}
	}

	//igazat ad vissza, ha a táblán az adott játékosnak van olyan bábuja, amivel ütni tud
	public boolean findBeat(boolean isWhitePlayer) {
		//végig megy a táblán
		for(int i=0; i<SIZE; i++) {
			
			for(int j=0; j<SIZE; j++) {
				
				if(board[i][j].getType() != squareType.empty && isWhitePlayer == board[i][j].getPiece().getIsWhite()){
					//megnezi, ha az adott bábu ütni tud
					if(canBeat(board[i][j])) {
						
						return true;
						
					}
				}
			}
		}
		
		return false;
		
	}
	
	//megnézi, ha az adott bábu ütni tud-e
	public boolean canBeat(square sq) {
		
		boolean beat = false;
		//ha dáma
		if(sq.getType() == squareType.blackqueen || sq.getType() == squareType.whitequeen) {
			
			beat = canBeatBlack(sq) || canBeatWhite(sq);
		//ha fekete bábu
		}else if(sq.getType()==squareType.blackpiece) {
			
			beat = canBeatBlack(sq);
		//ha fehér bábu
		}else if(sq.getType()==squareType.whitepiece) {
			
			beat = canBeatWhite(sq);
			
		}
		
		return beat;
		
	}
	
	//megnézi ha a bábu ütni tud-e, ha fekete
	public boolean canBeatBlack(square sq) {
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row+2 < SIZE && col-2 >= 0) {
			
			if(board[row+2][col-2].getType() == squareType.empty) {
				
				if((board[row+1][col-1].getType() != squareType.empty) && (board[row+1][col-1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					return true;
					
				}
			}
		}
		
		if(row+2 < SIZE && col+2 < SIZE) {
			if(board[row+2][col+2].getType() == squareType.empty) {
				if((board[row+1][col+1].getType() != squareType.empty) && (board[row+1][col+1].getPiece().getIsWhite() != sq.getPiece().getIsWhite())) {
					
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	//megnézi ha a bábu ütni tud-e, ha fehér
	public boolean canBeatWhite(square sq) {
		
		int row = sq.getRow();
		int col = sq.getCol();
		
		if(row-2 >= 0 && col-2 >= 0) {
			
			if(board[row-2][col-2].getType()==squareType.empty) {
				
				if((board[row-1][col-1].getType()!=squareType.empty) && (board[row-1][col-1].getPiece().getIsWhite()!=sq.getPiece().getIsWhite())) {
					
					return true;
					
				}
			}
		}
		
		if(row-2>=0 && col+2<SIZE) {
			
			if(board[row-2][col+2].getType()==squareType.empty)  {
				
				if((board[row-1][col+1].getType()!=squareType.empty) && (board[row-1][col+1].getPiece().getIsWhite()!=sq.getPiece().getIsWhite())) {
					
					return true;
					
				}
			}
		}
		return false;
		
	}
	
	//visszaadja az első bábut, amellyel ütni lehet
	public square getBeatPiece(boolean isWhiteTurn) {
			
		for(int i=0; i<SIZE; i++) {
				
			for(int j=0; j<SIZE; j++) {
			
				if(board[i][j].getType() != squareType.empty && isWhiteTurn == board[i][j].getPiece().getIsWhite()){
						
					if(canBeat(board[i][j])) {
							
						return board[i][j];
							
					}
				}
			}
		}
			
		return null;
		
	}
	
	//visszaadja azokat a bábukat, amelyekkel lépni lehet
	public List<square> getMovablePieces(boolean isWhitePlayer){
		
		List<square> list = new ArrayList<>();
		//végigmegy a táblán
		for(int i=0; i<SIZE; i++) {
			
			for(int j=0; j<SIZE; j++) {
				//ha a játékosnak a bábuja
				if(board[i][j].getType()!=squareType.empty && board[i][j].getPiece().getIsWhite()==isWhitePlayer) {
					if(!(getMoves(board[i][j]).isEmpty())){
						list.add(board[i][j]);
					}
				}
			}
		}
		
		return list;
		
	}
	
	//magától lép a táblán a megadott szinű bábu egyikével
	public void botMove(boolean isWhitePlayer) {
		
		//ha tud ütni
		if(findBeat(isWhitePlayer)) {
			//visszaadja azt a bábut, amellyel ütni lehet
			square selected = getBeatPiece(isWhitePlayer);
			//ameddig ütni tudunk vele
			while(canBeat(selected)) {
				//megkeresi azokat a mezőket, ahova lépve ütni tud
				List<square> lepesek = getMoves(selected);
				//random kiválaszt egyet
				int rNumb = random.nextInt(lepesek.size());
				square beat = lepesek.get(rNumb);
				
				removeBeated(selected, beat);
				//ha a tábla végére ért
				if(reached(beat,selected)){
					//ha igen a bábu királynő lesz, és a kiválasztott üres mezőre kerül
					if(selected.getPiece().getIsWhite()) {
						
						beat.setSquare(squareType.whitequeen);
						
					}else {
						
						beat.setSquare(squareType.blackqueen);
						
					}
				//ha nem a bábu nem változik,  és a kiválasztott üres mezőre kerül
				}else {
					
					beat.setSquare(selected.getType());
					
				}
				
				selected.setempty();
				selected = beat;
				
			}
		//ha nem tud ütni	
		}else{
			
			List<square> list = new ArrayList<>();
			//beleteszem az összes bábut, amely a játékosé
			list = getMovablePieces(isWhitePlayer);
			
			if(!list.isEmpty()) {
				
				int rNumb1 = random.nextInt(list.size());
				//kiválasztok egy random mezőt
				square selected = list.get(rNumb1); 
				List<square> lepesek = getMoves(selected);
			
				//random kiválaszt egy lépést
				int rNumb2 = random.nextInt(lepesek.size());
				square lepes = lepesek.get(rNumb2);
				
				if(reached(lepes,selected)){
					//ha igen a bábu királynő lesz, és a kiválasztott üres mezőre kerül
					if(selected.getPiece().getIsWhite()) {
						
						lepes.setSquare(squareType.whitequeen);
						
					}else {
						
						lepes.setSquare(squareType.blackqueen);
						
					}
					
				//ha nem a bábu nem változik,  és a kiválasztott üres mezőre kerül
				}else {
					
					lepes.setSquare(selected.getType());
					
				}
				
				selected.setempty();
				
			}
		}	
	}
	
	//törli a tábláról az ütött bábut
	public void removeBeated(square sq1,square sq2) {
				
		int row = (sq1.getRow()+sq2.getRow())/2;
		int col = (sq1.getCol()+sq2.getCol())/2;
				
		board[row][col].setSquare(squareType.empty);
				
	}
	
	//megnézi, ha az sq2 bábu az sq1 mezőre lép, akkor eléri-e a tábla végét
	public boolean reached(square sq1, square sq2) {
			
		if(sq2.getPiece().getIsWhite()) {
				
			if(sq1.getRow()==0) {
					
				return true;
					
			}
				
		}else {
				
			if(sq1.getRow()==SIZE-1) {
					
				return true;
					
			}
		}
			
		return false;
			
	}
	
	//megnézi, hogy a megadott játékos nyert-e
	public boolean checkWin(boolean isWhiteTurn) {
		if(isWin(isWhiteTurn)) {
			//létrehoz egy új ablakot
			JFrame frame = new JFrame("Message Example");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(300, 300);
	        
			if(isWhiteTurn) {
				
				JOptionPane.showMessageDialog(frame, "White wins!");
				
			}else {
				
				JOptionPane.showMessageDialog(frame, "Black wins");
				
			}
			
			return true;
			
		}else {
			
			return false;
			
		}
	}
	
	public boolean isWin(boolean isWhiteTurn) {
		
		if(getMovablePieces(!isWhiteTurn).isEmpty()) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	//ha valamelyik mezőre kattintunk
	private class checkForMove implements ActionListener{
		//a mező, amelyikre kattintottunk
		private square sq;
		
		public checkForMove(square sq) {
			
			this.sq=sq;
			
		}
		
		@Override  
	    public void actionPerformed(ActionEvent e) { 
	
			mv.movePiece(sq);
			
		}
	}
}