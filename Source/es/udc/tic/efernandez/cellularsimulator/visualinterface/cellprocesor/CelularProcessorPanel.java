/*
 * Created on 01-sep-2005
 */
package es.udc.tic.efernandez.cellularsimulator.visualinterface.cellprocesor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.SortOrder;

import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel.GridGenesPanel;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;


/**
 * @author Enrique Fernández Blanco
 */
public class CelularProcessorPanel extends JPanel {

	//FIXME nombres y comentarios
	private static String [] proteinSequences;
    
    private FacadeInterface facade;
    
    private ChartPanel chartPane;
    private GridCellPanel table;
    private JPanel main;
    private JLabel iterationLabel;
    private Point selected;
    private JSlider sliderIteration;
    private JPanel gPanel;
    private JButton nextButton;
    private JButton endButton;
    private GridGenesPanel gridGenesPanel;
    //private int iteracion;
    
    public CelularProcessorPanel(FacadeInterface facade){
        super();
        this.facade = facade;
        proteinSequences = new String[(Integer)facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
        selected = null;
        //iteracion=0;
        inicialize();
    }
    private void inicialize() {
    	
    	for( int i = 0;  i < (Integer)facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)facade.getParameter(ParametersMap.PROTEINE_MIN_LENGTH));
    	}
        
        this.setLayout(new BorderLayout());
        
        JPanel buttonContainer = new JPanel(new GridLayout(0,3));
        nextButton = new JButton("Next");
        endButton = new JButton("End");
        iterationLabel =new JLabel();
        
        buttonContainer.add(iterationLabel);
        buttonContainer.add(nextButton);
        buttonContainer.add(endButton);
        
        nextButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	if(facade.getTissue()!= null){
            		facade.iterationTissue();
            		actualize(facade.getIterationTissue()-1);
                }
            }
        });
        
        endButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	if(facade.getTissue() != null){
            		for(int i = facade.getIterationTissue(); i < (Integer)facade.getParameter(ParametersMap.EVALUATION_NUM_ITERATION); i++){
            			facade.iterationTissue();
            		}
            		actualize(facade.getIterationTissue()-1);
            	}
            }
        });
        
        main = new JPanel(new GridLayout(2,0));
        
        table = new GridCellPanel(facade);
        table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				selected = table.position(arg0.getX(),arg0.getY());
				gPanel = new JPanel();
	        	gPanel.setLayout(new BorderLayout());
				
	        	//System.out.print("Listener Slider,iteracion: ");
	        	//System.out.println(facade.getIterationTissue());
				if(facade.getIterationTissue()>=1){
					sliderIteration = new JSlider( SwingConstants.HORIZONTAL, 0, facade.getIterationTissue()-1, facade.getIterationTissue()-1);
				    sliderIteration.setMajorTickSpacing( 1 );
				    sliderIteration.setPaintTicks( true );
				    	        	
				    sliderIteration.addChangeListener(

	                    new ChangeListener() {  // clase interna anónima

	                       // manejar cambio en el valor del control deslizable
	                       public void stateChanged( ChangeEvent e )
	                       {
	                          //poner la accion del slider
	                    	   gPanel = new JPanel();
	           	        	   gPanel.setLayout(new BorderLayout());
	                    	   gPanel.add(sliderIteration, BorderLayout.NORTH);
	                    	   actualize(sliderIteration.getValue());
	                    	   //actualizar la grilla con el historico
	                    	           	   
	                       }
	                    } // fin de la clase interna anónima
	                 ); // fin de la llamada a addChangeListener
				    gPanel.add(sliderIteration, BorderLayout.NORTH);
				    
				    if(nextButton.isEnabled()){
				    	if((facade.getTissue().get(selected.x, selected.y)!=null)&&!(facade.getTissue().get(selected.x, selected.y) instanceof ReceptorCell ))
				    		gridGenesPanel = new GridGenesPanel(facade.getTissue().get(selected.x, selected.y).
				    		getDNA().getComponents());
				    	gPanel.add(gridGenesPanel, BorderLayout.SOUTH);
				    }
				    else{
				    	//se carga de fichero el genoma
				    	
				    }
				    
				    actualize(facade.getIterationTissue()-1);
				    //System.out.println("Slider añadido");
					}
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
        });
        JScrollPane tablePane = new JScrollPane(table);
        main.add(tablePane);
        main.add(new JScrollPane(gPanel));
        
        this.add(main,BorderLayout.CENTER);
        this.add(buttonContainer,BorderLayout.NORTH);
    }
    
    public void init(){
        selected = null;
        //iteracion=0;
        facade.newHystorical();
        actualize(facade.getIterationTissue()-1);
    }

    private void actualize(int iteracion) {
        int indice;
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        String[] seriesNames = new String[] {"to Enter", "in Use"};
        proteinSequences= new String[(Integer)facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
		
		for( int i = 0;  i < (Integer)facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)facade.getParameter(ParametersMap.PROTEINE_MIN_LENGTH));
    	}
        
    	iterationLabel.setText(display());
