package es.udc.tic.efernandez.cellularsimulator.model.cellular.enviroment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BaseDVFactory;

import es.udc.tic.efernandez.cellular.enviroment.Enviroment;
import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.Gradient;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist.BasicGradientList;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.gradient.gradientlist.GradientList;

public class GradientEnviromentWrapper implements Enviroment {

	private Enviroment baseEnviroment;
	private GradientList gradientList;
	
	public GradientEnviromentWrapper(Enviroment enviroment) {
		baseEnviroment = enviroment;
		gradientList = new BasicGradientList();
	}
	
	public void inicialize(int maxRow, int maxColumn){
		baseEnviroment.inicialize(maxRow, maxColumn);
	}
	
	public void addGradient(Gradient gradient){
		gradientList.addGradient(gradient);
	}
	
	public GradientList gradientList(){
		return gradientList;
	}

	public void removeGradient(int index){
		gradientList.removeGradient(index);
	}
	
	public int consultProtein(Point position, String sequence) {
		
		return baseEnviroment.consultProtein(position, sequence);
	}

	public void setGradients(GradientList gradientList){
		this.gradientList = gradientList;
	}

	public Protein[] get(Point p, long instant) {
		ArrayList<Protein> obteined = new ArrayList();
		Protein[] fromEnviroment = baseEnviroment.get(p, instant);
		Protein[] toReturn = new Protein[0];
		Protein temp;
		
		if(fromEnviroment!=null)
			for(int i = 0; i<fromEnviroment.length; i++)
				obteined.add(fromEnviroment[i]);
		
		for(int i = 0; i < gradientList.size(); i++){
			temp = gradientList.getGradient(i).getProtein(p,instant);
			if(temp!=null)
				obteined.add(temp);
		}
		toReturn = obteined.size()==0? null : obteined.toArray(toReturn);
		return toReturn;
	}

	public void set(Point p, Protein protein) {
		baseEnviroment.set(p, protein);
	}

	public void update(long instant) {
		baseEnviroment.update(instant);
	}

	public boolean valid(int row, int column) {
		return baseEnviroment.valid(row, column);
	}
	
	public Object clone(){
		GradientEnviromentWrapper gradientEnviromentWrapper = new GradientEnviromentWrapper((Enviroment)baseEnviroment.clone());
		
		for(int i = 0; i<gradientList.size(); i++){
			gradientEnviromentWrapper.addGradient(gradientList.getGradient(i));
		}
		
		return gradientEnviromentWrapper;
	}

	public Protein getProtein(Point p, String sequence) {
		return baseEnviroment.getProtein(p, sequence);
	}

	public Set listedProteins(Point p) {
		return baseEnviroment.listedProteins(p);
	}


}
