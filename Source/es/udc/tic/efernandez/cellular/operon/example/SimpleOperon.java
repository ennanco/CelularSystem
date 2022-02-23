package es.udc.tic.efernandez.cellular.operon.example;

import java.util.HashMap;
import java.util.Iterator;

import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.operon.Operon;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

public class SimpleOperon implements Operon {

	private boolean constitutive;
	private float activationPercentage;
	private Gene[] genes;
	private String[] promoter;
	
	public SimpleOperon(boolean constitutive, String[] promoter, float activationPercentage, Gene[] genes) {
		this.constitutive = constitutive;
		this.promoter = promoter;
		this.activationPercentage = activationPercentage;
		this.genes = genes;
	}

	public String[] active(ProteinList list) throws InternalErrorException {
		String proteineSequence  = "";
        float concentration;
        int nonMatches;
        boolean activate;
        String [] selected = new String[promoter.length];
        boolean active = true;
        HashMap amountProteine = new HashMap();
        
        for( int i = 0; i < selected.length; i++ ){
            selected[i] = "";
        }
        
        for( int i = 0; i < promoter.length; i++){
        	activate = false;
            for(Iterator iter = list.listedProteins().iterator(); iter.hasNext()&&(!activate);){
                proteineSequence = (String)iter.next();
                nonMatches = connect(proteineSequence, promoter[i]);
                concentration = list.concentration(proteineSequence);
                // change to have a percencent for each promoter to have the product
                activate = ((nonMatches + 1) * activationPercentage) < concentration ;
                if(activate){
                    Integer amount = (Integer) amountProteine.get(proteineSequence);
                    if(amount == null)
                        amount = new Integer(0);
                    if(list.enough(proteineSequence,amount.intValue()+1)){
                       selected[i] = proteineSequence;
                       amountProteine.remove(proteineSequence);
                       amountProteine.put(proteineSequence,new Integer(amount.intValue()+1));
                    } else{
                        activate = false;
                    }
                }
            }
        }
        for(int i = 0; (i < selected.length)&& active; i++){
            active &= !(selected[i].equals("")); 
        }
        if(!active){
            selected = new String[1];
            selected[0] ="";
        }
        return selected;

    }
    
    /* 
     * Method uses intern to get the distance beetween activePart and proteine
     */
    private int connect(String proteineSequence,String activePart)throws InternalErrorException{
        
        int distance = activePart.length();
        try{
          distance = BinaryStringOperations.dist(proteineSequence,activePart);
        }catch (NonValidSequenceException e){
            throw new InternalErrorException(e);
        }
        return distance;
	}
    
    public float getPercentage(){
    	return activationPercentage;
    }

	public Gene[] getContainedGenes() {
		return genes;
	}

	public boolean isConstitutive() {
		return constitutive;
	}
	
	public boolean isOperon(){
		return true;
	}
	
	public String toString(){
		String toReturn="OPERON\n";
		toReturn+="Length: "+genes.length +"\nPercentage: "+Float.toString(activationPercentage)+
					"\nConstitutive: ";
		if(constitutive)
			toReturn+="yes \n";
		else
			toReturn+="no \n";
		
		toReturn+="Genes: "+Integer.toString(genes.length)+"\n";
		for(int i=0; i<genes.length; i++)
			toReturn+=Integer.toString(i)+" Gen:\n"+genes[i].toString()+"\n";
		
		return toReturn;
	}

}
