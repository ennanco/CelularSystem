/*
 * Created on 15-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.population;

import es.udc.tic.efernandez.geneticalgorithm.util.configurationmanager.ConfigurationManager;

/**
 * @author Enrique Fernández Blanco
 */
public class PopulationFactory {

	private static String POPULATION_CLASS_NAME_PARAMETER="PopulationFactory/populationClassName";
    public static Population newInstance(){
    	
    	String populationClassName = ConfigurationManager.getParameter(POPULATION_CLASS_NAME_PARAMETER);
    	Class populationClass;
    	try {
			populationClass= Class.forName(populationClassName);
	    	return (Population)populationClass.newInstance();
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
