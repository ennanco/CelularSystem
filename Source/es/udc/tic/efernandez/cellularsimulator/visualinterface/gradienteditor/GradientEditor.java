package es.udc.tic.efernandez.cellularsimulator.visualinterface.gradienteditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.BasicGradient;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.DNAPopulationWindow;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;

public class GradientEditor extends JDialog {

	private GradientGrid grid;
	private DNAPopulationWindow parent;
	private Point selected;
	private DefaultCategoryDataset matriz [][];
	private int gradientes=0;
	private ArrayList erasablesGradients;
	
	public GradientEditor(DNAPopulationWindow parent){
		super(parent);
		this.parent=parent;
		matriz = new DefaultCategoryDataset [(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_ROW)]
		                                    [(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN)];
		for(int i=0;i<(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_ROW);i++)
			for(int j=0;j<(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN);j++)
				matriz[i][j] = null;
		
//		mostrar los gradientes existentes
		for(int i=0;i<parent.getModel().getGradients().size();i++){
			//se fija el foco de emision
			DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();			
	    	categoryDataset.setValue(parent.getModel().getGradients().getGradient(i).getDistance(), "Valores", "Radio");
	    	try {
				categoryDataset.setValue(BinaryStringOperations.convert(parent.getModel().getGradients().getGradient(i).getSequence()), "Valores", "ProteinaEmitida");
			} catch (NonValidSequenceException e) {
				e.printStackTrace();
			}
	    	categoryDataset.setValue(1, "Valores", "Foco");
	    	categoryDataset.setValue(0, "Valores", "Radiado");
	    	categoryDataset.setValue(-1, "Valores", "ProteinaRecivida");
	    	matriz[parent.getModel().getGradients().getGradient(i).getPosition().x][parent.getModel().getGradients().getGradient(i).getPosition().y] = categoryDataset;
	    	
	    	//se irradia la proteina
			for(int j=0;j<matriz.length;j++)
				for(int k=0;k<matriz[0].length;k++){
					Point pointSelect = new Point(j,k);
					float probabilidad = parent.getModel().getGradients().getGradient(i).presenceProbability(pointSelect);
					if(matriz[j][k]==null){
						DefaultCategoryDataset categoryDatasetRadiado = new DefaultCategoryDataset();
						categoryDatasetRadiado.setValue(0, "Valores", "Radio");
						categoryDatasetRadiado.setValue(-1, "Valores", "ProteinaEmitida");
						categoryDatasetRadiado.setValue(0, "Valores", "Foco");
						categoryDatasetRadiado.setValue(probabilidad, "Valores", "Radiado");
				    	try {
				    		categoryDatasetRadiado.setValue(BinaryStringOperations.convert(parent.getModel().getGradients().getGradient(i).getSequence()), "Valores", "ProteinaRecivida");
						} catch (NonValidSequenceException e) {
							e.printStackTrace();
						}
				    	matriz[j][k] = categoryDatasetRadiado;
					}
					else
						if(probabilidad>0.0){
							matriz[j][k].setValue(probabilidad, "Valores", "Radiado");
							try {
								matriz[j][k].setValue(BinaryStringOperations.convert(parent.getModel().getGradients().getGradient(i).getSequence()), "Valores", "ProteinaRecivida");
							} catch (NonValidSequenceException e) {
								e.printStackTrace();
							}
						}
				}
		}
		erasablesGradients = new ArrayList(); 
		inicialice();
	}
	
	
	
