package es.udc.tic.efernandez.cellularsimulator.visualinterface.templateeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.DNAPopulationWindow;
import es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.GeneralFileFilter;

public class TemplateEditor extends JDialog {
	
	private TemplateGrid grid;
	private DNAPopulationWindow parent;
	private boolean [][] matrix;
	private boolean [][] matrix_old;
	private Point selected;
	private int loaded=0;
	
	public TemplateEditor(DNAPopulationWindow parent, boolean [][] matrix){
		super(parent);
		this.parent=parent;
		this.matrix=matrix;
		inicialice();
	}
	
	private void inicialice(){
		//todo el codigo y lafuncion actualice()
		this.setLayout(new BorderLayout());
		
		this.getContentPane().setLayout(new BorderLayout());

		JPanel buttonContainer = new JPanel(new GridLayout(0,2));
		grid = new TemplateGrid(matrix);
		
        this.getContentPane().add(buttonContainer,BorderLayout.SOUTH);
        this.getContentPane().add(grid,BorderLayout.CENTER);
        
        JButton acceptButton = new JButton("Accept");
        JButton cancelButton = new JButton("Cancel");
        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        
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
        
        loadButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	GeneralFileFilter filter = new GeneralFileFilter();
                filter.addExtension("txt");
                filter.setDescription("TXT Files");
                String fileName = parent.generalOpenFileDialog(filter);
                if(fileName!=null) try {
                	loaded=1;
                	matrix_old=parent.getModel().getEvaluationTemplate();
                	parent.getModel().loadTemplate(fileName);
                	matrix=parent.getModel().getEvaluationTemplate();
                	TemplateEditor.this.setVisible(false);
                	TemplateEditor.this.remove(grid);
                	grid = new TemplateGrid(matrix);
                	TemplateEditor.this.getContentPane().add(grid,BorderLayout.CENTER);
                	TemplateEditor.this.setVisible(true);
                	grid.repaint();
					} catch (IOException e) {
						e.printStackTrace();
					}
            }
        });
        
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	GeneralFileFilter filter = new GeneralFileFilter();
            	filter.addExtension("txt");
                filter.setDescription("TXT Files");
                String fileName = parent.generalSaveFileDialog(filter);
                if(fileName!=null)
                	parent.getModel().saveTemplate(fileName, matrix);
            }
        });
        
        buttonContainer.add(acceptButton);
        buttonContainer.add(cancelButton);
        buttonContainer.add(loadButton);
        buttonContainer.add(saveButton);
        
        this.pack();        
		
        this.setSize(500, 500);

        
    	grid.addMouseListener(new MouseListener(){
    		public void mouseClicked(MouseEvent arg0) {
    			selected = grid.position(arg0.getX(),arg0.getY());
    			matrix[selected.x][selected.y]=!matrix[selected.x][selected.y];
    			grid.repaint();
    		}
    		public void mousePressed(MouseEvent arg0) {}
    		public void mouseReleased(MouseEvent arg0) {}
    		public void mouseEntered(MouseEvent arg0) {}
    		public void mouseExited(MouseEvent arg0) {}
    	});		
	}

	private void cancelAction(){
		if(loaded==1)
			parent.getModel().registerTemplate(matrix_old);
        this.setVisible(false);
    }
    
    private void acceptAction(){
    	parent.getModel().registerTemplate(matrix);
    	this.setVisible(false);
    	
    }
}
