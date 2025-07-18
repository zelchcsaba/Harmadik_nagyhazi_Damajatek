package checkers;

import java.awt.*;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//a táblán elhelyezhető mező osztálya

public class square extends JButton implements Serializable{

	private static final long serialVersionUID = 6898193592271715003L;
	//táblában elfoglalt sor és oszlopszám
	private int row;
	private int col;
	//mező tipusa
	private squareType type;
	//mezőn található bábu
    private piece p;
    //mező nagysága
    private int dimension = 90;
    
    public square(int row, int col) {
    	//mező beállitása
    	this.row=row;
    	this.col=col;
        setPreferredSize(new Dimension(dimension, dimension));
        setOpaque(true);
        setBorderPainted(false);
        setempty();
        
    }
    
    //visszaadja a mező tipusát
    public squareType getType() {
    	return type;
    }
    
    //visszaadja a mezőn található bábut
    public piece getPiece() {
    	return p;
    }
    
    //visszaadja, hogy a mező melyik sorban található
    public int getRow() {
    	return row;
    }
    
    //visszaadja, hogy a mező melyik oszlopban található
    public int getCol() {
    	return col;
    }
    
    //a mező tipusát megadva automatikusan beállitja a mezőt
    public void setSquare(squareType sqType) {
    	
    	switch(sqType) {
    	
    		case squareType.empty:
    			setempty();
    		break;
    		
    		case squareType.blackpiece:
    			setblackpiece();
    		break;
    		
    		case squareType.whitepiece:
    			setwhitepiece();
    		break;
    		
    		case squareType.blackqueen:
    			setblackqueen();
    		break;
    		
    		case squareType.whitequeen:
    			setwhitequeen();
    		break;
    		
    	}
    }
    
    //üres mező beállitása
    public void setempty() {
		type = squareType.empty;
		p=null;
		setIcon(null);
		
	}
	
    //fekete bábut tartalmazó mező beállitása
	public void setblackpiece() {
		type = squareType.blackpiece;
		p=new piece(squareType.blackpiece);
		ImageIcon icon = scale("black.png");
		setIcon(icon);
	}
	
	//fehér bábut tartalmazó mező beállitása
	public void setwhitepiece() {
		type = squareType.whitepiece;
		p=new piece(squareType.whitepiece);
		ImageIcon icon = scale("white.png");
		setIcon(icon);
	}
	
	//fekete dámát tartalmazó mező beállitása
	public void setblackqueen() {
		type = squareType.blackqueen;
		p=new piece(squareType.blackqueen);
		ImageIcon icon = scale("blackqueen.png");
		setIcon(icon);
	}
	
	//fehér dámát tartalmazó mező beállitása
	public void setwhitequeen() {
		type = squareType.whitequeen;
		p=new piece(squareType.whitequeen);
		ImageIcon icon = scale("whitequeen.png");
		setIcon(icon);
	}
	
	//visszaadja az adott icont a megfelelő felbontásban
	private ImageIcon scale(String s) {
		
		ImageIcon icon = new ImageIcon(s);
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance( (int)(0.8*dimension), (int)(0.8*dimension),  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		return icon;
		
	} 
}

