package es.udc.tic.efernandez.DNA.gene;

public class GeneIdentifier {
	
	private String sequence;
	private double minValue, maxValue;

	
	public GeneIdentifier(String sequence, double minValue, double maxValue) {
		super();
		this.sequence = sequence;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public double getMinimumValue() {
		return minValue;
	}

	public void setMinimumValue(double minValue) {
		this.minValue = minValue;
	}
	
	public double getSaturationValue(){
		return this.maxValue;
	}
	
	public void setSaturationValue(double maxValue){
		this.maxValue = maxValue;
	}
	
}
