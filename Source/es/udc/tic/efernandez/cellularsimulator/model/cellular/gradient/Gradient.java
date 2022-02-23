package es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient;

import java.awt.Point;

import es.udc.tic.efernandez.cellular.protein.Protein;

public interface Gradient {
	
	public String getSequence();
	
	public Point getPosition();
	
	public float getDistance();
	
	public float presenceProbability(Point p);
	
	public Protein getProtein(Point p, long instant);

}
