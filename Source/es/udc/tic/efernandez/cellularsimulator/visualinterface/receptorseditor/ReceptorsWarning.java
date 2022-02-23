package es.udc.tic.efernandez.cellularsimulator.visualinterface.receptorseditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReceptorsWarning extends JDialog {
	
	public ReceptorsWarning() {
		inicialice();
	}
    
	private void inicialice() {
		
		this.getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2,5));
        JPanel buttonContainer = new JPanel(new GridLayout(0,3));

        this.getContentPane().add(buttonContainer,BorderLayout.SOUTH);
        this.getContentPane().add(mainPanel,BorderLayout.CENTER);
        
        JButton firstButton = new JButton();
        JButton acceptButton = new JButton("Accept");
        JButton secondButton = new JButton();
        
        acceptButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	acceptAction();
            }
        });
        
        buttonContainer.add(firstButton);
        firstButton.setVisible(false);
        buttonContainer.add(acceptButton);
        buttonContainer.add(secondButton);
        secondButton.setVisible(false);
        
        mainPanel.add(new JLabel("Tissue null, can´t established receptors"));
        
        this.pack();        
	}
	
	private void acceptAction(){	   	
		this.setVisible(false);    	
    }

}
