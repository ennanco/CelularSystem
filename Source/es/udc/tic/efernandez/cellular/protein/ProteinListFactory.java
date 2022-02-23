/*
 * Created on 15-ago-2005
 */
package es.udc.tic.efernandez.cellular.protein;

import es.udc.tic.efernandez.cellular.protein.example.BasicProteinList;

/**
 * @author Enrique Fernández Blanco
 */
public class ProteinListFactory {

    /**
     * This operation creates a new ProteinList implementation
     * @return a new instance of an ProteinList implementation
     */    
    public static ProteinList getInstance(){
        return new BasicProteinList(); 
    }
}