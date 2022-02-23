/*
 * Created on 15-ago-2005
 */
package es.udc.tic.efernandez.cellular.tissue;

import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.Enviroment;

/**
 * @author Enrique Fernández Blanco
 */
public interface Tissue {
	/**
	 * The operation stablis the esencial properies of a tissue
	 * @param row number of rows
	 * @param column number of columns
	 * @param dna of every cell of the tissue
	 * @param enviroment instance of Enviroment where the tissue develops itself
	 */
	public void setParameters(int row, int column, DNA dna, Enviroment enviroment);
	/**
	 * Obtain the element at a position
	 * @param row
	 * @param column
	 * @return this operation returns the cell which is in the position or if there is nothing it returns null
	 */
    public Cell get(int row, int column);
    /**
     * Put a cell into a certain position
     * @param row
     * @param column
     * @param cell
     */
    public void set(int row, int column, Cell cell);
    /**
     * This pration executes a celular cycle for all the cells into the tissue
     *
     */
    public void iteration();
    /**
     * This operation verifies if the position is between the tissue limits and
     * the enviroment permits this position
     * @param row
     * @param column
     * @return true when the position can be filled
     */
    public boolean validPosition(int row,int column);
    /**
     * The operation returns the enviroment where the tissue develops itself
     * @return the enviroment
     */
    public Enviroment getEnviroment();
}
