package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.implementation;

import java.awt.Point;
import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellular.cell.StaticCell;
import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.enviroment.EnviromentFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.tissue.TissueFactory;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.enviroment.GradientEnviromentWrapper;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.BasicGradient;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.DNAEvaluationCriterion;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.evaluationcriterion.test.BasicTest;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.globalvariables.GlobalVariables;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.util.transformation.IndividualToDNA;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.test.testcontainer.TestContainer;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;

public class EXORProcessingEvaluationCriterion implements DNAEvaluationCriterion {

	
	private static boolean[][] template ={{true,true,true,true,true},
											{true,true,true,true,true},
											{true,true,true,true,true},
											{true,true,true,true,true},
											{true,true,true,true,true}
										};
	
	private BasicTest test;
	private ParametersMap parameters = new ParametersMap();
	private static String [] proteinSequences;
	private StoreTissueParameters proteinActivity;
	private TestContainer testContainer;//=new TestContainer();

	public TestContainer evaluation(Individual individual) {
		testContainer = new TestContainer();
		DNA dna = IndividualToDNA.DNAIndividualToDNA(individual);
		float fitness = 0.0F;
		float fitnessEscenario = 0.0F;
		Tissue tissue;
        GradientEnviromentWrapper enviroment;
        ReceptorCell receptor;
        Boolean alive;
        
        proteinSequences= new String[(Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
		
		for( int i = 0;  i < (Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)parameters.getParameters().get(ParametersMap.PROTEINE_MIN_LENGTH));
    	}
		
        //escenario 00
//		System.out.println("Escenario 00");
		enviroment = new GradientEnviromentWrapper(EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
		tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
				GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), 
				dna, enviroment);
        receptor = new ReceptorCell(10, 11, tissue, 0);
        tissue.set(10, 11, receptor);
        proteinActivity = new StoreTissueParameters();

        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
	        proteinActivity.storeProteins(tissue);
	    }
        