//        table.setTissue(facade.getTissue());
        table.setVisible(false);
        table.setVisible(true);
        
        //cambiar el if para trabajar con el historico        
        //if((selected != null) && (facade.getTissue()!= null) &&(facade.getTissue().get(selected.x,selected.y) != null)){
//        Se quito esto de if:&& (facade.getTissue()!= null)
        if((selected != null) && (facade.getHystorical()!= null) && (iteracion >= 0)){
        	
        	//String[] seriesNames = new String[] {"to Enter", "in Use"};
        	// the proteins strings 
        	//DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        	
            
            indice=(Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_ROW)*
			((Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN)* iteracion
					+selected.x)+selected.y;           
            
//            System.out.print("Tamaño del historico:");
//            System.out.println(facade.getHystorical().size());
//            System.out.print("iteracion:");
//            System.out.print(iteracion);
//            System.out.print("\t");
//            System.out.print("indice:");
//            System.out.println(indice);
            
            CategoryAxis categoryAxis = new CategoryAxis3D("Proteins");
            ValueAxis valueAxis = new NumberAxis3D("Concentration");
            valueAxis.setRange(0.0,100.0);
            BarRenderer3D renderer = new BarRenderer3D();
            CategoryPlot plot = null;
            
            if(facade.getHystorical().get(indice)!=null){
	            //para q no pinte la parte enla q se almacena la validez       
	            for(int j=1;j<((CategoryDataset)facade.getHystorical().get(indice)).getRowCount();j++){
					for(int k=1;k<((CategoryDataset)facade.getHystorical().get(indice)).getColumnCount();k++){
						categoryDataset.setValue(((CategoryDataset)facade.getHystorical().get(indice)).getValue(j,k),seriesNames[j-1],displayName(proteinSequences[k-1]));
					}
	            }
	            
	            plot = new CategoryPlot(categoryDataset, categoryAxis, valueAxis, renderer);

            }
            else{
	            plot = new CategoryPlot(null, categoryAxis, valueAxis, renderer);
            }
            plot.setOrientation(PlotOrientation.HORIZONTAL);
            plot.setRowRenderingOrder(SortOrder.DESCENDING);
            plot.setColumnRenderingOrder(SortOrder.DESCENDING);
            plot.setForegroundAlpha(0.75f);
            JFreeChart chart = new JFreeChart(
        			"cell ("+ selected.x+","+selected.y+")", JFreeChart.DEFAULT_TITLE_FONT, plot, true
        					);
//	          change rendering order to ensure that bar overlapping is the 
            // right way around                                        	       	
        	chartPane = new ChartPanel(chart);
            gPanel.add(chartPane,BorderLayout.CENTER);
            
            //hay q actualizar tb el Genoma desde el historico
            if((facade.getTissue().get(selected.x, selected.y)!=null)&&!(facade.getTissue().get(selected.x, selected.y) instanceof ReceptorCell)){
		    	gridGenesPanel = new GridGenesPanel(facade.getTissue().get(selected.x, selected.y).
		    		getDNA().getComponents());
		    	gPanel.add(gridGenesPanel, BorderLayout.SOUTH);
		    }
        }
        
        main.remove(1);   
        main.add(new JScrollPane(gPanel));
        main.setVisible(false);
        main.setVisible(true);
    }
    
    private String display(){
        return "Iteration "+ facade.getIterationTissue() +"/"+facade.getParameter(ParametersMap.EVALUATION_NUM_ITERATION);
    }
    
    
    private String displayName(String sequence){

    	if(facade.getParameter(ParametersMap.APOPTOSIS_STRING).equals(sequence))
    		return "Apoptosis "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_N).equals(sequence))
    		return "Grown N "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_S).equals(sequence))
    		return "Grown S "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_E).equals(sequence))
    		return "Grown E "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_W).equals(sequence))
    		return "Grown W "+sequence;
    	else
    		return sequence;
    }
    
    public void enableButtoms(boolean estado){
    	nextButton.setEnabled(estado);
    	endButton.setEnabled(estado);
    }
}
