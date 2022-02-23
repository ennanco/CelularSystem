/*
 * Created on 13-jul-2005
 */
package es.udc.tic.efernandez.cellular.protein.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinList;


/**
 * @author Enrique Fernández Blanco
 */
public class BasicProteinList implements ProteinList{

    private HashMap proteines;
    private int numberOfElements;
 
    public BasicProteinList() {
        super();
        proteines = new HashMap();
        numberOfElements = 0;
 	}
	
	public void setProtein(Protein proteine){
	    LinkedList listed = (LinkedList)proteines.get(proteine.getSequence());
	    if(listed == null){
	        listed = new LinkedList<Protein>();
	        proteines.put(proteine.getSequence(),listed);
	    }
	    listed.addLast(proteine);
	    numberOfElements++;
	}
	
	public Set listedProteins(){
	    return proteines.keySet();
	}
	
	public Protein getProtein(String proteineSequence){
	    Protein proteine = null;
	    if(numberOfElements>0){
	        LinkedList<Protein> listed = (LinkedList<Protein>)proteines.get(proteineSequence);
		    proteine = (listed!=null)?listed.getFirst():null;
	    }
	    return proteine;
	}
	
	public Protein[] getProteins(String proteineSequence){
		return (Protein[])((LinkedList)proteines.get(proteineSequence)).toArray(new Protein[0]);
	}
	
	public Protein[] getProteins(){
		Protein[] toReturn = new Protein[numberOfElements];
		int index = 0;
		for(Iterator iterator = proteines.keySet().iterator(); iterator.hasNext(); ){
			LinkedList<Protein> list = (LinkedList<Protein>)proteines.get(iterator.next());
			for(Protein p : list){
				toReturn[index] = p;
				index++;
			}
		}
		return toReturn;
	}
		
	public Protein removeProtein(String proteineSequence){
	    Protein proteine = null;
	    if(numberOfElements>0){
	        LinkedList listed = (LinkedList)proteines.get(proteineSequence);
	        if(listed!=null){
	            proteine = (Protein)listed.removeFirst();
	            if(listed.size()==0)
	                //remove the list if this is empty
	                proteines.remove(proteineSequence);
	            if(proteine != null)
	                numberOfElements--;
	        }
	    }
	    return proteine;
	}
	
	public Protein removeProtein(Protein protein){
		boolean erased = false;
		if(numberOfElements>0){
	        LinkedList listed = (LinkedList)proteines.get(protein.getSequence());
	        if(listed!=null){
	            erased = listed.remove(protein);
	            if(listed.size()==0)
	                proteines.remove(protein.getSequence());
	            if(erased)
	                numberOfElements--;
	        }
	    }
	    return erased?protein:null;
	}
	
	public void updateList(long instant){
	    LinkedList listed;
	    Protein proteine;
	    String proteineSequence;
	    Vector toErase = new Vector();
	    for(Iterator iter = proteines.keySet().iterator();iter.hasNext();){
	       proteineSequence = (String) iter.next();
	       listed = (LinkedList)proteines.get(proteineSequence);
	       for(int i = 0; i<listed.size();){
	           proteine = (Protein)listed.get(i);
	           if(proteine.getDegraded(instant)){
	               listed.remove(i);
	               numberOfElements--;
	           } else{
	               i++;
	           }
	       }
	       if(listed.isEmpty())
	           toErase.add(proteineSequence);
	    }
	    for(Iterator iter = toErase.iterator();iter.hasNext();){
	        proteineSequence = (String) iter.next();
	        proteines.remove(proteineSequence);	
	    }
	}
	
	public int size(){
	    return numberOfElements;
	}
   
	public float concentration(String proteineSequence) {
	    float concen = 0;
	    LinkedList listed = (LinkedList)proteines.get(proteineSequence);
	    if(listed!= null){
	        concen = ((float)listed.size()/(float)size())*100;
	    }
	    return concen;
	}

	public int amount(String sequence){
		int toReturn = 0;
		LinkedList listed = (LinkedList)proteines.get(sequence);
	    if(listed!= null){
	    	toReturn = listed.size();
	    }
	    return toReturn;
	}
	
    public boolean enough(String sequence, int amount) {
        return ((LinkedList)proteines.get(sequence)).size() >= amount;
    }


}
