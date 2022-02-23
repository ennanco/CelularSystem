package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.xml;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.generationjava.io.xml.XmlWriter;

import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterionFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.DNAVariable;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.individual.IndividualFactory;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.population.PopulationFactory;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

public class XMLDNAVariableChain extends AbstractXMLChainProcesor {

	private static final String ID = "DNAVariable";

	public XMLDNAVariableChain(AbstractXMLChainProcesor nextLink) {
		super(nextLink);
	}

	protected Population executeRead(Document document, boolean [][] template, FileTraining training) {
        Population toReturn = PopulationFactory.newInstance();
        Individual[] toPopulation = null;
        String geneSequence;
        String[]activePart;
        Float activePercentage;
        Boolean constitutive;
        DNAVariable[] variables;
        
            NodeList individuals = document.getElementsByTagName("individual");
            Element individual;
            NodeList gens;
            Element gen;
            NodeList actives;
            Element active;
            
            toPopulation = new Individual[individuals.getLength()];
            for(int i = 0; i < individuals.getLength();i++){
                individual = (individuals.item(i).getNodeType() == Node.ELEMENT_NODE)?
                        (Element) individuals.item(i):null;
                if(individual != null){
                    gens = individual.getElementsByTagName("gen");
                    variables = new DNAVariable[gens.getLength()];
                    for(int j = 0;j < gens.getLength();j++){
                        gen = (gens.item(j).getNodeType() == Node.ELEMENT_NODE)?
                                (Element) gens.item(j):null;
                        if(gen!=null){
                            geneSequence = gen.getAttributes().getNamedItem("sequence").getNodeValue();
                            activePercentage = new Float(gen.getAttributes().getNamedItem("activePercentage").getNodeValue());
                            constitutive = new Boolean(gen.getAttributes().getNamedItem("constitutive").getNodeValue());
                            actives = gen.getElementsByTagName("activePart");
                            activePart = new String[actives.getLength()];
                            for(int k = 0; k < actives.getLength(); k++){
                                active = (actives.item(k).getNodeType() == Node.ELEMENT_NODE)?
                                        (Element) actives.item(k):null;
                                if(active!=null){
                                    activePart[k] = active.getAttribute("sequence");
                                }
                            }
                         variables[j]= (DNAVariable)VariableFactory.getInstance();
                         variables[j].inicialize(geneSequence,activePart,activePercentage,constitutive);
                        }
                   }
                   toPopulation[i] = IndividualFactory.getInstance(variables);

                }
            }
        
        DNAEvaluationCriterion criterion = DNAEvaluationCriterionFactory.getInstance(template,training);
            
        toReturn.setCriterion(criterion.isRisingOrder());
        if(toPopulation != null)
            for(int i = 0; i < toPopulation.length; i++){
                toReturn.add(toPopulation[i],criterion.evaluation(toPopulation[i]));
            }
            
        return toReturn;
	}

	protected void executeWrite(XmlWriter xmlWriter, Population population) throws IOException {
		Individual individual;
    	DNAVariable sequenceDNAVariable ;
    	String[] activePart;
    	
    	xmlWriter.writeEntity("population");
		xmlWriter.writeEntity("Variable");
		xmlWriter.writeAttribute("class",ID);
		xmlWriter.endEntity();
    	for( int i = 0; i < population.populace();i++){
    		individual = population.get(i);
    	   	xmlWriter.writeEntity("individual");
        	for( int j = 0; j < individual.numberOfVariables();j++){
        		sequenceDNAVariable = (DNAVariable)individual.get(j);
        		xmlWriter.writeEntity("gen");
                xmlWriter.writeAttribute("sequence",sequenceDNAVariable.getGeneSequences());
                xmlWriter.writeAttribute("activePercentage",sequenceDNAVariable.getActivePercentage());
                xmlWriter.writeAttribute("constitutive",sequenceDNAVariable.getConstitutive());
                activePart = sequenceDNAVariable.getActivePart();
                for(int k = 0; k < activePart.length;k++){
                	xmlWriter.writeEntity("activePart");
                	xmlWriter.writeAttribute("sequence",activePart[k]);
                	xmlWriter.endEntity();
                }
                xmlWriter.endEntity();

        	}
        	xmlWriter.endEntity();
    	}
    	xmlWriter.endEntity();
    }

	protected boolean canProcess(Document document) {
        NodeList individuals = document.getElementsByTagName("Variable");
        
        Element individual = (individuals.item(0).getNodeType() == Node.ELEMENT_NODE)?
                (Element) individuals.item(0):null;
		
       return ID.equals(individual.getAttribute("class"));
	}

	protected boolean canProcess(Population population) {
		return population.get(0).get(0) instanceof DNAVariable;
	}

}
