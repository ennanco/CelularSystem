 package es.udc.tic.efernandez.cellularsimulator.model.facade;
/**
 * This class implements a State instance of the Facade Interface,
 * this hides the trasactions among the two libraries to the application interface
 * 
 * @author Enrique Fernández Blanco
 * @author José Andrés Serantes
 */
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.udc.tic.efernandez.cellular.cell.ReceptorCell;
import es.udc.tic.efernandez.cellular.enviroment.EnviromentFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.enviroment.GradientEnviromentWrapper;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist.BasicGradientList;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist.GradientList;
import es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.CommonFunctions;
import es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.xml.XMLManager;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.ExamineAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.LoadGradientAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.LoadHistoricalLogAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.LoadIndividualAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.LoadTemplateAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.LoadTrainingAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.SaveGradientAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.SaveHistoricalLogAction;
import es.udc.tic.efernandez.cellularsimulator.model.facade.actions.SaveTemplateAction;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.algorithm.GeneticAlgorihtm;
import es.udc.tic.efernandez.geneticalgorithm.algorithm.example.RouletteSelectionParentReplacementGeneticAlgorithm;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;

public class StateFacade implements FacadeInterface {

	private GeneticAlgorihtm ga;
	private int generation;
	private Tissue tissue = null;
	private int iterationTissue;
	private ParametersMap parameters;
	private GradientEnviromentWrapper enviroment;
	private StoreTissueParameters storeTissueParamaters; 
	private boolean [][] template;
	private FileTraining training;
	
	public StateFacade(){
		generation = 0;
		ga = new RouletteSelectionParentReplacementGeneticAlgorithm(null);
		tissue = null;
		iterationTissue = 0;
		parameters = new ParametersMap();
		enviroment = new GradientEnviromentWrapper(
				EnviromentFactory.getInstance((Integer)getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN),
						(Integer)getParameter(ParametersMap.EVALUATION_DIMENSION_ROW)));
		storeTissueParamaters = new StoreTissueParameters();
	}

	public void newPopulation() {
		EnviromentFactory.register(enviroment);
		ga.setPopulation(CommonFunctions.randPopulation(
				(Integer)parameters.getParameters().get(ParametersMap.NUMBER_INDIVIDUALS), template, training));
		generation = 0;
	}

	public void savePopulation(String fileName) {
		XMLManager.store( fileName, ga.getPopulation());
	}
	
	public void loadPopulation(String fileName) {
		EnviromentFactory.register(enviroment);
		generation = 0;
		ga.setPopulation(XMLManager.load(fileName, template,training));
	}
	
	public Population getPopulation() {
		return ga.getPopulation();
	}
	
	public int getGeneration(){
		return generation;
	}
	
	public void resetGeneration(){
		generation = 0;
	}
	
	public void setParameters(ParametersMap parametersMap) {
		this.parameters = parametersMap;
		enviroment.inicialize((Integer)getParameter(ParametersMap.EVALUATION_DIMENSION_ROW),(Integer)getParameter(ParametersMap.EVALUATION_DIMENSION_COLUMN));
	}

	public ParametersMap getParameters() {
		return parameters;
	}
	
	public Object getParameter(int key){
		return parameters.getParameters().get(key);
	}

	public void evolvePopulation() {
		HashMap parametersHashMap = parameters.getParameters();
		if(generation < ((Integer)parametersHashMap.get(ParametersMap.NUMBER_GENERATIONS))){
			ga.cross((Float)parametersHashMap.get(ParametersMap.CROSSOVER_PERCENTAGE));
			ga.mutation((Float) parametersHashMap.get(ParametersMap.MUTATION_PERCENTAGE));
			ga.selection((Integer)parametersHashMap.get(ParametersMap.NUMBER_INDIVIDUALS));
			generation++;
		}
	}

	public void loadIndividual(int indexNumber) {
		HashMap parametersHashMap = parameters.getParameters();
		LoadIndividualAction action;
			action = new LoadIndividualAction(ga.getPopulation().get(indexNumber),
					EnviromentFactory.getInstance((Integer)parametersHashMap.get(ParametersMap.EVALUATION_DIMENSION_ROW),
							(Integer) parametersHashMap.get(ParametersMap.EVALUATION_DIMENSION_COLUMN)), 
							(Integer)parametersHashMap.get(ParametersMap.EVALUATION_DIMENSION_ROW),
							(Integer) parametersHashMap.get(ParametersMap.EVALUATION_DIMENSION_COLUMN));		
		if (action.execute()){
			tissue = action.getResult();
			iterationTissue = 0;
		}	
	}
	
	public String examine(Individual individual) {
		String toReturn = "";
		ExamineAction action = new ExamineAction(individual);
		if(action.execute())
			toReturn = action.getReturn();
		return toReturn;
	}

	public Tissue getTissue() {
		return tissue;
	}

	public void iterationTissue() {
		if(tissue != null){
			iterationTissue++;
			tissue.iteration();
			storeTissueParamaters.storeProteins(tissue);
		}
	}
	public int getIterationTissue(){
		return iterationTissue;
	}
	
	public GradientList getGradients(){
		return enviroment.gradientList();
	}
	
	public void setGradients(GradientList gradients){
		enviroment.setGradients(gradients);
		EnviromentFactory.register(enviroment);
	}
	
	public ArrayList getHystorical(){
		return(storeTissueParamaters.getLog());
	}
	
	public void saveHystoricalLog(String fileName) {
		SaveHistoricalLogAction action = new SaveHistoricalLogAction(this,fileName,storeTissueParamaters);
		action.execute();
	}
	
	public void loadHystoricalLog(String fileName) throws IOException {
		LoadHistoricalLogAction action = new LoadHistoricalLogAction(fileName,storeTissueParamaters);
		iterationTissue = action.execute()? action.getResult(): iterationTissue; 
	}
	
	public void newHystorical(){
		storeTissueParamaters.newLog();
	}
	
	public void registerTemplate(boolean [][] matrix){
		template = matrix;
	}
	
	public boolean[][] getEvaluationTemplate(){
		return template;
	}
	
	public void saveGradient(String fileName) {
		SaveGradientAction action = new SaveGradientAction(this,fileName);
		action.execute();
	}
	
	public void loadGradient(String fileName) throws IOException {
		enviroment.setGradients(new BasicGradientList());
		LoadGradientAction action = new LoadGradientAction(this,fileName);
		action.execute();
	}
	
	public void saveTemplate(String fileName, boolean [][] matrix) {
		SaveTemplateAction action = new SaveTemplateAction(this,fileName,matrix);
		action.execute();
	}
	
	public void loadTemplate(String fileName) throws IOException {
		LoadTemplateAction action = new LoadTemplateAction(this,fileName);
		action.execute();			
	}
	
	public ArrayList getReceptors(){
		ArrayList existentes = new ArrayList();
		
		for(int i=0;i<(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW);i++)
			for(int j=0;j<(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW);j++)
				if(tissue.get(i, j) instanceof ReceptorCell)
					existentes.add(new Point(i,j));
		return existentes;
	}
	
	public void loadTraining(String fileName) throws IOException {
		LoadTrainingAction action = new LoadTrainingAction(this,fileName);
		action.execute();
	}
	
	public void registerFileTrainning(FileTraining training){
		this.training = training;
	}
	
	public FileTraining getTraining(){
		return training;
	}
	
	
}
