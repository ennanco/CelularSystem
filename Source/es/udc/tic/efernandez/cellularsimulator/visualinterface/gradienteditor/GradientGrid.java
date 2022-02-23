package es.udc.tic.efernandez.cellularsimulator.visualinterface.gradienteditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import org.jfree.data.category.DefaultCategoryDataset;

public class GradientGrid extends JPanel {
	
	private static final int MINIMUM_ROW_DIMENSION = 10;
	private static final int MINIMUM_COLUMN_DIMENSION = 10;
	private static final int MINIMUM_ROW = 1;
	private static final int MINIMUM_COLUMN = 1;
	private int cellHeight = MINIMUM_ROW_DIMENSION;
	private int cellWidth = MINIMUM_COLUMN_DIMENSION;
	private int numberRows = MINIMUM_ROW;
	private int numberColumns = MINIMUM_COLUMN;
	private int topMargin = 0;
	private int leftMargin = 0;
	private DefaultCategoryDataset matriz[][];
	
	
	public GradientGrid(DefaultCategoryDataset matriz[][]){
		super();
		numberRows = matriz.length;
		numberColumns = matriz[0].length;
		this.matriz = matriz;
	}
	
	public Point position(int coordX, int coordY){	
		Point toReturn = null;
		if (coordY >= topMargin && coordX >= leftMargin 
				&& coordY <= topMargin+cellHeight*numberRows 
				&& coordX <= leftMargin+ cellWidth*numberColumns)
		toReturn = new Point((coordY-topMargin)/cellHeight,(coordX-leftMargin)/cellWidth);
		
		return toReturn;
	}
	
	public void paint(Graphics g) {
		this.setBackground(java.awt.Color.gray);
		
		cellHeight = Math.round((float)Math.floor(this.getHeight()/numberRows));
		cellWidth = Math.round((float)Math.floor(this.getWidth()/ numberColumns));
		
		if(cellHeight<MINIMUM_ROW_DIMENSION || cellWidth < MINIMUM_COLUMN_DIMENSION){
			cellHeight = (cellHeight<MINIMUM_ROW_DIMENSION)? MINIMUM_ROW_DIMENSION: cellHeight;
			cellWidth = (cellWidth<MINIMUM_COLUMN_DIMENSION)? MINIMUM_COLUMN_DIMENSION: cellWidth;
			this.setMinimumSize(new Dimension(cellWidth*numberColumns,cellHeight*numberRows));
			this.setPreferredSize(new Dimension(cellWidth*numberColumns,cellHeight*numberRows));
		}
		
//		calculate the margins to center the graphic
		topMargin = (this.getHeight() - cellHeight*numberRows) / 2;
		leftMargin = (this.getWidth() - cellWidth*numberColumns)/2;
		

		g.clearRect(0,0,this.getWidth(),this.getHeight());
		for(int i = 0; i < numberRows; i++){
			for(int j = 0; j< numberColumns; j++){
				g.setColor(java.awt.Color.black);
				g.drawRect((j*cellWidth) +leftMargin,(i*cellHeight) + topMargin,cellWidth,cellHeight);
				if(matriz[i][j]==null)
					g.setColor(java.awt.Color.white);
				else 
					if(matriz[i][j].getValue("Valores","Foco").intValue()==1)
						g.setColor(java.awt.Color.blue);
					else {
//						System.out.println(matriz[i][j].getColumnKeys());
						if(matriz[i][j].getValue("Valores","Radiado").floatValue()>0.0)	
							g.setColor(java.awt.Color.yellow);
						else 
							g.setColor(java.awt.Color.white);
					}
				g.fillRect((j*cellWidth) +leftMargin +1,(i*cellHeight) + topMargin +1,cellWidth-1,cellHeight-1);
			}
		}
//		System.out.println("//////////////////////////////////////////////////");
		
	}
}
