/*
 * Created on 26-ago-2005
 */
package es.udc.tic.efernandez.cellular.util.globalconfiguration;

/**
 * @author Enrique Fernández Blanco
 */
public class GlobalConfiguration {

    public final static int PROTEIN_LOWER_LIMIT = 0;
    public final static int PROTEIN_HIGH_LIMIT = 1;
    public final static int PROTEIN_APOPTOSIS_LIMIT = 2;
    
    private static float[] LIMIT_VALUES = {5.0F,10.0F,10.0F};

    public final static int APOPTOSIS_STRING = 0;
    public final static int GROWN_STRING_W = 1;//"1000";
    public final static int GROWN_STRING_N = 2;//"1100";
    public final static int GROWN_STRING_E = 3;//"1010";
    public final static int GROWN_STRING_S = 4;//"1110";

    private static String[] PROTEINS_VALUES = {"0000","1100","1010","1110","1000"};
    
    private GlobalConfiguration(){}

    public static float getLimit(int id){
    	return LIMIT_VALUES[id];
    }
    
    public static void setLimit(int id, float value){
    	LIMIT_VALUES[id] = value;
    }
    
    public static String getProtein(int id){
    	return PROTEINS_VALUES[id];
    }
    
    public static void setProtein(int id, String value){
    	PROTEINS_VALUES[id] = value;
    }
}
