package es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist;

import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.Gradient;

public interface GradientList {
	
	public void addGradient(Gradient gradient);
	
	public void removeGradient(int index);
	
	public Gradient getGradient(int index);
	
	public Gradient getFirstGradient();
	
	public void removeFirstGradient();
	
	public int size();
	
	public boolean isEmpty();

}
