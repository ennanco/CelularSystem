package es.udc.tic.efernandez.cellular.enviroment.example;

import java.awt.Point;
import java.util.Set;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.cellular.protein.ProteinListFactory;

public class Square3x3Enviroment implements Enviroment {

	private ProteinList list;
	public Square3x3Enviroment() {
		super();
		list = ProteinListFactory.getInstance();
	}
	
	public void inicialize(int maxRow, int maxColumn){
	}

	public boolean valid(int row, int column) {
		return (row > 8) && (row < 12) &&
				(column > 10) && (column < 14)? false:true;
	}

	public Protein[] get(Point p, long instant) {
		return null;
	}

	public void set(Point p, Protein protein) {
		list.setProtein(protein);

	}

	public void update(long instant) {
		list.updateList(instant);
	}
	
	public Object clone(){
		return new Square3x3Enviroment();
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
