package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion;

import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.util.configurationmanager.ConfigurationManager;

/**
 * @author Enrique Fernández Blanco
 */


public class DNAEvaluationCriterionFactory {

		private static String EVALUATION_CRITERIO_PARAMETER_NAME="EvaluationCriterionFactory/evaluationCriterionClassName";
		
		private DNAEvaluationCriterionFactory(){}
		
		public static DNAEvaluationCriterion getInstance(){
			String evaluationCriterionClassName = ConfigurationManager.getParameter(EVALUATION_CRITERIO_PARAMETER_NAME);
	    	Class evaluationCriterionClass;
	    	try {
				evaluationCriterionClass = Class.forName(evaluationCriterionClassName);
				DNAEvaluationCriterion evaluationCriterion = (DNAEvaluationCriterion) evaluationCriterionClass.newInstance();
				return evaluationCriterion;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;	
		}
		
	    public static DNAEvaluationCriterion getInstance(boolean[][] template, FileTraining training){
	    	String evaluationCriterionClassName = ConfigurationManager.getParameter(EVALUATION_CRITERIO_PARAMETER_NAME);
	    	Class evaluationCriterionClass;
	    	try {
				evaluationCriterionClass = Class.forName(evaluationCriterionClassName);
				DNAEvaluationCriterion evaluationCriterion = (DNAEvaluationCriterion) evaluationCriterionClass.newInstance();
				evaluationCriterion.registerTemplate(template);
				evaluationCriterion.registerTraining(training);
				return evaluationCriterion;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;    	
	    }
}
