/*
 * Created on 15-ago-2005
 */
package es.udc.tic.efernandez.cellular.enviroment;

import es.udc.tic.efernandez.cellular.util.configurationmanager.ConfigurationManager;

/**
 * @author Enrique Fernández Blanco
 */
public class EnviromentFactory {

	private static String ENVIROMENT_CLASS_NAME_PARAMETER="EnviromentFactory/enviromentClassName";
	private static Enviroment baseEnviroment = null;;
	
    public static Enviroment getInstance(int maxRow, int maxColumn){
    	String enviromentClassName;
    	Class enviromentClass;
    	
    	try {
    		Enviroment enviroment;
    		if(baseEnviroment == null){
        		enviromentClassName = ConfigurationManager.getParameter(ENVIROMENT_CLASS_NAME_PARAMETER);
        		enviromentClass = Class.forName(enviromentClassName);
        		enviroment = (Enviroment)enviromentClass.newInstance();
        	} else{
        		enviroment = (Enviroment)baseEnviroment.clone();
        	}
    		enviroment.inicialize(maxRow, maxColumn);
    		return enviroment;
		} catch (ClassNotFoundException e) {
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public static void register(Enviroment enviroment){
    	baseEnviroment = enviroment;
    }
}
