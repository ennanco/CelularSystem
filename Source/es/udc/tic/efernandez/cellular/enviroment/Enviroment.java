/*
 * Created on 10-ago-2005
 */
package es.udc.tic.efernandez.cellular.enviroment;

import java.awt.Point;
import java.util.Set;

import es.udc.tic.efernandez.cellular.protein.Protein;

/**
 * @author Enrique Fernández Blanco
 */
public interface Enviroment extends Cloneable{
	
		public void inicialize(int maxRow, int maxColumn);
    
	    /**
	     * Check if a position is valid into an enviroment.
	     * @param row
	     * @param column
	     * @return a boolean with true when a cell can be put in those position
	     */
	    public boolean valid(int row, int column);
	    /**
	     * This operation checks for proteins putted in that position by other cells
	     * @param p Point of check
	     * @param instant celular instant to check
	     * @return The first protein in that position and valid
	     */
	    public Protein[] get(Point p,long instant);
	    /**
	     * Operation store a protein with a position asociated
	     * @param p the position where we put the protein
	     * @param protein the protein to put
	     */
	    public void set(Point p, Protein protein);
	    /**
	     * this operation erase the proteins that are degradated at a celular cycle
	     * the objective is not to store a huge unusefull information amount 
	     * @param instant the processing celular cycle 
	     */
		public void update(long instant);
		/**
		 * This operation return the current number ofa determined type protein  on a determined position
		 * @param position the position to examine
		 * @param sequence the protein which is consulted.
		 * @return the number of proteins of a type
		 */
		public int consultProtein(Point position, String sequence);
		
		public Set listedProteins(Point p);
		
		public Protein getProtein(Point p, String sequence);
		
		public Object clone();
}
