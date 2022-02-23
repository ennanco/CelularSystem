package es.udc.tic.efernandez.cellularsimulator.model.util.pattern;

public class GradientsPattern {
	
	private String protein;
	private float init;
	private int radio;
	private int x;
	private int y;
	
	public GradientsPattern(String protein, float init, int radio, int x, int y){
		this.protein = protein;
		this.init = init;
		this.radio = radio;
		this.x = x;
		this.y = y;
	}
	
	public String getProtein(){
		return protein;
	}
	
	public float getInit(){
		return init;
	}
	
	public int getRadio(){
		return radio;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

}
