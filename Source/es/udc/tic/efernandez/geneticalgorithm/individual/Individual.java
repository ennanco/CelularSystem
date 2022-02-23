/*
 * Created on 08-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.individual;

import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;

/**
 * @author Enrique Fernández Blanco
 */
public interface Individual {
	
	public static final int MAX_DNA_LENGTH = 50;
	
    
    public Variable get(int index);
    
    public void setVariables(Variable[] variable);
    
    public Individual[] cross(Individual indivial, float percentage);
    
    public Individual[] cross(Individual individual);
    
    public Individual mutation();
    
    public int numberOfVariables();

}
