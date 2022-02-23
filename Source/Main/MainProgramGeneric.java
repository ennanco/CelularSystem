package Main;

import java.io.IOException;
import java.util.ArrayList;

import util.LoadKEELFile;
import util.PatternSet;
import util.TrainPattern;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterionFactory;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.GenericGenesEvaluationCriterion;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.individual.IndividualFactory;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.population.PopulationFactory;
import es.udc.tic.efernandez.geneticalgorithm.variable.GenericGeneVariable;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

public class MainProgramGeneric {
	
	
	public static void main( String[] args){
		
		
		// Read 10 files that are in the standard input
		
		PatternSet[] patterns = new PatternSet[10];
		
		for (int i = 0; i < 10; i= i+2){
			try {
				patterns[i] = LoadKEELFile.readFile(args[i]);
				patterns[i+1] = LoadKEELFile.readFile(args[i+1]);
			} catch (IOException e) {
				System.out.println("El fichero no se encuentra");
				e.printStackTrace();
			}	
		}
		
		//Prepare the Variable
		
		ArrayList<String> values = new ArrayList<String>();
		for(String s:patterns[0].getPossibleOutputs())
			values.add(s);
		for(String s:patterns[0].getInputNames())
			values.add(s);
		//XXX add sequence to invalidate the gene or to erase the section
		values.add("AA");
		values.add("AA");
		values.add("AA");
		values.add("");
		GenericGeneVariable.setVALUES(values.toArray(new String[1]));
		GenericGeneVariable.SIZE_PROMOTERS = Integer.parseInt(args[10]);
		GenericGeneVariable.SIZE_INHIBITORS = Integer.parseInt(args[10]);
		
		//Genetic Algorithm execution

		int populationSize = 200;
		int percentOvercross = 90;
		int percentMutation = 10;
		int initialVariables = 30;
		int numberGeneration= 2000;
		Population p;
		
		GAResult[] results = new GAResult[patterns.length/2];
		
		for(int indice_CV = 0; indice_CV < patterns.length; indice_CV = indice_CV + 2){			
		//Prepare populations and execute GA
			 p = populationInicialization(patterns[indice_CV].getPattern(), 
					populationSize, initialVariables);
			results[indice_CV/2]= GATrainTest.execute(patterns[indice_CV].getPattern(),
					patterns[indice_CV+1].getPattern(), p, numberGeneration, percentOvercross, percentMutation);
			// Test as Train and Train as Test
//			 p = populationInicialization(patterns[indice_CV+1].getPattern(), 
//						populationSize, initialVariables);
//				results[indice_CV+1]= GATrainTest.execute(patterns[indice_CV+1].getPattern(),
//						patterns[indice_CV].getPattern(), p, numberGeneration, percentOvercross, percentMutation);
		}
				
		// Print the 10 tests
		
		
		for(int i = 0; i < results.length; i++){
			System.out.println("NUMBER STEP: "+i+"\n");
			System.out.println("Best Fitness "+ results[i].getMinTrainTrainValue()+" Test "+
					results[i].getMinTrainTestValue()+"\n");
			System.out.println("Train "+results[i].getMinTrainTrainSerie()+"\n");
			System.out.println("Test "+results[i].getMinTrainTestSerie()+"\n");
			System.out.println("Best Individual" + results[i].getMinTrainIndividual()+"\n\n");
			System.out.println("BestTest Fitness "+ results[i].getMinTestTrainValue()+" Test "+
					results[i].getMinTestTestValue()+"\n");
			System.out.println("Train "+results[i].getMinTestTrainSerie()+"\n");
			System.out.println("Test "+results[i].getMinTestTestSerie()+"\n");
			System.out.println("Best Individual" + results[i].getMinTestIndividual()+"\n\n");
		}
		
		double acum_train = 0;
		int trainNumber = 0;
		int trainMatch = 0;
		double acum_test = 0;
		int testNumber = 0;
		int testMatch = 0;
		char temp;
		for(GAResult r:results){
			acum_train += r.getMinTestTrainValue();
			for(int i = 0; i < r.getMinTestTrainSerie().length();i++){
				temp = r.getMinTestTrainSerie().charAt(i);
				if( temp == '0'){
					trainNumber++;
				} else if(temp == '1'){
					trainNumber++; trainMatch++;
				}
			}
			acum_test += r.getMinTestTestValue();
			for(int i = 0; i < r.getMinTestTestSerie().length();i++){
				temp = r.getMinTestTestSerie().charAt(i);
				if( temp == '0'){
					testNumber++;
				} else if(temp == '1'){
					testNumber++; testMatch++;
				}
			}
		}
		
		// Print the average Test value
		System.out.println("CV Train avg = "+acum_train/results.length+" % avgmatches = "+(trainMatch+0.0)/trainNumber +"\n");
		System.out.println("CV Test avg = "+acum_test/results.length+" % avgmatches = "+(testMatch+0.0)/testNumber+"\n");
		
	}

	/**
	 * This method will subdivided the set of patterns pls in two sub set for the Cross-Validation
	 * @param pls All the patterns
	 * @param train The 1st subset of patterns
	 * @param test The second subset which can have 1 pattern less than the first
	 */
	
	
	private static Population populationInicialization(TrainPattern[] set,int populationSize, int initialVariables){
		GenericGenesEvaluationCriterion.setPatterns(set);
		GenericGenesEvaluationCriterion ec = (GenericGenesEvaluationCriterion) EvaluationCriterionFactory.
												getInstance();
		
		Population p = PopulationFactory.newInstance();
		Variable[] variables;
		Individual individual;
		for(int i = 0; i < populationSize;i++){
			variables = new Variable[initialVariables];
			for(int j = 0; j < initialVariables; j++){
				variables[j] = VariableFactory.getInstance();
				variables[j].randomInicialize();
			}
			individual = IndividualFactory.getInstance(variables);
			p.setCriterion(ec.isRisingOrder());
			p.add(individual, ec.evaluation(individual));
		}
		
		return p;

	}
		
}
