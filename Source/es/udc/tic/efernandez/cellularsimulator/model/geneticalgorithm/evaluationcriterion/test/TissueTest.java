package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;
import es.udc.tic.efernandez.geneticalgorithm.test.Test;

public interface TissueTest extends Test{
	public String getName();
	
	public Tissue getTissue();
	
	public int getIterationTissue();
	
	public Enviroment getEnviroment();
	
	public StoreTissueParameters getStoreTissueParameters();
	
	public float getFitness();
	
}
