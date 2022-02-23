/*
 * Created on 04-sep-2005
 */
package es.udc.tic.efernandez.cellularsimulator.visualinterface.configuration;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.DNAPopulationWindow;


/**
 * @author Enrique Fernández Blanco
 */
public class DNAConfigureDialog extends JDialog {

    private FacadeInterface facade;

    private JTextField generationText;
    private JTextField individualsText;
    private JTextField numberVariablesText;
    private JTextField goodCrossText;
    private JTextField mutationPercentageText;
    private JTextField minimizationNumberParameterText;
    private JTextField activePartText;
    private JTextField proteineNumberText;
    private JTextField proteineLengthText;
    private JTextField dimensionRowText;
    private JTextField dimensionColumnText;
    private JTextField iterationText;
    private JTextField lowerLimitText;
    private JTextField higherLimitText;
    private JTextField apoptosisLimitText;
    private JTextField apoptosisProteineText;
    private JTextField northProteineText;
    private JTextField southProteineText;
    private JTextField westProteineText;
    private JTextField eastProteineText;


    public DNAConfigureDialog(DNAPopulationWindow parent) throws HeadlessException {
        super();
        this.facade = parent.getModel();
        inicialize();
    }

    private void inicialize() {
        this.getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(20,2));
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
        
        generationText = new JTextField(""+facade.getParameter(ParametersMap.NUMBER_GENERATIONS));
        individualsText = new JTextField(""+facade.getParameter(ParametersMap.NUMBER_INDIVIDUALS));
        numberVariablesText = new JTextField(""+facade.getParameter(ParametersMap.NUMBER_VARIABLES));
        goodCrossText = new JTextField(""+facade.getParameter(ParametersMap.CROSSOVER_PERCENTAGE));
        mutationPercentageText = new JTextField(""+facade.getParameter(ParametersMap.MUTATION_PERCENTAGE));
        minimizationNumberParameterText = new JTextField(""+facade.getParameter(ParametersMap.MINIMIZATION_NUMBER_PARAMETER));
        activePartText = new JTextField(""+facade.getParameter(ParametersMap.ACTIVE_PART_MAX_PROTEINE));
        proteineNumberText = new JTextField(""+facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE));
        proteineLengthText = new JTextField(""+facade.getParameter(ParametersMap.PROTEINE_MIN_LENGTH));
        dimensionRowText = new JTextField(""+facade.getParameter(ParametersMap.EVALUATION_DIMENSION_ROW));
        dimensionColumnText = new JTextField(""+facade.getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN));
        iterationText = new JTextField(""+facade.getParameter(ParametersMap.EVALUATION_NUM_ITERATION));
        lowerLimitText = new JTextField(""+facade.getParameter(ParametersMap.PROTEIN_LOWER_LIMIT));
        higherLimitText = new JTextField(""+facade.getParameter(ParametersMap.PROTEIN_HIGH_LIMIT));
        apoptosisLimitText = new JTextField(""+facade.getParameter(ParametersMap.PROTEIN_APOPTOSIS_LIMIT));
        apoptosisProteineText = new JTextField(""+facade.getParameter(ParametersMap.APOPTOSIS_STRING));
        northProteineText = new JTextField(""+facade.getParameter(ParametersMap.GROWN_STRING_N));
        southProteineText = new JTextField(""+facade.getParameter(ParametersMap.GROWN_STRING_S));
        westProteineText = new JTextField(""+facade.getParameter(ParametersMap.GROWN_STRING_W));
        eastProteineText = new JTextField(""+facade.getParameter(ParametersMap.GROWN_STRING_E));
        
        mainPanel.add(new JLabel("Generations"));
        mainPanel.add(generationText);
        mainPanel.add(new JLabel("Population Individuals"));
        mainPanel.add(individualsText);
        mainPanel.add(new JLabel("Number Initial Length"));
        mainPanel.add(numberVariablesText);
        mainPanel.add(new JLabel("Crossover percentage"));
        mainPanel.add(goodCrossText);
        mainPanel.add(new JLabel("Mutation percentage"));
        mainPanel.add(mutationPercentageText);
        mainPanel.add(new JLabel("Minimization Size Parameter"));
        mainPanel.add(minimizationNumberParameterText);
        mainPanel.add(new JLabel("Active part number"));
        mainPanel.add(activePartText);
        mainPanel.add(new JLabel("Proteine number"));
        mainPanel.add(proteineNumberText);
        mainPanel.add(new JLabel("Proteine length"));
        mainPanel.add(proteineLengthText);
        mainPanel.add(new JLabel("Rows"));
        mainPanel.add(dimensionRowText);
        mainPanel.add(new JLabel("Columns"));
        mainPanel.add(dimensionColumnText);
        mainPanel.add(new JLabel("Iteration number"));
        mainPanel.add(iterationText);
        mainPanel.add(new JLabel("Concentration lower limit"));
        mainPanel.add(lowerLimitText);
        mainPanel.add(new JLabel("Concentration higher limit"));
        mainPanel.add(higherLimitText);
        mainPanel.add(new JLabel("Apoptosis Limit"));        
        mainPanel.add(apoptosisLimitText);
        mainPanel.add(new JLabel("Apoptosis proteine"));        
        mainPanel.add(apoptosisProteineText);
        mainPanel.add(new JLabel("North proteine"));        
        mainPanel.add(northProteineText);
        mainPanel.add(new JLabel("South proteine"));        
        mainPanel.add(southProteineText);
        mainPanel.add(new JLabel("West proteine"));        
        mainPanel.add(westProteineText);
        mainPanel.add(new JLabel("East proteine"));        
        mainPanel.add(eastProteineText);

        this.pack();
    }
    
    private void cancelAction(){
        this.setVisible(false);
    }
    
    private void acceptAction(){
    	ParametersMap map = facade.getParameters();
    	HashMap parameters = map.getParameters();
    
    	parameters.clear();
	    parameters.put(ParametersMap.NUMBER_GENERATIONS, Integer.parseInt(generationText.getText()));
	    parameters.put(ParametersMap.NUMBER_INDIVIDUALS, Integer.parseInt(individualsText.getText()));
	    parameters.put(ParametersMap.NUMBER_VARIABLES, Integer.parseInt(numberVariablesText.getText()));
	    parameters.put(ParametersMap.CROSSOVER_PERCENTAGE, Float.parseFloat(goodCrossText.getText()));
	    parameters.put(ParametersMap.MUTATION_PERCENTAGE, Float.parseFloat(mutationPercentageText.getText()));
	    parameters.put(ParametersMap.MINIMIZATION_NUMBER_PARAMETER, Float.parseFloat(minimizationNumberParameterText.getText()));
	    parameters.put(ParametersMap.ACTIVE_PART_MAX_PROTEINE, Integer.parseInt(activePartText.getText()));
	    parameters.put(ParametersMap.MAX_NUMBER_OF_PROTEINE, Integer.parseInt(proteineNumberText.getText()));
	    parameters.put(ParametersMap.PROTEINE_MIN_LENGTH, Integer.parseInt(proteineLengthText.getText()));
	    parameters.put(ParametersMap.EVALUATION_DIMENSION_ROW, Integer.parseInt(dimensionRowText.getText()));
	    parameters.put(ParametersMap.EVALUATION_DIMENSION_COLUMN, Integer.parseInt(dimensionColumnText.getText()));
	    parameters.put(ParametersMap.EVALUATION_NUM_ITERATION, Integer.parseInt(iterationText.getText()));
	    parameters.put(ParametersMap.PROTEIN_LOWER_LIMIT, Float.parseFloat(lowerLimitText.getText()));
	    parameters.put(ParametersMap.PROTEIN_HIGH_LIMIT, Float.parseFloat(higherLimitText.getText()));
	    parameters.put(ParametersMap.PROTEIN_APOPTOSIS_LIMIT, Float.parseFloat(apoptosisLimitText.getText()));
	    parameters.put(ParametersMap.APOPTOSIS_STRING, apoptosisProteineText.getText());
	    parameters.put(ParametersMap.GROWN_STRING_N, northProteineText.getText());
	    parameters.put(ParametersMap.GROWN_STRING_S, southProteineText.getText());
	    parameters.put(ParametersMap.GROWN_STRING_W, westProteineText.getText());
	    parameters.put(ParametersMap.GROWN_STRING_E, eastProteineText.getText());
	    
	    map.setParameters(parameters);
	    facade.setParameters(map);
	    facade.resetGeneration();
	    
	    this.setVisible(false);
    }
}
