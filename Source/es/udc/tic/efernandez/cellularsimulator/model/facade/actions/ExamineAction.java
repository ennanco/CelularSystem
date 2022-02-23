package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import es.udc.tic.efernandez.cellularsimulator.model.cellular.xml.DNAVariableChainProcessor;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.xml.AbstractGenChainProcessor;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.xml.OperonProteinVariableChainProcessor;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.xml.ProteinVariableChainProcessor;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;

public class ExamineAction extends AbstractFacadeAction {
	
	private static AbstractGenChainProcessor chain = new OperonProteinVariableChainProcessor(
			new ProteinVariableChainProcessor(new DNAVariableChainProcessor(null)));
	
	private Individual individual;
	private String toReturn;

	public ExamineAction(Individual individual){
		this.individual = individual;
		toReturn = "";
	}
	
	protected boolean preconditions() {
		return individual != null;
	}
	
	protected void process() {
		toReturn="";
        for(int i = 0; i < individual.numberOfVariables(); i++){
            toReturn += chain.process(individual.get(i));
        }
	}
	
	public String getReturn(){
		return toReturn;
	}

}
