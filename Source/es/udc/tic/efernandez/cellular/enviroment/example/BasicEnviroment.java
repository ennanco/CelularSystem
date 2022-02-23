/*
 * Created on 10-ago-2005
 */
package es.udc.tic.efernandez.cellular.enviroment.example;

import java.awt.Point;
import java.util.Set;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.cellular.protein.ProteinListFactory;

/**
 * @author Enrique Fernández Blanco
 */
public class BasicEnviroment implements Enviroment {

    private ProteinList list;
    private int dimension_row;
    private int dimension_column;
    
	public void inicialize(int maxRow, int maxColumn){
		dimension_row = maxRow;
		dimension_column = maxColumn;
	}
    
    public boolean valid(int row, int column){
        return (row>=0)&&(row<dimension_row)&&(column>=0)&&(column<dimension_column);
    }
    
    public BasicEnviroment(){
        list = ProteinListFactory.getInstance();
    }
    public Protein[] get(Point p, long instant) {
        return null;
    }
    
    public void set(Point p, Protein protein){
        list.setProtein(protein);
    }
    
	public void update(long instant) {
		list.updateList(instant);
	}
	public Object clone(){
		return new BasicEnviroment();
	}

	public int consultProtein(Point position, String sequence) {
		return 0;
	}

	public Protein getProtein(Point p, String sequence) {
		return null;
	}

	public Set listedProteins(Point p) {
		return null;
	}

}