	private void inicialice(){
		//todo el codigo y lafuncion actualice()
		this.setLayout(new BorderLayout());
		
		this.getContentPane().setLayout(new BorderLayout());

		JPanel buttonContainer = new JPanel(new GridLayout(0,2));
		grid = new GradientGrid(matriz);
		
        this.getContentPane().add(buttonContainer,BorderLayout.SOUTH);
        this.getContentPane().add(grid,BorderLayout.CENTER);
        
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
        
        this.pack();        
		
        this.setSize(500, 500);

        
    	grid.addMouseListener(new MouseListener(){
    		public void mouseClicked(MouseEvent arg0) {
    			selected = grid.position(arg0.getX(),arg0.getY());
    			//comprobar si existe el gradiente, y si existe,desactibarlo
    			if(matriz[selected.x][selected.y]!=null && matriz[selected.x][selected.y].getValue("Valores", "Foco").intValue()==1)
    				(new GradientDisabledDialog(GradientEditor.this, selected)).setVisible(true);
    			else
    				(new GradientConfigureDialog(GradientEditor.this)).setVisible(true);
    		}
    		public void mousePressed(MouseEvent arg0) {}
    		public void mouseReleased(MouseEvent arg0) {}
    		public void mouseEntered(MouseEvent arg0) {}
    		public void mouseExited(MouseEvent arg0) {}
    	});		
	}

	private void cancelAction(){
		for(int i=0;i<gradientes;i++)
			parent.getModel().getGradients().removeGradient(parent.getModel().getGradients().size()-1);
		this.setVisible(false);
    }
    
    private void acceptAction(){
    	this.setVisible(false);
    	if(!erasablesGradients.isEmpty())
    		for(int i=0;i<erasablesGradients.size();i++)
    			parent.getModel().getGradients().removeGradient(Integer.parseInt(erasablesGradients.get(i).toString()));
    }
	
    public FacadeInterface getModel(){
    	return(parent.getModel());
    }
    
	public void setAux(DefaultCategoryDataset categoryDataset){
		matriz[selected.x][selected.y]=categoryDataset;
		radiacion(selected);
		grid.repaint();
	}
	
	public void eraseGradient(Point point){
		for(int i=0;i<parent.getModel().getGradients().size();i++)
			if(parent.getModel().getGradients().getGradient(i).getPosition().x==point.x && parent.getModel().getGradients().getGradient(i).getPosition().y==point.y){
				//se elimina el gradiente del entorno y en la matriz se borra la emision del gradiente
				//borrado de la difusion
				for(int j=0;j<matriz.length;j++)
					for(int k=0;k<matriz[0].length;k++){
						Point pointSelect = new Point(j,k);
						float probabilidad = parent.getModel().getGradients().getGradient(i).presenceProbability(pointSelect);
							if(probabilidad>0.0){
								matriz[j][k]=null;								
							}
					}
				//borrado del foco
				matriz[point.x][point.y]=null;
				//borrado del gradiente en el entorno
//				parent.getModel().getGradients().removeGradient(i);
				erasablesGradients.add(i);
				break;
			}
		grid.repaint();
	}
	
	private void radiacion(Point selected){
		
		int radio = (matriz[selected.x][selected.y].getValue("Valores", "Radio")).intValue();

		BasicGradient gradient = new BasicGradient(BinaryStringOperations.toBinary(matriz[selected.x][selected.y].getValue("Valores", "ProteinaEmitida").intValue(),4),selected,radio);
		parent.getModel().getGradients().addGradient(gradient);
		gradientes++;
		for(int i=0;i<matriz.length;i++)
			for(int j=0;j<matriz[0].length;j++){
				Point pointSelect = new Point(i,j);
				float probabilidad = gradient.presenceProbability(pointSelect);
				if(matriz[i][j]==null){
					DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
					categoryDataset.setValue(0, "Valores", "Radio");
			    	categoryDataset.setValue(-1, "Valores", "ProteinaEmitida");
			    	categoryDataset.setValue(0, "Valores", "Foco");
			    	categoryDataset.setValue(probabilidad, "Valores", "Radiado");
			    	categoryDataset.setValue(matriz[selected.x][selected.y].getValue("Valores", "ProteinaEmitida"), "Valores", "ProteinaRecivida");
			    	matriz[i][j] = categoryDataset; 
				}
				else
					if(probabilidad>0.0){
						matriz[i][j].setValue(probabilidad, "Valores", "Radiado");
						matriz[i][j].setValue(matriz[selected.x][selected.y].getValue("Valores", "ProteinaEmitida"), "Valores", "ProteinaRecivida");
					}
			}
	}
	
}
