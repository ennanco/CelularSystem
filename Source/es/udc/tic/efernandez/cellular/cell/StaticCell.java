package es.udc.tic.efernandez.cellular.cell;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.gene.Gene;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.protein.ProteinFactory;
import es.udc.tic.efernandez.cellular.protein.ProteinList;
import es.udc.tic.efernandez.cellular.protein.ProteinListFactory;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.util.globalconfiguration.GlobalConfiguration;
import es.udc.tic.efernandez.cellular.util.proteinidentifier.ProteinIdentifier;
import es.udc.tic.efernandez.util.exception.InternalErrorException;
import es.udc.tic.efernandez.util.valuetable.ValueTable;

public class StaticCell implements Cell {
	private static final int[] NEIGHBOUR_ROW = {-1,-1,0,1,1,1,0,-1};
    private static final int[] NEIGHBOUR_COLUMN = {0,1,1,1,0,-1,-1,-1};
    
    private DNA dna;
    private Point position;
    private long instant;
    private Tissue tissue;
    private LinkedList entries;
    private ProteinList using; 
    private boolean created;
    
    public StaticCell(int row, int column,DNA dna,Tissue tissue,long instant) {
        this.position = new Point(row,column);
        this.dna = dna;
        this.tissue= tissue;
        this.instant = instant;
        this.created = true;
        this.entries = new LinkedList();
        this.using = ProteinListFactory.getInstance();
    }
    
    public StaticCell(){
    	this.created = true;
        this.entries = new LinkedList();
        this.using = ProteinListFactory.getInstance();
    }
    
	public void setParameters(int row, int column, DNA dna, Tissue tissue, long instant) {
    	this.position = new Point(row,column);
        this.dna = dna;
        this.tissue= tissue;
        this.instant = instant;
	}
    
    public void recive(Protein message){
        if(message!= null)
            entries.add(message);
    }
    
    public boolean isNew(){
        return created;
    }

    public void setOld(){
        created = false;
    }
    
    public long getInstant(){
        return instant;
    }
    
    public Point getPosition(){
        return position;
    }
    
    public float concentration(String proteinSequence, boolean inUse) {
		
    	if(inUse)
			return using.concentration(proteinSequence);
		else {
			float counter = 0.0F;
			for(int i= 0; i < entries.size();i++){
				counter += ((Protein)entries.get(i)).getSequence().equals(proteinSequence)? 1.0: 0.0;
			}
			return counter/entries.size()* 100;
		}    	
	}

	public void iteration() throws InternalErrorException{
        if(!isNew()){
	        //Actualize the time.
	        instant++;
	        //Put the entries in the using group
	        actualizeProteines(entries,using);
	        //erase the degraded proteines
	        using.updateList(instant);
	        // if(!died()){
	            //find the genes that are activate in this iteration
            Gene[] genes = dna.posibleActivates(using);
	            /* look for the proteines that can make reaction with the proteines that are activate*/
            process(genes);
	       // }
        }
    }

    private void actualizeProteines(LinkedList entries, ProteinList using) {
        for(int i = 0; i < entries.size();){
            if(((Protein)entries.get(i)).getCreated() < instant){
                using.setProtein((Protein)entries.remove(i));
            } else {
                i++;
            }
        }
    }

    private void process(Gene[] actives) throws InternalErrorException{
        
        String[] proteineSequence;
        String sequence = "";
        Vector proteine = new Vector();
        
        for(int i = 0; i < actives.length; i++){
            proteineSequence = actives[i].active(using);
            for( int j = 0; j < proteineSequence.length; j++){
                if(!proteineSequence[j].equals(""))
                    proteine.add(using.removeProtein(proteineSequence[j]));
            }
            if((actives[i].getConstitutive() && proteine.isEmpty())){
                sequence = actives[i].sintesis(null);
                react(ProteinFactory.getInstance(sequence,instant,
                         ValueTable.getNumber(sequence),position, actives[i].getDirection()));
            } else if (!actives[i].getConstitutive() && !proteine.isEmpty()) {
                Protein[] proteineArray = new Protein[proteine.size()];
                for(int k = 0; k< proteine.size(); k++){
                    proteineArray[k] = (Protein)proteine.get(k);
                }
                sequence = actives[i].sintesis(proteineArray);
                react(ProteinFactory.getInstance(sequence,instant,
                        ValueTable.getNumber(sequence),position,actives[i].getDirection()));
            }
            proteine.clear();
        }
    }

    private void react(Protein proteine) {
    	
        if(using.concentration(proteine.getSequence()) < GlobalConfiguration.getLimit(GlobalConfiguration.PROTEIN_LOWER_LIMIT))
    		recive(proteine);
   	    else 
   	    	if(using.concentration(proteine.getSequence()) < GlobalConfiguration.getLimit(GlobalConfiguration.PROTEIN_HIGH_LIMIT)){
   	    		int index = ((int)Math.floor( (Math.random()* 8) ) ) % 8;
    	        Cell cell = tissue.get(position.x + NEIGHBOUR_ROW[index], position.y + NEIGHBOUR_COLUMN[index]);
    	        if(cell != null)
    	            cell.recive(proteine);
    	        else if(tissue.getEnviroment() != null)
    	            tissue.getEnviroment().set(new Point(position.x + NEIGHBOUR_ROW[index], position.y + NEIGHBOUR_COLUMN[index]), proteine);
    	    } 
   	    	else{
   	    		// to all neigbours.
    	        Cell cell; 
    	        for(int i = 0; i < 8; i++){
        	        cell = tissue.get(position.x + NEIGHBOUR_ROW[i], position.y + NEIGHBOUR_COLUMN[i]);
        	        if(cell != null)
        	            cell.recive(proteine);
        	        else 
        	        	if(tissue.getEnviroment() != null)
        	        		tissue.getEnviroment().set(new Point(position.x + NEIGHBOUR_ROW[i], position.y + NEIGHBOUR_COLUMN[i]), proteine);
    	        }
    	    }
    }
    
    public boolean died(){
        //String[] proteineSequence = ProteinIdentifier.getNociveList();
        boolean died = false;
        //for(int i = 0; (i < proteineSequence.length) && !died; i++)
        //    died = GlobalConfiguration.getLimit(GlobalConfiguration.PROTEIN_APOPTOSIS_LIMIT) < using.concentration(proteineSequence[i]);
       return died;
    }
    
    public String toString(){
        String toReturn = "";
        
        toReturn = "\n la celula ("+position.y+","+position.x+") en la iteracion "+instant+"\n";
        toReturn += "entradas: ";
        for(Iterator iter = entries.iterator(); iter.hasNext();){
            toReturn += ((Protein)iter.next()).toString()+" ";
        }
        toReturn += "\n usando: ";
        for(Iterator iter = using.listedProteins().iterator();iter.hasNext();){
            String proteineSequence = (String)iter.next();
            toReturn += proteineSequence+"("+using.concentration(proteineSequence)+") ,";
        }
        toReturn +="\n";
        
        return toReturn;
    }
    
    public DNA getDNA(){
    	return dna;
    }


}
