/*
 * Created on 01-sep-2005
 */
package es.udc.tic.efernandez.cellularsimulator.visualinterface.populationlist;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.util.cronometer.Cronometer;

/**
 * @author Enrique Fernández Blanco
 */
public class PopulationListPanel extends JPanel{
	
    private int selected;
    private FacadeInterface facade;
    
    private JTable listIndividuals;
    private JLabel generationIndicator;

    //parameters for the progressBar
    JProgressBar progressBar;
    boolean pause = false;
    Thread thread = null;
    Object object = new Object();
    
    public PopulationListPanel(FacadeInterface facade){
        
    	this.facade = facade;
        selected = 0;
        inicialize();
        displayListActualization();
    }

    private void inicialize() {

        this.setLayout(new BorderLayout());

        generationIndicator = new JLabel(generationTitle());

        JButton nextButton = new JButton("Next");
        JButton endButton = new JButton("End");
        JButton pauseButton = new JButton("Pause");

        progressBar = new JProgressBar();
        
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        
        JPanel buttonContainer = new JPanel(gb);
        
        constraints.fill = GridBagConstraints.BOTH;
        
        constraints.weightx = 1.0;
        gb.setConstraints(nextButton,constraints);
        buttonContainer.add(nextButton);
        
        gb.setConstraints(endButton,constraints);
        buttonContainer.add(endButton);
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gb.setConstraints(pauseButton,constraints);
        buttonContainer.add(pauseButton);
        
        gb.setConstraints(generationIndicator,constraints);
        buttonContainer.add(generationIndicator);
        
        constraints.weightx = GridBagConstraints.REMAINDER;
        gb.setConstraints(progressBar,constraints);
        buttonContainer.add(progressBar);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                iteration();
                displayListActualization();
            }
        });
        
        endButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
               	startCount();
            	displayListActualization();
            }
            
        });

        pauseButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt){
        		stopCount();
        	}
        });
        
        add(buttonContainer, BorderLayout.NORTH);
        
        TableModel model = new AbstractTableModel(){

            public int getColumnCount() {
                return 3;
            }

            public int getRowCount() {
                if(facade.getPopulation() != null)
                    return facade.getPopulation().populace();
                else 
                    return 0;
            }

            public Object getValueAt(int arg0, int arg1) {
                Object toReturn = null;
                if(arg1 == 0){
                    toReturn = "Individuo"+ arg0;
                } else if(arg1 ==1){
                    toReturn = new Float(facade.getPopulation().getEvaluation(arg0));
                } else if(arg1 == 2){
                    toReturn = new Integer(facade.getPopulation().get(arg0).numberOfVariables());
                }
                return toReturn;
            }            
        };
        
        listIndividuals = new JTable(model);
        listIndividuals.setTableHeader(null);
        listIndividuals.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

            public void valueChanged(ListSelectionEvent arg0) {
                selected = listIndividuals.getSelectedRow();                
            }
            
        });
        listIndividuals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(new JScrollPane(listIndividuals),BorderLayout.CENTER);
    }
    
    public void newPopulation(){
        facade.resetGeneration();
        Cronometer.reset();
        displayListActualization();
    }
   
    public void displayListActualization(){
        generationIndicator.setText(generationTitle());
        listIndividuals.setVisible(false);
        listIndividuals.setVisible(true);
    }
    
    private String generationTitle(){
    	return "Generation " + facade.getGeneration() + "/" + facade.getParameter(ParametersMap.NUMBER_GENERATIONS);
    }
 
    private synchronized void iteration(){
    	Cronometer.start();
    	if(facade.getPopulation() == null)
            return;
        facade.evolvePopulation();
        Cronometer.stop();
        System.out.println("Generation: "+facade.getGeneration()+" iteration Time "+Cronometer.getPartialTime()
        		+" Global "+Cronometer.getGlobalTime()/60000+"min "+Cronometer.getGlobalTime()%60000/1000+"s");
    }
    
    public int getSelected(){
    	return selected;
    }
    
    private void startCount(){
    	if(thread == null){
    		thread = new RunThread();
    		pause = false;
    		thread.start();
    	}
    }
    
    private void stopCount(){
    	synchronized (object) {
    		pause = true;
		}
    }

    // internal class to run the thread of the Progress Bar
    class RunThread extends Thread{
    	public void run(){
        	int min = 0;
        	int max = (Integer)facade.getParameter(ParametersMap.NUMBER_GENERATIONS);
        	int actual = facade.getGeneration();
        	
        	progressBar.setMinimum(min);
        	progressBar.setMaximum(max);
        	progressBar.setValue(actual);
        	
        	for(int i = actual; i < max; i++){
        		synchronized (object) {
        			if (pause){
        				break;
        			} else{
        				progressBar.setValue(i);
        				iteration();
        			}
				}
        	}
        	displayListActualization();
    		thread = null;    		
    	}
    }

}

