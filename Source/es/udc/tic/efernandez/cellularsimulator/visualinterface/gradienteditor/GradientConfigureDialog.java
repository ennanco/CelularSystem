package es.udc.tic.efernandez.cellularsimulator.visualinterface.gradienteditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.data.category.DefaultCategoryDataset;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;
public class GradientConfigureDialog extends JDialog {
	
    private JTextField radio;
    private JComboBox proteinas;
    private GradientEditor parent;
    private String [] proteinSequences;
    private String [] proteinNames;
    private Object protein;
    private Number proteinSelected;
    
    
    public GradientConfigureDialog(GradientEditor parent) {
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
        
        radio = new JTextField("1");//+plantilla.getNumRow());   

        proteinSequences= new String[(Integer)parent.getModel().getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
        proteinNames= new String[(Integer)parent.getModel().getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
        for( int i = 0;  i < (Integer)parent.getModel().getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)parent.getModel().getParameter(ParametersMap.PROTEINE_MIN_LENGTH));
    		proteinNames[i] = displayName(proteinSequences[i]);
    	}
        proteinas = new JComboBox(proteinNames);
        
        mainPanel.add(new JLabel("Radix"));
        mainPanel.add(radio);
        
        mainPanel.add(new JLabel("Proteins"));
        mainPanel.add(proteinas);
//        proteinas.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent arg0) {
        		 
  //      	}
//        });
        
        this.pack();        
	}
	
	private void cancelAction(){
        this.setVisible(false);
    }
	
	private void acceptAction(){
		int i=0;
		
    	DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
		int radix = (radio.getText().equals("")||Integer.parseInt(radio.getText())<1)? 1: Integer.parseInt(radio.getText());
		
		protein = proteinas.getSelectedItem();
		while(!protein.equals(proteinNames[i])){
			i=i+1;
		}
		try {
			proteinSelected = BinaryStringOperations.convert(proteinSequences[i]);
		} catch (NonValidSequenceException e) {
			e.printStackTrace();
		}
		
    	categoryDataset.setValue(radix, "Valores", "Radio");
    	categoryDataset.setValue(proteinSelected, "Valores", "ProteinaEmitida");
    	categoryDataset.setValue(1, "Valores", "Foco");
    	categoryDataset.setValue(0, "Valores", "Radiado");
    	categoryDataset.setValue(-1, "Valores", "ProteinaRecivida");
   	
    	parent.setAux(categoryDataset);
    	this.setVisible(false);
    }
	
	private String displayName(String sequence){

    	if(parent.getModel().getParameter(ParametersMap.APOPTOSIS_STRING).equals(sequence))
    		return "Apoptosis "+sequence;
    	else if(parent.getModel().getParameter(ParametersMap.GROWN_STRING_N).equals(sequence))
    		return "Grown N "+sequence;
    	else if(parent.getModel().getParameter(ParametersMap.GROWN_STRING_S).equals(sequence))
    		return "Grown S "+sequence;
    	else if(parent.getModel().getParameter(ParametersMap.GROWN_STRING_E).equals(sequence))
    		return "Grown E "+sequence;
    	else if(parent.getModel().getParameter(ParametersMap.GROWN_STRING_W).equals(sequence))
    		return "Grown W "+sequence;
    	else
    		return sequence;
    }
}
