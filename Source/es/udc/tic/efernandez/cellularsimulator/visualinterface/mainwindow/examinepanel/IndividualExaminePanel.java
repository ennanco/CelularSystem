/*
 * Created on 13-sep-2005
 */
package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.operon.Operon;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;


/**
 * @author Enrique Fernández Blanco
 */
public class IndividualExaminePanel extends JPanel {

	private String description;
	private DNA dna;
	private GridGenesPanel genes;
	private JTextArea text;
	private JPanel genoma;
	private Point selected;
	private DNAComponent[] componentes;
	
    
    public IndividualExaminePanel(String description, Individual individual){
        super();
        selected = null;
        this.description = description;
        this.dna = IndividualToDNA.DNAIndividualToDNA(individual);
//        System.out.println("Componenetes del dna: "+dna.getComponents().length);
        
        inicialize();
    }
    
    
	private void inicialize() {
    	
        this.setLayout(new BorderLayout());
        
        //genoma
    	genoma = new JPanel(new GridLayout(2,0));
    	componentes = dna.getComponents();
    	genes = new GridGenesPanel(componentes);
    	
        genes.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				int numberColumns = Math.min(20, componentes.length);
				selected = genes.position(arg0.getX(),arg0.getY());
				if(selected!=null){
					JDialog dialog = new JDialog();
					JTextArea textaux = new JTextArea();
					if(!componentes[selected.x*numberColumns + selected.y].isOperon()){
						textaux.setText(((Gene)componentes[selected.x*numberColumns + selected.y]).toString());
					}
					else{
						textaux.setText(((Operon)componentes[selected.x*numberColumns + selected.y]).toString());
					}
					dialog.add(new JScrollPane(textaux));
					dialog.setSize(200, 200);
					dialog.setVisible(true);
					
				}
				 
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        
        text = new JTextArea();
        text.setText(description);
        JScrollPane genesPanel = new JScrollPane(genes);
        genoma.add(genesPanel);
        genoma.add(new JScrollPane(text));   
        this.add(genoma,BorderLayout.CENTER);
//        this.setSize(200,200);
    }
        
}
