package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.tissue.TissueFactory;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;

public class LoadIndividualAction extends AbstractFacadeAction {

	private Individual individual;
	private Enviroment enviroment;
	private int rows;
	private int columns;
	private Tissue toReturn;
	
	public LoadIndividualAction(Individual individual, Enviroment enviroment, int rows, int columns){
		super();
		this.individual = individual;
		this.enviroment = enviroment;
		this.rows = rows;
		this.columns = columns;
	}
	protected boolean preconditions() {
		return individual != null;
	}

	protected void process() {
		toReturn = TissueFactory.newInstance(rows, columns, 
					IndividualToDNA.DNAIndividualToDNA(individual), enviroment);
	}
	
	public Tissue getResult(){
		return toReturn;
	}

}
