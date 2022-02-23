/*
 * Created on 08-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.population;

import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Enrique Fernández Blanco
 */
public interface Population {
    
    public Individual get(int index);
    
    public float getEvaluation(int index);
    
    public float getInverseEvaluation(int index);
    
    public Collection getBetter(int number);
    
    public Collection getWorse(int number);
    
    public void add(Individual individual, float evaluation);

    public Individual remove( int index);
    
    public void setCriterion(boolean rising);
    
    public boolean getCriterion();
    
    public Iterator iterator();
    
    public int size();
    
    public int populace();
}
