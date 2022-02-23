/*
 * Created on 12-jul-2005
 */
package es.udc.tic.efernandez.cellular.dna.example;

import java.util.ArrayList;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

/**
 * @author Enrique Fernández Blanco
 */
public class SimpleDNA implements DNA{

    private ArrayList<DNAComponent> genes;

    public SimpleDNA(){
    	genes= new ArrayList<DNAComponent>();
    }
    public SimpleDNA(Gene[] genes) {
        super();
        // all the cells have the same genes.
        this.genes = new ArrayList<DNAComponent>();
        setGenes(genes);
    }
    
    public void setGenes(Gene[] genes){
    	for(Gene g : genes)
    		this.genes.add(g);
    }
    
    public Gene[] posibleActivates(ProteinList proteineList) throws InternalErrorException{
        return getGenes();
    }
    
	public Gene[] getGenes() {
		Gene[] temp = new Gene[0];
		return genes.toArray(temp);
	}
	
	public int numberGenes() {
		return genes.size();
	}
	public void addComponent(DNAComponent component) {
		if(component instanceof Gene){
			this.genes.add(component);
		}
	}
	public DNAComponent[] getComponents() {
		DNAComponent[] temp = new DNAComponent[0];
		return genes.toArray(temp);
	}
    
}
