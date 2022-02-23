/*
 * Created on 22-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion;

import es.udc.tic.efernandez.geneticalgorithm.util.configurationmanager.ConfigurationManager;

/**
 * @author Enrique Fernández Blanco
 */
public class EvaluationCriterionFactory {

	private static String EVALUATION_CRITERIO_PARAMETER_NAME="EvaluationCriterionFactory/evaluationCriterionClassName";
	
	private EvaluationCriterionFactory(){}
    public static EvaluationCriterion getInstance(){
    	String evaluationCriterionClassName = ConfigurationManager.getParameter(EVALUATION_CRITERIO_PARAMETER_NAME);
    	Class evaluationCriterionClass;
    	try {
			evaluationCriterionClass = Class.forName(evaluationCriterionClassName);
			return (EvaluationCriterion) evaluationCriterionClass.newInstance();
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
