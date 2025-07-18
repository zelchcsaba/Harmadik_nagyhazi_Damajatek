package checkers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//ez az osztály gondoskodik a tábla adott fájlból betöltéséről

public class fileLoader extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -7696253401422681506L;
	//fájlok listázásához
	private JList<String> fileList;
	private DefaultListModel<String> listModel;
    //betöltésgomb
    private JButton loadButton;
    //aktuális mappa, ahol a fájlok vannak
    private final String DIRECTORY_PATH = "."; 
    //a tábla, amit be kell olvasnunk fájlból
    private table t;
    

    public fileLoader(window parent) {
    	
        super(parent, "Load Table", true);   
        setSize(300, 300);
        setLayout(new BorderLayout());

        //fájlok listázásához
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        
        //scrollPane létrehozása
        JScrollPane scrollPane = new JScrollPane(fileList);

        //betöltésgomb
        loadButton = new JButton("Load Table");
        loadButton.addActionListener(this);
        
        add(scrollPane, BorderLayout.CENTER);
        add(loadButton, BorderLayout.SOUTH);

        // fileok betöltése
        loadFilesList();

        //főablakhoz viszonyitva helyezi el
        setLocationRelativeTo(parent);
        
    }

    // fileok betöltése
    public void loadFilesList() {
    	//file-ok kilistázása az aktuális könyvtárból
    	File wd = new File(DIRECTORY_PATH);
    	File[] files = wd.listFiles();
    	
    	//a listModel feltöltése azokkal a fileokkal, amelyeknek végződése .txt
    	if (files != null) {
    		
            for (File file : files) {
            	
                if (file.isFile() && file.getName().endsWith(".txt")) {
                	
                    listModel.addElement(file.getName());
                    
                }
            }
        }
    }

    //a tábla betöltése az adott file-ból
    public void loadTableFromFile(String fileName) {
    	
    	try {
    		
			FileInputStream f=new FileInputStream(DIRECTORY_PATH + "/" + fileName);
			ObjectInputStream in=new ObjectInputStream(f);
			
			t=(table)in.readObject();
			
			if(t != null) { 
				
				JOptionPane.showMessageDialog(this, "Table loaded" );
				
			}else {
				
				JOptionPane.showMessageDialog(this, "No table found in the file.");
				
			}
	        
			in.close();
			
		}catch(IOException e1) {
			
			e1.printStackTrace();
			
		}catch(ClassNotFoundException e2) {
			
			e2.printStackTrace();
			
		}	
    }
    
    //betöltött tábla tovább adása
    public table getLoadedTable() {
    	
    	return t;
    	
    }
    
    //ha a Load Table gombra kattintunk
    public void actionPerformed(ActionEvent e) {
    	
        String selectedFile = fileList.getSelectedValue();
        
        if (selectedFile != null) {
        	
            loadTableFromFile(selectedFile);
            dispose();
            
        } else {
        	
            JOptionPane.showMessageDialog(fileLoader.this, "Please select a file.");
            
        }
    }
}
