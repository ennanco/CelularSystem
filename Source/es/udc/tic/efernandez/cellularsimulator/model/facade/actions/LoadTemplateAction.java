package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;


public class LoadTemplateAction extends AbstractFacadeAction {

	private String fileName;
	private FacadeInterface facade;
	
	public LoadTemplateAction(FacadeInterface facade,String fileName) {
		this.fileName = fileName;
		this.facade = facade;
	}
	@Override
	protected boolean preconditions() {
		return !fileName.equals("");
	}

	@Override
	protected void process() {
		boolean matriz [][];
		try{
			int ini,fin,i,j;
			Reader in= new FileReader(fileName);
			BufferedReader buff= new BufferedReader(in);
			String linea = new String("");
			
			linea = buff.readLine();
			ini=0;
			fin=linea.indexOf(" ");
			matriz = new boolean [Integer.parseInt(linea.substring(0, fin))]
			                     [Integer.parseInt(linea.substring(fin+1, linea.length()-1))];
			
			i=0;
			while (true) {
				linea = buff.readLine();
				if (linea==null){
					break;
				}
				j=0;
				ini=0;
				while(ini<linea.length()){
					fin=linea.indexOf(" ", ini);
					if(linea.substring(ini, fin).compareTo("1")==0)
						matriz[i][j]= true;
					else
						matriz[i][j]=false;
					ini=fin+1;
					j++;
				}
				i++;
				}		
				in.close();
				facade.registerTemplate(matriz);
		    
		}catch(IOException e){
			e.printStackTrace();
		}

	}

}
