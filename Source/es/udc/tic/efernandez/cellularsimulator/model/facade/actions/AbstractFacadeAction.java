package es.udc.tic.efernandez.cellularsimulator.model.facade.actions;

public abstract class AbstractFacadeAction {

	public boolean execute(){
		
		boolean correctExecution = false;
		if(preconditions()){
			process();
			correctExecution = true;
		}
		return correctExecution;
		
	}
	
	protected abstract boolean preconditions();
	
	protected abstract void process();
}
