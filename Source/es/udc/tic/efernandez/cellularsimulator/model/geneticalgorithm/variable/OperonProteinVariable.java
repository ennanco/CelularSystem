package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable;

import java.util.BitSet;

import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;

public class OperonProteinVariable implements Variable{

	private BitSet sequence; // Operon(1)/Promotor(1)/Constitutive(1)/OperonLength(3)/Direction(3)/Sequence/Percentage(31)
	private int length;
	
	public OperonProteinVariable() {
		super();
	}

	public OperonProteinVariable(BitSet sequence) {
		super();
		this.sequence = sequence;
	}
	
	public void inicialize(Boolean operon,Boolean promotor, 
						Boolean constitutive, int operonLength,int direction,String geneSequence, Float percentage){
		// the 40 = 1+1+1+3+3+31
		length =  GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH) + 40;
		int position = 0;
		sequence = new BitSet(length);
		
		sequence.set(position, operon.booleanValue());
		position++;
		
		sequence.set(position, promotor.booleanValue());
		position++;
		
		sequence.set(position, constitutive.booleanValue());
		position++;
		
		String value = BinaryStringOperations.toBinary(operonLength, 3);
		for ( int i = 0; i < 3; i++){
			sequence.set(position, value.charAt(i) == '1');
			position++;
		}
		
		value = BinaryStringOperations.toBinary(direction, 3);
		for(int i = 0;i <3; i++){
			sequence.set(position,value.charAt(i)=='1');
			position++;
		}
		
		for(int i = 0; i < geneSequence.length(); i++){
			sequence.set(position, geneSequence.charAt(i) == '1');
			position++;
		}
		
		String temp = Integer.toBinaryString(Float.floatToIntBits(percentage.floatValue()));
		//the sequence is less than the global
		//position on the length
		position = position + 31 - ((temp.length() == 32)? 31 : temp.length() );
		//if is negative move 1 position bit 32
		for(int i = (temp.length() == 32)?1:0; i < temp.length() ; i++){
			sequence.set(position, temp.charAt(i) == '1');
			position++;
		}

	}

	public void randomInicialize() {
		length =  GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH) + 40;
		sequence = new BitSet(length);
		//how many bits to change
		int toChange = Math.round((float)(length * Math.random()));
		for(int i = 0; i < toChange; i++){
			sequence.flip(Math.round((float)(length * Math.random())));
		}
	}
	
	// one point crossover
	public Variable[] cross(Variable crossVariable){
		OperonProteinVariable toCross = null;
		BitSet[] toOperate;
		
		if(crossVariable instanceof OperonProteinVariable){
			toCross = (OperonProteinVariable) crossVariable;
		}
		int cutPosition = (int)Math.floor(Math.random()*length);
		
		toOperate = new BitSet[2];
		
		toOperate[0] = (BitSet)sequence.clone();
		toOperate[1] = (BitSet)sequence.clone();
		
		for(int i = 0;(i < cutPosition); i++){
			toOperate[1].set(i,toCross.getBinary().get(i));
		}
		for(int i = cutPosition; i< length; i++){
			toOperate[0].set(i,toCross.getBinary().get(i));
		}
		
		Variable[] toReturn = {new OperonProteinVariable(toOperate[0]),new OperonProteinVariable(toOperate[1])};
		return toReturn;
		
	}

	public Variable mutation() {
		int toFlip; // the bit that will be changed.
		BitSet toCreate =(BitSet)sequence.clone();
		int length = 3; //Operon(1)/Promotor(1)/Constitutive(1)
		
		if(isOperon()){
			length += 3 ; //Operon Length
			toFlip = Math.round((float)(length * Math.random()));
		} else if (isPromotor()){
			// Add the sequence and the interger fot the percentage
			length += GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH) + 31;
			toFlip = Math.round((float)(length * Math.random()));
			toFlip += (toFlip >= 3)?  6 : 0; // positions that must jump (OperonLength(3) & Direction (3)
		} else{ // is a Gene
			length += GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH); // The sequence
			length += 3; // the direction of the response
			toFlip = Math.round((float)(length * Math.random()));
			toFlip += (toFlip >= 3)?  3 : 0; // positions of the OperonLength
		}
		
		
		toCreate.flip(toFlip);

		Variable toReturn = new OperonProteinVariable(toCreate);
		return toReturn;
	}
	
	public boolean isOperon(){
		return sequence.get(0);
	}
	
	public int getOperonLength(){
		String temp = "";
		for( int i = 3; i < 6; i++){
			temp+= (sequence.get(i))?"1":"0";
		}
	
		try {
			return BinaryStringOperations.convert(temp) + 2; // the minimum length must be 2 to be an operon.
		} catch (NonValidSequenceException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean isPromotor(){
		return sequence.get(1);
	}
	
	public boolean isConstitutive(){
		return sequence.get(2);
	}
	
	public int getDirection(){
		String temp = "";
		for(int i = 6; i<9; i++)
			temp+=(sequence.get(i))?"1":"0";
		
		try {
			return BinaryStringOperations.convert(temp);
		} catch (NonValidSequenceException e) {
			return -1;
		}
	}
	
	public String getSequence(){
		String toReturn ="";
		for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH); j++){
			toReturn += (sequence.get(9 + j))? "1" : "0";
		}

		return toReturn;
	}
	
	public Float getPercentage(){
		Integer toCalculate;
		String tmp = "0";
		
		for(int j = 0; j < 31; j++){
			// one position added for the constitutive bit + promotor + operon+ protein length 
			tmp += (sequence.get(9+GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH)+j))? "1" : "0";
		}
		// take the string and convert it to a int
		// the bits of the int converted to a float
		// calculate the percentage of this value.
		toCalculate = Integer.parseInt(tmp,2);
		return ((float)toCalculate/(float)Integer.MAX_VALUE)*100.0F;
	}
	
	public Float getNumber(){
		Float toReturn;
		String tmp = "0";
		
		for(int j = 0; j < 31; j++){
			// one position added for the constitutive bit + promotor + operon+ protein length 
			tmp += (sequence.get(8+GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH)+j))? "1" : "0";
		}
		// take the string and convert it to a int
		// the bits of the int converted to a float
		// calculate the percentage of this value.
		toReturn = new Float(Float.intBitsToFloat(Integer.parseInt(tmp,2)));
		return toReturn;
	}
	
	public BitSet getBinary(){
		return sequence;
	}
	
	public Variable clone(){
		return new OperonProteinVariable((BitSet)sequence.clone());
	}

}
