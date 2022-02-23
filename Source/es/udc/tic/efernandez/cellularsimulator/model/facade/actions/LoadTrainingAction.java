package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.GradientsPattern;
import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.Patterns;

public class LoadTrainingAction extends AbstractFacadeAction {

	private String fileName;
	private FacadeInterface facade;
	
	public LoadTrainingAction(FacadeInterface facade,String fileName) {
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
			int leidas,ini,fin,posicionX,posicionY,radio, patrones, gradientesPatron, receptores, individuos, salidas, salidasPatron, ultimo;
			Point posicion;
			String proteina;
			Reader in= new FileReader(fileName);
			BufferedReader buff= new BufferedReader(in);
			String linea = new String("");
			Point [] individuals;
			Point [] receptors;
			String [][] outputs;
			FileTraining training;
			Float init;			
//			lees los INDIVIDUOS
			
//			System.out.println("individuos");
			leidas=0;
			linea = buff.readLine();
//			System.out.println(linea);
			leidas++;
			
			linea = buff.readLine();
			if (linea!=null){
				individuos=Integer.parseInt(linea);
//				System.out.println(individuos);
		   	  	leidas++;
	 		}else
	 			individuos=0;
			individuals = new Point [individuos];
//			System.out.println(leidas+"/"+(leidas+individuos));
			for(int i=leidas; i< leidas+individuos; i++){
				linea = buff.readLine();
//				System.out.println(linea);
				if(linea==null)
					break;  
				ini=0;
				fin=linea.indexOf(" ");
				posicionX=Integer.parseInt((linea.substring(ini, fin)));
				ini=fin+1;
				fin=linea.length();
				posicionY=Integer.parseInt((linea.substring(ini, fin)));   
				posicion = new Point(posicionX,posicionY);
				//guardaros en un arraylist de individuos
				individuals[i-leidas]=posicion;			
			}
			leidas+=individuos;

			
			  //lees los RECEPTORES
			
//			System.out.println("receptores");
			linea = buff.readLine();
//			System.out.println(linea);
			leidas++;
			
			linea = buff.readLine();
	      	if(linea!=null){
	      		receptores=Integer.parseInt(linea);
//	      		System.out.println(receptores);
	      		leidas++;
	      	}else
	 			receptores=0;
	      	
	      	receptors = new Point [receptores];
	      	
	      	for(int i=leidas; i<leidas+receptores; i++){
	      	  linea = buff.readLine();
//	      	System.out.println(linea);
	      	  if(linea==null)
	      		  break; 
	      	  ini=0;
	      	  fin=linea.indexOf(" ");
	      	  posicionX=Integer.parseInt((linea.substring(ini, fin)));
	      	  ini=fin+1;
	      	  fin=linea.length();
	      	  posicionY=Integer.parseInt((linea.substring(ini, fin)));   
	      	  posicion = new Point(posicionX,posicionY);
	      	  //guardaros en un arraylist
	      	  receptors[i-leidas]=posicion;
	        }
	      	leidas+=receptores;
	      	
	      	
//	      lees las SALIDAS deseadas
	      	
//	      	System.out.println("Patrones");
	      	linea = buff.readLine();
//	      	System.out.println(linea);
			leidas++;
			linea = buff.readLine();
			if(linea!=null){
				salidas=Integer.parseInt(linea);
//		    	  System.out.println(salidas);
				leidas++;
			}else
				salidas=0;
			linea = buff.readLine();
//				System.out.println(linea);
			if(linea!=null){
				salidasPatron=Integer.parseInt(linea);
				leidas++;
			}
			else
				salidasPatron=0;
			outputs = new String [salidas][salidasPatron];
			for(int i=leidas; i<leidas+salidas; i++){
				linea = buff.readLine();
//		    	  System.out.println(linea);
				if(linea==null)
					break;
				
				ini=0;
				for(int j=0; j<salidasPatron; j++){
					if(j!=(salidasPatron-1))
						fin=linea.indexOf(" ",ini);
					else
						fin=linea.length();
					
					proteina=linea.substring(ini, fin);
					outputs[i-leidas][j] = new String();
					outputs[i-leidas][j] = outputs[i-leidas][j].concat(proteina);
					ini=fin+1;
				}			
			}
			leidas+=salidas;
			
//			Lees los PATRONES, generando un escenario para cada uno
			
	     	linea = buff.readLine();
//	  	System.out.println(linea);
	     	leidas++;
	     	linea = buff.readLine();

			if(linea!=null){
	    	  patrones=Integer.parseInt(linea);
//	    	  System.out.println(patrones);
	    	  leidas++;
	      	}else
	 			patrones=0;
			linea = buff.readLine();
//			System.out.println(linea);
			if(linea!=null){
	      		gradientesPatron=Integer.parseInt(linea);
	      		leidas++;
	      	}
	      	else
	      		gradientesPatron=0;
			
			training = new FileTraining(individuos,receptores,salidas,salidasPatron,patrones,gradientesPatron);
			training.setIndividuals(individuals);
			training.setReceptors(receptors);
			training.setOutputs(outputs);
//	      System.out.println("/////////////////////////////////");
	      for(int i=leidas; i<leidas+patrones; i++){
	    	  linea = buff.readLine();
//	    	  System.out.println(linea);
	    	  if(linea==null)
	    		  break;	    	  
	    	  training.getPatterns()[i-leidas] = new Patterns(gradientesPatron);
//	    	  añdes los gradientes de los que esta compuesto el patron
	    	  ini=0;
	    	  fin=linea.indexOf(" ");
	    	  ultimo=0;
//	    	  System.out.println("Gradientes: "+gradientesPatron);
	    	  for(int j=0; j<gradientesPatron; j++){
	    		  
	    		  if(j==(gradientesPatron-1))
	    			  ultimo=1;
	    		  
//	    		  System.out.println("Gradiente: "+j+"\tultimo: "+ultimo);
	    		  proteina=linea.substring(ini, fin);
//	    		  System.out.print("Proteina: "+proteina+"\t");
				  ini=fin+1;
			      fin=linea.indexOf(" ",ini);
			      init=Float.parseFloat((linea.substring(ini, fin)));
//			      System.out.print("init: "+init+"\t");
				  ini=fin+1;
			      fin=linea.indexOf(" ",ini);
			      radio=Integer.parseInt((linea.substring(ini, fin)));
//			      System.out.print("Radio: "+radio+"\t");
				  ini=fin+1;
			      fin=linea.indexOf(" ",ini);
			      posicionX=Integer.parseInt((linea.substring(ini, fin)));
//			      System.out.print("X: "+posicionX+"\t");
			      ini=fin+1;
			      if(ultimo==0)
			    	  fin=linea.indexOf(" ",ini);
			      else
			    	  fin=linea.length();
			      posicionY=Integer.parseInt((linea.substring(ini, fin)));
//			      System.out.println("Y: "+posicionY);
			      posicion = new Point(posicionX,posicionY);
			      
			      training.getPatterns()[i-leidas].setGradient( j, new GradientsPattern(proteina, init, radio, posicionX, posicionY));
			      
			      if(j<gradientesPatron-1){
			    	  ini=linea.indexOf("/", fin)+2;
			    	  fin=linea.indexOf(" ", ini);
			      }    
	    	  }
	      }
	      leidas+=patrones;
	      in.close();
	      facade.registerFileTrainning(training);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
