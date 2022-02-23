package es.udc.tic.efernandez.cellularsimulator.model.util;

import java.util.ArrayList;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import es.udc.tic.efernandez.cellular.cell.Cell;
import es.udc.tic.efernandez.cellular.tissue.Tissue;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;


public class StoreTissueParameters {
	
	private ArrayList log;
	private String [] proteinSequences;
	private ParametersMap parameters;
	
	public StoreTissueParameters(){
		parameters = new ParametersMap();
		int size = (Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW)*(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW);
		log = new ArrayList(size);
	}
	
	public void storeProteins(Tissue tissue) {
		int rows,columns;
		Cell cell;
		String[] seriesNames = new String[] {"to Enter", "in Use"};
		proteinSequences= new String[(Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE)];
		
		for( int i = 0;  i < (Integer)parameters.getParameters().get(ParametersMap.MAX_NUMBER_OF_PROTEINE); i++){
    		proteinSequences[i] = BinaryStringOperations.toBinary(i, (Integer)parameters.getParameters().get(ParametersMap.PROTEINE_MIN_LENGTH));
    	}
		
		rows = (Integer)parameters.getParameters().get(14);
		columns = (Integer)parameters.getParameters().get(15);
		DefaultCategoryDataset categoryDataset;
		for (int i=0; i < rows; i++){
			for (int j=0; j < columns; j++){
				if(tissue.validPosition(i,j))	
		        	if((cell=tissue.get(i,j)) != null){
		        		categoryDataset = new DefaultCategoryDataset();
		        		categoryDataset.setValue(1,"posicion","posicion");
		  
		        		for(int m = 0; m < proteinSequences.length; m++){
		        			categoryDataset.setValue(cell.concentration(proteinSequences[m],false),seriesNames[0],displayName(proteinSequences[m]));
		        			categoryDataset.setValue(cell.concentration(proteinSequences[m],true),seriesNames[1],displayName(proteinSequences[m]));
		        		}
		        		log.add(categoryDataset);
		        	}
		        	else
		        		log.add((CategoryDataset)null);
				else{
					categoryDataset = new DefaultCategoryDataset();
	        		categoryDataset.setValue(0,"posicion","posicion");
					log.add(categoryDataset);
				}
			}
		}
	}
	
	private String displayName(String sequence){

    	if(parameters.getParameters().get(ParametersMap.APOPTOSIS_STRING).equals(sequence))
    		return "Apoptosis "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_N).equals(sequence))
    		return "Grown N "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_S).equals(sequence))
    		return "Grown S "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_E).equals(sequence))
    		return "Grown E "+sequence;
    	else if(parameters.getParameters().get(ParametersMap.GROWN_STRING_W).equals(sequence))
    		return "Grown W "+sequence;
    	else
    		return sequence;
    }

	public ArrayList getLog(){
		return(log); 
	}
	
	public int resetLog(int ultPos){
		int iterations,size;
		
		iterations = ultPos/((Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW)*(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_COLUMN))+1;
		size = ((Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_ROW)*(Integer)parameters.getParameters().get(ParametersMap.EVALUATION_DIMENSION_COLUMN))*iterations;		
		
		log=new ArrayList();		

		for(int i=0;i<size;i++)
			log.add((CategoryDataset)null);
//		System.out.print("Nuevo tamaño del historico:");
//		System.out.println(log.size());
		return(iterations);
	}

	public void setPositionLog(int index, CategoryDataset value) {
		log.set(index, value);
		
	}
	
	public void newLog(){
		log = new ArrayList();
	}
	
}
