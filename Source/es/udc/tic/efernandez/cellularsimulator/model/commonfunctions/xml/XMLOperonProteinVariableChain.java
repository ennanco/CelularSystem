package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.xml;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.generationjava.io.xml.XmlWriter;

import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterionFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.OperonProteinVariable;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.individual.IndividualFactory;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
import es.udc.tic.efernandez.geneticalgorithm.population.PopulationFactory;
import es.udc.tic.efernandez.geneticalgorithm.variable.VariableFactory;

public class XMLOperonProteinVariableChain extends AbstractXMLChainProcesor {

	private static final String ID = "OperonProteinVariable";
	
	XMLOperonProteinVariableChain(AbstractXMLChainProcesor nextLink) {
		super(nextLink);
	}

	@Override
	protected boolean canProcess(Document document) {
		 NodeList individuals = document.getElementsByTagName("variable");
	        
	        Element individual = (individuals.item(0).getNodeType() == Node.ELEMENT_NODE)?
	                (Element) individuals.item(0):null;
			
	       return ID.equals(individual.getAttribute("class"));
	}

	@Override
	protected boolean canProcess(Population population) {
		return population.get(0).get(0) instanceof OperonProteinVariable;
	}

	@Override
	protected Population executeRead(Document document, boolean [][] template, FileTraining training) {
		Population toReturn = PopulationFactory.newInstance();
	    Individual[] toPopulation = null;
	    String geneSequence;
	    Float activePercentage;
	    Boolean constitutive;
	    Boolean promotor;
	    Boolean operon;
	    Integer operonLength;
	    Integer direction;
	    OperonProteinVariable[] variables;
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
                variables = new OperonProteinVariable[gens.getLength()];
                for(int j = 0;j < gens.getLength();j++){
                    gen = (gens.item(j).getNodeType() == Node.ELEMENT_NODE)?
                            (Element) gens.item(j):null;
                    if(gen!=null){
                        geneSequence = gen.getAttributes().getNamedItem("sequence").getNodeValue();
                        activePercentage = new Float(gen.getAttributes().getNamedItem("activePercentage").getNodeValue());
                        constitutive = new Boolean(gen.getAttributes().getNamedItem("constitutive").getNodeValue());
                        promotor = new Boolean(gen.getAttributes().getNamedItem("promotor").getNodeValue());
                        operon = new Boolean(gen.getAttributes().getNamedItem("operon").getNodeValue());
                        operonLength = new Integer(gen.getAttributes().getNamedItem("operonLength").getNodeValue());
                        direction = new Integer(gen.getAttributes().getNamedItem("direction").getNodeValue());
                     variables[j]=(OperonProteinVariable)VariableFactory.getInstance();
                     variables[j].inicialize(operon,promotor,constitutive,operonLength,direction,geneSequence,activePercentage);
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

	@Override
	protected void executeWrite(XmlWriter xmlWriter, Population population)
			throws IOException {
		Individual individual;
		OperonProteinVariable operonProteinVariable;
	  	xmlWriter.writeEntity("population");
		xmlWriter.writeEntity("variable");
		xmlWriter.writeAttribute("class",ID);
		xmlWriter.endEntity();
    	for( int i = 0; i < population.populace();i++){
        	xmlWriter.writeEntity("individual");
        	individual = population.get(i);
        	for( int j = 0; j < individual.numberOfVariables();j++){
    	        operonProteinVariable = (OperonProteinVariable) individual.get(j);
    	        xmlWriter.writeEntity("gen");
    	        xmlWriter.writeAttribute("sequence",operonProteinVariable.getSequence());
    	        xmlWriter.writeAttribute("activePercentage",operonProteinVariable.getNumber());
    	        xmlWriter.writeAttribute("constitutive",operonProteinVariable.isConstitutive());
    	        xmlWriter.writeAttribute("promotor",operonProteinVariable.isPromotor());
    	        xmlWriter.writeAttribute("operon", operonProteinVariable.isOperon());
    	        // the 2 points substracted is for the base length of an operon
    	        xmlWriter.writeAttribute("operonLength", operonProteinVariable.getOperonLength()-2);
    	        xmlWriter.writeAttribute("direction", operonProteinVariable.getDirection());
    	        xmlWriter.endEntity();
        	}
        	xmlWriter.endEntity();
    	}
    	xmlWriter.endEntity();

	}

}
