package es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable;

import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
/**
 * this interface marks all the classes that can return components to be used by a cellular
 * system, beacause the class represents a gen of an DNA Individual
 * @author Enrique Fernández Blanco
 *
 */
public interface DNAVariable extends Variable {

	/**
	 * this method is used to inicalkize the values of the variable to this concrete values.
	 * @param geneSequences
	 * @param activePart
	 * @param activePercentage
	 * @param constitutive
	 */
	public void inicialize(String geneSequences, String[] activePart,
            Float activePercentage, Boolean constitutive);
	/**
	 * this method returns the protein's sequence that are needed to activate de gen 
	 * @return the protein sequence with are needed to activate the gen
	 */
	public String[] getActivePart();
	/**
	 * 
	 * @return to get the active percentage of the gen
	 */
    public Float getActivePercentage();
    /**
     * 
     * @return examine if the gen is constitutive or not
     */
    public Boolean getConstitutive();
    /**
     * 
     * @return the protein that the gen synthetize
     */
    public String getGeneSequences();

}
