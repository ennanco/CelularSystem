package es.udc.tic.efernandez.cellular.enviroment.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinFactory;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.cellular.protein.ProteinListFactory;
import es.udc.tic.efernandez.cellular.util.cellularDirection.CellularDirection;
import es.udc.tic.efernandez.util.Mathematical.Distance;
import es.udc.tic.efernandez.util.valuetable.ValueTable;

public class GradientCommunicationEnviroment implements Enviroment { 

	private final static int maximumDistance = 4;
	
	private LinkedList<Protein> recived;
	private ProteinList[][][] containedGradients;
	private int numberDirections = ProteinFactory.getInstance("", 0, 0, null, 0).getNumberDirections();
	
	public void inicialize(int maxRow, int maxColumn){
		containedGradients = new ProteinList[maxRow][maxColumn][numberDirections];
		recived = new LinkedList();
	}
	
	public Protein[] get(Point p, long instant) {
		ArrayList<Protein> toReturn = new ArrayList<Protein>();
		
		// iterations over the increments of horizontal distance respect to the vertical one
		int row = p.x;
		int column = p.y;
		int secondDimension = 0;
		float probability;
		int direction;
		Point destination = new Point();
		boolean found;
		ProteinList list;
		for(int i = -maximumDistance; i <= maximumDistance; i++){
			secondDimension = maximumDistance - Math.abs(i);
			row = p.x + i;
			for(int j = -secondDimension; j <= secondDimension; j++){
				column = p.y + j;
				if((row >= 0) && (column >= 0) 
						&& ((row< containedGradients.length)&&(column<containedGradients[row].length)) &&
						!((p.x == row) && (p.y == column))){
					destination.setLocation(row, column);
					direction = CellularDirection.direction(p, destination);
					//proteins in the neighbourhood
					list = getAtPosition(row,column,direction);
					if(list != null){
						// probability at that distance
						probability = presenceProbability(p, destination);
						// iterarla con esa probabilidad
						for(Iterator iter = list.listedProteins().iterator(); iter.hasNext();){
							String sequence = (String)iter.next();
							int proteinNumber = list.amount(sequence);
							for( int k = 0; k< proteinNumber;k++){
								//for each protein decide if its acepted.
								found = Math.random()<probability;
								if(found){
									Protein protein = getAtPosition(row, column, direction).getProtein(sequence);
									toReturn.add(ProteinFactory.getInstance(protein.getSequence(),
																			instant,ValueTable.getDefault(),
																			protein.getOrigin(), direction));
								}
							}
						}	
					}
				}
			}
		}
		return toReturn.size() > 0? toReturn.toArray(new Protein[0]):null;
	}

	public void set(Point p, Protein protein) {
		recived.add(protein);
	}

	public void update(long instant) {
		// introduce new proteins.
		int i = 0;
		Protein protein;
		while(i < recived.size()){
			protein = recived.get(i);
			if (protein.getCreated()<=instant){
				Point p = protein.getOrigin();
				ProteinList list = getAtPosition(p.x,p.y, protein.getDirection());
				if(list==null){
					list = ProteinListFactory.getInstance();
					containedGradients[p.x][p.y][protein.getDirection()] = list;
				}
				// esto casi puede quedar a menos que desaparezcan los
				list.setProtein(protein);
				recived.remove(i);
			} else {
				i++;
			}
		}
		// update time to life
		ProteinList list;
		for( int index_row = 0; index_row < containedGradients.length; index_row++){
			for(int index_column = 0; index_column < containedGradients[0].length;index_column++){
				for(int index_direction= 0; index_direction <numberDirections; index_direction++){
					list = containedGradients[index_row][index_column][index_direction];
					if(list!=null)
						list.updateList(instant);						
				}
			}
		}
	}

	public boolean valid(int row, int column) {
		return true;
	}
	
	public int consultProtein(Point position, String Sequence){
		return 0;
	}

	public Object clone(){
		return new GradientCommunicationEnviroment();
	}
	
	private ProteinList getAtPosition(int row, int column, int direction){
		ProteinList toReturn = null;
		
		if((row < containedGradients.length)&& (row >= 0) && (column < containedGradients[row].length)  && (column >= 0)
				&& direction >= 0 && direction <= numberDirections)
			toReturn = containedGradients[row][column][direction];
		
		return toReturn;
	}
	
	private float presenceProbability(Point origin, Point destination){
		
		int distance = Distance.manhattanDistance(origin, destination);
		float probability = -distance / ((float) maximumDistance + 1) + 1.0F;
		return (probability < 0.0F)||( distance > maximumDistance )? 0.0F : 0.25F * probability;
	}

	public Protein getProtein(Point p, String sequence) {
		return null;
	}

	public Set listedProteins(Point p) {
		return null;
	}
}
