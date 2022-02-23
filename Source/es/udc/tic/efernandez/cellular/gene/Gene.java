/*
 * Created on 15-sep-2005
 */
package es.udc.tic.efernandez.cellular.gene;

import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

/**
 * @author Enrique Fernández Blanco
 */
public interface Gene extends DNAComponent{

	/**
	 * This operation is to request the sequence cotain into the gen
	 * @return a String with the requested sequence
	 */
    public String getSequence();
    /**
     * This function asked the gene if it's constitutive type
     * @return aboolean value with the answered
     */
    public boolean getConstitutive();
    /**
     * This operation determines if the gen is activated
     * @param list the list of avaliable proteins
     * @return a list of the proteine sequences that are used
     * @throws InternalErrorException
     */
    public String[] active(ProteinList list) throws InternalErrorException;
    /**
     * This operation calculates the Hamming distance between the gene 
     * sequence and a given sequence from a particular point
     * @param proteineSequence the sequence which we compare
     * @param index start point to compare
     * @return a int value with the hamming distance
     * @throws InternalErrorException
     */
    public int distance(String proteineSequence, int index) throws InternalErrorException;
    /**
     * This operation calculates the Hamming distance between the gene 
     * sequence and a given sequence from every point
     * @param proteineSequence to compare with the gene sequence
     * @return an array with all the posibles distance between the sequence gene and the the given one
     * @throws InternalErrorException
     */
    public int[] distance(String proteineSequence) throws InternalErrorException;
    /**
     * This operation return the protein sequence of the gene
     * @param proteineArray the proteins that are neede to sintesis the gene
     * @return the sequece of the gene
     */
    public String sintesis(Protein[] proteineArray);
    /**
     * The operation returns the direction of the gene expresion
     * @return an integer that identifies one of the directions in the space.
     */
    public int getDirection();
}
