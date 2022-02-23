/*
 * Created on 13-jul-2005
 */
package es.udc.tic.efernandez.util.binaryString;

/**
 * @author Enrique Fernández Blanco
 *This exception is used when we try to use a non binary secuence in the Binary Operations
 */
public class NonValidSequenceException extends Exception {

	private String message;
    public NonValidSequenceException(String args) {
        super();
        this.message = args;
    }    
    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }
}
