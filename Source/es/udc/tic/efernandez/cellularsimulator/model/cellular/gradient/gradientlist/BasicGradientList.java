package es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist;

import java.util.ArrayList;

import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.Gradient;

public class BasicGradientList implements GradientList {

	private ArrayList gradients;
	
	public BasicGradientList() {
		super();
		gradients = new ArrayList();
	}

	public void addGradient(Gradient gradient) {
		gradients.add(gradient);
	}

	public void removeGradient(int index) {
		gradients.remove(index);
	}

	public Gradient getGradient(int index) {
		return (Gradient)gradients.get(index);
	}

	public Gradient getFirstGradient() {
		return getGradient(0);
	}

	public void removeFirstGradient() {
		removeGradient(0);
	}

	public int size() {
		return gradients.size();
	}

	public boolean isEmpty() {
		return gradients.isEmpty();
	}

}
