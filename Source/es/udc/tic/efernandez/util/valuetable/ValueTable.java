/*
 * Created on 09-ago-2005
 */
package es.udc.tic.efernandez.util.valuetable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Enrique Fernández Blanco
 */
public class ValueTable {
    
    private static int defaultReturn = 0;

    private static ArrayList list = new ArrayList();
    private static HashMap lifeTimes = new HashMap();
        
    public static void add(String arg0,int arg1) {
        list.add(arg0);
        lifeTimes.put(arg0,new Integer(arg1));
        
    }

    public static String getWord(int pos) {
        return (String)list.get(pos);
    }
    
    public static int getNumber(String entry){
        Object object = lifeTimes.get(entry);
        if(object != null)
            return ((Integer)object).intValue();
        else
            return defaultReturn;
    }
    
    public static void setDefault(int i){
        defaultReturn = i;
    }

    public static int getDefault(){
		return defaultReturn;
	}

	public static boolean isEmpty() {
        return list.isEmpty();
    }

    public static String remove(int pos) {
        String sequence =(String)list.remove(pos);
        lifeTimes.remove(sequence);
        return sequence;
    }

    public static int size() {
        return list.size();
    }
}
