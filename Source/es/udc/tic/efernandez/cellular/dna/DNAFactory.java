/*
 * Created on 26-ago-2005
 */
package es.udc.tic.efernandez.cellular.dna;

import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.util.configurationmanager.ConfigurationManager;

/**
 * @author Enrique Fernández Blanco
 */
public class DNAFactory {
	
	private static String DNA_CLASS_NAME_PARAMETER="DNAFactory/DNAClassName";

    public static DNA newInstance(Gene[] genes){
    	
    	String DNAClassName = ConfigurationManager.getParameter(DNA_CLASS_NAME_PARAMETER);
    	Class DNAClass;
    	try {
			DNAClass = Class.forName(DNAClassName);
			DNA toreturn = (DNA) DNAClass.newInstance();
			for (DNAComponent i : genes)
				toreturn.addComponent(i);
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
    
    public static DNA newInstance(DNAComponent[] components){
    	String DNAClassName = ConfigurationManager.getParameter(DNA_CLASS_NAME_PARAMETER);
    	Class DNAClass;
    	try {
			DNAClass = Class.forName(DNAClassName);
			DNA toreturn = (DNA) DNAClass.newInstance();
			for (DNAComponent i:components)
				toreturn.addComponent(i);
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
