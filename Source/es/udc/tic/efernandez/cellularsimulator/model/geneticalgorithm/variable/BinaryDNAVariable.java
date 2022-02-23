package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable;

import java.util.BitSet;

import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;


/**
 * @author Enrique Fernández Blanco
 * this class implements a gen of Individual DNA for an Genetic algorithm
 */
public class BinaryDNAVariable implements DNAVariable {

	private BitSet sequence= null;	// Contitutive / Active Part / Percentage / Sequence.
	private int numberPromotors = 0;
	
	public BinaryDNAVariable() {
		super();
	}
	
	private BinaryDNAVariable(BitSet sequence, int numberPromotors){
		this.sequence = sequence;
		this.numberPromotors = numberPromotors;
	}
	
	public void randomInicialize(){
		// constitutive(1)+ n*proteine length + percentage(31) + proteine length
		numberPromotors = Math.round((float)(GlobalVariables.getEvaluationParameter(GlobalVariables.ACTIVE_PART_MAX_PROTEINE) * Math.random()));
		int length;
		length = 1 + (numberPromotors + 1) * GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH) + 31;
		sequence = new BitSet(length);
		//how many bits to change
		int toChange = Math.round((float)(length * Math.random()));
		for(int i = 0; i < toChange; i++){
			sequence.flip(Math.round((float)(length * Math.random())));
		}
	}
	
	public void inicialize(String geneSequences, String[] activePart, Float activePercentage, Boolean constitutive) {
		int length = geneSequences.length() + activePart.length * GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH) + 31 + 1;
		int position = 0;
		sequence = new BitSet(length);
		//constitutive
		sequence.set(position, constitutive.booleanValue());
		position++;
		//active part
		this.numberPromotors = activePart.length;
		for (int i =0; i < activePart.length; i++){
			for( int j = 0 ; j < GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH); j++ ){
				if(activePart[i].charAt(j) == '1')
					sequence.set(position,true);
				position++;
			}
		}
		//activePercent
		String temp = Integer.toBinaryString(Float.floatToIntBits(activePercentage.floatValue()));
		//the sequence is less than the global
		//position on the length
		position = position + 31 - ((temp.length() == 32)? 31 : temp.length() );
		//if is negative move 1 position bit 32
		for(int i = (temp.length() == 32)?1:0; i < temp.length() ; i++){
			if(temp.charAt(i) == '1')
				sequence.set(position, true);
			position++;
		}
		// geneSequence
		for(int i = 0; i < geneSequences.length(); i++){
			if(geneSequences.charAt(i) == '1')
				sequence.set(position, true);
			position++;
		}		
	}

	public String[] getActivePart() {		
		String [] toReturn = new String[numberPromotors];
		for(int i = 0; i < numberPromotors; i++){
			toReturn[i]="";
			for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH); j++){
				// one position added for the constitutive bit
				toReturn[i] += (sequence.get(1+i*GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH)+j))? "1" : "0";
			}
		}
		return toReturn;
	}

	public Float getActivePercentage() {
		Float toReturn;
		String tmp = "0";
		
		for(int j = 0; j < 31; j++){
			// one position added for the constitutive bit + number of promoters bits 
			tmp += (sequence.get(1+numberPromotors*GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH)+j))? "1" : "0";
		}
		// take the string and convert it to a int
		// the bits of the int converted to a float
		// calculate the percentage of this value.
		toReturn = new Float(Float.intBitsToFloat(Integer.parseInt(tmp,2)));
		
		return toReturn;
	}

	public Boolean getConstitutive() {
		return new Boolean(sequence.get(0));
	}

	public String getGeneSequences() {
		String toReturn ="";
		for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH); j++){
			toReturn += (sequence.get(1 + numberPromotors*GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH) + 31 + j))? "1" : "0";
		}

		return toReturn;
	}

	public Variable mutation() {
		BitSet toCreate =(BitSet)sequence.clone();
		toCreate.flip(Math.round((float)(sequence.size() * Math.random())));

		Variable toReturn = new BinaryDNAVariable(toCreate,numberPromotors);
		return toReturn;
	}
	
	public Variable[] cross(Variable crossVariable){
		Variable[] toReturn = {crossVariable,this};
		return toReturn;
	}
	
	public Variable clone(){
		return new BinaryDNAVariable((BitSet)sequence.clone(),this.numberPromotors);
	}

}
