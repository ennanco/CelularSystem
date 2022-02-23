/**
 * This class is only a set of gene ( rules that the system will check in its development
 * 
 * Date 9-2-2009
 * 
 * @author Quique
 */
package es.udc.tic.efernandez.DNA;

import java.util.ArrayList;

import es.udc.tic.efernandez.DNA.gene.Gene;


public class DNA {
	
	private ArrayList<Gene> genes;
	
	public DNA(){
		genes = new ArrayList<Gene>();
	}
	
	public ArrayList<Gene> getGenes(){
		return genes;
	}
	
	public void setGenes(ArrayList<Gene> genes){
		this.genes = genes;
	}
	
	public Gene getGene(int index){
		return genes.get(index);
	}
	
	public void addGene( int index, Gene gene){
		genes.add(index, gene);
	}
	
	public void removeGene(int index){
		genes.remove(index);
	}
	
	public void clearDNA(){
		genes.clear();
	}

}
