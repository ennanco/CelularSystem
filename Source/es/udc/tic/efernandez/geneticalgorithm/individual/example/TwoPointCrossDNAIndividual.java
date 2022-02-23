package es.udc.tic.efernandez.geneticalgorithm.individual.example;

import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

public class TwoPointCrossDNAIndividual implements Individual {

    private Variable[] variable;
	
	public TwoPointCrossDNAIndividual(){
		super();
	}
	
	public TwoPointCrossDNAIndividual(Variable[] variables){
		super();
		setVariables(variables);
	}
	
	public Variable get(int index) {
       if((variable!=null)&&(variable.length > index)){
            return variable[index];
        } else{
            return null;
        }
	}

	public void setVariables(Variable[] variable) {
    	int realLength = (variable.length < MAX_DNA_LENGTH)? variable.length : MAX_DNA_LENGTH;
        this.variable =  new Variable[realLength];
        for(int i = 0; i < realLength ; i++){
        	this.variable[i] =  variable[i];
        }		
	}
	
	public Individual[] cross(Individual indivial, float percentage) {
		return cross(indivial);
	}

	public Individual[] cross(Individual individual) {
		   Individual[] toReturn;
		   
		   float percentageFirst1 = 0.0F;
		   float percentageFirst2 = 0.0F;
		   float percentageSecond1 = 0.0F;
		   float percentageSecond2 = 0.0F;
		   
		   int cutPositionFirstParent1 = 0;
		   int cutPositionFirstParent2 = 0;
		   int cutPositionSecondParent1 = 0;
		   int cutPositionSecondParent2 = 0;
		   
		   int lengthSon1 = MAX_DNA_LENGTH;
		   int lengthSon2 = MAX_DNA_LENGTH;
		   int attemps = 0;
		   
		   while(((lengthSon1 >= MAX_DNA_LENGTH) || (lengthSon2 >= MAX_DNA_LENGTH)
				   ||(lengthSon1 <= 0) || (lengthSon2 <= 0))
				   &&(attemps < 5)){
			   percentageFirst1 = (float)Math.random();
			   percentageFirst2 = (float)Math.random();
			   percentageSecond1 = (float)Math.random();
			   percentageSecond2 = (float)Math.random();
		   
			   cutPositionFirstParent1 = (int)Math.floor(numberOfVariables() * percentageFirst1);
			   cutPositionFirstParent2 = cutPositionFirstParent1 + 
			   		(int)Math.floor((numberOfVariables() - cutPositionFirstParent1) * percentageFirst2);
			   cutPositionSecondParent1 = (int)Math.floor(individual.numberOfVariables() * percentageSecond1);
			   cutPositionSecondParent2 = cutPositionSecondParent1 + 
			   		(int)Math.floor((individual.numberOfVariables() - cutPositionSecondParent1 )* percentageSecond2);
			   
			   lengthSon1 = cutPositionFirstParent1 + (cutPositionSecondParent2 - cutPositionSecondParent1) 
			   			+ (numberOfVariables() - cutPositionFirstParent2);
			   lengthSon2 = cutPositionSecondParent1 + (cutPositionFirstParent2 -cutPositionFirstParent1)
			   			+ (individual.numberOfVariables() - cutPositionSecondParent2);
			   attemps++;
		   }
		   
		   //this code is to not enter in a infinite loop
		   
		   if(attemps >= 30){
			   cutPositionFirstParent1 = (int)Math.floor(numberOfVariables() * 0.5);
			   cutPositionFirstParent2 = numberOfVariables();
			   cutPositionSecondParent1 = (int)Math.floor(individual.numberOfVariables() * 0.5);
			   cutPositionSecondParent2 = individual.numberOfVariables();
	       
			   lengthSon1 = cutPositionFirstParent1 + (individual.numberOfVariables() - cutPositionSecondParent1);
			   lengthSon2 = cutPositionSecondParent1 + (numberOfVariables() - cutPositionFirstParent1);
			   
			   if((lengthSon1<=0)||(lengthSon2<=0)||(lengthSon1>MAX_DNA_LENGTH)||(lengthSon2>MAX_DNA_LENGTH)){
				   toReturn = new Individual[]{this,individual};
				   return toReturn;
			   }
		   }
		   
		   
	       // the begining of the current individual(n+1) add the second part of the other individual
	       Variable[] newIndividual1 = new Variable[lengthSon1];
	       // the complementary of the other
	       Variable[] newIndividual2 = new Variable[lengthSon2];
	       
	       Variable[] cutPositions1 = variable[cutPositionFirstParent1].cross(individual.get(cutPositionSecondParent1));
	       Variable[] cutPositions2 = variable[cutPositionFirstParent2].cross(individual.get(cutPositionSecondParent2));
	       
	       // fill first individual variables
	       
	       // calculate the Cut point corresponding into the sons
	       int firstSonPoint1 = cutPositionFirstParent1;
	       int firstSonPoint2 = firstSonPoint1 + (cutPositionSecondParent2-cutPositionSecondParent1);
	       int secondSonPoint1 = cutPositionSecondParent1;
	       int secondSonPoint2 = (secondSonPoint1 + cutPositionFirstParent2-cutPositionFirstParent1);
	       
	       for(int i = 0; i < lengthSon1; i++){
	           if(i < firstSonPoint1){
	               newIndividual1[i] = variable[i];
	           }else if((i == firstSonPoint1) && (cutPositions1 != null)){
	        	   newIndividual1[i] = cutPositions1[0];
	           } else if(i < firstSonPoint2){
	        	   newIndividual1[i] = individual.get(cutPositionSecondParent1+(i-firstSonPoint1));
	           }else if((i == firstSonPoint2) && (cutPositions1 != null)){
	        	   newIndividual1[i] = cutPositions2[1];
	           }else{
	               newIndividual1[i] = variable[cutPositionFirstParent2 + (i - firstSonPoint2)];
	           }
	       }
	       
	       //fill the second
	       for(int i = 0; i < lengthSon2; i++){
	           if(i < secondSonPoint1){
	               newIndividual2[i] = individual.get(i);
	           }else if((i == secondSonPoint1) && (cutPositions1 != null)){
	        	   newIndividual2[i] = cutPositions1[1];
	           } else if(i < secondSonPoint2){
	        	   newIndividual2[i] = variable[cutPositionFirstParent1+(i-secondSonPoint1)];
	           }else if((i == secondSonPoint2) && (cutPositions1 != null)){
	        	   newIndividual2[i] = cutPositions2[0];
	           }else{
	               newIndividual2[i] = individual.get(cutPositionSecondParent2 + (i - secondSonPoint2));
	           }
	       }

	       //complete the variable toReturn
	       toReturn = new Individual[]{new TwoPointCrossDNAIndividual(newIndividual1), 
	               new TwoPointCrossDNAIndividual(newIndividual2)};
	         
	       return toReturn;
		   
	}

	public Individual mutation(){
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
                }else if(rand <= 0.1){
                	// copy the gene
                	newIndividual[i++] = variable[j];
                	//duplicate a part of the gene
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
        
        toReturn = new TwoPointCrossDNAIndividual(newIndividual);
        return toReturn;
	}

	public int numberOfVariables() {
		return (variable!=null)? variable.length : 0;
	}

}
