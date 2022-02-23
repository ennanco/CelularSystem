package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow.examinepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.gene.Gene;


public class GridGenesPanel extends JPanel {

	private static final int MINIMUM_ROW_DIMENSION = 10;
	private static final int MINIMUM_COLUMN_DIMENSION = 10;
	public static final int MAX_IN_ROW = 20;
	private int genHeight = MINIMUM_ROW_DIMENSION;
	private int genWidth = MINIMUM_COLUMN_DIMENSION;
	private int topMargin = 0;
	private int leftMargin = 0;
	DNAComponent[] componentes;
	
	public GridGenesPanel(DNAComponent[] componentes) {
		super();
		this.componentes = componentes;
	}
	
	public Point position(int coordX, int coordY){
		
		Point toReturn = null;
		int numberRows = componentes.length/MAX_IN_ROW;
		int finalColumn = componentes.length%MAX_IN_ROW;
		if(finalColumn > 0){
			numberRows++;
		}
//		System.out.println("X: "+coordX+"\tY: "+coordY+"\tTopMargin:"+topMargin+"\tleftMargin: "+leftMargin);
//		System.out.println("GenHeight: "+genHeight+"\tGenWidth:"+genWidth);
		if (coordY >= topMargin && coordX >= leftMargin && coordY <= topMargin+genHeight*numberRows && coordX <= leftMargin+ genWidth*MAX_IN_ROW)
			toReturn = new Point((coordY-topMargin)/genHeight,(coordX-leftMargin)/genWidth);
		
//		System.out.println(toReturn);
		return toReturn;
	}
	
	public void paint(Graphics g) {
	
		boolean salir = false;
		int numberColumns = Math.min(MAX_IN_ROW, componentes.length);
		int numberRows = (componentes.length/MAX_IN_ROW);
		
		int finalColumn = componentes.length%MAX_IN_ROW;

		if(finalColumn>0 || numberRows<1)
			numberRows++;
		
		if(numberColumns<1)
			numberColumns++;
		
		this.setBackground(java.awt.Color.gray);
		
//		System.out.println("componentes: "+componentes.length);
//		System.out.println("Numero de filas: "+numberRows);
		
		genHeight = Math.round((float)Math.floor(this.getHeight()/numberRows));
		genWidth = Math.round((float)Math.floor(this.getWidth()/ numberColumns));
		
		if(genHeight<MINIMUM_ROW_DIMENSION || genWidth < MINIMUM_COLUMN_DIMENSION){
			genHeight = (genHeight<MINIMUM_ROW_DIMENSION)? MINIMUM_ROW_DIMENSION: genHeight;
			genWidth = (genWidth<MINIMUM_COLUMN_DIMENSION)? MINIMUM_COLUMN_DIMENSION: genWidth;
			this.setMinimumSize(new Dimension(genWidth*numberColumns,genHeight*numberRows));
			this.setPreferredSize(new Dimension(genWidth*numberColumns,genHeight*numberRows));
		}
		//calculate the margins to center the graphic
			topMargin = (this.getHeight() - genHeight*numberRows) / 2;
			leftMargin = (this.getWidth() - genWidth*numberColumns)/2;
			g.clearRect(0,0,this.getWidth(),this.getHeight());
			
//			System.out.println("Columnas: "+numberColumns);
			for(int i = 0; i < numberRows; i++){
				int columnToEnd = finalColumn>0 && i==(numberRows-1)? finalColumn:numberColumns;
				for(int j = 0; j < columnToEnd ; j++){
				g.setColor(java.awt.Color.black);
				g.drawRect((j*genWidth) +leftMargin,(i*genHeight) + topMargin,genWidth,genHeight);
				//mirar q tipo es para darle un  color y poner la etiqueta
				if((i*numberColumns + j)<componentes.length && componentes[i*numberColumns + j]!=null){
					if(componentes[i*numberColumns + j].isOperon()){		
//						g.drawRect((j*genWidth) +leftMargin,(i*genHeight) + topMargin,genWidth,genHeight);
						g.setColor(java.awt.Color.red);
//						System.out.println(((Operon)componentes[i*numberColumns + j]).toString());
						g.fillRect((j*genWidth) +leftMargin +1,(i*genHeight) + topMargin +1,genWidth-1,genHeight-1);
						g.setColor(java.awt.Color.black);
						g.drawString("Operon", (j*genWidth+genWidth/4) +leftMargin,
								(i*genHeight+genHeight/2) + topMargin);
					}
					else{
						g.setColor(java.awt.Color.yellow);
//						System.out.println(((Gene)componentes[i*numberColumns + j]).getSequence().toCharArray());
						g.fillRect((j*genWidth) +leftMargin +1,(i*genHeight) + topMargin +1,genWidth-1,genHeight-1);
						g.setColor(java.awt.Color.black);
						g.drawString(((Gene)componentes[i*numberColumns + j]).getSequence(),
								(j*genWidth+genWidth/4) +leftMargin,(i*genHeight+genHeight/2) + topMargin);
					}
				}else{
					salir = true;
					break;
				}
				
				
				//poner la etiqueta con el nombre del gen
				}
				if(salir == true)
					break;
			}
	}
}
