/*
 * Created on 22-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion;

import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

/**
 * @author Enrique Fernández Blanco
 */
public interface EvaluationCriterion {

    public TestContainer evaluation(Individual individual);
    
    public boolean isRisingOrder();
}
