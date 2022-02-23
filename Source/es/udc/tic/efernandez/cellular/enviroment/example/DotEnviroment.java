package es.udc.tic.efernandez.cellular.enviroment.example;

import java.awt.Point;
import java.util.Set;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.cellular.protein.ProteinListFactory;

public class DotEnviroment implements Enviroment {

	private ProteinList list;
	public DotEnviroment() {
		this.list = ProteinListFactory.getInstance();
	}

	public void inicialize(int maxRow, int maxColumn){
	}
	
	public boolean valid(int row, int column) {
		if((row % 2 == 1)&&(column % 2 == 1))
			return false;
		else
			return true;
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
		return new DotEnviroment();
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
