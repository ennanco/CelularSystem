package es.udc.tic.efernandez.cellularsimulator.model.facade;

import java.io.IOException;
import java.util.ArrayList;

import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist.GradientList;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.individual.Individual;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;
/**
 * This interface marks the operations that can be realized over the Genetic Algorithm library.
 * The Facades which implements this interface are usually sate class, so they enclosed the state 
 * of the aplicacion parameters
 * @author Enrique Fernández Blanco
 *
 */
public interface FacadeInterface {
	/**
	 * Obtain a new population
	 *
	 */
	public void newPopulation();
	/**
	 * Load a population stored into a file
	 * @param fileName the file's name where the population to load is.
	 */
	public void loadPopulation(String fileName);
	/**
	 * This operation stores the population
	 * @param fileName the file's name to store the population
	 */
	public void savePopulation(String fileName);
	
	/**
	 * This operation takes the population and processes it to a new generation.
	 * If the population is not created this operation creates one random population
	 */
	public void evolvePopulation();
	/**
	 * To obtain the population which is enclosed at the facade
	 * @return
	 */
	public Population getPopulation();
	/**
	 * The method returns the id of the actual generation
	 * @return aun int with the value of the generation number
	 */
	public int getGeneration();
	/**
	 * this method reset the number of generations to zero.
	 *
	 */
	public void resetGeneration();
	/**
	 * Obtain all the parameters that are used into the sistem
	 * @return a hashmap where each parameter is enclosed by ParameterName-ValueObject, 
	 * for example Itarations - Integer(1000)
	 */
	public ParametersMap getParameters();
	/**
	 * stablish all the parameters into the HashMap for the system. 
	 * @param parameters the haspmap containing all the parameters
	 */
	public void setParameters(ParametersMap parameters);
	/**
	 * This method returns a unique configuration parameter.
	 * @param key the parameter key access
	 * @return an object which contends the required parameter
	 */
	public Object getParameter(int key);
	/**
	 * This operation returns a XML description of the indivial DNA 
	 * @param individual the individual to get the description
	 * @return a String containing the description
	 */
	public String examine(Individual individual);
	/**
	 * this operation converts an Individual from Genetic Algorithm Population into 
	 * an Celular system DNA to process a simulation
	 * @param indexNumber the individual index into the GA-population.
	 */
	public void loadIndividual(int indexNumber);
	/**
	 * Executes an iteration of the Tissue. If no DNA is charged then nothing is doing in it
	 *
	 */
	public void iterationTissue();
	/**
	 * The operation returns a the tissue with its details to consult.
	 * @return A tissue object with the state at this moment of the simulation.
	 */
	public Tissue getTissue();
	/**
	 * This method consults the development statement of the tissue.
	 * @return the number of celular cycles executed
	 */
	public int getIterationTissue();
	/**
	 * This method rescues the vales of thegradients
	 * @return a list of Gradients.
	 */
	public GradientList getGradients();
	/**
	 * Stablish the gradients
	 * @param gradients to stablish into the enviroment
	 */
	public void setGradients(GradientList gradients);
	
	/**
	 * This function saves the gradients of an enviroment
	 * @param fileName the name of the file to save
	 */
	public void saveGradient(String fileName);
	/**
	 * This function loads a file with its gradients
	 * @param fileName the name of the file to load
	 * @throws IOException
	 */
	public void loadGradient(String fileName) throws IOException;

	/**
	 * This function return the celular simulation historical log to verificate the information among the iterations
	 */
	public ArrayList getHystorical();
	/**
	 * This function loads a Historical Log to study the development of a particular tissue
	 * @param fileName the file to load
	 * @throws IOException 
	 */
	public void loadHystoricalLog(String fileName) throws IOException;
	/**
	 * This function save a Historical Log for its study in the future
	 * @param fileName the name of the file to save
	 */
	public void saveHystoricalLog(String fileName);
	/**
	 * This function creates a new simulation Historical register log.
	 *
	 */
	public void newHystorical();
	/**
	 * This function stablish a template for future individual evaluation
	 * @param matrix the template representation
	 */
	public void registerTemplate(boolean[][] matrix);
	/**
	 * This function returns user registered template for individual evaluation
	 * @return a matrix that represents the template
	 */
	public boolean[][] getEvaluationTemplate();
	/**
	 * This function saves ina a file the boolean template used in the system to evaluate
	 * @param fileName the name to use as file identifier
	 * @param matrix boolean matrix with the template to save
	 */
	public void saveTemplate(String fileName, boolean [][] matrix);
	/**
	 * This function loads a template to evaluate 
	 * @param fileName the file that contains the presaved template
	 * @throws IOException
	 */
	public void loadTemplate(String fileName) throws IOException;

	public void loadTraining(String fileName) throws IOException ;
	
	public void registerFileTrainning(FileTraining training);
	
	public FileTraining getTraining();
	
	
}
