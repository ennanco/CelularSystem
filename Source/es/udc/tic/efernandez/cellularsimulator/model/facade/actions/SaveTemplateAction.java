package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;

public class SaveTemplateAction extends AbstractFacadeAction {

	private String fileName;
	private boolean [][] matriz;
	
	public SaveTemplateAction(FacadeInterface facade,String fileName, boolean [][] matrix) {
		this.fileName = fileName;
		matriz=matrix;
	}
	@Override
	protected boolean preconditions() {
		return matriz.length>0 && !fileName.equals("");
	}

	@Override
	protected void process() {
		try{
			Writer out= new FileWriter(fileName);
			
			String linea = new String("");
			linea=linea.concat(matriz.length+" "+matriz[0].length+" \n");
			out.write(linea,0,linea.length());
			
			for (int i=0;i<matriz.length;i++){
				linea = new String("");
				for(int j=0;j<matriz[0].length;j++){
					if(matriz[i][j])
						linea=linea.concat("1 ");
					else
						linea=linea.concat("0 ");
					
//					if(j!=matriz[0].length-1)
//						linea=linea.concat(" ");
				}
				linea=linea.concat("\n");
				out.write(linea,0,linea.length());
			}
			out.close();
			}catch(IOException exp){
				exp.printStackTrace();
			}
	}

}
