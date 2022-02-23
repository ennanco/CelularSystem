package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.Gradient;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist.GradientList;
import es.udc.tic.efernandez.cellularsimulator.model.facade.FacadeInterface;

public class SaveGradientAction extends AbstractFacadeAction {
	
	private String fileName;
	private FacadeInterface facade;
	
	public SaveGradientAction(FacadeInterface facade,String fileName) {
		this.fileName = fileName;
		this.facade = facade;
	}

	@Override
	protected boolean preconditions() {
		return !facade.getGradients().isEmpty() && facade.getGradients().size()>0  && !fileName.equals("");
	}

	@Override
	protected void process() {
		try{
			Writer out= new FileWriter(fileName);
			GradientList gradients = facade.getGradients();
			Gradient gradient;
			
			for (int i=0;i<gradients.size();i++){
				if((gradient=gradients.getGradient(i))!=null){
					String linea = new String("");
					linea=linea.concat(gradient.getSequence()+" "+((Float)gradient.getDistance()).intValue()+" "+gradient.getPosition().x+" "+gradient.getPosition().y+"\n");
					out.write(linea,0,linea.length());
				}
			}
			out.close();
			}catch(IOException exp){
				exp.printStackTrace();
			}
	}

}
