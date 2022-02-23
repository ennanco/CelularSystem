package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;

public class BasicTest implements TissueTest{

	private String name;
	private Tissue tissue;
	private Enviroment enviroment;
	private StoreTissueParameters storeTissueParamaters;
	private float fitness;
	private int iterationTissue;
	
	public BasicTest(String name, Tissue tissue, Enviroment enviroment, StoreTissueParameters storeTissueParamaters, float fitness, int iterationTissue){
		this.name  = name;
		this.tissue = tissue;
		this.enviroment = enviroment;
		this.storeTissueParamaters = storeTissueParamaters;
		this.fitness = fitness;
		this.iterationTissue = iterationTissue;
	}
	
	public String getName(){
		return name;
	}
	
	public Tissue getTissue(){
		return tissue;
	}
	
	public int getIterationTissue(){
		return iterationTissue;
	}
	
	public Enviroment getEnviroment(){
		return enviroment;
	}
	
	public StoreTissueParameters getStoreTissueParameters(){
		return storeTissueParamaters;
	}
	
	public float getFitness(){
		return fitness;
	}
}
