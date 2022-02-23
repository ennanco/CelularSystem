package es.udc.tic.efernandez.cellular.operon;

import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

public interface Operon extends DNAComponent{
	
	public boolean isConstitutive();
		
	public String[] active(ProteinList list) throws InternalErrorException;
	
	public Gene[] getContainedGenes();
	
	
}
