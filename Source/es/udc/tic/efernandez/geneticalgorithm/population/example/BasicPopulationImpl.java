/*
 * Created on 09-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.population.example;

import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Enrique Fernández Blanco
 */
public class BasicPopulationImpl implements Population{

    private ArrayList members;
    private ArrayList evaluation;
    private boolean rising;
    public BasicPopulationImpl(){
        super();
        members = new ArrayList();
        evaluation = new ArrayList();
        rising = false;
    }
    
    public BasicPopulationImpl(Individual[] members, TestContainer[] testContainer, boolean rising){
        this();
        this.rising = rising;
        for(int i = 0; i < members.length; i++)
            add(members[i],testContainer[i]);
    }

    public Individual get(int index) {
        return (Individual)members.get(index);
    }
    
    public float getEvaluation(int index){
        return ((TestContainer)evaluation.get(index)).getFitness();
    }
    
    public TestContainer getScenaries (int index){
    	return ((TestContainer)evaluation.get(index));
    }

    public float getInverseEvaluation(int index){
        return  1.0F/(((TestContainer)evaluation.get(index)).getFitness()+1.0F);
    }

    public Collection getBetter(int number) {
        if(members.size()< number){
            return members;
        }else{
            return members.subList(0,number);
        }
    }    

    public Collection getWorse(int number) {
        if(members.size()< number){
            return members;
        }else{
            return members.subList(members.size() - number, members.size());
        }
    }
    
    public void add(Individual individual, TestContainer testContainer) {
        int position = 0;
        boolean notFound = true;
        for(int i = 0; i < evaluation.size() && notFound; i++){
            if( !rising && testContainer.compareTo((TestContainer)evaluation.get(i)) >= 0){
                	notFound = false;
                    position = i;
            } else if(rising && testContainer.compareTo((TestContainer)evaluation.get(i)) <= 0 ){
                notFound = false;
                position = i;
            }
        }
        if(notFound && !rising){
            members.add(members.size(),individual);
            evaluation.add(evaluation.size(),testContainer);            
        } else if(notFound && rising){
            members.add(members.size(),individual);
            evaluation.add(evaluation.size(),testContainer);
        } else{
            members.add(position,individual);
            evaluation.add(position,testContainer);
        }
    }

    public Individual remove(int index) {
        evaluation.remove(index);
       return (Individual)members.remove(index);
    }

    public void setCriterion(boolean rising){
        this.rising = rising;
    }
    
    public boolean getCriterion(){
    	return rising;
    }
    public Iterator iterator() {
        return members.iterator();
    }

    public int populace() {
        
        return members.size();
    }
}
