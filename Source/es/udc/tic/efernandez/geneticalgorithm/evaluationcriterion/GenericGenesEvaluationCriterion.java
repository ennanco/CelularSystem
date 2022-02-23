package es.udc.tic.efernandez.geneticalgorithm.evaluationcriterion;

import java.util.ArrayList;
import java.util.HashMap;

import util.MathOperation;
import util.Pair;
import util.Section;
import util.TrainPattern;
import es.udc.tic.efernandez.DNA.DNA;
import es.udc.tic.efernandez.DNA.gene.Gene;
import es.udc.tic.efernandez.DNA.gene.GeneIdentifier;
import es.udc.tic.efernandez.DNA.gene.GeneSection;
import es.udc.tic.efernandez.DNA.gene.Protein;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.variable.GenericGeneVariable;

public class GenericGenesEvaluationCriterion implements EvaluationCriterion {

	public static TrainPattern[] patterns;
	
	@Override
	public float evaluation(Individual individual) {
		DNA dna = convert(individual);
		TrainPattern[] p = patterns;
		
		return (float)executeCriterion(p, dna) + 0.001F * individual.numberOfVariables();
	}

	public static DNA convert(Individual individual) {
		
		ArrayList<Gene> genes = new ArrayList<Gene>();
		
		GenericGeneVariable geneVariable;
		Gene geneDNA;
		
		Pair<String, Double>[] tempPairs;
		Section <String, Double, Double>[] tempSections;
		
		for ( int i = 0; i < individual.numberOfVariables();i++){
			geneVariable = (GenericGeneVariable)individual.get(i);
			geneDNA = new Gene();
			tempSections = geneVariable.getPromoters();
			for( Section<String, Double, Double> section: tempSections){
				if(!section.getFirst().equals(""))
					geneDNA.addPromoter(new GeneSection(section.getFirst(), section.getSecond(), 
							MathOperation.clamp(section.getThird()+section.getSecond(),0.0,1.0)));
			}
			tempPairs = geneVariable.getInhibitors();
			for( Pair<String, Double> pair: tempPairs){
				if(!pair.getFirst().equals(""))
					geneDNA.addInhibitor(new GeneSection(pair.getFirst(), pair.getSecond(), 1.0));
			}
			Section<String, Double, Double> section = geneVariable.getSequence();
			//At this point the Saturation Value is the sum of the second and third terms clamped between 0 and 1
			geneDNA.addSequence(new GeneIdentifier(section.getFirst(),section.getSecond(), 
					MathOperation.clamp(section.getThird()+section.getSecond(),0.0,1.0)));
			genes.add(geneDNA);
		}
		DNA dna = new DNA();
		dna.setGenes(genes);
		return dna;
	}

	@Override
	public boolean isRisingOrder() {
		return true;
	}
	
	public static void setPatterns(TrainPattern[] p){
		patterns = p;
	}

	public static double Test(Individual individual, TrainPattern[] p){
		return executeCriterion(p,convert(individual));
	}
	
	private static double executeCriterion(TrainPattern[] p, DNA dna){
		double error = 0.0;
		Protein protein = null;
		//FIXME más de una vuelta en al que lo producido antes entre a jugar.
		
		for (int index = 0; index < p.length; index++){
			HashMap<String,Double> output = new HashMap<String, Double>();
			for(Gene g : dna.getGenes()){
				protein = g.transcript(p[index].getInputs());
				
				if(protein!=null){
					String key = protein.getSequence();
					if(output.get(key) != null){
						
						// FIXME be careful not to sum instead of select the max
						//output.put(key, Math.max(output.get(key), protein.getValue()));
						output.put(key, MathOperation.clamp(output.get(key)+protein.getValue(), 0.0, 1.0));
					} else{
						output.put(key, protein.getValue());								
					}
				}
			}
			
			Double maxValue = 0.0;
			boolean repeated = false;
			String maxValueSequence = null;
			for(String key : output.keySet()){
				if(maxValue < output.get(key)){
					maxValue = output.get(key);
					maxValueSequence = key;
					repeated = false;
				} else if(maxValue == output.get(key)){
					repeated = true;
				}
			}
			String desiredOutput = p[index].getOutput();
			Double desiredOutputValue = output.get(desiredOutput);
				if(output.size()==0){
					error += 3;
				}
				if(desiredOutputValue == null || desiredOutputValue == 0.0)
					error += 2 + 0.1*output.keySet().size();
				else if((!desiredOutput.equals(maxValueSequence)) || repeated )
					error += 1 + 0.1*(output.keySet().size() - 1);
				else
					error += 0.1 * (output.keySet().size()-1);
		}
		return error + dna.getGenes().size();
	}
}
