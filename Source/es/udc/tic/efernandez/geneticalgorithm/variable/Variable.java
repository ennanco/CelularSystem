/*
 * Created on 08-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.variable;

/**
 * @author Enrique Fernández Blanco
 */
public interface Variable {
	
	public Variable mutation();
	
	public Variable[] cross(Variable crossVariable);
	
	public void randomInicialize();
	
	public Variable clone();

}
