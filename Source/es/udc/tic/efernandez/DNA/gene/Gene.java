/**
 * 
 * This class represents a basic rule of a DNA based system.
 * 
 * Created: 9-2-2009
 * 
 * @author Enrique Fernández Blanco
 */
package es.udc.tic.efernandez.DNA.gene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Gene {
	
	private ArrayList<GeneSection>promoters;
	private ArrayList<GeneSection>inhibitors;
	private GeneIdentifier sequences;
	
	private double sumMinValue;
	private double sumMaxValue;
	
	private boolean logInizialized;
	private double logarithm;
	
	public Gene(){
		promoters = new ArrayList<GeneSection>();
		inhibitors = new ArrayList<GeneSection>();
		this.sumMinValue = 0.0;
		this.sumMaxValue = 0.0;
		logInizialized = false;
	}
	
	public void addPromoter(GeneSection promoter){
		promoters.add(promoter);
		this.sumMaxValue+=promoter.getSaturationValue();
		this.sumMinValue+=promoter.getMinimumValue();
	}
	
	public void addInhibitor(GeneSection inhibitor){
		inhibitors.add(inhibitor);
	}
	
	public void addSequence(GeneIdentifier sequence){
		this.sequences = sequence;
	}
	
	public Iterator<GeneSection> getPromoterIterator(){
		return promoters.iterator();
	}
	
	public Iterator<GeneSection> getInhibitorIterator(){
		return inhibitors.iterator();
	}
	
	public Protein transcript(HashMap<String,Double> input){
		
		boolean active = true;
		double acum = 0.0;
		
		GeneSection section;
		// calculate the amount of the return
		for(Iterator<GeneSection> iter = getInhibitorIterator();active && iter.hasNext();){
			section = iter.next();
			active = active &&
					(input.get(section.getSequence())==null ||
					(input.get(section.getSequence()) < section.getMinimumValue()));
		}
		
		for(Iterator<GeneSection> iter = getPromoterIterator(); active && iter.hasNext();){
			section = iter.next();
			if(input.get(section.getSequence()) != null &&
					input.get(section.getSequence())>section.getMinimumValue()){
				
				acum = acum + ((input.get(section.getSequence())>=section.getSaturationValue())?
						section.getSaturationValue():
							input.get(section.getSequence()));
			
			} else {
				active = false;
			}
		}
		
		if(active){
				double tmp = sequences.getMinimumValue() + (sequences.getSaturationValue() - sequences.getMinimumValue())*
				transferFunction(acum);
				return(new Protein(sequences.getSequence(),tmp));
		} else {
			return null;
		}
	}
	
	
	private double transferFunction(double x){
		
		double value;
		
		if(logInizialized){
			value = Math.log10(x-sumMinValue+1)/logarithm;			
		}else{
			logarithm = Math.log10(sumMaxValue-sumMinValue+1);
			logInizialized = true;
			value = Math.log10(x-sumMinValue+1)/logarithm;
		}
		return value;
	}
	
}
