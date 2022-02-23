package es.udc.tic.efernandez.util.files;

import java.io.File;
/**
 * This utility class includes some static method which realize common operation avore diferent files.
 * 
 * 
 * @author Enrique Fernandez-Blanco
 * 21-02-2008
 *
 */
public class FileUtilities {

	
	private FileUtilities(){}
	/**
	 * This utility method deletes recursively de files and directories given as reference.
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file){
		if(file.isDirectory()){
			for(File f: file.listFiles()){
				deleteFile(f);
			}
			return file.delete();
		} else {
			return file.delete();
		}
	}
}
