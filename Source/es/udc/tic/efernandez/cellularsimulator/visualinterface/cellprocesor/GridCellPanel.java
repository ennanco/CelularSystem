package es.udc.tic.efernandez.cellularsimulator.visualinterface.cellprocesor;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.data.category.CategoryDataset;

import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;



public class GridCellPanel extends JPanel {

	private static final int MINIMUM_ROW_DIMENSION = 10;
	private static final int MINIMUM_COLUMN_DIMENSION = 10;
//	private Tissue tissue = null;
	private int cellHeight = MINIMUM_ROW_DIMENSION;
	private int cellWidth = MINIMUM_COLUMN_DIMENSION;
	private int topMargin = 0;
	private int leftMargin = 0;
	
	private FacadeInterface facade;
	
	public GridCellPanel(FacadeInterface facade) {
		super();
		this.facade = facade;
	}
	
	public Point position(int coordX, int coordY){
		
		Point toReturn = null;
		if (coordY >= topMargin && coordX >= leftMargin 
				&& coordY <= topMargin+cellHeight*(Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_ROW) 
				&& coordX <= leftMargin+ cellWidth* (Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN))
		toReturn = new Point((coordY-topMargin)/cellHeight,(coordX-leftMargin)/cellWidth);
		
		return toReturn;
	}
	
	public void paint(Graphics g) {
				
		int numberColumns = (Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN);
		int numberRows = (Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_ROW);
		ArrayList historico = facade.getHystorical();
		int iteracion = facade.getIterationTissue()-1;
		int indice;
		CategoryDataset categoryDataset;
		int gradient=0; 
		boolean pintado=false;
		
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
				
				// select color				
//				Tissue tissue = facade.getTissue();
//				if(tissue == null)
//					g.setColor(java.awt.Color.white);
//				else if(!tissue.validPosition(i,j))
//					g.setColor(java.awt.Color.red);
//				else if(tissue.get(i,j) == null)
//					g.setColor(java.awt.Color.white);					
//				else
//					g.setColor(java.awt.Color.green);
////				 draw fill rectangles
//					g.fillRect((j*cellWidth) +leftMargin +1,(i*cellHeight) + topMargin +1,cellWidth-1,cellHeight-1);
//				}
				
//				en vez de simular con tissue usar el historico
//				indice=(Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_ROW)*
//				((Integer)facade.getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN)* iteracion+i)+j;
				if(!facade.getGradients().isEmpty())
					for(int k=0;k<facade.getGradients().size();k++){
//						System.out.println("gradiente: "+k+"\t .x: "+facade.getGradients().getGradient(k).getPosition().x+" .y: "+facade.getGradients().getGradient(k).getPosition().y+"\t i:"+i+" j: "+j);
						if(facade.getGradients().getGradient(k).getPosition().x==i && facade.getGradients().getGradient(k).getPosition().y==j){
							gradient=1;
//							System.out.println("X: "+i+" Y: "+j+" pinto gradiente");
							g.setColor(java.awt.Color.blue);
							break;
						}else{
							pintado = false;
							for(int r=0;r<facade.getGradients().size();r++)
								if(facade.getGradients().getGradient(r).getPosition().x==i && facade.getGradients().getGradient(r).getPosition().y==j){
									pintado = true;
									break;
								}
							
							if(facade.getGradients().getGradient(k).presenceProbability(new Point(i,j))>0 && !pintado){
								gradient=1;
								g.setColor(java.awt.Color.yellow);
								break;
							}
						}
					}
				
				if(facade.getTissue()== null){
//					System.out.println("Tejido nulo en el grid");
					if((historico.size()!=0)){
						indice=numberRows*(numberColumns*iteracion+i)+j;		
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
						if(facade.getTissue().validPosition(i,j))
							if(facade.getTissue().get(i,j)!=null || (numberRows*i+j)==210)
								if(facade.getTissue().get(i,j) instanceof ReceptorCell)
									g.setColor(java.awt.Color.pink);
								else
									g.setColor(java.awt.Color.green);
							else
								if(gradient==0)
									if(facade.getTissue().get(i,j) instanceof ReceptorCell)
										g.setColor(java.awt.Color.pink);
									else
										g.setColor(java.awt.Color.white);
								else;
						else
							g.setColor(java.awt.Color.red);	
					else{
						indice=numberRows*(numberColumns*iteracion+i)+j;
						categoryDataset = (CategoryDataset)historico.get(indice);
						if(categoryDataset == null){
							if(gradient==0)
								if(facade.getTissue().get(i,j) instanceof ReceptorCell)
									g.setColor(java.awt.Color.pink);
								else
									g.setColor(java.awt.Color.white);
						}		
						else 
							if((Double)categoryDataset.getValue(0,0) == 0.0)
								g.setColor(java.awt.Color.red);
							else
//								if(gradient==0)
									if(facade.getTissue().get(i,j) instanceof ReceptorCell)
										g.setColor(java.awt.Color.pink);
									else
										g.setColor(java.awt.Color.green);
					}
				}
//				 draw fill rectangles
				//System.out.println("Lo pinto:");
				g.fillRect((j*cellWidth) +leftMargin +1,(i*cellHeight) + topMargin +1,cellWidth-1,cellHeight-1);				
			}
		}
	}
}
