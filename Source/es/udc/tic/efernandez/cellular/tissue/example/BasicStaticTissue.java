package es.udc.tic.efernandez.cellular.tissue.example;

import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.cell.CellFactory;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.util.matrix.JMatrix;

public class BasicStaticTissue extends AbstractStaticTissue {

	public BasicStaticTissue() {
        super();
    }
    
	public void setParameters(int rows, int columns,DNA dna, Enviroment enviroment) {
        super.cells = new JMatrix(rows,columns);
        Cell newcell = CellFactory.getInstance(rows/2,columns/2,dna,this,0);
        super.cells.setElement(rows/2,columns/2,newcell);
        super.enviroment = enviroment;	
	}

}
