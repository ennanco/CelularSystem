package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import org.jfree.data.category.CategoryDataset;

import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;
import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;
import es.udc.tic.efernandez.cellularsimulator.model.util.StoreTissueParameters;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;

public class SaveHistoricalLogAction extends AbstractFacadeAction {

	private String fileName;
	private StoreTissueParameters storeTissueParamaters = null;
	private String [] proteinSequences;
	private FacadeInterface facade;
	
	public SaveHistoricalLogAction(FacadeInterface facade,String fileName,StoreTissueParameters storeTissueParamaters) {
		this.fileName = fileName;
		this.storeTissueParamaters = storeTissueParamaters;
		this.facade = facade;
	}

	@Override
	protected boolean preconditions() {
		return storeTissueParamaters != null && !fileName.equals("");
	}

	@Override
	protected void process() {
		try{
		Writer out= new FileWriter(fileName);
		ArrayList historico = storeTissueParamaters.getLog();
		
		Number valor,validez;
		String[] seriesNames = new String[] {"to Enter", "in Use"};
		proteinSequences= new String[(Integer)facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
		
		for( int i = 0;  i < (Integer)facade.getParameter(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)facade.getParameter(ParametersMap.PROTEINE_MIN_LENGTH));
    	}
		
		//guardar el genoma, el DNAComponent[]
		
		for (int i=0;i<historico.size();i++){
			if(historico.get(i)!=null){
				validez = ((CategoryDataset)historico.get(i)).getValue(0,0);
				if(validez.doubleValue()==1.0)
					for(int j=1;j<((CategoryDataset)historico.get(i)).getRowCount();j++){
						for(int k=1;k<((CategoryDataset)historico.get(i)).getColumnCount();k++){
							valor = ((CategoryDataset)historico.get(i)).getValue(j,k);
							String linea = new String("");
							if(valor!=null){
								linea=linea.concat(((Integer)i).toString());
								linea=linea.concat(" / ");
								linea=linea.concat(validez.toString());
								linea=linea.concat(" / ");
								if ((valor.toString()).equals("NaN")){
									linea=linea.concat("0.0");
								}else
									linea=linea.concat(valor.toString());
								linea=linea.concat(" / ");
								linea=linea.concat(seriesNames[j-1]);
								linea=linea.concat(" / ");
								linea=linea.concat(displayName(proteinSequences[k-1]));
								linea=linea.concat("\n");
								out.write(linea,0,linea.length());							
							}
						}	
					}
				else{
					String linea = new String("");
					linea=linea.concat(((Integer)i).toString());
					linea=linea.concat(" / ");
					linea=linea.concat(validez.toString());
					linea=linea.concat(" / ");
					linea=linea.concat("\n");
					out.write(linea,0,linea.length());
				}
			}
			
		}
		out.close();
		}catch(IOException exp){
			exp.printStackTrace();
		}
	}
	
	private String displayName(String sequence){

    	if(facade.getParameter(ParametersMap.APOPTOSIS_STRING).equals(sequence))
    		return "Apoptosis "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_N).equals(sequence))
    		return "Grown N "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_S).equals(sequence))
    		return "Grown S "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_E).equals(sequence))
    		return "Grown E "+sequence;
    	else if(facade.getParameter(ParametersMap.GROWN_STRING_W).equals(sequence))
    		return "Grown W "+sequence;
    	else
    		return sequence;
    }


}
