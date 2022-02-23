package es.udc.tic.efernandez.cellularsimulator.model.util.pattern;

public class Patterns {
	
	private int gradientsPatterns;
	private GradientsPattern [] gradients;
	
	public Patterns(int gradientsPatterns){
		this.gradientsPatterns = gradientsPatterns;
		gradients = new GradientsPattern [gradientsPatterns];
	}
	
	public int getGradientsPattern(){
		return gradientsPatterns;
	}
	
	public GradientsPattern [] getGradients(){
		return gradients;
	}
	
	public void setGradients(GradientsPattern [] gradients){
		this.gradients = gradients;
	}
	
	public void setGradient(int position, GradientsPattern gradient){
		this.gradients[position] = gradient;
	}

}
