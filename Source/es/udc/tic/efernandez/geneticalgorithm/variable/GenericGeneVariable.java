package es.udc.tic.efernandez.geneticalgorithm.variable;

import util.Section;
import util.Pair;

public class GenericGeneVariable implements Variable {

	private static String[] VALUES = new String[]{"0","1","2", ""};
	public static int SIZE_PROMOTERS = 4;
	public static int SIZE_INHIBITORS = 2;
	
	private Section<String, Double, Double>[] promoters;
	private Pair<String, Double>[] inhibitors;
	private Section<String, Double, Double> sequence;
	
	public GenericGeneVariable() {
		randomInicialize();
	}
	
	public Section<String,Double, Double>[] getPromoters() {
		return promoters;
	}

	public void setPromoters(Section<String,Double,Double>[] promoters) {
		this.promoters = promoters;
	}
	
	public Pair<String,Double>[] getInhibitors() {
		return inhibitors;
	}

	public void setInhibitors(Pair<String,Double>[] inhibitors) {
		this.inhibitors = inhibitors;
	}

	public Section<String,Double, Double> getSequence() {
		return sequence;
	}

	public void setSequence(Section<String,Double, Double> sequence) {
		this.sequence = sequence;
	}

	@Override
	public Variable[] cross(Variable crossVariable) {
		return new Variable[]{this, crossVariable};
	}

	@Override
	public Variable mutation() {
		GenericGeneVariable toReturn = (GenericGeneVariable) this.clone();
		int max = promoters.length + inhibitors.length + 1;
		int position = (int) Math.floor(Math.random()* max);
		if(promoters.length > position){
			Section<String, Double, Double>[] temp = toReturn.getPromoters();
			temp[position] = generateSection(VALUES.length-1);
			toReturn.setPromoters(temp);
		} else if(position < promoters.length + inhibitors.length){
			Pair<String, Double>[] temp = toReturn.getInhibitors();
			temp[position- promoters.length] = generatePair(VALUES.length-1);
			toReturn.setInhibitors(temp);
		} else{
			toReturn.setSequence( generateSection(VALUES.length-2));
		}
		return toReturn;
	}

	@Override
	public void randomInicialize() {
		promoters = new Section[SIZE_PROMOTERS];
		for(int i = 0; i <SIZE_PROMOTERS; i++){
			promoters[i] = generateSection(VALUES.length-1);;
		}
		inhibitors = new Pair[SIZE_INHIBITORS];
		for(int i = 0; i <SIZE_INHIBITORS; i++){
			inhibitors[i] = generatePair(VALUES.length-1);
		}
		sequence = generateSection(VALUES.length -2);
	}
	
	public Variable clone(){
		GenericGeneVariable toReturn = new GenericGeneVariable();
		Section<String, Double, Double>[] promoters = new Section[this.promoters.length];
		Pair<String, Double>[] inhibitors= new Pair[this.inhibitors.length];
		Section<String, Double, Double> sequence = (Section<String, Double, Double>) this.sequence.clone();
		for(int i = 0; i <promoters.length; i++){
			promoters[i] = (Section<String, Double, Double>) this.promoters[i].clone();
		}
		for(int i = 0; i <inhibitors.length; i++){
			inhibitors[i] = (Pair<String, Double>) this.inhibitors[i].clone();
		}
		
		toReturn.setPromoters(promoters);
		toReturn.setInhibitors(inhibitors);
		toReturn.setSequence(sequence);
		return toReturn;
	}
	
	public String toString(){
		String toReturn = "";
		toReturn+="Promoters \n";
		for(Section<String, Double, Double> s: promoters){
			toReturn += s.getFirst()+"("+s.getSecond()+") "+s.getThird()+"\n";
		}
		toReturn+="Inhibitors \n";
		for(Pair<String, Double> s: inhibitors){
			toReturn += s.getFirst()+"("+s.getSecond()+") \n";
		}
		toReturn+="Sequence \n"+sequence.getFirst()+" ("+sequence.getSecond()+") "+sequence.getThird()+"\n";
		
		return toReturn;
	}
	
	private Pair<String, Double> generatePair(int limit){
		int position =	(int) Math.round(Math.random()*limit);
		return new Pair<String, Double>(VALUES[position], Math.random());
	}

	private Section<String, Double, Double> generateSection(int limit){
		int position =	(int) Math.round(Math.random()*limit);
		return new Section<String, Double, Double>(VALUES[position], Math.random(), Math.random());
	}

	public static void setVALUES(String[] values){
		VALUES = values;
	}
	
	public static String[] getVALUES() {
		return VALUES;
		
	}
	

}
