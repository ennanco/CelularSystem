package es.udc.tic.efernandez.cellularsimulator.visualinterface.templateeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.DNAPopulationWindow;


public class TemplateConfigureDialog extends JDialog {
	private JTextField dimensionRowText;
    private JTextField dimensionColumnText;
    private boolean matrix [][];
    private DNAPopulationWindow parent;
    
    
    public TemplateConfigureDialog(DNAPopulationWindow parent) {
		super(parent);
		this.parent = parent;
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
        
        dimensionRowText = new JTextField("10");//+plantilla.getNumRow());
        dimensionColumnText = new JTextField("10");//+plantilla.getNumColumn());
        
        mainPanel.add(new JLabel("Rows"));
        mainPanel.add(dimensionRowText);
        mainPanel.add(new JLabel("Columns"));
        mainPanel.add(dimensionColumnText);
        
        this.pack();        
	}
	
	private void cancelAction(){
        this.setVisible(false);
    }
    
    private void acceptAction(){
    	
    	int rows = (dimensionRowText.getText().equals("")||Integer.parseInt(dimensionRowText.getText())<1)? 10: Integer.parseInt(dimensionRowText.getText());
    	int columns = (dimensionColumnText.getText().equals("")||Integer.parseInt(dimensionColumnText.getText())<1)? 10: Integer.parseInt(dimensionColumnText.getText());
    	
    	matrix = new boolean [rows][columns];
    	
    	for(int i=0;i<rows;i++)
    		for(int j=0;j<columns;j++){
    			matrix[i][j]=false;
    		}
    	this.setVisible(false);
		 (new TemplateEditor(parent,matrix)).setVisible(true);

    }
}
