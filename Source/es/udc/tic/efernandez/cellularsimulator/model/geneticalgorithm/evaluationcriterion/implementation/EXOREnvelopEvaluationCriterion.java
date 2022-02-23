package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.implementation;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.EnviromentFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.tissue.TissueFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterion;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

public class EXOREnvelopEvaluationCriterion implements EvaluationCriterion{
	private static final boolean[][] template = {{ true, true, true, true, true }, 
												 { true, true, true, true, true },
												 { true, true, true, true, true }, 
												 { true, true, true, true, true },
												 { true, true, true, true, true } };

	public TestContainer evaluation(Individual individual) {
		DNA dna = IndividualToDNA.DNAIndividualToDNA(individual);
		Tissue tissue = TissueFactory.newInstance(
						GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
						GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN),
						dna, EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
				        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));

		for (int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++) {
			tissue.iteration();
		}

		return null;//criterion(tissue) + ((individual.numberOfVariables() > 0) ? 
				//GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.MINIMIZATION_NUMBER_PARAMETER)* dna.numberGenes()
				//: 200);
	}

	private float criterion(Tissue tissue) {

		int lowestRow = Integer.MAX_VALUE;
		int highestRow = Integer.MIN_VALUE;
		int lowestColumn = Integer.MAX_VALUE;
		int highestColumn = Integer.MIN_VALUE;
		int numberCells = 0;

		for (int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++) {
			for (int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++) {
				if ((tissue.get(i, j) != null) || !tissue.getEnviroment().valid(i,j)) {
					numberCells++;
					lowestRow = (i < lowestRow) ? i : lowestRow;
					lowestColumn = (j < lowestColumn) ? j : lowestColumn;
					highestRow = (i > highestRow) ? i : highestRow;
					highestColumn = (j > highestColumn) ? j : highestColumn;
				}
			}
		}

		int centroidRow = lowestRow + (highestRow - lowestRow)/2;
		int centroidColumn = lowestColumn + (highestColumn - lowestColumn)/2;
		int error = (numberCells <= 0) ? 400 : 0;

		// check the template centered into the centroid of the cells
		int checkPositionRow = 0;
		int checkPositionColumn = 0;
		int startPositionRow = (centroidRow - template.length/2);
		int startPositionColumn = 0; // this position has to be calculated dinamicaly
		boolean isCell = false;

		for (int i = 0; i < template.length; i++) {
			startPositionColumn = (centroidColumn - template[i].length / 2);
			for (int j = 0; j < template[i].length; j++) {
				checkPositionRow = startPositionRow + i;
				checkPositionColumn = startPositionColumn + j;
				if (!tissue.validPosition(checkPositionRow, checkPositionColumn)
					  && tissue.getEnviroment().valid(checkPositionRow,checkPositionColumn))
					error++;
				else {
					isCell = (tissue.get(checkPositionRow, checkPositionColumn) != null) || !tissue.getEnviroment().valid(checkPositionRow,checkPositionColumn);
					numberCells -= (isCell) ? 1 : 0;
					error += (template[i][j] == isCell) ? 0 : 1;
				}
			}
		}

		return error + numberCells + 0.01F * (Math.abs(centroidRow - GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW)/2) 
				+ Math.abs(centroidColumn - GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)/2 - 2));
	}

	public boolean isRisingOrder() {
		return true;
	}

}
