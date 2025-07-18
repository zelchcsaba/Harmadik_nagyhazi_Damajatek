package checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

//ez az osztály gondoskodik a tábla fájlba mentéséről a megadott filenév szerint

public class fileSaver extends JDialog implements ActionListener{
    
	private static final long serialVersionUID = 3876859086991232837L;
	//a mező, amelybe a file nevét irjuk
	private JTextField fileNameField;
	//a tábla, amit el akarunk menteni
    private table t;

    public fileSaver(window parent) {
        super(parent, "Save Table", true);
        //átveszem az elmentendő táblát
        t = parent.getShowedTable();
        
        setSize(300, 300);
        setLayout(new FlowLayout());
        
        //a mező, amelybe a file nevét irjuk
        fileNameField = new JTextField(20);
        add(new JLabel("Enter file name:"));
        add(fileNameField);

        // OK gomb a dialog ablakban
        JButton save = new JButton("Save Table");
        save.addActionListener(this);

        add(save);
        //főablakhoz viszonyitva helyezi el
        setLocationRelativeTo(parent); 
    }

    //elmentjük a táblát a megadott fileba
    public void saveTableToFile(String fileName) {
        
    	try {
    		
			FileOutputStream f=new FileOutputStream(fileName);
			ObjectOutputStream out=new ObjectOutputStream(f);
	
			out.writeObject(t);
			JOptionPane.showMessageDialog(this, "Table saved to " + fileName);
			
			out.close();
			
		}catch(IOException e) {
			
			 e.printStackTrace();
			 
		}	
    }
    
    //ha megnyomjuk a Save Table gombot
    public void actionPerformed(ActionEvent e) {
    	
        String fileName = fileNameField.getText();
        
        if (!fileName.isEmpty()) {
        	
        	//elmentjük a táblát és bezárjuk az ablakot
            saveTableToFile(fileName);
            dispose();
            
        } else {
        	
            JOptionPane.showMessageDialog(fileSaver.this, "Please enter a valid file name.");
            
        }
    }
}