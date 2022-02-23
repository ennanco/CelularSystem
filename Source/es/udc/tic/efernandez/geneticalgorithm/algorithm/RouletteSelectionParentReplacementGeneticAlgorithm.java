/*
 * Created on 06-sep-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.algorithm;

import es.udc.tic.efernandez.geneticalgorithm.algorithm.GeneticAlgorihtm;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterion;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterionFactory;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;

/**
 * @author Enrique Fernández Blanco
 */
public class RouletteSelectionParentReplacementGeneticAlgorithm implements GeneticAlgorihtm {

    Population population;
    
    public RouletteSelectionParentReplacementGeneticAlgorithm(Population population) {
        super();
        this.population = population;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public Population cross(float percentage) {
        
        int numberOfCross = (int) Math.floor((percentage/100.0) * population.populace())/2;
        
        float select;

        int[][] parents = new int[numberOfCross][2];
        Individual[] nextGeneration = new Individual[population.populace()];
        Float[] nextValues = new Float[population.populace()];
        Individual[] sons;
        float accumulate;
        int pos;
        float sonEvaluation;
        EvaluationCriterion criterion = EvaluationCriterionFactory.getInstance();

        float sum = 0.0F;
        //add the inverse evaluations.
        for(int i = 0; i < population.populace(); i++){
            sum += criterion.isRisingOrder()? 
            		population.getInverseEvaluation(i) :population.getEvaluation(i);
         }
        
        //parents selection.
        float partial = 0.0F;
        for(int i = 0; i < numberOfCross; i++){
            for(int j = 0; j < parents[i].length; j++){
                accumulate = 0.0F;
                select = sum * (float)Math.random();
                pos = 0;
                partial =  criterion.isRisingOrder()? 
                		population.getInverseEvaluation(pos) :population.getEvaluation(pos);
                while((pos < population.populace()-1)&&(select >(accumulate + partial))){
                    accumulate += partial;
                    pos++;
                    partial = criterion.isRisingOrder()? 
                    		population.getInverseEvaluation(pos) :population.getEvaluation(pos);
                }
                parents[i][j] = pos;
            }
        }
        
        for(int i = 0; i < numberOfCross; i++){
            sons = population.get(parents[i][0]).cross(population.get(parents[i][1]));
            // sustitutions           
            for(int j = 0; j < sons.length; j ++){
            	sonEvaluation= evaluationCriterion(sons[j]);
                // parents remplacement 
                if(parents[i][0]!=0){
                	if((nextGeneration[parents[i][0]] == null)||(isWorse(nextValues[parents[i][0]], sonEvaluation))){
                		nextGeneration[parents[i][0]] = sons[j];
                		nextValues[parents[i][0]]= sonEvaluation;
                	}else if(parents[i][1]!=0){
                		if((nextGeneration[parents[i][1]] == null)||(isWorse(nextValues[parents[i][1]], sonEvaluation))){
                			nextGeneration[parents[i][1]] = sons[j];
                			nextValues[parents[i][1]]= sonEvaluation;
                		}
                	}

                }else if(parents[i][1]!=0){
                	if((nextGeneration[parents[i][1]] == null)||(isWorse(nextValues[parents[i][1]], sonEvaluation))){
                		nextGeneration[parents[i][1]] = sons[j];
                		nextValues[parents[i][1]]= sonEvaluation;
                	}
                }
            }
        }
        // prepare new population with elities strategy => not remplacement of best individual(0)
        for(int i = population.populace() -1; i >0 ; i--){
            if(nextGeneration[i] != null){
                population.remove(i);
            }
        }
        
        for(int i = 1; i < nextGeneration.length ; i++){
            if(nextGeneration[i] != null){
                population.add(nextGeneration[i],nextValues[i]);
            }
        }  
        
        return population;
    }

    public Population mutation(float percentage) {
        int numberOfMutans = (int) Math.ceil(population.populace()*percentage/100);
        Individual [] mutans = new Individual[population.populace()];
        int position;
        float acumulate;
        float select;
        EvaluationCriterion criterion = EvaluationCriterionFactory.getInstance();
        
        float sum = 0.0F;
        //elities strategy avoid best individual in mutations and inicialize counter mutations
        for(int i = 1; i < population.populace(); i++){
            sum += criterion.isRisingOrder()? 
            		population.getInverseEvaluation(i) :population.getEvaluation(i);
            mutans[i] = null;
         }
        //select mutans positions with elities strategy and do the mutation
        float partial = 0.0F;
        for(int i = 0; i < numberOfMutans; i++){
            acumulate = 0.0F;
            select = sum * (float)Math.random();
            position = 1;
            partial = criterion.isRisingOrder()? 
            		population.getInverseEvaluation(position) :population.getEvaluation(position);
            while((position < population.populace()) && (select >(acumulate + partial))){
                acumulate += partial;
                position++;
                partial = criterion.isRisingOrder()? 
                		population.getInverseEvaluation(position) :population.getEvaluation(position);
            }
            mutans[position] = (mutans[position]== null)?population.get(position).mutation():mutans[position].mutation();
        }
        
        // selected mutans to introduce.
        for(int i = mutans.length - 1; i >= 1; i--){
        	if(mutans[i] != null){
        		population.remove(i);
        	}
        }
        for(int i = 1; i < mutans.length; i++){
        	if(mutans[i] != null){
        		population.add(mutans[i], evaluationCriterion(mutans[i]));
        	}
        }
        return population;
   }

    public Population selection(int numberOfIndividuals) {
        if(numberOfIndividuals < population.populace()){
            for(int i= population.populace() - 1; i >= numberOfIndividuals ; i-- )
                population.remove(i);
        }
        return population;
    }

    private Float evaluationCriterion(Individual individual){
    	return EvaluationCriterionFactory.getInstance().evaluation(individual); 
    }
    
    private boolean isWorse(float parentEvaluation, float sonEvaluation){
    	boolean toReturn = false;
    	
    	if(EvaluationCriterionFactory.getInstance().isRisingOrder()){
    		toReturn = parentEvaluation > sonEvaluation;
    	} else{
    		toReturn = parentEvaluation < sonEvaluation;
    	}
    	
    	return toReturn;
    }

}
