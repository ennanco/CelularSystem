package es.udc.tic.efernandez.util.compression.zip;
/**
 * This class implements the more common operation the compresion of a set of files with the ZIP algorithm.
 * @author Enrique Fernandez-Blanco
 * 20-02-2008
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
	
	private ZipFileManager(){}
	
	/**
	 *  This operation compress the files that are included in the enumeration
	 * @param zipFileName the name for the file
	 * @param files list of the files that will be stored in the zip file
	 */
	public static void deflation(String zipFileName, File[] files){
		ZipFileManager.deflation(zipFileName,files,"");
	}
	
	/**
	 * Similar to the above function but including a comment into the zip File
	 * @param zipFileName the name for the file
	 * @param files directories to be compressed into the zip file
	 * @param comment the string with the comment that will be included
	 */
	public static void deflation(String zipFileName, File[] files, String comment){
		try {
			// prepare an output channel which calculates the CheckSum
			CheckedOutputStream csum = new CheckedOutputStream(
					new FileOutputStream(zipFileName),new Adler32());
			// create the ZipOutput that will use the channels created before
			ZipOutputStream out = new ZipOutputStream(csum);
			out.setComment(comment);
			//Read and write each entry
			for(File f:files){
				Vector list_files = new Vector(Arrays.asList(f.list()));
				for(int i = 0; i < list_files.size();i++){
					String fileName = (String) list_files.get(i);
					BufferedReader in = new BufferedReader(new FileReader(
							f.getCanonicalPath()+File.separator+fileName));
					// start writing the input files into the zip files
					out.putNextEntry(new ZipEntry(fileName));
					int data;
					while((data = in.read()) != -1)
						out.write(data);
					in.close();
					out.closeEntry();
				}
			}
			// close the zip file
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function inflates a zip file in the destination gived.
	 * @param zipFileName the file to be inflated
	 * @param destination the route where the content of the zip file will be decompressed
	 */
	public static void inflation(String zipFileName, String destination){
		
	      CheckedInputStream csum;
		try {
			// load the zip file
			csum = new CheckedInputStream(new FileInputStream(zipFileName), new Adler32());
		    ZipInputStream in = new ZipInputStream(new BufferedInputStream(csum));
		    // process entry by entry to inflates the files at the zip one
		    ZipEntry entry;
		    while((entry = in.getNextEntry()) != null){
			    BufferedWriter out = new BufferedWriter(new FileWriter(destination + entry.getName()));
		    	int data;
		    	while((data = in.read())!= -1)
		    		out.write(data);
		    	out.close();
		    	in.closeEntry();
		    }
		   // close the file
		   in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
