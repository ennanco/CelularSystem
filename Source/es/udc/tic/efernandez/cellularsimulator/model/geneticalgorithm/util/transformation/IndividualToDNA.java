/*
 * Created on 01-sep-2005
 */
package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation;

import java.util.ArrayList;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.dna.DNAFactory;
import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.gene.example.AdvanceGene;
import es.udc.tic.efernandez.cellular.gene.example.BasicGene;
import es.udc.tic.efernandez.cellular.operon.example.AdvanceOperon;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.DNAVariable;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.OperonProteinVariable;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.ProteinVariable;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;

/**
 * @author Enrique Fernández Blanco
 */
public class IndividualToDNA {
	//This class may be impplementated with Chain of responsability patern but searching a more efficient
	// implementation the calls to external method have been changued by internal calls
	// the chain must be implementated if you want to combine different variable types
    private IndividualToDNA(){}
    
    public static DNA DNAIndividualToDNA(Individual individual){
        if(individual == null)
            return null;
        DNA toReturn = null;
        if(individual.numberOfVariables() > 0 && individual.get(0) instanceof ProteinVariable)
        	toReturn = ProteinDNAIndividualToDNA(individual);
        else if(individual.numberOfVariables() > 0 && individual.get(0) instanceof OperonProteinVariable)
        	toReturn = OperonIndividualToDNA(individual);
        else
        	toReturn = DNAVariableIndividualToDNA(individual);
        return toReturn;
    }
    
    
    private static DNA DNAVariableIndividualToDNA(Individual individual){
        Gene[] gen = new BasicGene[individual.numberOfVariables()];
        DNAVariable var;
        for(int i = 0; i < individual.numberOfVariables(); i++){
            var = (DNAVariable) individual.get(i);
            gen[i] = new BasicGene(var.getGeneSequences(),var.getConstitutive().booleanValue(),
                  var.getActivePart(),var.getActivePercentage().floatValue());
        }
        return DNAFactory.newInstance(gen);   	
    }
    
    private static DNA ProteinDNAIndividualToDNA(Individual individual){
    	
    	int numberGenes = 0;
    	int endGen = 0;
    	int beginningGen = 0;
    	
    	for(int i = 0; i < individual.numberOfVariables(); i++){
    		if(!((ProteinVariable)individual.get(i)).isPromotor()){
    			numberGenes++;
    			beginningGen = endGen;
    			endGen = i;
    		}
    	}
    	
    	Gene[] gene = new Gene[numberGenes];
    	String[] activePart;
    	float[] percentages;
    	int count = numberGenes - 1;
    	int numberPromotors;
    	while (count >= 0){
    		//substract 1 for the promoters
    		numberPromotors = endGen-beginningGen-1;
    		numberPromotors = ( numberPromotors < 0 )? 0:numberPromotors;
    		activePart = new String[numberPromotors];
    		percentages = new float[numberPromotors];
    		for(int i = beginningGen+1; i < endGen; i++){
    			activePart[i - beginningGen-1] = ((ProteinVariable)individual.get(i)).getSequence();
    			percentages[i - beginningGen-1] = ((ProteinVariable)individual.get(i)).getPercentage();
    		}
    		// create the new gene
    		ProteinVariable var = (ProteinVariable)individual.get(endGen);
    		gene[count]= new AdvanceGene(var.getSequence(),var.isConstitutive(),activePart,percentages,var.getDirection());
//    			new BasicGene(var.getSequence(),var.isConstitutive(),
//                    activePart,var.getPercentage().floatValue());
    		//actualize the index for the next
    		endGen = beginningGen;
    		beginningGen--;
    		while(beginningGen >= 0 && ((ProteinVariable)individual.get(beginningGen)).isPromotor())
    			beginningGen--;
    		count--;
    	}
    	
    	return DNAFactory.newInstance(gene);
    }

	private static DNA OperonIndividualToDNA(Individual individual){
		ArrayList<DNAComponent> components = new ArrayList<DNAComponent>();
		ArrayList<Gene> genes = new ArrayList<Gene>();
		
		int begin = 0;
		int end = 0;
		
		for(int i = 0; i<individual.numberOfVariables(); i++){
			end = i;
			if(((OperonProteinVariable)individual.get(i)).isOperon()){
				if(!genes.isEmpty()){
					// search the promoters
					String[] promoters = new String[end - begin];
					float[] percentages = new float[end-begin];
					for(int j = 0; j < end - begin; j++){
						promoters[j] = ((OperonProteinVariable)individual.get(begin+j)).getSequence();
						percentages[j] = ((OperonProteinVariable)individual.get(begin+j)).getPercentage();
					}
					//create the operon
					OperonProteinVariable variable = (OperonProteinVariable) individual.get(end);
					int operonLength = genes.size() > variable.getOperonLength()?genes.size()-variable.getOperonLength():0;
					for(int j = 0; j<operonLength; j++)
						components.add(genes.get(j));
					
					// introduce the genes into the array
					int sizeGenesArray = (genes.size()-variable.getOperonLength())>0? 
							variable.getOperonLength(): genes.size();
					Gene[] genesArray = new Gene[sizeGenesArray];
					int startPoint = (genes.size()-variable.getOperonLength())>0? 
							genes.size()-variable.getOperonLength():0;
					for(int j = 0; j < sizeGenesArray; j++)
						genesArray[j] = genes.get(startPoint + j);
					// insert the operon and delete the genes memory
					components.add(new AdvanceOperon(variable.isConstitutive(),promoters,percentages,genesArray));
//							new SimpleOperon(variable.isConstitutive(),promoters,variable.getPercentage(),genesArray));
					genes.clear();
				}
				//correct the begining index
				begin = i+1;
			} else if(!((OperonProteinVariable)individual.get(i)).isPromotor()){
				// create the gen and store for the next Operon
				String[] promoters = new String[end - begin];
				float[] percentages = new float[end-begin];
				for(int j = 0; j < end - begin; j++){
					promoters[j] = ((OperonProteinVariable)individual.get(begin+j)).getSequence();
					percentages[j] = ((OperonProteinVariable)individual.get(begin+j)).getPercentage();
				}
				OperonProteinVariable variable = (OperonProteinVariable) individual.get(i);
				genes.add(new AdvanceGene(variable.getSequence(),variable.isConstitutive(),promoters,percentages,variable.getDirection()));
//						new BasicGene(variable.getSequence(),variable.isConstitutive(),promoters,variable.getPercentage()));
				begin = i+1;
			} //else{
				// promoter
			//}
		}
		
		// if there are some genes remaining included them
		if(!genes.isEmpty()){
			for(Gene g : genes)
				components.add(g);
		}
	    	DNAComponent[] componentsArray = new DNAComponent[0];
	    	return DNAFactory.newInstance(components.toArray(componentsArray));
	    }

}
