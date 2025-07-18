package checkers;

import java.io.Serializable;

//a bábu osztálya, amely a bábu szinét tartalmazza

public class piece implements Serializable{

	private static final long serialVersionUID = 3161673692932032991L;
	//igaz, ha a bábu szine fehér
	boolean isWhite;
	//bábu inicializálása a fajtája alapján
	public piece(squareType type) {
		
		if(type == squareType.blackpiece || type == squareType.blackqueen) {
			
			isWhite = false;
			
		}else {
			
			isWhite = true;
			
		}
	}
	
	//igazat ad vissza, ha a bábu fehér, ellenkező esetben hamist
	public boolean getIsWhite() {
		
		return isWhite;
		
	}
}
