/*
 * Created on 09-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.population;

import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Enrique Fernández Blanco
 */
public class BasicPopulationImpl implements Population{

    private ArrayList<Individual> members;
    private ArrayList<Float> evaluation;
    private boolean rising;
    public BasicPopulationImpl(){
        super();
        members = new ArrayList<Individual>();
        evaluation = new ArrayList<Float>();
        rising = false;
    }
    
    public BasicPopulationImpl(Individual[] members, Float[] testContainer, boolean rising){
        this();
        this.rising = rising;
        for(int i = 0; i < members.length; i++)
            add(members[i],testContainer[i]);
    }

    public Individual get(int index) {
        return members.get(index);
    }
    
    public float getEvaluation(int index){
        return (evaluation.get(index));
    }
    
    public Float getTests (int index){
    	return evaluation.get(index);
    }

    public float getInverseEvaluation(int index){
        return  1.0F/((evaluation.get(index))+1.0F);
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
    
    public void add(Individual individual, float testContainer) {
        int position = 0;
        boolean notFound = true;
        for(int i = 0; i < evaluation.size() && notFound; i++){
            if( rising && evaluation.get(i) >= testContainer){
                	notFound = false;
                    position = i;
            } else if(!rising && evaluation.get(i) <= testContainer ){
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
       return members.remove(index);
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
    
    public int size(){
    	return members.size();
    }
}
