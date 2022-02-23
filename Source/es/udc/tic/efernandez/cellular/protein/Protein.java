/*
 * Created on 13-jul-2005
 */
package es.udc.tic.efernandez.cellular.protein;

import java.awt.Point;

/**
 * @author Enrique Fernández Blanco
 */
public interface Protein {

	/**
	 * This operations checks the sequence of the protein
	 * @return The sequence that represents this protein.
	 */
    public String getSequence();
    /**
     * This operation checks the creation celular cycle of this protein
     * @return a long value with this information, this value will be allways more than 0
     */
    public long getCreated();
    /**
     * Check for the degradation of a protein. When a protein is degradated it has no effect into the system.
     * @param instant the instant in what we check.
     * @return a boolean value with the information.
     */
    public boolean getDegraded(long instant);
    /**
     * Every protein keeps the information of the cell position which has
     * generated it.
     * @return return that position.
     */
    public Point getOrigin();
    
    public int getDirection();
    
    public int getNumberDirections();
}
