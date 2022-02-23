/*
 * Created on 12-jul-2005
 */
package es.udc.tic.efernandez.util.matrix;



/**
 * @author Enrique Fernández Blanco.
 * @param <E>
 *
 */
public class JMatrix<E> {
	
	private E[][] elements;
	private int numberOfElements;
	private int maxDimensionRow;
	private int maxDimensionColum;
	
	public JMatrix(int row, int colum) {
		elements = (E[][])new Object[row][colum];
		maxDimensionRow = row;
		maxDimensionColum = colum;
		numberOfElements = 0;
	}
	/**
	 * 
	 * @param x the element's row
	 * @param y the element's colum
	 * @return the element at this position or null.
	 */
	public E getElement(int row, int colum){
	    
	    if((row>=maxDimensionRow)||(colum>=maxDimensionColum)|| (row < 0) || (colum < 0))
	        return null;
	    return(elements[row][colum]);
	}
	/**
	 * 
	 * @param x Element's row
	 * @param y Element's column
	 * @param e the Object to insert.
	 */
	public void setElement(int row,int colum, E e){
	    elements[row][colum] = e;
	    numberOfElements++;
	}
	
	
	
    /**
     * @return Returns the maxDimensionX.
     */
    public int getMaxDimensionRow() {
        return maxDimensionRow;
    }
    /**
     * @return Returns the maxDimensionY.
     */
    public int getMaxDimensionColum() {
        return maxDimensionColum;
    }
    /**
     * @return Returns the numberElements.
     */
    public int getNumberOfElements() {
        return numberOfElements;
    }
}
