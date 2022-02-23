/*
 * Created on 12-jul-2005
 */
package es.udc.tic.efernandez.cellular.gene.example;

import java.util.HashMap;
import java.util.Iterator;

import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;
import es.udc.tic.efernandez.util.exception.InternalErrorException;

/**
 * @author Enrique Fernández Blanco
 */
public class BasicGene implements Gene{

    private String sequence;
    private boolean constitutive;
    private String[] activePart;
    private float activePercentage;
    
    public BasicGene(String sequence, boolean constitutive,
            String[] activePart, float activePercentage) {
        super();
        this.sequence = sequence;
        this.constitutive = constitutive;
        this.activePart = activePart;
        this.activePercentage = activePercentage;
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
        boolean active = true;
        HashMap amountProteine = new HashMap();
        
        for( int i = 0; i < selected.length; i++ ){
            selected[i] = "";
        }
        
        for( int i = 0; i < activePart.length; i++){
        	activate = false;
            for(Iterator iter = list.listedProteins().iterator(); iter.hasNext()&&(!activate);){
                proteineSequence = (String)iter.next();
                nonMatches = connect(proteineSequence, activePart[i]);
                concentration = list.concentration(proteineSequence);
                //TODO revisar la formula
                activate = ((nonMatches + 1) * activePercentage) < concentration ;
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
    
    public int getDirection(){
    	return -1;
    }
    
    public String sintesis(Protein[] proteineArray){    
        return sequence;
    }
    
    public boolean isOperon(){
    	return false;
    }
    
    public String toString(){
    	String toReturn="GEN\n";
    	
    	toReturn+="Secuence: "+sequence+"\nConstitutive: ";
    	
    	if(constitutive)
    		toReturn+="yes \n";
    	else
    		toReturn+="no \n";
    	
    	toReturn+="Percentage: "+Float.toString(activePercentage)+"\nPromotors: ";
    	
    	if(activePart.length<=0)
    		toReturn+="no \n";
    	else
    		for(int i=0; i<activePart.length; i++)
    			toReturn+="\t"+activePart[i]+"\n";
    	
    	return toReturn;
    }
}
