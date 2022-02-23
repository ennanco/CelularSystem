/*
 * Created on 08-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.algorithm;

import es.udc.tic.efernandez.geneticalgorithm.population.Population;

/**
 * @author Enrique Fernández Blanco
*/
public interface GeneticAlgorihtm {
    
    public Population getPopulation();
    
    public void setPopulation(Population population);

    public Population cross(float percentage);
    
    public Population mutation(float percentage);
    
    public Population selection(int numberOfIndividuals);
    
}
