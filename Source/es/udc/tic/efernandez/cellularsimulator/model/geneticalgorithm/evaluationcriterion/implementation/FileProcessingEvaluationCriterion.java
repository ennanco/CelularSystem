package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.implementation;

import java.awt.Point;

import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellular.cell.StaticCell;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.EnviromentFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.tissue.TissueFactory;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.enviroment.GradientEnviromentWrapper;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.BasicGradient;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.Gradient;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test.BasicTest;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;

public class FileProcessingEvaluationCriterion implements
		DNAEvaluationCriterion {

	private static boolean[][] template ={{true,true,true,true,true},
		{true,true,true,true,true},
		{true,true,true,true,true},
		{true,true,true,true,true},
		{true,true,true,true,true}
	};

	private static FileTraining training = null;
	private static String [] proteinSequences= null;
	
	private BasicTest test;
	private ParametersMap parameters = new ParametersMap();
	private StoreTissueParameters proteinActivity;
	private TestContainer testContainer;

	public TestContainer evaluation(Individual individual) {
		
		testContainer = new TestContainer();
		DNA dna = IndividualToDNA.DNAIndividualToDNA(individual);
		float fitness = 0.0F;
		float fitnessEscenario = 0.0F;
		Tissue tissue;
		GradientEnviromentWrapper enviroment;
		ReceptorCell receptor;
		Gradient gradient;
		Cell cell;		

		if(proteinSequences == null){
			proteinSequences= new String[(Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE)];

			for( int i = 0;  i < (Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
				proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)parameters.getParameters().get(ParametersMap.PROTEINE_MIN_LENGTH));
			}
		}
		
    	if(training != null){
			for(int i =0; i < training.getNumberPatterns(); i++){
				enviroment = new GradientEnviromentWrapper(EnviromentFactory.getInstance(
						GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
						GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
				tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
						GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), 
						dna, enviroment);
	    	  
				//añades los individuos y los receptores y se cargan las proteinas de cada receptor(salidas deseadas)
				for(int j=0; j < training.getNumberIndividuals(); j++){
					cell = new StaticCell(training.getIndividuals()[j].x, training.getIndividuals()[j].y,dna,tissue,0);
					tissue.set(training.getIndividuals()[j].x, training.getIndividuals()[j].y, cell);    		    		 
				}
				for(int j=0; j < training.getNumberReceptors() ; j++){
					receptor = new ReceptorCell(training.getReceptors()[j].x, training.getReceptors()[j].y, tissue, 0);
					tissue.set(training.getReceptors()[j].x, training.getReceptors()[j].y, receptor);
				}
				//añdes los gradientes de los que esta compuesto el patron
	
				for(int j=0; j<training.getGradientsPattern(); j++){
					gradient = new BasicGradient(training.getPatterns()[i].getGradients()[j].getProtein(),
							new Point(training.getPatterns()[i].getGradients()[j].getX(), training.getPatterns()[i].getGradients()[j].getY()),
							training.getPatterns()[i].getGradients()[j].getRadio(), training.getPatterns()[i].getGradients()[j].getInit());
					enviroment.addGradient(gradient);
				}
				proteinActivity = new StoreTissueParameters();
				
				for(int j = 0; j< GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); j++){
					tissue.iteration();
//					proteinActivity.storeProteins(tissue);
				}
				proteinActivity.storeProteins(tissue);
				

				fitnessEscenario=criterion(tissue, i);
				test = new BasicTest(Integer.toString(i), tissue, enviroment, proteinActivity, fitnessEscenario, 
						GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
				fitness=fitness+fitnessEscenario;
				testContainer.add(test);
			} 
			
			if (fitness <= 1){
				System.out.println(" O");
			}
			fitness = fitness + ((dna.numberGenes() > 0) ? 
					(GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.MINIMIZATION_NUMBER_PARAMETER)
					* dna.numberGenes())	: 200);

			testContainer.setFitness(fitness);

			return testContainer;
    	}else
    		return null;
	}

	private float criterion(Tissue tissue, int numberPatron){
		float fitness = 0.0F;
//		Point origin;
		ReceptorCell receptor = null;
		int maxManhattan = GlobalVariables.getEvaluationParameter(GlobalVariables.PROTEINE_MIN_LENGTH)+1;
		int minManhattan = maxManhattan;
		
		for(int i=0; i < training.getNumberReceptors(); i++){
			receptor = (ReceptorCell) tissue.get(training.getReceptors()[i].x, training.getReceptors()[i].y);
			for( int j = 0;  j < proteinSequences.length; j++){	
				minManhattan = maxManhattan;
				for(int k=0; k < training.getNumberOutputsPattern(); k++){
					minManhattan = Math.min(minManhattan, d_manhattan(proteinSequences[j],training.getoutputs()[numberPatron][k]));
				}
				// dividir entre deseadas y no
				if(minManhattan == 0){
						if(!Float.isNaN(receptor.concentration(proteinSequences[j], false)) &&
									receptor.concentration(proteinSequences[j], false)>0.0f ){
								fitness = fitness + 1.0f* (100.0f-receptor.concentration(proteinSequences[j], false));
						} else{
							fitness = fitness + 500.0f;
						}
				} else{
						if(!Float.isNaN(receptor.concentration(proteinSequences[j], false)) &&
									receptor.concentration(proteinSequences[j], false)>0.0f ){
							fitness = fitness + 1.0f*receptor.concentration(proteinSequences[j], false) * (minManhattan);
						} else{
							fitness = fitness + 0;
						}
				}
			}									
		}
		return fitness;
	}
	
	private int d_manhattan(String inProtein, String outProtein){
		int distancia=0;
		
		if(inProtein.length()!=outProtein.length())
			System.out.println("Error en la distancia manhattan");
		else
			for(int i=0;i<inProtein.length();i++){
				distancia=distancia+ ((inProtein.charAt(i)==(outProtein.charAt(i)))?0:1);
			}
		return distancia;
	}
	
	
	public boolean isRisingOrder() {
		return true;
	}

	public boolean[][] getTemplate() {
		return template;
	}

	public void registerTemplate(boolean[][] template) {
		if(template != null)
			registerTemp(template);
		
	}	
	
	private static void registerTemp(boolean[][] template){
		FileProcessingEvaluationCriterion.template = template;
	}
	
	public FileTraining getTraining(){
		return training;
	}
	
	public void registerTraining(FileTraining training){
		if(training!=null)
			registerTrain(training);
	}
	
	private static void registerTrain(FileTraining training){
		FileProcessingEvaluationCriterion.training = training;
	}
}
	