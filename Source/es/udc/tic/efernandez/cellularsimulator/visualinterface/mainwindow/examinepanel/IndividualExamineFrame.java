package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

public class IndividualExamineFrame extends JFrame {
	
	private IndividualExaminePanel individualExaminePanel;
	private ScenaryProcessorPanel scenaryProcessorPanel;
	
	private String description;
	private Individual individual;
	private TestContainer escenarios;
	
	public IndividualExamineFrame(String description, Individual individual, TestContainer escenarios){
		super();
		this.description = description;
		this.individual = individual;
        this.escenarios = escenarios;
		inicialize();
	}

	 private void inicialize() {
	        // prepare the window.
	        this.setSize(600,600);
	        this.addWindowListener(new WindowAdapter(){
	            public void windowClosing(java.awt.event.WindowEvent evt) {
//	                System.exit(0);
	            }
	        });
	        this.setTitle("Examine Processor");
	        
	        getContentPane().setLayout( new BorderLayout());
	        
	        JSplitPane mainWorkingPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	        
	        individualExaminePanel = new IndividualExaminePanel(description, individual);
	        scenaryProcessorPanel = new ScenaryProcessorPanel(escenarios);
	        scenaryProcessorPanel.setBackground(java.awt.Color.BLACK);
	        
	        mainWorkingPane.setDividerLocation(0.25);
	        mainWorkingPane.add(individualExaminePanel);
	        mainWorkingPane.add(scenaryProcessorPanel);
	        mainWorkingPane.setOneTouchExpandable(true);
	        
	        getContentPane().add(mainWorkingPane,BorderLayout.CENTER);
	        
	 }
}
