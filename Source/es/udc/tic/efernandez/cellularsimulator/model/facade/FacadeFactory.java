package es.udc.tic.efernandez.cellularsimulator.model.facade;
/**
 * @autor Enrique Fernández Blanco
 */
public class FacadeFactory {

	
	private FacadeFactory(){}
	
	public static FacadeInterface newInstance(){
		return new StateFacade();
	}
}
