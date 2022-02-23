/*
 * Created on 15-ago-2005
 */
package es.udc.tic.efernandez.cellular.tissue;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.util.configurationmanager.ConfigurationManager;

/**
 * @author Enrique Fernández Blanco
 */
public class TissueFactory {
	private static String TISSUE_CLASS_NAME_PARAMETER="TissueFactory/tissueClassName";

	/**
     * This operation creates a new Tissue implementation
     * @return a new instance of an Tissue implementation
     */
    public static Tissue newInstance(int rows, int columns,DNA dna, Enviroment enviroment){
    	
    	String TissueClassName = ConfigurationManager.getParameter(TISSUE_CLASS_NAME_PARAMETER);
    	Class TissueClass;
    	try {
			TissueClass = Class.forName(TissueClassName);
			Tissue toreturn = (Tissue) TissueClass.newInstance();
			toreturn.setParameters(rows,columns, dna, enviroment);
			return toreturn;
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