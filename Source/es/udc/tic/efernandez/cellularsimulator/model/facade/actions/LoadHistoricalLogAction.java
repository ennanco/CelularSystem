package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;

public class LoadHistoricalLogAction extends AbstractFacadeAction {

	private String fileName;
	private StoreTissueParameters storeTissueParamaters;
	private int result;
	
	public LoadHistoricalLogAction(String fileName,StoreTissueParameters storeTissueParameters) {
		this.fileName = fileName;
		this.storeTissueParamaters = storeTissueParameters;
	}

	@Override
	protected boolean preconditions() {
		return !fileName.equals("");
	}

	@Override
	protected void process() {
		try{
			
			int ini,fin,posicion,aux;
//			int j;
			float valor,validez;
			ArrayList leidos =new ArrayList();
			ArrayList index =new ArrayList();
			Reader in= new FileReader(fileName);
			BufferedReader buff= new BufferedReader(in);
			String linea = new String("");
			String nombre = new String("");
			String tipo = new String("");
			DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset(); 
//			CategoryDataset categoryDatasetaux;
			
			linea = buff.readLine();
			ini=0;
		    fin=linea.indexOf(" / ");
		    posicion=Integer.parseInt((linea.substring(ini, fin)));
		    aux=posicion;
		    
			while (true) {	      
		      if (linea==null){
		    	  leidos.add(categoryDataset);
		    	  index.add(posicion);
		    	  aux=posicion;
		    	  break;
		      }
		      
		      ini=0;
			  fin=linea.indexOf(" / ");
			  posicion=Integer.parseInt((linea.substring(ini, fin)));
//		      System.out.print("posicion: "+posicion);
		      
			  ini=fin+3;
		      fin=linea.indexOf(" / ",ini);
		      validez=Float.parseFloat((linea.substring(ini, fin)));
//		      System.out.print("\t validez: "+validez+"\t");
		      
		      if(validez==1.0){
		    	  ini=fin+3;
		    	  fin=linea.indexOf(" / ",ini);
		    	  valor=Float.parseFloat((linea.substring(ini, fin)));
		      
		    	  ini=fin+3;
		    	  fin=linea.indexOf(" / ",ini);
		    	  tipo=(linea.substring(ini, fin));
		      
		    	  ini=fin+3;
		    	  fin=linea.length();
		    	  nombre=(linea.substring(ini, fin));
		      	    
		    	  if(aux==posicion){
		    		  categoryDataset.setValue(validez,"posicion","posicion");
		    		  categoryDataset.setValue(valor,tipo,nombre);  
		    	  }
		    	  else{
		    		  leidos.add(categoryDataset);
		    		  index.add(aux);
//		    		  System.out.println("\t almacenado --> posicion: "+aux+"\tvalidez: "+validez);
		    		  categoryDataset = new DefaultCategoryDataset();
		    		  categoryDataset.setValue(validez,"posicion","posicion");
		    		  categoryDataset.setValue(valor,tipo,nombre);
		    		  aux=posicion;
		    	  }
		      }
		      else{
		    	  if(aux==posicion)
		    		  categoryDataset.setValue(validez,"posicion","posicion");  
		    	  else{
		    		  leidos.add(categoryDataset);
		    		  index.add(aux);
//		    		  System.out.println("\t almacenado --> posicion: "+aux+"\tvalidez: "+validez);
		    		  categoryDataset = new DefaultCategoryDataset();
		    		  categoryDataset.setValue(validez,"posicion","posicion");
		    		  aux=posicion;
		    	  }  
		      }
		      linea = buff.readLine();	    
			}		
			in.close();
			result = storeTissueParamaters.resetLog(aux);
	
		    for(int i=0;i<leidos.size();i++){
		    	storeTissueParamaters.setPositionLog((Integer)index.get(i),(CategoryDataset)leidos.get(i));
//		    	categoryDatasetaux=(CategoryDataset)leidos.get(i);
//		    	System.out.println("Posicion: "+index.get(i)+" valor: "+categoryDatasetaux.getValue("posicion", "posicion"));
		    }
		}catch(IOException e){
			e.printStackTrace();
			result = 0;
		}

	}
	
	public int getResult(){
		return result;
	}

}
