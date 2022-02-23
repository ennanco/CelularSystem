/*
 * Created on 31-ago-2005
 */
package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.parametersmanagerxml.ParametersManagerXML;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeFactory;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.cellprocesor.CelularProcessorPanel;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.configuration.DNAConfigureDialog;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.gradienteditor.GradientEditor;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel.IndividualExamineFrame;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.populationlist.PopulationListPanel;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.receptorseditor.ReceptorsEditor;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.receptorseditor.ReceptorsWarning;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.templateeditor.TemplateConfigureDialog;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;
import es.udc.tic.efernandez.util.valuetable.ValueTable;


/**
 * @author Enrique Fernández Blanco
 */
public class DNAPopulationWindow extends JFrame {

	private FacadeInterface facade = FacadeFactory.newInstance();
	
    private PopulationListPanel populationListPanel;
    private CelularProcessorPanel celularProcessorPanel;
    
    public DNAPopulationWindow(){
        super();
        inicialize();
    }

    private void inicialize() {
        // prepare the window.
        this.setSize(600,600);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
        this.setTitle("Cellular Processor");
        
        getContentPane().setLayout( new BorderLayout());
 
        // create the menu
        JMenuBar menuBar = new JMenuBar();
        
        // create parameters manager
        JMenu configurationParameters = new JMenu("Parameters");
        JMenuItem editParameters = new JMenuItem("Edit...");
        JMenuItem loadParameters = new JMenuItem("Load parameters...");
        JMenuItem storeParameters = new JMenuItem("Save parameters...");
        JMenuItem evaluationTemplate = new JMenuItem("Evaluation template...");
        JMenuItem loadFileTraining = new JMenuItem("Load File Training...");
        configurationParameters.add(editParameters);
        configurationParameters.add(loadParameters);
        configurationParameters.add(storeParameters);
        configurationParameters.add(evaluationTemplate);
        configurationParameters.add(loadFileTraining);
        menuBar.add(configurationParameters);
        
        editParameters.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                configureDialog();
            }
         });
        loadParameters.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
                GeneralFileFilter filter = new GeneralFileFilter();
                filter.addExtension("prm");
                filter.setDescription("Parameters Files");
                String fileName = generalOpenFileDialog(filter);
                if(fileName!=null){
                	facade.getParameters().setParameters(ParametersManagerXML.load(fileName));
                }
        	}
        });
        
        storeParameters.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
                GeneralFileFilter filter = new GeneralFileFilter();
                filter.addExtension("prm");
                filter.setDescription("Parameters Files");
                String fileName = generalSaveFileDialog(filter);
                if(fileName!=null){
                	ParametersManagerXML.store(fileName, facade.getParameters());	
                }
			}
        });
        
        evaluationTemplate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//private function to configurate the Template
				configureTemplate();
			}
        });
        
        loadFileTraining.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		GeneralFileFilter filter = new GeneralFileFilter();
            	filter.addExtension("txt");
                filter.setDescription("TXT Files");
                String fileName = generalOpenFileDialog(filter);
                if(fileName!=null) try {
                	facade.loadTraining(fileName);
//                	celularProcessorPanel.repaint();
					} catch (IOException e) {
						e.printStackTrace();
					}
        	}
        });
        
        // principal population
        JMenu configuration = new JMenu("Population");
        JMenuItem inicializePopulation = new JMenuItem("New random population");
        JMenuItem loadPopulation = new JMenuItem("Load population...");
        JMenuItem storePopulation = new JMenuItem("Save population...");
        configuration.add(inicializePopulation);
        configuration.add(loadPopulation);
        configuration.add(storePopulation);
        menuBar.add(configuration);
        
        inicializePopulation.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                randomPopulation();
                populationListPanel.displayListActualization();
            }
        });
        
        loadPopulation.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                GeneralFileFilter filter = new GeneralFileFilter();
                filter.addExtension("xml");
                filter.setDescription("XML Files");
                String fileName = generalOpenFileDialog(filter);
                if(fileName!=null){
                	facade.loadPopulation(fileName);
                    populationListPanel.newPopulation();
                    populationListPanel.displayListActualization();
                }
            }
        });
        
        storePopulation.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent arg0) {
               GeneralFileFilter filter = new GeneralFileFilter();
               filter.addExtension("xml");
               filter.setDescription("XML Files");
               String fileName = generalSaveFileDialog(filter);
               if(fileName!=null){
            	   facade.savePopulation(fileName);
               }
           }
        });        
        
        // create the gradients.
        JMenu configurationGradients = new JMenu("Gradients");
        JMenuItem editGradients = new JMenuItem("Edit Gradients...");
        JMenuItem loadGradients = new JMenuItem("Load Gradients...");
        JMenuItem storeGradients = new JMenuItem("Save Gradients...");
        configurationGradients.add(editGradients);
        configurationGradients.add(loadGradients);
        configurationGradients.add(storeGradients);
        menuBar.add(configurationGradients);
        
        editGradients.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                configureGradient();
            }
        });
        
        loadGradients.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	GeneralFileFilter filter = new GeneralFileFilter();
            	filter.addExtension("txt");
                filter.setDescription("TXT Files");
                String fileName = generalOpenFileDialog(filter);
                if(fileName!=null) try {
                	facade.loadGradient(fileName);
//                	celularProcessorPanel.repaint();
					} catch (IOException e) {
						e.printStackTrace();
					}
            }
        });
        
        storeGradients.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	GeneralFileFilter filter = new GeneralFileFilter();
                filter.addExtension("txt");
                filter.setDescription("TXT Files");
                String fileName = generalSaveFileDialog(filter);
                if(fileName!=null) 
 					facade.saveGradient(fileName);
            }
        });
        
