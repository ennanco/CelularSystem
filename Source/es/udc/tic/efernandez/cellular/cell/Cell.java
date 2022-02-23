/*
 * Created on 10-sep-2005
 */
package es.udc.tic.efernandez.cellular.cell;

import java.awt.Point;


import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

/**
 * @author Enrique Fernández Blanco
 */
public interface Cell {

	
	public void setParameters(int row, int column,DNA dna,Tissue tissue,long instant);
	/**
	 * This operation tells the cel to recive a message protein 
	 * @param message the protein that is comunicated to the cell
	 */
    public void recive(Protein message);
    /**
     * This operation return if the cell was created in this celular cycle
     * @return the answer of the question
     */
    public boolean isNew();
    /**
     * This operation stablish the state old into the cell to indicate that it is not new
     *
     */
    public void setOld();
    /**
     * This operation let consult the celular cycle that the cell was processing
     * @return a long value whith the value of the cycle.
     */
    public long getInstant();
    /**
     * This operation consults the position into the enviroment of a particular cell
     * @return The position of the cell where x indicates the row and y the column
     */
    public Point getPosition();
    /**
     * The operation consults the concentration in percentage of a particular
     * protein sequence
     * @param proteinSequence the protein consulted
     * @param inUse this boolean value indicates if the concentration is of proteins in Use or into the entering queue
     * @return The concentration percentage.
     */
    public float concentration(String proteinSequence,boolean inUse);
    /**
     * tell the cell to do a new cycle.
     * @throws InternalErrorException
     */
    public void iteration() throws InternalErrorException;
    /**
     * This operation is to consult if the cell is dead.
     */
    public boolean died();
    
    public DNA getDNA();
}
