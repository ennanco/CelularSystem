package es.udc.tic.efernandez.cellularsimulator.batchinterface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeFactory;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.util.compression.zip.ZipFileManager;
import es.udc.tic.efernandez.util.cronometer.Cronometer;
import es.udc.tic.efernandez.util.files.FileUtilities;
import es.udc.tic.efernandez.util.valuetable.ValueTable;

public class BatchInterface {
	
		public static void main(String[] args){
			
			FacadeInterface facade = FacadeFactory.newInstance();
			
			String fileName = "";
			
			if((args.length < 23)||(args.length > 24)){
				System.out.println( "Parameters Format: NumberGenerations NumberIndividuals" +
						"CrossPercent MutatationPercent MinimizationParameter InitialVariables NumberActivePart" +
						"ProteinNumber ProteinLength ProteineNumber RowDimension ColumnDimension DevelopmentIterations " +
						"lowerThreshold upperThreshold ApoptosisThreshold ApoptosisSequence GrowN GrowS GrowE GrowW " +
						"TimeToLifeProtein TemplateFile FileToSave [FileToLoad]");
				System.exit(0);
			}
			
			// process the parameters of the program
			HashMap map = new HashMap();
			
			int index = 0;
//			System.out.println("Generations:" + args[index]);
			map.put(ParametersMap.NUMBER_GENERATIONS, new Integer(args[index++]));
//			System.out.println("Number Individuals:" + args[index]);
			map.put(ParametersMap.NUMBER_INDIVIDUALS, new Integer(args[index++]));
//			System.out.println("Cross %:" + args[index]);
			map.put(ParametersMap.CROSSOVER_PERCENTAGE, new Float(args[index++]));
//			System.out.println("Mutation %:" + args[index]);
			map.put(ParametersMap.MUTATION_PERCENTAGE, new Float(args[index++]));
//			System.out.println("Minimization:" + args[index]);
			map.put(ParametersMap.MINIMIZATION_NUMBER_PARAMETER, new Float(args[index++]));
//			System.out.println("Number active Part:" + args[index]);
			map.put(ParametersMap.ACTIVE_PART_MAX_PROTEINE, new Integer(args[index++]));
//			System.out.println("Number of Proteins:"+ args[index]);
			map.put(ParametersMap.MAX_NUMBER_OF_PROTEINE, new Integer(args[index++]));
//			System.out.println("Protein Min Len:" + args[index]);
			map.put(ParametersMap.PROTEINE_MIN_LENGTH, new Integer(args[index++]));
//			System.out.println("Number variables:" + args[index]);
			map.put(ParametersMap.NUMBER_VARIABLES, new Integer(args[index++]));
//			System.out.println("Rows:" + args[index]);
			map.put(ParametersMap.EVALUATION_DIMENSION_ROW, new Integer(args[index++]));
//			System.out.println("Columns:" + args[index]);
			map.put(ParametersMap.EVALUATION_DIMENSION_COLUMN, new Integer(args[index++]));
//			System.out.println("Iterations:" + args[index]);
			map.put(ParametersMap.EVALUATION_NUM_ITERATION, new Integer(args[index++]));
//			System.out.println("Lower Limit:" + args[index]);
			map.put(ParametersMap.PROTEIN_LOWER_LIMIT, new Float(args[index++]));
//			System.out.println("High Limit:" + args[index]);
			map.put(ParametersMap.PROTEIN_HIGH_LIMIT, new Float(args[index++]));
//			System.out.println("Apoptosis:" + args[index]);
			map.put(ParametersMap.PROTEIN_APOPTOSIS_LIMIT, new Float(args[index++]));
//			System.out.println("Sequence A:" + args[index]);
			map.put(ParametersMap.APOPTOSIS_STRING, args[index++]);
//			System.out.println("Sequence N:" + args[index]);
			map.put(ParametersMap.GROWN_STRING_N, args[index++]);
//			System.out.println("Sequence S:" + args[index]);
			map.put(ParametersMap.GROWN_STRING_S, args[index++]);
//			System.out.println("Sequence E:" + args[index]);
			map.put(ParametersMap.GROWN_STRING_E, args[index++]);
//			System.out.println("Sequence W:" + args[index]);
			map.put(ParametersMap.GROWN_STRING_W, args[index++]);
//			TODO reparar lo del tiempo de vida de las proteinas
//			System.out.println("Protein TTL:" + args[index]);
			ValueTable.setDefault((new Integer(args[index++])).intValue());
			
			//Load the evaluation template
			try {
//				facade.loadTemplate(args[index++]);
				facade.loadTraining(args[index++]);
			} catch (IOException e) {
				throw new Error("Misses or incorrect format for Template File");
			}
			
			fileName = args[index++];
			
			
			ParametersMap parametersMap = facade.getParameters();
			parametersMap.setParameters(map);
			if(args.length==24)
				facade.loadPopulation(args[index++]);
			else
				facade.newPopulation();
			
			// generate the population and create the Genetic Algoritm
			// the generations of the Genetic Algorithm
			
			
			new File(fileName).mkdir();
			//y poner mejor fitness y fitness medio, quitar las escrituras por pantalla
			
			boolean rising = facade.getPopulation().getCriterion();
			float previousBestFitness = (rising)? Float.MAX_VALUE : -1;

	        System.out.println("Generation Inicial" + "Best Fitness: "+ facade.getPopulation().getEvaluation(0));
			
			
			for(int i=0; i < (Integer)facade.getParameter(ParametersMap.NUMBER_GENERATIONS) 
				&& (0.001F <= facade.getPopulation().getEvaluation(0)); i++){
				
				if(i%100==0){
					String name = fileName+"/"+fileName+"_Generation_"+i
						+"_BF_"+facade.getPopulation().getEvaluation(0)+".xml";
					facade.savePopulation(name);
					if(previousBestFitness == facade.getPopulation().getEvaluation(0)){
						float percent = (Float)facade.getParameter(ParametersMap.MUTATION_PERCENTAGE);
						percent += (percent + percent * 0.05 < 100.0F)? percent * 0.05 : 0.0;
					}else{
						previousBestFitness = facade.getPopulation().getEvaluation(0);
						float percent = (Float)facade.getParameter(ParametersMap.MUTATION_PERCENTAGE);
						percent -= (percent - percent * 0.05 > 0.0F)? percent * 0.05 : 0.0;
					}
				}
				Cronometer.start();
				facade.evolvePopulation();
		        Cronometer.stop();
		        System.out.println("Generation "+i + " Partial Time: "+Cronometer.getPartialTime()+" Global Time: "
		        		+ Cronometer.getGlobalTime()/60000+"min"+ (Cronometer.getGlobalTime()%60000)/1000+"s" + 
		        		"Best Fitness: "+ facade.getPopulation().getEvaluation(0));
			}
			
			// grabar a disco el XML
			facade.savePopulation(fileName+"/"+fileName+"_BF_"+facade.getPopulation().getEvaluation(0)+".xml");
			
			File file = new File(fileName);
			ZipFileManager.deflation(
					fileName+"_BF_"+facade.getPopulation().getEvaluation(0)+".zip",
					new File[]{file});
			FileUtilities.deleteFile(file);			
		}
}
