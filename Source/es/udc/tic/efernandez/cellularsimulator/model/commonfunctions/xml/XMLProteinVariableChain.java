package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.xml;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.generationjava.io.xml.XmlWriter;

import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterionFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.ProteinVariable;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.individual.IndividualFactory;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.population.PopulationFactory;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

class XMLProteinVariableChain extends AbstractXMLChainProcesor {

	
	private static final String ID = "ProteinVariable";
	
	public XMLProteinVariableChain(AbstractXMLChainProcesor nextLink) {
		super(nextLink);
		}
    
	protected Population executeRead(Document document, boolean[][] template, FileTraining training) {	        
		Population toReturn = PopulationFactory.newInstance();
	    Individual[] toPopulation = null;
	    String geneSequence;
	    Float activePercentage;
	    Boolean constitutive;
	    Boolean promotor;
	    Integer direction;
	    ProteinVariable[] variables;
	    NodeList individuals = document.getElementsByTagName("individual");
	    Element individual;
	    NodeList gens;
	    Element gen;
	        
	    toPopulation = new Individual[individuals.getLength()];
	    for(int i = 0; i < individuals.getLength();i++){
	    	individual = (individuals.item(i).getNodeType() == Node.ELEMENT_NODE)?
	                    (Element) individuals.item(i):null;
	        if(individual != null){
	        	gens = individual.getElementsByTagName("gen");
                variables = new ProteinVariable[gens.getLength()];
                for(int j = 0;j < gens.getLength();j++){
                    gen = (gens.item(j).getNodeType() == Node.ELEMENT_NODE)?
                            (Element) gens.item(j):null;
                    if(gen!=null){
                        geneSequence = gen.getAttributes().getNamedItem("sequence").getNodeValue();
                        activePercentage = new Float(gen.getAttributes().getNamedItem("activePercentage").getNodeValue());
                        constitutive = new Boolean(gen.getAttributes().getNamedItem("constitutive").getNodeValue());
                        promotor = new Boolean(gen.getAttributes().getNamedItem("promotor").getNodeValue());
                        direction = new Integer(gen.getAttributes().getNamedItem("direction").getNodeValue());
                     variables[j]=(ProteinVariable)VariableFactory.getInstance();
                     variables[j].inicialize(promotor,constitutive,direction,geneSequence,activePercentage);
                    }
               }
               toPopulation[i] = IndividualFactory.getInstance(variables);

            }
        }
	    DNAEvaluationCriterion criterion = DNAEvaluationCriterionFactory.getInstance(template, training);
	    
	    toReturn.setCriterion(criterion.isRisingOrder());
	    if(toPopulation != null)
	        for(int i = 0; i < toPopulation.length; i++){
	            toReturn.add(toPopulation[i],criterion.evaluation(toPopulation[i]));
	        }
	        
	    return toReturn;
	}

	protected boolean canProcess(Document document) {
        NodeList individuals = document.getElementsByTagName("variable");
        
        Element individual = (individuals.item(0).getNodeType() == Node.ELEMENT_NODE)?
                (Element) individuals.item(0):null;
		
       return ID.equals(individual.getAttribute("class"));
	}

	protected void executeWrite(XmlWriter xmlWriter, Population population) throws IOException{
		Individual individual;
		ProteinVariable proteinVariable;
	  	xmlWriter.writeEntity("population");
		xmlWriter.writeEntity("variable");
		xmlWriter.writeAttribute("class",ID);
		xmlWriter.endEntity();
    	for( int i = 0; i < population.populace();i++){
        	xmlWriter.writeEntity("individual");
        	individual = population.get(i);
        	for( int j = 0; j < individual.numberOfVariables();j++){
    	        proteinVariable = (ProteinVariable) individual.get(j);
    	        xmlWriter.writeEntity("gen");
    	        xmlWriter.writeAttribute("sequence",proteinVariable.getSequence());
    	        xmlWriter.writeAttribute("activePercentage",proteinVariable.getNumber());
    	        xmlWriter.writeAttribute("constitutive",new Boolean(proteinVariable.isConstitutive()));
    	        xmlWriter.writeAttribute("promotor",new Boolean(proteinVariable.isPromotor()));
    	        xmlWriter.writeAttribute("direction", new Integer(proteinVariable.getDirection()));
    	        xmlWriter.endEntity();
        	}
        	xmlWriter.endEntity();
    	}
    	xmlWriter.endEntity();
    }

	protected boolean canProcess(Population population) {
		return population.get(0).get(0) instanceof ProteinVariable;
	}

}
