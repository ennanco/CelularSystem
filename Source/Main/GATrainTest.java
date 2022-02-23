package Main;

import java.util.HashMap;

import util.TrainPattern;
import es.udc.tic.efernandez.DNA.DNA;
import es.udc.tic.efernandez.DNA.gene.Gene;
import es.udc.tic.efernandez.DNA.gene.Protein;
import es.udc.tic.efernandez.geneticalgorithm.algorithm.GeneticAlgorihtm;
import es.udc.tic.efernandez.geneticalgorithm.algorithm.RouletteSelectionParentReplacementGeneticAlgorithm;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.GenericGenesEvaluationCriterion;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;

public class GATrainTest {



	public static GAResult execute(TrainPattern[] train, TrainPattern[] test,
			Population population, int numberGeneration, int percentOvercross, 
			int percentMutation){
				
		GeneticAlgorihtm ga = new RouletteSelectionParentReplacementGeneticAlgorithm(population);

		double minTrainTestValue = Double.MAX_VALUE; // valor minimo del test durante el entrenamiento
		double minTrainTrainValue = Double.MAX_VALUE; // valor mínimo del fitness durante el entrenamiento
		double minTestTestValue=Double.MAX_VALUE; // valor minimo del test con el mejor test
		double minTestTrainValue=Double.MAX_VALUE; // valor fitness entrenamiento con el mejor trest
		String minTrainIndividual = ""; // transcripción del mejor individuo en train
		String minTestIndividual=""; // transcripción mejor individuo en test
		String minTestTrainSerie=""; // serie de aciertos fallos
		String minTestTestSerie=""; // serie de aciertos fallos
		String minTrainTrainSerie=""; // serie de aciertos fallos
		String minTrainTestSerie=""; // serie de aciertos fallos
		
		
		for(int i = 0; i < numberGeneration; i++){
			ga.cross(percentOvercross);
			ga.mutation(percentMutation);
			minTrainTrainValue = ga.getPopulation().getEvaluation(0);
			minTrainTestValue = GenericGenesEvaluationCriterion.Test(ga.getPopulation().get(0), test);
			minTrainTrainSerie = successPatterns(train,ga.getPopulation().get(0));
			minTrainTestSerie = successPatterns(test,ga.getPopulation().get(0));

			if(minTestTestValue > minTrainTestValue){
				minTestTestValue = minTrainTestValue;
				minTestTrainValue = minTrainTrainValue;
				minTestTrainSerie = minTrainTrainSerie;
				minTestTestSerie = minTrainTestSerie;
				minTestIndividual = getDNA(ga.getPopulation().get(0));
			}
		}
		
		minTrainIndividual = getDNA(ga.getPopulation().get(0));
		
		double average = 0;
		double stddv = 0;
		for (int i = 0; i <ga.getPopulation().size(); i++){
			average += ga.getPopulation().getEvaluation(i);
		}
		average = average/ga.getPopulation().size();

		for (int i = 0; i <ga.getPopulation().size(); i++){
			stddv += Math.pow(ga.getPopulation().getEvaluation(i)-average, 2);
		}
		
		stddv = Math.sqrt(stddv/ga.getPopulation().size());
		
		return new GAResult(minTrainTrainValue, minTrainTestValue, minTrainTrainSerie,minTrainTestSerie,minTrainIndividual,
				minTestTrainValue, minTestTestValue,minTestTrainSerie,minTestTestSerie, minTestIndividual,average,stddv);
	}
	
		
		
		private static String successPatterns(TrainPattern[] p, Individual i){
			String toReturn = "";
			DNA dna = GenericGenesEvaluationCriterion.convert(i);
			for (int index = 0; index < p.length; index++){
				Protein protein = null;
				HashMap<String,Double> output = new HashMap<String, Double>();
				for(Gene g : dna.getGenes()){
					protein = g.transcript(p[index].getInputs());
					
					if(protein != null){
						String key = protein.getSequence();
						if(output.get(protein.getSequence()) != null){
							output.put(key, Math.max(output.get(key), protein.getValue()));
						} else{
							output.put(key, protein.getValue());								
						}
					}
				}
				
				Double maxValue = 0.0;
				boolean repeated = false;
				String maxValueSequence = null;
				for(String key : output.keySet()){
					if(maxValue < output.get(key)){
						maxValue = output.get(key);
						maxValueSequence = key;
						repeated = false;
					} else if (maxValue == output.get(key)){
						repeated = true;
					}
				}
				String desiredOutput = p[index].getOutput();
				Double desiredOutputValue = output.get(desiredOutput);
					if(output.size()==0){
						toReturn+= "0 ";
					}
					if(desiredOutputValue == null || desiredOutputValue == 0.0)
						toReturn += "0 ";
					else if(!desiredOutput.equals(maxValueSequence)||repeated)
						toReturn += "0 ";
					else
						toReturn += "1 ";
			}
			return toReturn;
		}
		
		private static String getDNA(Individual individual){
			String toReturn = "";
			for(int i = 0; i < individual.numberOfVariables(); i++){
				toReturn += individual.get(i);
			}
			return toReturn;
		}

	
}
