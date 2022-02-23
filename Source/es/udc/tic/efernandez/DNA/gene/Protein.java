package es.udc.tic.efernandez.DNA.gene;

public class Protein {
	
	private String sequence;
	private double value;
	
	public Protein(String sequence, double value) {
		super();
		this.sequence = sequence;
		this.value = value;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
