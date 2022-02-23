/**
 * 
 * This class will model a set of input for the system operation.
 * 
 * Created 9-2-2009
 * 
 * @author efernandez	
 */
package util;

import java.util.HashMap;

public class TrainPattern {
		
	private HashMap<String,Double> inputs;
	private String output;
	
	public TrainPattern(){
		inputs = new HashMap<String, Double>();
		output = "";
	}
	
	public void putInput(String sequence, Double value){
		inputs.remove(sequence);
		inputs.put(sequence, value);
	}

	public double getInput(String sequence){
		Double value = inputs.get(sequence);
		return value!=null ? value: 0.0;
	}
	
	public HashMap<String,Double> getInputs(){
		return inputs;
	}
	
	public void setOutput(String s){
		this.output = s;
	}
	
	public String getOutput(){
		return output;
	}
	
	public void clearSet(){
		inputs.clear();
		output = "";
	}
	
}
