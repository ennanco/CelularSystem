package es.udc.tic.efernandez.cellularsimulator.visualinterface.receptorseditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ReceptorsDisabledDialog extends JDialog {
	private ReceptorsEditor parent;
	private Point selected;
	
	public ReceptorsDisabledDialog(ReceptorsEditor parent, Point selected) {
		super(parent);
		this.parent = parent;
		this.selected = selected;
		inicialice();
	}
    
	private void inicialice() {
		
		this.getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2,5));
        JPanel buttonContainer = new JPanel(new GridLayout(0,2));

        this.getContentPane().add(buttonContainer,BorderLayout.SOUTH);
        this.getContentPane().add(mainPanel,BorderLayout.CENTER);
        
        JButton acceptButton = new JButton("Accept");
        JButton cancelButton = new JButton("Cancel");
        
        acceptButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	acceptAction();
            }
        });
        
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                cancelAction();
            }
        });
        
        buttonContainer.add(acceptButton);
        buttonContainer.add(cancelButton);
        
        mainPanel.add(new JLabel("Disabled Receptor?"));
        
        this.pack();        
	}
	
	private void cancelAction(){
        this.setVisible(false);
    }
	
	private void acceptAction(){	   	
		this.setVisible(false);
		parent.eraseReceptor(selected);    	
    }
}
