/*
 * Created on 15-ago-2005
 */
package es.udc.tic.efernandez.cellular.protein;

import java.awt.Point;

import es.udc.tic.efernandez.cellular.protein.example.BasicProtein;

/**
 * @author Enrique Fernández Blanco
 */
public class ProteinFactory {
	/**
     * This operation creates a new object that implements Protein interface
     * @return a new instance of an Protein implementation
     */
    public static Protein getInstance(String sequence, long instant, long timeToLife, Point position,int direction){
        return new BasicProtein(sequence,instant,timeToLife,position,direction);
    }
}
