package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
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
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test.TissueTest;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;

public class ScenaryProcessorPanel extends JPanel {

	private static String [] proteinSequences;
	
	private TestContainer testContainer;
	private String[] escenarios;
	private int escenario;
	private JPanel main;
	private GridScenaryPanel table;
	private JPanel gPanel;
	private Point selected;
	private JSlider sliderIteration;
	private ParametersMap parameters;
	private ChartPanel chartPane;
	private JComboBox comboBox;
	
	public ScenaryProcessorPanel(TestContainer escenarios){
		super();
		this.testContainer = escenarios;
		parameters = new ParametersMap();
		proteinSequences = new String[(Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
		selected = null;
		escenario = 0;
		inicialize();
	}
	
	public TissueTest getTest(){
		return (TissueTest)testContainer.getTest(escenario);
	}
	
	private void inicialize() {
		
		this.setLayout(new BorderLayout());
		
		main = new JPanel(new GridLayout(2,0));
		
		JPanel boxContainer = new JPanel();
		boxContainer.setLayout(new BorderLayout());
		
		escenarios = new String[testContainer.numberTest()];
		for(int i=0; i<testContainer.numberTest(); i++){
			if(testContainer.getTest(i)!=null)
				escenarios[i] = ((TissueTest)testContainer.getTest(i)).getName()+" (Fitness: "+testContainer.getTest(i).getFitness()+")";
		}
		
		comboBox = new JComboBox(escenarios);
		comboBox.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				escenario = comboBox.getSelectedIndex();
				actualize(((TissueTest)testContainer.getTest(escenario)).getIterationTissue()-1);
//				System.out.println("Escenario: "+escenario);
			}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
		    });
		
		table = new GridScenaryPanel(this);
        table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				selected = table.position(arg0.getX(),arg0.getY());
				gPanel = new JPanel();
	        	gPanel.setLayout(new BorderLayout());
	        	
	        	TissueTest tissueTest = (TissueTest)testContainer.getTest(escenario);
	        	
	        	if(tissueTest.getIterationTissue()>=1){
					sliderIteration = new JSlider( SwingConstants.HORIZONTAL, 0, tissueTest.getIterationTissue()-1,tissueTest.getIterationTissue()-1);
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
				    actualize(tissueTest.getIterationTissue()-1);
				    //System.out.println("Slider añadido");
					}
	        	
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0){}
		});
		
		JScrollPane tablePane = new JScrollPane(table);
        main.add(tablePane);
        main.add(new JScrollPane(gPanel));
		this.add(main,BorderLayout.CENTER);
		
		boxContainer.add(new JLabel("Scenaries"),BorderLayout.NORTH);
		boxContainer.add(comboBox,BorderLayout.CENTER);
		this.add(boxContainer, BorderLayout.NORTH);
		
	}
	
	
    private void actualize(int iteracion) {
        int indice;
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        String[] seriesNames = new String[] {"to Enter", "in Use"};
        proteinSequences= new String[(Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
		
		for( int i = 0;  i < (Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)parameters.getParameters().get(ParametersMap.PROTEINE_MIN_LENGTH));
    	}
        
        table.setVisible(false);
        table.setVisible(true);
        
        TissueTest tissueTest = (TissueTest)testContainer.getTest(escenario);
        
        if((selected != null) && (tissueTest.getStoreTissueParameters()!= null) && (iteracion >= 0)){
            indice=(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW)*
//			((Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_COLUMN)* iteracion
					selected.x+selected.y;           
            CategoryAxis categoryAxis = new CategoryAxis3D("Proteins");
            ValueAxis valueAxis = new NumberAxis3D("Concentration");
            valueAxis.setRange(0.0,100.0);
            BarRenderer3D renderer = new BarRenderer3D();
            CategoryPlot plot = null;
            
            if(tissueTest.getStoreTissueParameters().getLog().get(indice)!=null){
	            //para q no pinte la parte enla q se almacena la validez       
	            for(int j=1;j<((CategoryDataset)tissueTest.getStoreTissueParameters().getLog().get(indice)).getRowCount();j++){
					for(int k=1;k<((CategoryDataset)tissueTest.getStoreTissueParameters().getLog().get(indice)).getColumnCount();k++){
						categoryDataset.setValue(((CategoryDataset)tissueTest.getStoreTissueParameters().getLog().get(indice)).getValue(j,k),seriesNames[j-1],displayName(proteinSequences[k-1]));
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
        }
        
        main.remove(1);   
        main.add(new JScrollPane(gPanel));
        main.setVisible(false);
        main.setVisible(true);
    }
    
    private String displayName(String sequence){

    	if(parameters.getParameters().get(ParametersMap.APOPTOSIS_STRING).equals(sequence))
    		return "Apoptosis "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_N).equals(sequence))
    		return "Grown N "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_S).equals(sequence))
    		return "Grown S "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_E).equals(sequence))
    		return "Grown E "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_W).equals(sequence))
    		return "Grown W "+sequence;
    	else
    		return sequence;
    }
	
	
	
}
