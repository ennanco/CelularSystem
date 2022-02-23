package es.udc.tic.efernandez.geneticalgorithm.variable;

import es.udc.tic.efernandez.geneticalgorithm.util.configurationmanager.ConfigurationManager;

public class VariableFactory {

	private static String VARIABLE_PARAMETER_NAME="VariableFactory/VariableClassName";
	
	private VariableFactory(){}
	/**
	 * This method returns an instance of a Variable, but beware because this instance can not be
	 * inicializated its data. 
	 * @return an instance of a Variable
	 */
    public static Variable getInstance(){
    	String evaluationCriterionClassName = ConfigurationManager.getParameter(VARIABLE_PARAMETER_NAME);
    	Class evaluationCriterionClass;
    	try {
			evaluationCriterionClass = Class.forName(evaluationCriterionClassName);
			return (Variable) evaluationCriterionClass.newInstance();
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
