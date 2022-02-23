/*
 * Created on 08-ago-2005
 */
package es.udc.tic.efernandez.geneticalgorithm.individual;

import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

/**
 * @author Enrique Fernández Blanco
 */
public class OnePointCrossDNAIndividual implements Individual {

    private Variable[] variable;
    
    public OnePointCrossDNAIndividual(){
    	super();
    }
    private OnePointCrossDNAIndividual(Variable[] variable){
    	super();
    	setVariables(variable);
    }
        
    public Variable get(int index) {
        if((variable!=null)&&(variable.length > index)){
            return variable[index];
        } else{
            return null;
        }
    }
 
    /*
     * This method is used by the factory to put the variables of the individual.
     */
    public void setVariables(Variable[] variable){
    	int realLength = (variable.length < MAX_DNA_LENGTH)? variable.length : MAX_DNA_LENGTH;
        this.variable =  new Variable[realLength];
        for(int i = 0; i < realLength ; i++){
        	this.variable[i] =  variable[i];
        }
    }
    
   public Individual[] cross(Individual individual, float percentage) {
       Individual[] toReturn;

       int cutPosition1 = (int)Math.floor(numberOfVariables() * percentage/100);
       int cutPosition2 = (int)Math.floor(individual.numberOfVariables() * percentage/100);
       
       // the begining of the current individual(n+1) add the second part of the other individual
       Variable[] newIndividual1 = 
           new Variable[cutPosition1 + (individual.numberOfVariables() - cutPosition2)];
       // the complementary of the other
       Variable[] newIndividual2 = 
           new Variable[cutPosition2 + (numberOfVariables() - cutPosition1)];
       
       // fill first individual variables
       for(int i = 0; i < newIndividual1.length; i++){
           if(i < cutPosition1){
               newIndividual1[i] = variable[i];
           }else{
               newIndividual1[i] = individual.get(cutPosition2 + i - cutPosition1);
           }
       }
       
       //fill the second
       for(int i = 0; i < newIndividual2.length; i++){
           if(i < cutPosition2){
               newIndividual2[i] = individual.get(i);
           }else{
               newIndividual2[i] = variable[cutPosition1 + i - cutPosition2];
           }
       }

       //complete the variable toReturn
       toReturn = new Individual[]{new OnePointCrossDNAIndividual(newIndividual1), 
               new OnePointCrossDNAIndividual(newIndividual2)};
         
       return toReturn;
   }
   
   public Individual[] cross(Individual individual){
	   
	   Individual[] toReturn;
	   
	   float percentage1 = 0.0F;
	   float percentage2 = 0.0F;
	   
	   int cutPosition1 = 0;
	   int cutPosition2 = 0;
	   
	   int lengthSon1 = MAX_DNA_LENGTH;
	   int lengthSon2 = MAX_DNA_LENGTH;
	   int attemps = 0;
	   
	   while(((lengthSon1 >= MAX_DNA_LENGTH) || (lengthSon2 >= MAX_DNA_LENGTH)
			   ||(lengthSon1 <= 0) || (lengthSon2 <= 0))
			   &&(attemps < 30)){
		   percentage1 = (float)Math.floor(Math.random()*100);
		   percentage2 = (float)Math.floor(Math.random()*100);
	   
		   cutPosition1 = (int)Math.floor(numberOfVariables() * percentage1/100);
		   cutPosition2 = (int)Math.floor(individual.numberOfVariables() * percentage2/100);
       
		   lengthSon1 = cutPosition1 + (individual.numberOfVariables() - cutPosition2);
		   lengthSon2 = cutPosition2 + (numberOfVariables() - cutPosition1);
		   attemps++;
	   }
	   
	   //this code is to not enter in a infinite loop
	   
	   if(attemps >= 30){
		   cutPosition1 = (int)Math.floor(numberOfVariables() * 0.5);
		   cutPosition2 = (int)Math.floor(individual.numberOfVariables() * 0.5);
       
		   lengthSon1 = cutPosition1 + (individual.numberOfVariables() - cutPosition2);
		   lengthSon2 = cutPosition2 + (numberOfVariables() - cutPosition1);
		   
		   if((lengthSon1<=0)||(lengthSon2<=0)||(lengthSon1>MAX_DNA_LENGTH)||(lengthSon2>MAX_DNA_LENGTH)){
			   toReturn = new Individual[]{this,individual};
			   return toReturn;
		   }
	   }
	   
	   
       // the begining of the current individual(n+1) add the second part of the other individual
       Variable[] newIndividual1 = new Variable[lengthSon1];
       // the complementary of the other
       Variable[] newIndividual2 = new Variable[lengthSon2];
       
       Variable[] cutPositions = variable[cutPosition1].cross(individual.get(cutPosition2));
       
       // fill first individual variables
       for(int i = 0; i < lengthSon1; i++){
           if(i < cutPosition1){
               newIndividual1[i] = variable[i];
           }else if((i == cutPosition1) && (cutPositions != null)){
        	   newIndividual1[i] = cutPositions[0];
           }else{
               newIndividual1[i] = individual.get(cutPosition2 + i - cutPosition1);
           }
       }
       
       //fill the second
       for(int i = 0; i < lengthSon2; i++){
           if(i < cutPosition2){
               newIndividual2[i] = individual.get(i);
           }else if((i == cutPosition2) && (cutPositions != null)){
        	   newIndividual2[i] = cutPositions[1];
           }else{
               newIndividual2[i] = variable[cutPosition1 + i - cutPosition2];
           }
       }

       //complete the variable toReturn
       toReturn = new Individual[]{new OnePointCrossDNAIndividual(newIndividual1), 
               new OnePointCrossDNAIndividual(newIndividual2)};
         
       return toReturn;
	   
   }
   
    public Individual mutation() {
        Individual toReturn;
        int index = (int)Math.floor((Math.random() * (variable.length -1))+0.5f);
        // beware with the length zero individuals. 
        if((index < 0)||(index >= variable.length)){
        	return this;
        }
        Variable selected = variable[index];
        
        float rand = (float)Math.random();
        Variable[] newIndividual;
        
        if(rand <= 0.1)
            // increase the number of variables in one
            newIndividual = new Variable[variable.length+1];
        else if((rand <= 0.2)&&(variable.length > 1))
            //decrease the number of variables in one.
            newIndividual = new Variable[variable.length-1];
        else
            //other changes.
            newIndividual = new Variable[variable.length];
        
        int i = 0;
        int j = 0;
        
        while( i < newIndividual.length){
            if(index != j){
                newIndividual[i] = variable[j];
                i++;
            }
            else{
                if(rand <= 0.05){
                    //copy the gene
                    newIndividual[i++] = variable[j];
                    // create a new aleatory gen.
                    newIndividual[i] = VariableFactory.getInstance();
                    newIndividual[i].randomInicialize();
                    i++;
                } else if(rand <= 0.1){
                	//duplicate a part of the gene
                	newIndividual[i++] = variable[j];
                	// the copy of the gen
                	newIndividual[i++] = variable[j].clone();
                } else if(rand <= 0.2){
                	if(variable.length <= 1)
                		newIndividual[i++] = variable[j];
                    //erase the variable
                } else{// if(rand > 0.2){
                	newIndividual[i] = selected.mutation();
                	i++;
                }
            }
            j++;
        }
        
        toReturn = new OnePointCrossDNAIndividual(newIndividual);
        return toReturn;
    }

   public int numberOfVariables() {
       
       return variable.length;
    }
	   
}
