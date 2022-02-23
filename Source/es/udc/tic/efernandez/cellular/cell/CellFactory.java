/*
 * Creation Date: 26-10-2007
 * Author: Enrique Fernández Blanco 
 */
package es.udc.tic.efernandez.cellular.cell;

import es.udc.tic.efernandez.cellular.dna.DNA;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.cellular.util.configurationmanager.ConfigurationManager;

public class CellFactory {

	private static String CELL_CLASS_NAME_PARAMETER = "CellFactory/cellClassName";
	
	private CellFactory(){}
	
	public static Cell getInstance(int row, int column,DNA dna,Tissue tissue,long instant){
		String cellClassName = ConfigurationManager.getParameter(CELL_CLASS_NAME_PARAMETER);
    	Class cellClass;
    	try {
			cellClass = Class.forName(cellClassName);
			Cell toreturn = (Cell) cellClass.newInstance();
			toreturn.setParameters(row, column, dna,tissue,instant);
			return toreturn;
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
     }
}
