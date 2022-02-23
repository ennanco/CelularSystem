package es.udc.tic.efernandez.cellularsimulator.visualinterface.mainwindow;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

public class GeneralFileFilter extends FileFilter {

	private String description = "";
	private ArrayList extensions;
	public GeneralFileFilter() {
		super();
		extensions = new ArrayList();
	}

	public boolean accept(File f) {
		if(f.isDirectory() || acceptedExtension(f))
			return true;
		else
			return false;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void addExtension(String extension){
		extensions.add(extension.toLowerCase());
	}
	
	private String getExtension(File f){
		String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
	}
	
	private boolean acceptedExtension(File f){
		
		String extension = getExtension(f);
		boolean accepted = false;
		
		for(int i = 0; (!accepted) && (i < extensions.size()); i++){
			accepted = (((String)extensions.get(i)).equals(extension));
		}
		return accepted;
		
	}

}
