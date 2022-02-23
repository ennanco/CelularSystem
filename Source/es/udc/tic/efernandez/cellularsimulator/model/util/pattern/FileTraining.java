package es.udc.tic.efernandez.cellularsimulator.model.util.pattern;

import java.awt.Point;

public class FileTraining {

	private int numberIndividuals;
	private Point[] individuals;
	private int numberReceptors;
	private Point[] receptors;
	private int numberOutputs;
	private int numberOutputsPattern;
	private String[][] outputs;
	private int numberPatterns;
	private int gradientsPattern;
	private Patterns [] patterns;
	
	public FileTraining (int numberIndividuals, int numberReceptors, int numberOutputs, 
			int numberOutputsPattern, int numberPatterns, int gedientsPattern){
		this.numberIndividuals = numberIndividuals;
		this.numberReceptors = numberReceptors;
		this.numberOutputs = numberOutputs;
		this.numberOutputsPattern = numberOutputsPattern;
		this.numberPatterns = numberPatterns;
		this.gradientsPattern = gedientsPattern;
		individuals = new Point [numberIndividuals];
		receptors = new Point [numberReceptors];
		outputs = new String [numberOutputs][numberOutputsPattern];
		patterns = new Patterns [numberPatterns];
	}
	
	public int getNumberIndividuals(){
		return numberIndividuals;
	}
	
	public int getNumberReceptors(){
		return numberReceptors;
	}
	
	public int getNumberOutputs(){
		return numberOutputs;
	}
	
	public int getNumberOutputsPattern(){
		return numberOutputsPattern;
	}
	
	public int getNumberPatterns(){
		return numberPatterns;
	}
	
	public int getGradientsPattern(){
		return gradientsPattern;
	}
	
	public Point[] getIndividuals(){
		return individuals;
	}
	
	public void setIndividuals(Point[] individuals){
		this.individuals = individuals;
	}
	
	public Point[] getReceptors(){
		return receptors;
	}
	
	public void setReceptors(Point[] receptors){
		this.receptors = receptors;
	}
	
	public String[][] getoutputs(){
		return outputs;
	}
	
	public void setOutputs(String[][] outputs){
		this.outputs = outputs;
	}
	
	public Patterns [] getPatterns(){
		return patterns;
	}
	
	public void setPatterns(Patterns [] patterns){
		this.patterns = patterns;
	}
}