//      create the gradients.
        JMenu configurationReceptors = new JMenu("Receptors");
        JMenuItem editReceptors = new JMenuItem("Edit Receptorss...");
        configurationReceptors.add(editReceptors);
        menuBar.add(configurationReceptors);
        
        editReceptors.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                configureReceptors();
            }
        });
        
//      create Hystorical options
        JMenu historical = new JMenu("Historical");
        JMenuItem loadHistorical = new JMenuItem("Load Historical...");
        JMenuItem saveHistorical = new JMenuItem("Save Historical...");
        historical.add(loadHistorical);
        historical.add(saveHistorical);
        menuBar.add(historical);
        
        loadHistorical.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	GeneralFileFilter filter = new GeneralFileFilter();
            	filter.addExtension("txt");
                filter.setDescription("TXT Files");
                String fileName = generalOpenFileDialog(filter);
                if(fileName!=null) try {
                	facade.loadHystoricalLog(fileName);
                	celularProcessorPanel.enableButtoms(false);
                	celularProcessorPanel.setVisible(false);
                	celularProcessorPanel.setVisible(true);
					} catch (IOException e) {
						e.printStackTrace();
					}                
            }
        });
        
        saveHistorical.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent arg0) {
        	   GeneralFileFilter filter = new GeneralFileFilter();
               filter.addExtension("txt");
               filter.setDescription("TXT Files");
               String fileName = generalSaveFileDialog(filter);
               if(fileName!=null) 
					facade.saveHystoricalLog(fileName);               
           }
        });

        // create the working area
        //the genetic algorithm part
        JSplitPane mainWorkingPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel listPane = new JPanel(new BorderLayout());
        populationListPanel = new PopulationListPanel(facade);
        JPanel buttonContainer = new JPanel(new GridLayout(0,2));
        JButton examineButton = new JButton("Examine");
        examineButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
            	Individual individual=facade.getPopulation().get(populationListPanel.getSelected());
            	String description = facade.examine(individual);
            	TestContainer escenarios = facade.getPopulation().getScenaries(populationListPanel.getSelected());
//            	System.out.println("Escenarios: "+escenarios.numberTest());
//            	System.out.println("******************************************************");
            	(new IndividualExamineFrame(description, individual, escenarios)).setVisible(true);
            }    
        });
      
        JButton simulateButton = new JButton("Simulate >>");
        simulateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	facade.loadIndividual(populationListPanel.getSelected());
            	celularProcessorPanel.init();
//            	activate the NEXT y END buttoms on celular processor panel
            	celularProcessorPanel.enableButtoms(true);
            }
        });
        
        buttonContainer.add(examineButton);
        buttonContainer.add(simulateButton);
        
        
        listPane.add(buttonContainer, BorderLayout.SOUTH);
        listPane.add(populationListPanel, BorderLayout.CENTER);
        
        
        //the celular system part
        
        populationListPanel.setBackground(java.awt.Color.black);
        celularProcessorPanel = new CelularProcessorPanel(facade);
        celularProcessorPanel.setBackground(java.awt.Color.BLACK);
        mainWorkingPane.setDividerLocation(0.25);
        mainWorkingPane.add(listPane);
        mainWorkingPane.add(celularProcessorPanel);
        mainWorkingPane.setOneTouchExpandable(true);
        
        getContentPane().add(mainWorkingPane,BorderLayout.CENTER);
        getContentPane().add(menuBar,BorderLayout.NORTH);
        
    }
    
    public void randomPopulation(){
        facade.newPopulation();
        //this operation acualize the counters (cronometer and generations)
        populationListPanel.newPopulation();
    }
    
    public String generalOpenFileDialog(GeneralFileFilter filter){
    	String fileName = null;
    	JFileChooser chooser;
        try {
            chooser = new JFileChooser(new File("").getCanonicalPath());
        } catch (IOException e) {
            chooser = new JFileChooser();
        }
        chooser.setFileFilter(filter);
        int returnValue = chooser.showOpenDialog(this);
        
        if(returnValue == JFileChooser.APPROVE_OPTION){
            fileName = chooser.getSelectedFile().getAbsolutePath();
        }
        return fileName;
    }
    public String generalSaveFileDialog(GeneralFileFilter filter){
    	String fileName = null;
    	JFileChooser chooser;
        try {
            chooser = new JFileChooser(new File("").getCanonicalPath());
        } catch (IOException e) {
            chooser = new JFileChooser();
        }
        chooser.setFileFilter(filter);
        int returnValue = chooser.showSaveDialog(this);
        
        if(returnValue == JFileChooser.APPROVE_OPTION){
            fileName = chooser.getSelectedFile().getAbsolutePath();
        }
        return fileName;
    }
    
    private void configureDialog(){
        new DNAConfigureDialog(this).setVisible(true);
    }
    
    public FacadeInterface getModel(){
    	return facade;
    }
    
    private void configureTemplate(){
    	(new TemplateConfigureDialog(this)).setVisible(true);
    }

    private void configureGradient(){
    	(new GradientEditor(this)).setVisible(true);
    }
    
    private void configureReceptors(){
    	if(facade.getTissue()==null)
    		(new ReceptorsWarning()).setVisible(true);
    	else
    		(new ReceptorsEditor(this)).setVisible(true);
    }
    
    public static void main(String[] args) {
        //TODO ficheros de configuración en XML para el tiempo de vida.
       ValueTable.setDefault(3);

       new DNAPopulationWindow().setVisible(true);
   }

}
