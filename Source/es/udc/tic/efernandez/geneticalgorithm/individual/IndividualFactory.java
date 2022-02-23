package es.udc.tic.efernandez.geneticalgorithm.individual;

import es.udc.tic.efernandez.geneticalgorithm.util.configurationmanager.ConfigurationManager;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;

/**
 * @author Enrique Fernández Blanco
 */
public class IndividualFactory {

		private static String INDIVIDUAL_PARAMETER_NAME="IndividualFactory/individualClassName";
		
		private IndividualFactory(){}
	    public static Individual getInstance(Variable[] variables){
	    	String evaluationCriterionClassName = ConfigurationManager.getParameter(INDIVIDUAL_PARAMETER_NAME);
	    	Class evaluationCriterionClass;
	    	try {
				evaluationCriterionClass = Class.forName(evaluationCriterionClassName);
				Individual individual =(Individual) evaluationCriterionClass.newInstance();
				individual.setVariables(variables);
				return individual;
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
