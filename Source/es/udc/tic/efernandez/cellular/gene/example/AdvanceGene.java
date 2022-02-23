package es.udc.tic.efernandez.cellular.gene.example;

import java.util.HashMap;
import java.util.Iterator;

import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

public class AdvanceGene implements Gene {

	    private String sequence;
	    private boolean constitutive;
	    private String[] activePart;
	    private float[] activePercentage;
	    private int direction;
	    
	    public AdvanceGene(String sequence, boolean constitutive,
	            String[] activePart, float[] activePercentage, int direction) {
	        super();
	        this.sequence = sequence;
	        this.constitutive = constitutive;
	        this.activePart = activePart;
	        this.activePercentage = activePercentage;
	        this.direction = direction;
	    }
	    
	    public String getSequence(){
	        return sequence;
	    }
	    
	    public boolean getConstitutive(){
	        return constitutive;
	    }
	        
	    public String[] active(ProteinList list) throws InternalErrorException{
	        
	        String proteineSequence  = "";
	        float concentration;
	        int nonMatches;
	        boolean activate;
	        String [] selected = new String[activePart.length];
	        boolean active = false;
	        HashMap amountProteine = new HashMap();
	        	        
	        for( int i = 0; i < activePart.length; i++){
	        	activate = false;
	        	selected[i] = "";
	            for(Iterator iter = list.listedProteins().iterator(); iter.hasNext()&&(!activate);){
	                proteineSequence = (String)iter.next();
	                nonMatches = connect(proteineSequence, activePart[i]);
	                concentration = list.concentration(proteineSequence);
	                //TODO revisar la formula
	                activate = /*nonMatches>0?false:(activePercentage[i] <concentration);*/((nonMatches + 1) * activePercentage[i]) < concentration ;
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
	        }else if(selected.length == 0){
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

	    public int distance(String proteineSequence, int index) throws InternalErrorException{
	        int distance = Integer.MAX_VALUE;
	        if((index >= 0)&&(index < activePart.length)){
	            distance = connect(proteineSequence,activePart[index]);
	        }
	        return distance;
	    }
	    
	    public int[] distance(String proteineSequence) throws InternalErrorException{
	        int[] distances = new int[activePart.length];
	        for(int i = 0; i < activePart.length; i++){
	            distances[i] = distance(proteineSequence,i);
	        }
	        return distances;
	    }
	    
	    public String sintesis(Protein[] proteineArray){    
	        return sequence;
	    }
	    
	    public int getDirection(){
	    	return direction;
	    }
	    
	    public boolean isOperon(){
	    	return false;
	    }
	    
	    public String toString(){
	    	String toReturn="GEN\n";
	    	
	    	toReturn+="Secuence: "+sequence+" Direction: "+ getDirection()+"\nConstitutive: ";
	    	
	    	if(constitutive)
	    		toReturn+="yes \n";
	    	else
	    		toReturn+="no \n";
	    	
	    	toReturn+="Promoters: ";
	    	
	    	if(activePart.length<=0)
	    		toReturn+="no \n";
	    	else
	    		for(int i=0; i<activePart.length; i++)
	    			toReturn+="\t"+activePart[i]+" Percentage: "+Float.toString(activePercentage[i])+"\n";
	    	
	    	return toReturn;
	    }
}
