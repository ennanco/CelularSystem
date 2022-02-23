package es.udc.tic.efernandez.geneticalgorithm.test.testcontainer;

import java.util.ArrayList;

import es.udc.tic.efernandez.geneticalgorithm.test.Test;

public class TestContainer {
//overwrite the equals methodo using the fitness
	private ArrayList test;
	private float fitness;
	
	public TestContainer(){
		test = new ArrayList();
		fitness = 0;
	}
	
	public float getFitness(){
		return fitness;
	}
	
	public void setFitness(Float fitness){
		this.fitness = fitness; 
	}
	
	public void add(Test escenario){
		test.add(escenario);
	}
	
	public Test getTest(int index){
		return (Test)test.get(index);
	}
	
	public int numberTest(){
		return test.size();
	}
	
	public int compareTo (TestContainer testContainer){
//		System.out.println("Fitness propio: "+fitness+"\tFitnes a comparar: "+testContainer.getFitness());
		if(fitness == testContainer.getFitness())
			return 0;
		else
			if(fitness > testContainer.getFitness())
				return 1;
			else
				return-1;
	}
}