        alive=false;
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++)
        	for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++)
        		if(tissue.get(i, j) instanceof StaticCell)
        			alive=true;
        if(!alive){
        	fitnessEscenario=16;
        	test = new BasicTest("00", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
//        	System.out.println("Se carga la calula");
        }
        else{
        	fitnessEscenario=criterion(tissue, "00");
        	test = new BasicTest("00", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
        }
        testContainer.add(test);
        
        //escenario 01
//        System.out.println("Escenario 01");
        enviroment = new GradientEnviromentWrapper(EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
		tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
				GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), 
				dna, enviroment);
		receptor = new ReceptorCell(10, 11, tissue, 0);
        tissue.set(10, 11, receptor);
        enviroment.addGradient(new BasicGradient("0001", new Point(11,10), 1));
        proteinActivity = new StoreTissueParameters();
        
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
	        proteinActivity.storeProteins(tissue);
	    }
        
        alive=false;
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++)
        	for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++)
        		if(tissue.get(i, j) instanceof StaticCell)
        			alive=true;
        if(!alive){
        	fitnessEscenario=16;
        	test = new BasicTest("01", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
//        	System.out.println("Se carga la calula");
        }
        else{
        	fitnessEscenario=criterion(tissue, "01");
        	test = new BasicTest("01", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
        }
        testContainer.add(test);
        
        //escenario 10
//        System.out.println("Escenario 10");
        enviroment = new GradientEnviromentWrapper(EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
		tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
				GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), 
				dna, enviroment);
		receptor = new ReceptorCell(10, 11, tissue, 0);
        tissue.set(10, 11, receptor);
        enviroment.addGradient(new BasicGradient("0011", new Point(9,10), 1));
        proteinActivity = new StoreTissueParameters();
        
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
	        proteinActivity.storeProteins(tissue);
	    }
        
        alive=false;
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++)
        	for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++)
        		if(tissue.get(i, j) instanceof StaticCell)
        			alive=true;
        if(!alive){
        	fitnessEscenario=16;
        	test = new BasicTest("10", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
//        	System.out.println("Se carga la calula");
        }
        else{
        	fitnessEscenario=criterion(tissue, "10");
        	test = new BasicTest("10", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
        }
        testContainer.add(test);
        
        //escenario 11
//        System.out.println("Escenario 11");
        enviroment = new GradientEnviromentWrapper(EnviromentFactory.getInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
        		GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN)));
		tissue = TissueFactory.newInstance(GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW),
				GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN), 
				dna, enviroment);
        receptor = new ReceptorCell(10, 11, tissue, 0);
        tissue.set(10, 11, receptor);
        enviroment.addGradient(new BasicGradient("0001", new Point(9,10), 1));
        enviroment.addGradient(new BasicGradient("0011", new Point(11,10), 1));
        proteinActivity = new StoreTissueParameters();
        
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION); i++){
	        tissue.iteration();
	        proteinActivity.storeProteins(tissue);
	    }
        
        alive=false;
        for(int i = 0; i < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_ROW); i++)
        	for(int j = 0; j < GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_DIMENSION_COLUMN); j++)
        		if(tissue.get(i, j) instanceof StaticCell)
        			alive=true;
        if(!alive){
        	fitnessEscenario=16;
        	test = new BasicTest("11", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
//        	System.out.println("Se carga la calula");
        }
        else{
        	fitnessEscenario=criterion(tissue, "11");
        	test = new BasicTest("11", tissue, enviroment, proteinActivity, fitnessEscenario, GlobalVariables.getEvaluationParameter(GlobalVariables.EVALUATION_NUM_ITERATION));
        	fitness=fitness+fitnessEscenario;
        }
        testContainer.add(test);
        
        
        
    	 fitness+= ((dna.numberGenes() > 0) ? 
    	GlobalVariables.getGeneticAlgorithmParameter(GlobalVariables.MINIMIZATION_NUMBER_PARAMETER)
    	* dna.numberGenes()	: 200);
    	 
    	testContainer.setFitness(fitness);
    	
//    	System.out.println("Fitness final= "+testContainer.getFitness());
//    	System.out.println("Escenarios: "+testContainer.numberTest());
//        System.out.println("///////////////////////////////");
        
    	return testContainer;
	}
	
	private float criterion(Tissue tissue, String intro){
		float fitness = 0.0F;
		
		if(intro=="00" || intro=="11"){
			for( int i = 0;  i < (Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
				
//				System.out.println("Proteina: "+proteinSequences[i]+"\t concentracion: "+tissue.get(10, 11).concentration(proteinSequences[i], false));
	    		if(!Float.isNaN(tissue.get(10, 11).concentration(proteinSequences[i], false))){
	    			if(tissue.get(10, 11).concentration(proteinSequences[i], false)>0)
	    				if(proteinSequences[i].equals("0010"))
	    					fitness = fitness+(tissue.get(10, 11).concentration(proteinSequences[i], false)/100)*2;
	    				else
	    					fitness = fitness+(tissue.get(10, 11).concentration(proteinSequences[i], false)/100)*d_manhattan(proteinSequences[i],"0010");
	    		}
	    	}
		}
		else{
			if((intro=="01" || intro=="10"))
				for( int i = 0;  i < (Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
		    		
//					System.out.println("Proteina: "+proteinSequences[i]+"\t concentracion: "+tissue.get(10, 11).concentration(proteinSequences[i], false));
					if(!Float.isNaN(tissue.get(10, 11).concentration(proteinSequences[i], false))){
						if(tissue.get(10, 11).concentration(proteinSequences[i], false)>0)
							if(!proteinSequences[i].equals("0010"))
								fitness = fitness+(tissue.get(10, 11).concentration(proteinSequences[i], false)/100)*d_manhattan(proteinSequences[i],"0010");		    				
		    		}
		    		else
		    			if(proteinSequences[i].equals("0010"))
		    				fitness=fitness+8;
		    	}
		}
		
//		System.out.println("Escenario "+intro);
//		System.out.println(tissue.get(10, 11).toString());
//		System.out.println("Ajuste de fitness: "+fitness);
//		System.out.println("///////////////////////////////");
		return fitness;
	}

	private float d_manhattan(String inProtein, String outProtein){
		float distancia=0.0F;
		
		if(inProtein.length()!=outProtein.length())
			System.out.println("Error en la distancia manhattan");
		else
			for(int i=0;i<inProtein.length();i++){
				distancia=distancia+Math.abs(Integer.parseInt(String.valueOf(inProtein.charAt(i)))-Integer.parseInt(String.valueOf(outProtein.charAt(i))));
			}
//		System.out.println("Distancia entre "+inProtein+" y "+outProtein+": "+distancia);
		return distancia;
	}
	
	
	public boolean isRisingOrder() {
		return true;
	}

	public boolean[][] getTemplate() {
		return template;
	}

	public void registerTemplate(boolean[][] template) {
		if(template != null)
			registerTemp(template);
		
	}
	
	private static void registerTemp(boolean[][] template){
		EXORProcessingEvaluationCriterion.template = template;
	}
	
	public FileTraining getTraining(){
		return null;
	}
	
	public void registerTraining(FileTraining training){
		;
	}
}
