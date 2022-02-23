package util;

public class PatternSet {

	private TrainPattern[] pattern;
	private String[] inputNames;
	private String[] possibleOutputs;
	
	public PatternSet(){
		super();
	}

	public TrainPattern[] getPattern() {
		return pattern;
	}

	public void setPattern(TrainPattern[] pattern) {
		this.pattern = pattern;
	}

	public String[] getInputNames() {
		return inputNames;
	}

	public void setInputNames(String[] inputNames) {
		this.inputNames = inputNames;
	}

	public String[] getPossibleOutputs() {
		return possibleOutputs;
	}

	public void setPossibleOutputs(String[] possibleOutputs) {
		this.possibleOutputs = possibleOutputs;
	}
	
	
	
	
}
