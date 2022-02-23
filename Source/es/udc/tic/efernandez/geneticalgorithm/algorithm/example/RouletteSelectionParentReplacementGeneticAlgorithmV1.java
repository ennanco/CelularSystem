/*
 * Created on 06-sep-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.algorithm.example;

import es.udc.tic.efernandez.geneticalgorithm.algorithm.GeneticAlgorihtm;
import es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion.EvaluationCriterionFactory;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;

/**
 * @author Enrique Fernández Blanco
 */
public class RouletteSelectionParentReplacementGeneticAlgorithmV1 implements GeneticAlgorihtm {

    Population population;
    
    public RouletteSelectionParentReplacementGeneticAlgorithmV1(Population population) {
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
        //float randomPercentage = (float) Math.floor(Math.random()*100);
        
        float select;

        
        int[][] parents = new int[numberOfCross][2];
        Individual[] nextGeneration = new Individual[population.populace()];
        TestContainer[] nextValues = new TestContainer[population.populace()];
        Individual[] sons;
        float accumulate;
        int pos;
        float sonEvaluation;

        float[] inverse = new float[population.populace()];
        float sum = 0.0F;
        //standarize the evaluations.
        for(int i = 0; i < population.populace(); i++){
            inverse[i] = (float)1.0/((population.getEvaluation(i)+1.0F));
            sum += inverse[i];
         }

        //parents selection.
        for(int i = 0; i < numberOfCross; i++){
            for(int j = 0; j < parents[i].length; j++){
                accumulate = 0.0F;
                select = sum * (float)Math.random();
                pos = 0;
                while(select >(accumulate + inverse[pos])){
                    accumulate += inverse[pos];
                    pos++;
                }
                parents[i][j] = pos;
            }
        }
        
        for(int i = 0; i < numberOfCross; i++){
            sons = population.get(parents[i][0]).cross(population.get(parents[i][1]));
            // sustitutions           
            for(int j = 0; j < sons.length; j ++){
                sonEvaluation = evaluationCriterion(sons[j]).getFitness();
                // check if the son is greater then the parent and the remplecement selected, if this exists
                if((population.getEvaluation(parents[i][0]) >= sonEvaluation)&&
                ((nextGeneration[parents[i][0]] == null)||(nextValues[parents[i][0]].getFitness() > sonEvaluation))){
                    if(population.getEvaluation(parents[i][0]) < population.getEvaluation(parents[i][1])){
                        nextGeneration[parents[i][1]] = sons[j];
                        nextValues[parents[i][1]]= new TestContainer();
                        nextValues[parents[i][1]].setFitness(sonEvaluation);
                    } else{
                        nextGeneration[parents[i][0]] = sons[j];
                        nextValues[parents[i][0]]= new TestContainer();
                        nextValues[parents[i][0]].setFitness(sonEvaluation);
                    }
                        
                }else if((population.getEvaluation(parents[i][1]) >= sonEvaluation)&&
                    ((nextGeneration[parents[i][0]] == null)||(nextValues[parents[i][1]].getFitness() > sonEvaluation))){
                        nextGeneration[parents[i][1]] = sons[j];
                        nextValues[parents[i][1]]= new TestContainer();
                        nextValues[parents[i][1]].setFitness(sonEvaluation);
                }
            }
        }
        // prepare new population
        for(int i = population.populace() -1; i >=0 ; i--){
            if(nextGeneration[i] != null){
                population.remove(i);
            }
        }
        
        for(int i = 0; i < nextGeneration.length ; i++){
            if(nextGeneration[i] != null){
                population.add(nextGeneration[i],nextValues[i]);
            }
        }  
        
        return population;
    }

    public Population mutation(float percentage) {
        int numberOfMutants = (int) Math.ceil(population.populace()*percentage/100);
        int position;
        Individual genereted;
        for(int i = 0; i < numberOfMutants; i++){
            position = (int)Math.floor(population.populace()*Math.random());
            genereted = population.get(position).mutation();
            population.remove(position);
            population.add(genereted, evaluationCriterion(genereted));
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

    private TestContainer evaluationCriterion(Individual individual){
    	return EvaluationCriterionFactory.getInstance().evaluation(individual);
    }

}
