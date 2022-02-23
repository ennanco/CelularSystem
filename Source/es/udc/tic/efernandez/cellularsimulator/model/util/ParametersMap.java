package es.udc.tic.efernandez.cellularsimulator.model.util;

import java.util.HashMap;

import es.udc.tic.efernandez.cellular.util.globalconfiguration.GlobalConfiguration;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;

public class ParametersMap {

	public final static int PROTEIN_LOWER_LIMIT = 0;
    public final static int PROTEIN_HIGH_LIMIT = 1;
    public final static int PROTEIN_APOPTOSIS_LIMIT = 2;

    public final static int APOPTOSIS_STRING = 3;//0000
    public final static int GROWN_STRING_W = 4;//"1000";
    public final static int GROWN_STRING_N = 5;//"1100";
    public final static int GROWN_STRING_E = 6;//"1010";
    public final static int GROWN_STRING_S = 7;//"1110";

	public final static int NUMBER_VARIABLES = 8;
	public final static int NUMBER_INDIVIDUALS = 9;
	public final static int NUMBER_GENERATIONS = 10;
	
    public final static int MINIMIZATION_NUMBER_PARAMETER = 11;
	public final static int CROSSOVER_PERCENTAGE = 12;
    public final static int MUTATION_PERCENTAGE = 13;
    
    public final static int EVALUATION_DIMENSION_ROW = 14;
    public final static int EVALUATION_DIMENSION_COLUMN = 15;
    public final static int EVALUATION_NUM_ITERATION = 16;
    
    public final static int ACTIVE_PART_MAX_PROTEINE = 17;
    public final static int MAX_NUMBER_OF_PROTEINE = 18;
    public final static int PROTEINE_MIN_LENGTH = 19;
    

    private HashMap map;
    
    public ParametersMap(){
    	map = new HashMap();
    	// inicialize the protein limits
    	map.put(PROTEIN_LOWER_LIMIT, GlobalConfiguration.getLimit(GlobalConfiguration.PROTEIN_LOWER_LIMIT));
    	map.put(PROTEIN_HIGH_LIMIT, GlobalConfiguration.getLimit(GlobalConfiguration.PROTEIN_HIGH_LIMIT));
    	map.put(PROTEIN_APOPTOSIS_LIMIT, GlobalConfiguration.getLimit(GlobalConfiguration.PROTEIN_APOPTOSIS_LIMIT));
    	// inicialize the special action asociated proteins
    	map.put(APOPTOSIS_STRING, GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING));
    	map.put(GROWN_STRING_W, GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_W));
    	map.put(GROWN_STRING_N, GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N));
    	map.put(GROWN_STRING_E, GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_E));
    	map.put(GROWN_STRING_S, GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_S));
    	// the number of initial variables for an individual
    	map.put(NUMBER_VARIABLES, GlobalVariables.getNumberVariables());
    	// the number of individual at the population
    	map.put(NUMBER_INDIVIDUALS, 500);
    	//the number of maximum generations for the genetic algorithm
    	map.put(NUMBER_GENERATIONS, 1000);
    	//Genetic algorithm control parameter
    	map.put(MINIMIZATION_NUMBER_PARAMETER, GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.MINIMIZATION_NUMBER_PARAMETER));
    	map.put(CROSSOVER_PERCENTAGE, GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.CROSSOVER_PERCENTAGE));
    	map.put(MUTATION_PERCENTAGE,GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.MUTATION_PERCENTAGE));
    	//Evaluation parameters
    	map.put(EVALUATION_DIMENSION_ROW, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW));
    	map.put(EVALUATION_DIMENSION_COLUMN, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN));
    	map.put(EVALUATION_NUM_ITERATION, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
    	map.put(ACTIVE_PART_MAX_PROTEINE, GlobalVariables.getEvaluationParameter(GlobalVariables.ACTIVE_PART_MAX_PROTEINE));
    	map.put(MAX_NUMBER_OF_PROTEINE, GlobalVariables.getEvaluationParameter(GlobalVariables.MAX_NUMBER_OF_PROTEINE));
    	map.put(PROTEINE_MIN_LENGTH, GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH));
    }
    
    
    public HashMap getParameters(){
    	return map;
    }
    public void setParameters(HashMap parameters){
    	GlobalConfiguration.setLimit(GlobalConfiguration.PROTEIN_LOWER_LIMIT,(Float)parameters.get(PROTEIN_LOWER_LIMIT));
    	GlobalConfiguration.setLimit(GlobalConfiguration.PROTEIN_HIGH_LIMIT,(Float)parameters.get(PROTEIN_HIGH_LIMIT));
    	GlobalConfiguration.setLimit(GlobalConfiguration.PROTEIN_APOPTOSIS_LIMIT,(Float)parameters.get(PROTEIN_APOPTOSIS_LIMIT));
    	// inicialize the special action asociated proteins
    	GlobalConfiguration.setProtein(GlobalConfiguration.APOPTOSIS_STRING, (String)parameters.get(APOPTOSIS_STRING));
    	GlobalConfiguration.setProtein(GlobalConfiguration.GROWN_STRING_W, (String)parameters.get(GROWN_STRING_W));
    	GlobalConfiguration.setProtein(GlobalConfiguration.GROWN_STRING_N, (String)parameters.get(GROWN_STRING_N));
    	GlobalConfiguration.setProtein(GlobalConfiguration.GROWN_STRING_E,(String)parameters.get(GROWN_STRING_E));
    	GlobalConfiguration.setProtein(GlobalConfiguration.GROWN_STRING_S,(String)parameters.get(GROWN_STRING_S));
    	// the number of initial variables for an individual
    	GlobalVariables.setNumberVariables((Integer)parameters.get(NUMBER_VARIABLES));
    	//Genetic algorithm control parameter
    	GlobalVariables.setGeneticAlgorithmParameter(GlobalVariables.MINIMIZATION_NUMBER_PARAMETER,(Float)parameters.get(MINIMIZATION_NUMBER_PARAMETER));
    	GlobalVariables.setGeneticAlgorithmParameter(GlobalVariables.CROSSOVER_PERCENTAGE,(Float)parameters.get(CROSSOVER_PERCENTAGE));
    	GlobalVariables.setGeneticAlgorithmParameter(GlobalVariables.MUTATION_PERCENTAGE, (Float)parameters.get(MUTATION_PERCENTAGE));
    	//Evaluation parameters
    	GlobalVariables.setEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW, (Integer)parameters.get(EVALUATION_DIMENSION_ROW));
    	GlobalVariables.setEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN, (Integer)parameters.get(EVALUATION_DIMENSION_COLUMN));
    	GlobalVariables.setEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION, (Integer)parameters.get(EVALUATION_NUM_ITERATION));
    	GlobalVariables.setEvaluationParameter(GlobalVariables.ACTIVE_PART_MAX_PROTEINE, (Integer)parameters.get(ACTIVE_PART_MAX_PROTEINE));
    	GlobalVariables.setEvaluationParameter(GlobalVariables.MAX_NUMBER_OF_PROTEINE, (Integer)parameters.get(MAX_NUMBER_OF_PROTEINE));
    	GlobalVariables.setEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH, (Integer)parameters.get(PROTEINE_MIN_LENGTH));
    	
    	map = parameters;
    }
}
