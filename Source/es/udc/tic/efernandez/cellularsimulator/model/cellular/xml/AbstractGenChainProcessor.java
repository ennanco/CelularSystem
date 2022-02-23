package es.udc.tic.efernandez.cellularsimulator.model.cellular.xml;

import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;

public abstract class AbstractGenChainProcessor {

	protected AbstractGenChainProcessor nextLink;
	
	AbstractGenChainProcessor(AbstractGenChainProcessor nextLink){
		this.nextLink = nextLink;
	}
	
	public String process(Variable variable){
		if (canProcess(variable)){
			return executeLink(variable);
		}else if(nextLink!= null){
			return nextLink.process(variable);
		}//else;
		return null;
	}
	
	protected abstract boolean canProcess(Variable variable);
	
	protected abstract String executeLink(Variable variable);
}
