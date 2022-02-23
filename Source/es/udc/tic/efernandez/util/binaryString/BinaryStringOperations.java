/*
 * Created on 13-jul-2005
 */
package es.udc.tic.efernandez.util.binaryString;

/**
 * @author Enrique Fernández Blanco
 * This is an utillity class witch works with Strings composed by 1 0 characters like they are 
 * binary sequence.
 */
public class BinaryStringOperations {

    
    /**
     * 
     */
    private BinaryStringOperations(){}

    /**
     * This makes the not peration over the binary number represented on the string.
     * @param entry the number to negate
     * @return the negation of the binary number represented by the string
     * @exception when an argument does not a valid binary secuence
     */
    public static String not(String entry)throws NonValidSequenceException{
        String response="";
        
        char [] entryArray = entry.toCharArray();
        check(entryArray);
        
        for(int i = 0; i < entryArray.length; i++){
            response += (entryArray[i] == '1')? "0" : "1";
        }
        return response;
    }
    
    /**
     * this mathed makes the operation and over the binary numbers.
     * @param entryA the first number
     * @param entryB the second number
     * @return a binary number as long as the longest entry
     * @exception when an argument does not a valid binary secuence
     */
    public static String and(String entryA, String entryB) throws NonValidSequenceException{
        
        String response = "";
        
        char [] entryAArray = entryA.toCharArray();
        char [] entryBArray = entryB.toCharArray();
        check(entryAArray);
        check(entryBArray);
        
        int minlength = (entryAArray.length >= entryBArray.length)? entryBArray.length : entryAArray.length;
        int maxlength = (entryAArray.length >= entryBArray.length)? entryAArray.length : entryBArray.length;
        
        for(int i = 0; i<minlength; i++){
            response += ((entryAArray[i]=='1') && (entryBArray[i]=='1'))? "1":"0";
        }
        
        for(int i = 0; i < (maxlength - minlength); i++)
            response += "0";
        
        return response;
    }
  
 
    /**
     * this mathed makes the operation or over the binary numbers.
     * @param entryA the first number
     * @param entryB the second number
     * @return a binary number as long as the longest entry
     * @exception when an argument does not a valid binary secuence
     */
    public static String or(String entryA, String entryB) throws NonValidSequenceException{
        
        String response = "";
        
        char [] entryAArray = entryA.toCharArray();
        char [] entryBArray = entryB.toCharArray();
        check(entryAArray);
        check(entryBArray);
        
        int minlength = (entryAArray.length >= entryBArray.length)? entryBArray.length : entryAArray.length;
        int maxlength = (entryAArray.length >= entryBArray.length)? entryAArray.length : entryBArray.length;
        
        for(int i = 0; i<minlength; i++){
            response += ((entryAArray[i]=='1') || (entryBArray[i]=='1'))? "1":"0";
        }
        
        for(int i = 0; i < (maxlength - minlength); i++)
            response += "0";
        
        return response;
    }
    
    public static int dist(String entryA, String entryB) throws NonValidSequenceException{
        
        int dist = 0;
        char [] entryAArray = entryA.toCharArray();
        char [] entryBArray = entryB.toCharArray();
        check(entryAArray);
        check(entryBArray);
  
        int minlength = (entryAArray.length >= entryBArray.length)? entryBArray.length : entryAArray.length;
        int maxlength = (entryAArray.length >= entryBArray.length)? entryAArray.length : entryBArray.length;
 
        for(int i = 0; i < minlength; i++){
            if(entryAArray[i] != entryBArray[i])
                dist++;
        }
        return dist + maxlength - minlength;
    }
    
    public static int convert(String number) throws NonValidSequenceException{
        int toReturn = 0;
        char[] binaryArray = number.toCharArray();
        check(binaryArray);
        for(int i = 0; i < binaryArray.length; i++)
            toReturn += ( binaryArray[i] == '1')? Math.pow(2,i) : 0;
            
       return toReturn;
    }
    
    public static String toBinary(int number, int minlength){
        String toReturn = "";
        
        int rest = number;
        while(rest >= 2){
            toReturn += (rest%2 == 1)? "1" : "0";
            rest = rest/2;
        }
        
        toReturn += (rest ==1)? "1":"0";
        
        if(toReturn.length() < minlength){
            int toIncrease = minlength - toReturn.length();
            for(int i = 0; i < toIncrease; i++){
                toReturn += "0";
            }
        }
        return toReturn;
    }
    
    /**
     * @param entryAArray
     */
    private static void check(char[] entry) throws NonValidSequenceException {
        
        for(int i = 0; i < entry.length; i++){
            if((entry[i]!='1') && (entry[i]!='0'))
                    throw new NonValidSequenceException("Invalid Binary Sequence");
        }
        
    }

}
