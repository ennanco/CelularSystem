/*
 * Created on 30-ago-2005
 */
package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.implementation;

import es.udc.tic.efernandez.cellular.enviroment.EnviromentFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.tissue.TissueFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterion;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

/**
 * @author Enrique Fernández Blanco
 */
public class CrossDNAEvaluationCriterion implements EvaluationCriterion {


    public TestContainer evaluation(Individual individual){
        Tissue tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), IndividualToDNA.DNAIndividualToDNA(individual),
	            EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
	            		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
	    
	    for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
	    }

    	return null;//criterion(tissue);
    }

	private float criterion(Tissue tissue){
	    int numberOfCells = 0;
	    int lowestRow = Integer.MAX_VALUE;
	    int highestRow = Integer.MIN_VALUE;
	    int lowestColumn = Integer.MAX_VALUE;
	    int highestColumn = Integer.MIN_VALUE;
	    
	    for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++){
	        for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++){
	            if(tissue.get(i,j)!= null){
	                numberOfCells++;
	                lowestRow = (i < lowestRow)? i: lowestRow;
	                lowestColumn = (j < lowestColumn)? j: lowestColumn;
	                highestRow = (i > highestRow)? i: highestRow;
	                highestColumn = (j > highestColumn)? j: highestColumn;
	            }
	        }
	    }
	    
	    int centroidRow = lowestRow + (highestRow-lowestRow)/2;
	    int centroidColumn = lowestColumn + (highestColumn-lowestColumn)/2;
	    //eliminate the individuals with no cells
	    float toReturn = (numberOfCells > 0)?
	    		Math.abs(numberOfCells - 9) + 
	            Math.abs(( highestRow - lowestRow ) - ( highestColumn - lowestColumn )) +
	            Math.abs(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW)/2 - centroidRow) + 
	            Math.abs(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)/2 - centroidColumn)+
	            Math.abs(4-highestColumn+lowestColumn)+ Math.abs(4-highestRow+lowestRow)
	            : 170;
	            
	    for(int i = lowestRow; i <= highestRow; i++)
	    	for(int j = lowestColumn; j <= highestColumn; j++){
	    		if((i != centroidRow) && (j != centroidColumn) && (tissue.get(i,j)!= null))
	    				toReturn++;
	    		if((i == centroidRow) || (j == centroidColumn))
	    			toReturn = (tissue.get(i,j) == null)? toReturn+1 : toReturn;
	    	}
	    
	    return toReturn;
	}

    public boolean isRisingOrder() {
       return true;
    }
}
