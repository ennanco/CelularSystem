package es.udc.tic.efernandez.cellular.enviroment.example;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.cellular.protein.ProteinListFactory;
import es.udc.tic.efernandez.cellular.util.cellularDirection.CellularDirection;
import es.udc.tic.efernandez.util.matrix.JMatrix;

public class DiscreteFluidEnviroment implements Enviroment {

	private JMatrix<LinkedList<Protein>> recived;
	private ProteinList[][][] containedProteinsDirection;
	private ProteinList[][] containedProteins;
	private int rows;
	private int columns;
	
	
	public void inicialize(int rows, int columns) {
		recived = new JMatrix<LinkedList<Protein>>(rows,columns);
		containedProteinsDirection = new ProteinList[rows][columns][CellularDirection.NUMBERDIRECTIONS];
		containedProteins =  new ProteinList[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++){
				recived.setElement(i, j, new LinkedList<Protein>());
				containedProteins[i][j] = ProteinListFactory.getInstance();
				for(int k = 0; k < CellularDirection.NUMBERDIRECTIONS; k++)
					containedProteinsDirection[i][j][k] = ProteinListFactory.getInstance();
			}
		this.rows = rows;
		this.columns = columns;
	}
	
	
	public int consultProtein(Point position, int direction, String sequence){
		ProteinList pointProteinsDirection = containedProteinsDirection[position.x][position.y][direction];
		return pointProteinsDirection.amount(sequence);
	}
		
	public int consultProtein(Point position, String sequence) {
		ProteinList pointProteins = containedProteins[position.x][position.y];
		return pointProteins.amount(sequence);
	}

	public Set listedProteins(Point p){
		return containedProteins[p.x][p.y].listedProteins();
	}

	public void set(Point p, Protein protein) {
		LinkedList<Protein> l = recived.getElement(p.x, p.y);
		if(l == null){
			l = new LinkedList<Protein>();
			recived.setElement(p.x, p.y, l);
		}
		l.add(protein);
	}

	public Protein[] get(Point p, long instant) {
		ProteinList list = containedProteins[p.x][p.y];
		return list.getProteins();
		
	}                                 
	
	public Protein getProtein(Point p, String sequence){
		Protein protein = containedProteins[p.x][p.y].removeProtein(sequence);
		if(protein != null){
			containedProteinsDirection[p.x][p.y][protein.getDirection()].removeProtein(protein);
		}
		return protein;
	}
	
	public void update(long instant) {
		
		for(int i = 0; i<rows; i++)
			for(int j = 0; j<columns; j++){
				// Update the recived ones
				for( Protein p : recived.getElement(i,j)){
					containedProteins[i][j].setProtein(p);
					containedProteinsDirection[i][j][p.getDirection()].setProtein(p);
				}
				// update the TimeToLife
				containedProteins[i][j].updateList(instant);
				for(int k = 0; k < CellularDirection.NUMBERDIRECTIONS; k++)
					containedProteinsDirection[i][j][k].updateList(instant);
			}
		
		// calculate the displacements
		JMatrix<LinkedList<Protein>> displacements = 
			new JMatrix<LinkedList<Protein>>(rows, columns);
		
		for(int i = 0; i< rows;i++){
			for(int j = 0; j < columns;j++){
				// go throught all neighbours
				for(int k = 0; k < CellularDirection.NUMBERDIRECTIONS; k++){
					int row = i + CellularDirection.desplacementX(k);
					int column = j + CellularDirection.desplacementY(k);
					if(valid(row,column)){
						containedProteinsDirection[i][j][k].listedProteins().toArray(new String[0]);
						LinkedList<String> toRemove = new LinkedList<String>();
						for(Iterator iter = containedProteinsDirection[i][j][k].listedProteins().iterator();iter.hasNext(); ){
							String key = (String)iter.next();
							int originTotalAmount =containedProteins[i][j].amount(key);
							int destinyTotalAmount = containedProteins[row][column].amount(key);
							int originDirectionAmount = containedProteinsDirection[i][j][k].amount(key);
							int destinyDirectionAmount = containedProteinsDirection[i][j][CellularDirection.oposite(k)].amount(key);
							
							if( originTotalAmount > destinyTotalAmount && originDirectionAmount > destinyDirectionAmount ){
								toRemove.add(key);
							}
						}
						for(String key :toRemove){
							Protein p = containedProteinsDirection[i][j][k].removeProtein(key);
							containedProteins[i][j].removeProtein(p);
							LinkedList<Protein> l = displacements.getElement(row, column);
							if(l==null){
								l = new LinkedList<Protein>();
								displacements.setElement(row, column, l);
							}
							l.add(p);	
						}
					}
				}					
			}
		}

		// Actualization of the recived for the next turn with the displacements of this one.
		Point point = new Point(0,0);
		for(int i = 0; i< rows;i++){
			for(int j = 0; j < columns;j++){
				LinkedList<Protein> l = displacements.getElement(i,j);
				if (l != null){
					point.setLocation(i, j);
					for(Protein p : l){
						set(point,p);
					}
				}
			}
		}	
	}

	public boolean valid(int row, int column) {
		return row >= 0 && column >= 0 && row < rows && column < columns;
	}
	
	public Object clone(){
		
		DiscreteFluidEnviroment object = new DiscreteFluidEnviroment();
		object.inicialize(this.rows, this.columns); 
		return object;
	}


}
