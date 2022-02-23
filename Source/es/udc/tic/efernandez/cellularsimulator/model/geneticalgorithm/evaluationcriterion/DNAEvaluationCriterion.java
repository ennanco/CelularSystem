package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion;

import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterion;

public interface DNAEvaluationCriterion extends EvaluationCriterion {
	
	public void registerTemplate(boolean[][] template);
	
	public boolean[][] getTemplate();
	
	public void registerTraining(FileTraining training);
	
	public FileTraining getTraining();

}
