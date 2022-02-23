/*
 * Created on 26-ago-2005
 */
package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables;

/**
 * @author Enrique Fernández Blanco
 */
public class GlobalVariables {

	private static int NUMBER_VARIABLES = 20;
	
    public final static int MINIMIZATION_NUMBER_PARAMETER = 0;
	public final static int CROSSOVER_PERCENTAGE = 1;
    public final static int MUTATION_PERCENTAGE = 2;
    
    private static float[] FLOAT_VALUES = {0.1F, 90.0F, 30.0F};
    
    public final static int EVALUATION_DIMENSION_ROW = 0;
    public final static int EVALUATION_DIMENSION_COLUMN = 1;
    public final static int EVALUATION_NUM_ITERATION = 2;
    
    public final static int ACTIVE_PART_MAX_PROTEINE = 3;
    public final static int MAX_NUMBER_OF_PROTEINE = 4;
    public final static int PROTEINE_MIN_LENGTH = 5;
    
    private static int[] INT_VALUES = {20,20,50,2,16,4};

    public static int getNumberVariables(){
    	return NUMBER_VARIABLES;
    }
    
    public static void setNumberVariables(int numberVariables){
    	NUMBER_VARIABLES = numberVariables;
    }
    
    public static float getGeneticAlgorithmParameter(int id){
    	return FLOAT_VALUES[id];
    }

    public static void setGeneticAlgorithmParameter(int id, float value){
    	FLOAT_VALUES[id] = value;
    }
    
    public static int getEvaluationParameter(int id){
    	return INT_VALUES[id];
    }
    
    public static void setEvaluationParameter(int id, int value){
    	INT_VALUES[id] = value;
    }
    
    private GlobalVariables(){}

}
