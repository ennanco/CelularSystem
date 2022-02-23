/*
 * Created on 12-oct-2005
 */

package es.udc.tic.efernandez.cellular.tissue.example;

import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.cell.CellFactory;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.util.matrix.JMatrix;

public class Square25Tissue extends AbstractTissue{

	public Square25Tissue(){
		super();
	}
	
	public void setParameters(int rows, int columns,DNA dna,Enviroment enviroment){
       super.cells = new JMatrix(rows,columns);
       Cell newcell;
       for(int i = rows/2 -2 ; i <= rows/2+2; i++)
    	   for(int j = columns/2 -2; j <= columns/2+2; j++){
    		   newcell = CellFactory.getInstance(i,j,dna,this,0);
    		   super.cells.setElement(i,j,newcell);
    	   }
       super.enviroment = enviroment;
	}
}
