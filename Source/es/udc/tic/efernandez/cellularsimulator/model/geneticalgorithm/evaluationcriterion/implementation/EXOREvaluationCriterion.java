package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.implementation;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.enviroment.EnviromentFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.tissue.TissueFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test.BasicTest;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
//XXXimport es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

public class EXOREvaluationCriterion implements DNAEvaluationCriterion {

	
	private static boolean[][] template ={{true,true,true,true,true},
											{true,true,true,true,true},
											{true,true,true,true,true},
											{true,true,true,true,true},
											{true,true,true,true,true}
										};

	private TestContainer testContainer;
//XXX	private StoreTissueParameters storeTissueParameters;
	
	public TestContainer  evaluation(Individual individual) {
		testContainer = new TestContainer();
//XXX		storeTissueParameters = new StoreTissueParameters();
		Enviroment enviroment = EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)); 
		DNA dna = IndividualToDNA.DNAIndividualToDNA(individual);
        Tissue tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        										GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), 
        										dna, enviroment);
	    
	    for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
//XXX       storeTissueParameters.storeProteins(tissue);
	    }

//    	return criterion(tissue)+ ((individual.numberOfVariables()>0)?
//    					GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.MINIMIZATION_NUMBER_PARAMETER)*individual.numberOfVariables(): 200);
    	float evaluationValue = criterion(tissue);
    	
    	testContainer.add(new BasicTest("Test",tissue,enviroment,/* XXX storeTissueParameters*/null,evaluationValue,
    			GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION)));
    	
    	if((dna.numberGenes() > 0) && (individual.numberOfVariables()>=0.5*Individual.MAX_DNA_LENGTH)) 
				evaluationValue += GlobalVariables.getGeneticAlgorithmParameter(
											GlobalVariables.MINIMIZATION_NUMBER_PARAMETER)* dna.numberGenes();
		else if(dna.numberGenes() <= 0)
			evaluationValue +=200;
    	
    	testContainer.setFitness(evaluationValue);
    	
    	return testContainer;
	}
	
	private float criterion(Tissue tissue){

		int lowestRow = Integer.MAX_VALUE;
	    int highestRow = Integer.MIN_VALUE;
	    int lowestColumn = Integer.MAX_VALUE;
	    int highestColumn = Integer.MIN_VALUE;
	    int numberCells = 0;
	    
	    for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++){
	        for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++){
	            if(tissue.get(i,j)!= null){
	            	numberCells++;
	                lowestRow = (i < lowestRow)? i: lowestRow;
	                lowestColumn = (j < lowestColumn)? j: lowestColumn;
	                highestRow = (i > highestRow)? i: highestRow;
	                highestColumn = (j > highestColumn)? j: highestColumn;
	            }
	        }
	    }
	    
	    int centroidRow = lowestRow + (highestRow-lowestRow)/2;
	    int centroidColumn = lowestColumn + (highestColumn-lowestColumn)/2;
	    int error = (numberCells == 0)? 50 : 0;
	    
	    //check the template centered into the centroid of the cells
	    int checkPositionRow = 0;
	    int checkPositionColumn = 0;
	    int startPositionRow = (centroidRow - template.length/2);
	    int startPositionColumn = 0; //this position has to be calculated dinamically 
	    boolean isCell = false;
	    
	    for(int i = 0; i < template.length; i++){
	    	startPositionColumn = (centroidColumn - template[i].length/2);
	    	for(int j = 0; j < template[i].length; j++){
	    		checkPositionRow =  startPositionRow + i;
	    		checkPositionColumn =  startPositionColumn + j;
	    		if(!tissue.validPosition(checkPositionRow,checkPositionColumn+j))
	    			error++;
	    		else{
	    			isCell = tissue.get(checkPositionRow,checkPositionColumn) != null;
	    			numberCells -= (isCell)? 1: 0;
	    			error += (template[i][j] == isCell)? 0 : 1;
	    		}
	    	}
	    }
	    
	    float evaluation = 2.0F * error + numberCells + 0.10F*(Math.abs(centroidRow - GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW)/2)
	    		+ Math.abs(centroidColumn - GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)/2));

	    
	    return evaluation;
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
		EXOREvaluationCriterion.template = template;
	}
	
	public FileTraining getTraining(){
		return null;
	}
	
	public void registerTraining(FileTraining training){
		;
	}

}
