/*
 * Created on 12-jul-2005
 */
package es.udc.tic.efernandez.cellular.protein.example;

import java.awt.Point;

import es.udc.tic.efernandez.cellular.protein.Protein;

/**
 * @author Enrique Fernández Blanco
 */
public class BasicProtein implements Protein{
    
	private static final int NUMBER_DIRECTIONS = 8;
	
    private String sequence;
    private long created;
    private long timeToLife;
    private Point origin;
    private int direction;
/**
 * 
 * @param sequence protein's identified 
 * @param instant creation protein cycle
 * @param timeToLife how much cycles will be the protein in the system
 * @param origin point with store x = row and y = column
 * @param direction identification integer for the propagation direction of the protein
 */
    public BasicProtein(String sequence,long instant,long timeToLife,Point origin,int direction) {
        super();
        this.sequence = sequence;
        this.created = instant;
        this.timeToLife = timeToLife;
        this.origin = origin;
        this.direction = direction;
    }
  
    /**
     * @return Returns the sequence.
     */
    public String getSequence() {
        return sequence;
    }

    public long getCreated(){
        return created;
    }
    
    public boolean getDegraded(long instant){
    	//TODO aumentar el tiempo de vida
        return (instant > (created + timeToLife));
    }
    
    public String toString(){
        return "Proteine: " + sequence;
    }
    public Point getOrigin(){
        return origin;
    }
    public int getDirection(){
    	return direction;
    }
    
    public int getNumberDirections(){
    	return NUMBER_DIRECTIONS;
    }

}
