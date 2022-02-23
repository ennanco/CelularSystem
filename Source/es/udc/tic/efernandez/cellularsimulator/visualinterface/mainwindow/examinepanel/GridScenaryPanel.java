package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.data.category.CategoryDataset;

import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.enviroment.GradientEnviromentWrapper;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test.TissueTest;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;

public class GridScenaryPanel extends JPanel {

	private static final int MINIMUM_ROW_DIMENSION = 10;
	private static final int MINIMUM_COLUMN_DIMENSION = 10;
	private int cellHeight = MINIMUM_ROW_DIMENSION;
	private int cellWidth = MINIMUM_COLUMN_DIMENSION;
	private int topMargin = 0;
	private int leftMargin = 0;
	private ParametersMap parameters;
	ScenaryProcessorPanel parent;
	private TissueTest test;
	
	public GridScenaryPanel(ScenaryProcessorPanel parent){
		super();
		this.parent = parent;
		parameters = new ParametersMap();
	}
	
	public Point position(int coordX, int coordY){
		Point toReturn = null;
		if (coordY >= topMargin && coordX >= leftMargin 
				&& coordY <= topMargin+cellHeight*(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW) 
				&& coordX <= leftMargin+ cellWidth* (Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_COLUMN))
		toReturn = new Point((coordY-topMargin)/cellHeight,(coordX-leftMargin)/cellWidth);
		
		return toReturn;
	}
	
	public void paint(Graphics g) {
		
		int numberColumns = (Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_COLUMN);
		int numberRows = (Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW);
		int gradient=0;
		boolean pintado=false;
		int indice;
		CategoryDataset categoryDataset;
		test = parent.getTest();
		int iteracion = test.getIterationTissue()-1;
		ArrayList historico = test.getStoreTissueParameters().getLog();
		
		this.setBackground(java.awt.Color.gray);
	
		cellHeight = Math.round((float)Math.floor(this.getHeight()/numberRows));
		cellWidth = Math.round((float)Math.floor(this.getWidth()/ numberColumns));
		
		if(cellHeight<MINIMUM_ROW_DIMENSION || cellWidth < MINIMUM_COLUMN_DIMENSION){
			cellHeight = (cellHeight<MINIMUM_ROW_DIMENSION)? MINIMUM_ROW_DIMENSION: cellHeight;
			cellWidth = (cellWidth<MINIMUM_COLUMN_DIMENSION)? MINIMUM_COLUMN_DIMENSION: cellWidth;
			this.setMinimumSize(new Dimension(cellWidth*numberColumns,cellHeight*numberRows));
			this.setPreferredSize(new Dimension(cellWidth*numberColumns,cellHeight*numberRows));
		}
		//calculate the margins to center the graphic
			topMargin = (this.getHeight() - cellHeight*numberRows) / 2;
			leftMargin = (this.getWidth() - cellWidth*numberColumns)/2;
			
			g.clearRect(0,0,this.getWidth(),this.getHeight());
		for(int i = 0; i < numberRows; i++){
			for(int j = 0; j< numberColumns; j++){
				gradient=0;
				g.setColor(java.awt.Color.black);
				g.drawRect((j*cellWidth) +leftMargin,(i*cellHeight) + topMargin,cellWidth,cellHeight);
				GradientEnviromentWrapper enviroment = (GradientEnviromentWrapper)test.getEnviroment();
				
				if(!enviroment.gradientList().isEmpty())
					for(int k=0;k<enviroment.gradientList().size();k++){
//						System.out.println("gradiente: "+k+"\t .x: "+facade.getGradients().getGradient(k).getPosition().x+" .y: "+facade.getGradients().getGradient(k).getPosition().y+"\t i:"+i+" j: "+j);
						if(enviroment.gradientList().getGradient(k).getPosition().x==i && enviroment.gradientList().getGradient(k).getPosition().y==j){
							gradient=1;
//							System.out.println("X: "+i+" Y: "+j+" pinto gradiente");
							g.setColor(java.awt.Color.blue);
							break;
						}else{
							pintado = false;
							for(int r=0;r<enviroment.gradientList().size();r++)
								if(enviroment.gradientList().getGradient(r).getPosition().x==i && enviroment.gradientList().getGradient(r).getPosition().y==j){
									pintado = true;
									break;
								}
							
							if(enviroment.gradientList().getGradient(k).presenceProbability(new Point(i,j))>0 && !pintado){
								gradient=1;
								g.setColor(java.awt.Color.yellow);
								break;
							}
						}
					}
				
				if(test.getTissue()== null){
//					System.out.println("Tejido nulo en el grid");
					if((historico.size()!=0)){
//						indice=numberRows*(numberColumns*iteracion+i)+j;
						indice=numberRows*i+j;
						categoryDataset = (CategoryDataset)historico.get(indice);
						if(categoryDataset == null){
							if(gradient==0)
								g.setColor(java.awt.Color.white);
						}
						else 
							if((Double)categoryDataset.getValue(0,0) == 0.0)
								g.setColor(java.awt.Color.red);
							else
								g.setColor(java.awt.Color.green);
					}
					else{
						if(gradient==0)
							g.setColor(java.awt.Color.white);
					}
				}
				else {
					//hay q tener en cuenta el tejido en el inicio
//					System.out.println("Tejido en el grid");
					if(historico.size()==0 || (iteracion < 0))
						if(test.getTissue().validPosition(i,j))
							if(test.getTissue().get(i,j)!=null || (numberRows*i+j)==210)
								if(test.getTissue().get(i,j) instanceof ReceptorCell)
									g.setColor(java.awt.Color.pink);
								else
									g.setColor(java.awt.Color.green);
							else
								if(gradient==0)
									if(test.getTissue().get(i,j) instanceof ReceptorCell)
										g.setColor(java.awt.Color.pink);
									else
										g.setColor(java.awt.Color.white);
								else;
						else
							g.setColor(java.awt.Color.red);	
					else{
//						indice=numberRows*(numberColumns*iteracion+i)+j;
						indice = numberRows*i+j;
						categoryDataset = (CategoryDataset)historico.get(indice);
						if(categoryDataset == null){
							if(gradient==0)
								if(test.getTissue().get(i,j) instanceof ReceptorCell)
									g.setColor(java.awt.Color.pink);
								else
									g.setColor(java.awt.Color.white);
						}		
						else 
							if((Double)categoryDataset.getValue(0,0) == 0.0)
								g.setColor(java.awt.Color.red);
							else
//								if(gradient==0)
									if(test.getTissue().get(i,j) instanceof ReceptorCell)
										g.setColor(java.awt.Color.pink);
									else
										g.setColor(java.awt.Color.green);
					}
				}
				
				g.fillRect((j*cellWidth) +leftMargin +1,(i*cellHeight) + topMargin +1,cellWidth-1,cellHeight-1);
			}
			
		}
		
	}
	
	
}
