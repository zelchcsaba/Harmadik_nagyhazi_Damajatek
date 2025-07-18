package checkers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//ez az osztály felel a játék ablakának megjelenitéséért

public class window extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 4437644973409263607L;
	//a megjelenitendő tábla
	private table t;
	//a tábla Jpanelje
	private JPanel board;
	//a menü bar, és komponensei
	private JMenuBar mb;
	private JMenu newGame, toFile, endGame;
	private JMenuItem oneVone, oneVbot, save, load, exit;
	//a tábla mentéséhez és beolvasásához segédosztályok
	private fileLoader from_File;
	private fileSaver to_File;
	
	public window() {

		//tábla inicializálása
		t = new table();
		//menü bar
		mb = new JMenuBar();
		//a menük
		newGame = new JMenu("New Game");
		toFile = new JMenu("To File");
		endGame = new JMenu("End Game?");
		//menü itemek
		oneVone = new JMenuItem("1 VS 1");  
		oneVbot = new JMenuItem("1 VS bot"); 
		save = new JMenuItem("Save Game"); 
		load = new JMenuItem("Load Game"); 
		exit = new JMenuItem("Exit");
		
		newGame.add(oneVone);
		newGame.add(oneVbot);
		toFile.add(save);
		toFile.add(load);
		endGame.add(exit);
		
		mb.add(newGame);
		mb.add(toFile);
		mb.add(endGame);
		
		//a menüitemekhez actionListenerek rendelése
		oneVone.addActionListener(this);  
		oneVbot.addActionListener(this);  
		save.addActionListener(this);  
		load.addActionListener(this);  
		exit.addActionListener(this);  
		
		setJMenuBar(mb);
		
		//tábla megjelenitésének beállitása
		board = new JPanel();
		board.setLayout(new GridLayout(t.getSiz(), t.getSiz()));
		setBoardPanel();
		
		//ablak megjelenitésének beállitása
		setSize(720, 780);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Dámajáték");
		setLayout(new BorderLayout());
        add(board, BorderLayout.NORTH);
            	
	}
	
	public void actionPerformed(ActionEvent e) {   
		//kétszemélyes játékra kattintva
		if(e.getSource()==oneVone) {
			//tábla kezdeti helyetbe állitása
			t.initTable();
			setBoardPanel();
		    t.setActionListener();

		}
		//egyszemélyes játékra kattintva
		if(e.getSource()==oneVbot) {
			//tábla kezdeti helyetbe állitása
			t.initTable();
			setBoardPanel();
			t.setActionListener();
	        t.setIsBot(true);
	     
		}
		//tábla mentésére kattintva
		if(e.getSource()==save) {
			//létrehozzuk a tábla mentéséért felelős osztályt
			to_File = new fileSaver(this);
			to_File.setVisible(true);
			
		}
		//tábla betöltésére kattintva
		if(e.getSource()==load) {
			//létrehozzuk a tábla betöltéséért felelős osztályt
			from_File = new fileLoader(this);
            from_File.setVisible(true);
			//ha érvényes táblát töltöttünk be, akkor felülirjuk a jelenlegi táblát
            if(from_File.getLoadedTable()!= null) {
            	 
            	t=from_File.getLoadedTable();
            	setBoardPanel();
            	t.setActionListener();
 			    
            }	
		}
		//ha a kilépésre kattintottunk
		if(e.getSource()==exit) {
			
			System.exit(0);
			
		}
	}
	
	//visszaadja az éppen megjelenitett táblát
	public table getShowedTable() {
		
		return t;
		
	}
	
	//a tábla Jpaneljét beállitja
	public void setBoardPanel() {
		//letörli a panleről az összes elemet
		board.removeAll();
		
		for(int i=0; i<t.getSiz(); i++) {
			
			for(int j=0; j<t.getSiz(); j++) {
				
				board.add(t.getSquare(i, j));
				
			}
		}
		//frissiti és újrarajzolja
		board.revalidate(); 
	    board.repaint();  
	    
	}		
}

