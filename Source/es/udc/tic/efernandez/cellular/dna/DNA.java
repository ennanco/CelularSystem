/*
 * Created on 26-ago-2005
 */
package es.udc.tic.efernandez.cellular.dna;

import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

/**
 * @author Enrique Fernández Blanco
 */
public interface DNA {

	/**
	 * This function stablish the genes of an DNA chain
	 * @param genes an array with the genes which compose the DNA
	 * @deprecated
	 */
	public void setGenes(Gene[] genes);
	/**
	 * The function do a first check of the genes that are avaliable to operate 
	 * with those proteins
	 * @param proteineList proteins avaliable to operate
	 * @return the genes that are avaliable to active with the proteins in the list
	 * @throws InternalErrorException
	 */
    public Gene[] posibleActivates(ProteinList proteineList) throws InternalErrorException;
    
	public int numberGenes();
	
	/**
	 * @deprecated
	 * @returnThe genes in a DNA
	 */
	public Gene[] getGenes();
	
	public void addComponent(DNAComponent component);
	
	public DNAComponent[] getComponents();

}
