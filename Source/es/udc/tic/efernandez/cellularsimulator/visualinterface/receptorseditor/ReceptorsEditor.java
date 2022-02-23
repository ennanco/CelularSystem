package es.udc.tic.efernandez.cellularsimulator.visualinterface.receptorseditor;

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

import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.DNAPopulationWindow;

public class ReceptorsEditor extends JDialog{
	private ReceptorsGrid grid;
	private DNAPopulationWindow parent;
	private Point selected;
	private Point matriz [][];
	private ArrayList createdReceptors;
	
	public ReceptorsEditor(DNAPopulationWindow parent){
		super(parent);
		this.parent=parent;
		matriz = new Point [(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_ROW)]
		                                    [(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN)];
		for(int i=0;i<(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_ROW);i++)
			for(int j=0;j<(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN);j++)
				matriz[i][j] = null;
		
		createdReceptors = new ArrayList();
//		mostrar los receptores existentes
		if(parent.getModel().getTissue()!=null)
			for(int i=0;i<(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_ROW);i++)
				for(int j=0;j<(Integer)parent.getModel().getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN);j++)
					if(parent.getModel().getTissue().get(i,j)!=null && parent.getModel().getTissue().get(i,j) instanceof ReceptorCell){
						matriz[i][j] = new Point(i,j);
						createdReceptors.add(new Point(i,j));
					}
		inicialice();
	}
	
	private void inicialice(){
		//todo el codigo y lafuncion actualice()
		this.setLayout(new BorderLayout());
		
		this.getContentPane().setLayout(new BorderLayout());

		JPanel buttonContainer = new JPanel(new GridLayout(0,2));
		grid = new ReceptorsGrid(matriz);
		
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
    			//comprobar si existe el receptor, y si existe,desactibarlo
    			if(matriz[selected.x][selected.y]!=null)
    				(new ReceptorsDisabledDialog(ReceptorsEditor.this, selected)).setVisible(true);
    			else{
    				matriz[selected.x][selected.y]= selected;
    				createdReceptors.add(selected);
    				grid.repaint();
    			}
    		}
    		public void mousePressed(MouseEvent arg0) {}
    		public void mouseReleased(MouseEvent arg0) {}
    		public void mouseEntered(MouseEvent arg0) {}
    		public void mouseExited(MouseEvent arg0) {}
    	});		
	}
	
	private void cancelAction(){
		this.setVisible(false);
    }
    
    private void acceptAction(){
    	this.setVisible(false);
    	if(!createdReceptors.isEmpty())
    		for(int i=0;i<createdReceptors.size();i++)
    			if(parent.getModel().getTissue().get(((Point)createdReceptors.get(i)).x,((Point)createdReceptors.get(i)).y) instanceof ReceptorCell 
    			|| parent.getModel().getTissue().get(((Point)createdReceptors.get(i)).x,((Point)createdReceptors.get(i)).y)==null)
    				parent.getModel().getTissue().set(((Point)createdReceptors.get(i)).x,((Point)createdReceptors.get(i)).y,
    				new ReceptorCell(((Point)createdReceptors.get(i)).x,((Point)createdReceptors.get(i)).y,
    						parent.getModel().getTissue(),parent.getModel().getIterationTissue()));
    }
	
    public FacadeInterface getModel(){
    	return(parent.getModel());
    }
    
	
	public void eraseReceptor(Point point){
		//borrado del receptor
		matriz[point.x][point.y]=null;
		createdReceptors.remove(createdReceptors.indexOf(point));
		parent.getModel().getTissue().set(point.x,point.y,(Cell)null);	
		grid.repaint();
	}
		
		
}
