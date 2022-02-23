/*
 * Created on 06-ago-2005
 */
package es.udc.tic.efernandez.cellular.util.proteinidentifier;

import java.awt.Point;

import es.udc.tic.efernandez.cellular.protein.Protein;
import es.udc.tic.efernandez.cellular.util.globalconfiguration.GlobalConfiguration;

/**
 * @author Enrique Fernández Blanco
 */
public class ProteinIdentifier {

    private ProteinIdentifier() {
        super();
    }
    /**
     * The operation verifies if the protein represents any apoptosis protein
     * @param proteine the protein checkd
     * @return true when it's a nocive protein
     */
    public static boolean checkApoptosis(Protein proteine){
        String sequence = proteine.getSequence();
        String subsequence = null;
        String tailSubsequence = null;
        if(proteine.getSequence().length() >= GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING).length())
        	subsequence = sequence.substring(0,GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING).length());
        if(proteine.getSequence().length() > GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING).length())
        	tailSubsequence = sequence.substring(GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING).length(), sequence.length());
        
        return (subsequence != null) && subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING))
        		&& ((tailSubsequence == null)||(tailSubsequence.equals(zeroChain(tailSubsequence.length()))));
    }
    
    /**
     * 
     * @return the list of nocive sequences
     */
	public static String[] getNociveList(){
        return new String[]{GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING)};
    }
        
	/**
     * Function checks if the a given protein is a growing protein
     * @param proteine the protein to check
     * @return true when it's a growing protein
     */
    public static boolean checkGrowthing(Protein proteine){
        String sequence = proteine.getSequence();
        String subsequence = null;
        String tailSubsequence = null;
        if(proteine.getSequence().length() >= GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N).length())
        	subsequence = sequence.substring(0,GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N).length());
        if(proteine.getSequence().length() > GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N).length())
        	tailSubsequence = sequence.substring(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N).length(), sequence.length());
        
        return (subsequence != null) && (subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_W)) ||
        	subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N))||
        	subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_E))||
        	subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_S)))
        	&& ((tailSubsequence == null)||(tailSubsequence.equals(zeroChain(tailSubsequence.length()))));
    }

    /**
     * Determines where the the protein tries to generate a new cell
     * @param proteine the given protein
     * @param p the point where the protein has be generated
     * @return The point where a new cell can be aparence
     */
    public static Point getPosition(Protein proteine,Point p) {
        String subsequence = proteine.getSequence().substring(0,GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N).length());
        if(subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_W))){
            return new Point(p.x,p.y-1);
        } else if(subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N))){
            return new Point(p.x-1,p.y);
        } else if(subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_E))){
            return new Point(p.x,p.y+1);
        } else if(subsequence.equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_S))){
            return new Point(p.x+1, p.y);
        } else
            return null;
    }

    private static Object zeroChain(int length) {
    	String sequence = "";
    	for(int i = 0; i < length; i++)
    		sequence += "0";
		return sequence;
	}

}
