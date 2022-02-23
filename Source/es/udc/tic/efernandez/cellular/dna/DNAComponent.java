package es.udc.tic.efernandez.cellular.dna;

import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

public interface DNAComponent {

	public boolean isOperon();
	
    /**
     * This operation determines if the gen is activated
     * @param list the list of avaliable proteins
     * @return a list of the gene sequences that are used
     * @throws InternalErrorException
     */
    public String[] active(ProteinList list) throws InternalErrorException;

}
