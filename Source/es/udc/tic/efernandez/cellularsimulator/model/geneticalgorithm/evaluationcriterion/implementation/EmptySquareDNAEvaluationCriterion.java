/*
 * Created on 12-sep-2005
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
public class EmptySquareDNAEvaluationCriterion implements EvaluationCriterion {

    public TestContainer evaluation(Individual individual) {
        Tissue tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), IndividualToDNA.DNAIndividualToDNA(individual), 
	            EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
	            		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
	    
	    for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
	    }
	    
        return null; //criterion(tissue, individual);
    }

    private float criterion(Tissue tissue, Individual individual) {
	    int numberOfCells = 0;
	    int lowestRow = Integer.MAX_VALUE;
	    int highestRow = Integer.MIN_VALUE;
	    int lowestColumn = Integer.MAX_VALUE;
	    int highestColumn = Integer.MIN_VALUE;
	    int high;
	    int wide;
	    
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
	    
	    float value = 0.0F;
	    if (numberOfCells ==0){ 
	    	return (value = 400);
//	    	value += 200;
	    }

	    
	    high = highestRow - lowestRow;
	    wide =highestColumn -lowestColumn;
	    
	    
	    value = Math.abs(16 - numberOfCells) + Math.abs( high - wide)
	    		+ Math.abs(4 - high) + Math.abs(4 - wide)
	    		+ Math.abs(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW)/2 - (lowestRow + high/2))
	    		+ Math.abs(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)/2 - (lowestColumn + wide/2)) ;//+
	    		//GlobalVariables.MINIMIZATION_NUMBER_PARAMETER * Math.abs(individual.numberOfVariables());
	    
	    int numberNeighbour;
	    for(int i = lowestRow; i <= highestRow; i++)
	        for(int j = lowestColumn; j <= highestColumn; j++){
	        	if(tissue.get(i,j) != null){
	        		numberNeighbour = 0;
	        		numberNeighbour = tissue.get(i - 1, j)!=null? numberNeighbour+ 1 : numberNeighbour;
//	        		numberNeighbour = tissue.get(i - 1, j + 1)!=null? numberNeighbour+ 1 : numberNeighbour;
	        		numberNeighbour = tissue.get(i , j + 1)!=null? numberNeighbour+ 1 : numberNeighbour;
//	        		numberNeighbour = tissue.get(i + 1, j + 1)!=null? numberNeighbour+ 1 : numberNeighbour;
	        		numberNeighbour = tissue.get(i + 1, j)!=null? numberNeighbour+ 1 : numberNeighbour;
//	        		numberNeighbour = tissue.get(i + 1,j - 1)!=null? numberNeighbour+ 1 : numberNeighbour;
	        		numberNeighbour = tissue.get(i ,j - 1)!=null? numberNeighbour+ 1 : numberNeighbour;
//	        		numberNeighbour = tissue.get(i - 1,j - 1)!=null? numberNeighbour+ 1 : numberNeighbour;
	        		if((i == lowestRow)||(i == highestRow)||(j == lowestColumn)||(j == highestColumn))
	        			value += Math.abs(numberNeighbour -2);
	        		else 
	        			value += numberNeighbour;
	        	}
	        }	    
        return value;
    }

    public boolean isRisingOrder() {
        return true;
    }

}
