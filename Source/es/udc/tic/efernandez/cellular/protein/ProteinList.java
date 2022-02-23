/*
 * Created on 13-jul-2005
 */
package es.udc.tic.efernandez.cellular.protein;

import java.util.Set;

/**
 * @author Enrique Fernández Blanco
 */
public interface ProteinList {
    
    /** this method adds a proteine to the List
     * @param proteine the proteine to add
     */
    public void setProtein(Protein proteine);
    /**
     * to get the sequence of the proteines in the List
     * @return a set with the sequences
     */
	public Set listedProteins();
	/**
	 * This function is to get a proteine in the List
	 * @param proteineSequence this makes the proteine type unique, and is use to find it.
	 * @return null if it isn't, and a Proteine if it's
	 */
    public Protein getProtein(String proteineSequence);
    /**
     * This function is to get the proteins of a particular sequence
     * @param proteineSequence the sequence that we are looking for
     * @return A List that contains all the proteins that match with the sequence
     */
    public Protein[] getProteins(String proteineSequence);
    
    public Protein[] getProteins();
    /**
	 * This function is to get a proteine and to erase it from the List
	 * @param proteineSequence this makes the proteine type unique, and is use to find it.
	 * @return null if it isn't, and a Proteine if it's
	 */
    public Protein removeProtein(String proteineSequence);
    
    public Protein removeProtein(Protein protein);
    /**
     * Actualize the list erasing the proteines that are degredad at an instant.
     * @param instant the instant to update the list
     */
    public void updateList(long instant);
    /**
     * This function obtains the number of Elements at the list.
     * @return the number of elements in the List
     */
    public int size();
    /**
     * This function gets the percent of the proteine in the queue.
     * @param Sequence the sequence of the proteine to get the concentration
     * @return the percent of this proteine
     */
    public float concentration(String Sequence);
    /**
     * This function return the amount of a certain sequence into the List
     * @param sequece the sequence to search
     * @return a integer value that represents how much protein are at the list
     */
    public int amount(String sequece);
    /**
     * This function is uses to check if the amount in the list is enough.
     * @param sequence the proteine sequence to check.
     * @param amount number that we want.
     * @return return true when the amount is less than the proteines avaliable.
     */
    public boolean enough(String sequence, int amount);
}
