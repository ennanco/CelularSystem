package es.udc.tic.efernandez.cellular.dna.example;

import java.util.ArrayList;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.dna.DNAComponent;
import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.operon.Operon;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

public class OperonDNA implements DNA{

	private ArrayList<DNAComponent> components;
	private int numberGenes;
	
	
	public OperonDNA(){
		numberGenes = 0;
		components = new ArrayList<DNAComponent>();
	}
	
	public Gene[] getGenes() {
		return null;
	}

	public int numberGenes() {
		return numberGenes;
	}

	public Gene[] posibleActivates(ProteinList proteineList) throws InternalErrorException {
		ArrayList<Gene> activeGenes = new ArrayList<Gene>();
		for(DNAComponent c : this.components){
			if(c.isOperon()){
				String[] sequences = c.active(proteineList);
				Operon o = (Operon)c;
				// this operation is for 0 length promotor and no constitutive genes.
				boolean activate = (sequences.length > 0) ? true : false;
				for(String sequence: sequences){
					activate &= !sequence.equals("");
				}
				if(activate){
					// in the situation that all the promoters are fullfilled
					for(String sequence: sequences){
						proteineList.removeProtein(sequence);
					}
					// if the operon isn't constitutive it will be transcripted
					// instead the operon isn't expresed
					if(!o.isConstitutive())
						for(Gene g : o.getContainedGenes())
							activeGenes.add(g);
				} else if(o.isConstitutive()){
					// in the case that the conditions are nor fullfilled the operon is only expresed
					// if is an constitutive operon
					for(Gene g : o.getContainedGenes())
						activeGenes.add(g);
				}
			} else {
				activeGenes.add((Gene)c);
			}
		}
		Gene[] geneArray = new Gene[0];
		return activeGenes.toArray(geneArray);
	}

	public void setGenes(Gene[] genes) {
		for(DNAComponent i:genes){
			components.add(i);
		}
	}
	
	public void addComponent(DNAComponent component){
		components.add(component);
		numberGenes+=component.isOperon()? ((Operon)component).getContainedGenes().length : 1;
		
	}
	
	public DNAComponent[] getComponents(){
		DNAComponent[] toReturn = new DNAComponent[0];
		return components.toArray(toReturn);
	}

}
