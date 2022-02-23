package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions;

import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterionFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.individual.IndividualFactory;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.population.PopulationFactory;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

/**
 * This class contains common functionalities of Batch Simulator Mode and the grafical mode.
 * This functions are refering to the use of Genetic Algorithms.
 * @author Enrique Fernández Blanco
 *
 */
public class CommonFunctions {

	private CommonFunctions() {
		super();
	}

	public static Population randPopulation(int numberIndividuals, boolean[][] template, FileTraining training) {
		
	    Population population = PopulationFactory.newInstance();
	    DNAEvaluationCriterion eval = DNAEvaluationCriterionFactory.getInstance(template, training);
	    Variable[] variable;
	    
	    population.setCriterion(eval.isRisingOrder());
	    
	    for(int i = 0; i < numberIndividuals; i++){
	        variable = new Variable[GlobalVariables.getNumberVariables()];
	        for(int j = 0; j < GlobalVariables.getNumberVariables();j++){               
	            variable[j] = VariableFactory.getInstance();
	            variable[j].randomInicialize();
	        }
	        
	        Individual individual = IndividualFactory.getInstance(variable);
	        population.add(individual,eval.evaluation(individual));
	    }
	    return population;        
	}
}
