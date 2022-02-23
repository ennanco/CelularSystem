package es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient;

import java.awt.Point;

import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinFactory;
import es.udc.tic.efernandez.cellular.util.cellularDirection.CellularDirection;
import es.udc.tic.efernandez.util.valuetable.ValueTable;

public class BasicGradient implements Gradient {
	
	private String sequence;
	private Point position;
	private int radius;
	private float init = 1.0F;
	
	public BasicGradient(String sequence, Point position, int radius) {
		super();
		
		this.sequence = sequence;
		this.position = position;
		this.radius = radius;
	}
	
	public BasicGradient(String sequence, Point position, int radius, float init) {
		super();
		
		this.sequence = sequence;
		this.position = position;
		this.radius = radius;
		this.init = init;
	}

	public String getSequence() {
		return sequence;
	}

	public Point getPosition() {
		return position;
	}

	public float getDistance() {
		return radius;
	}

	public float presenceProbability(Point p) {
		// square distance to the point
		int distance = Math.abs(p.x - position.x)+Math.abs(p.y-position.y);
		//with this distance we have to modificate the probability to found a protein
		float toReturn = decreasingFunction(distance);
		return (toReturn < 0.0F)||(distance >radius)? 0.0F : (toReturn * init);
	}
	
	public Protein getProtein(Point p,long instant){
		//calculate the probability to found a protein in that space
		float probability = presenceProbability(p);
		// randomly value
		boolean found = Math.random() < probability;
		Protein toReturn = found? ProteinFactory.getInstance(sequence,instant,ValueTable.getDefault(),position,CellularDirection.direction(position, p)) :null;
		return toReturn;
	}
	
	private float decreasingFunction(int x){
		// the funtion which specifies the behaviour
		return ((float)-x / (float)radius+1) + 1.0F;
	}

}
