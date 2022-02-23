package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.BasicGradient;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.Gradient;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;

public class LoadGradientAction extends AbstractFacadeAction {
	
	private String fileName;
	private FacadeInterface facade;
	
	public LoadGradientAction(FacadeInterface facade,String fileName) {
		this.fileName = fileName;
		this.facade = facade;
	}

	@Override
	protected boolean preconditions() {
		return !fileName.equals("");
	}

	@Override
	protected void process() {
		try{
			int ini,fin,posicionX,posicionY,radio;
			Point posicion;
			String proteina;
			Reader in= new FileReader(fileName);
			BufferedReader buff= new BufferedReader(in);
			String linea = new String("");
			Gradient gradient;

			while (true) {
			  linea = buff.readLine();
		      if (linea==null){
		    	  break;
		      }
		      
		      ini=0;
			  fin=linea.indexOf(" ");
			  proteina=linea.substring(ini, fin);
			  
		      
			  ini=fin+1;
		      fin=linea.indexOf(" ",ini);
		      radio=Integer.parseInt((linea.substring(ini, fin)));
		      
			  ini=fin+1;
		      fin=linea.indexOf(" ",ini);
		      posicionX=Integer.parseInt((linea.substring(ini, fin)));
		      
		      ini=fin+1;
		      fin=linea.length();
		      posicionY=Integer.parseInt((linea.substring(ini, fin)));   
		      
		      posicion = new Point(posicionX,posicionY);
		      
		      gradient = new BasicGradient(proteina, posicion, radio);
		      
		      facade.getGradients().addGradient(gradient);
			}		
			in.close();
		    
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
