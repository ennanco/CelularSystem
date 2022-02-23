package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.generationjava.io.xml.PrettyPrinterXmlWriter;
import com.generationjava.io.xml.SimpleXmlWriter;
import com.generationjava.io.xml.XmlWriter;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;

public class XMLManager {

	private static AbstractXMLChainProcesor chain = new XMLOperonProteinVariableChain(
			new XMLProteinVariableChain(new XMLDNAVariableChain(null)));
	
	private XMLManager() {}

	public static void store(String name,Population population){
		
		//To be sure that the file has the xml extension
		int index =name.lastIndexOf(".");
		name = ( index >= 0 && name.substring(index).toLowerCase().equals(".xml"))? name : name+".xml";
		
		File file = new File(name);
		XmlWriter xmlWriter;

        try {
            boolean created = file.createNewFile();
            if(!created){
                file.delete();
                file.createNewFile();
            }
            
            FileWriter fileWriter = new FileWriter(file);
            
            xmlWriter = new PrettyPrinterXmlWriter(new SimpleXmlWriter(fileWriter));
            
            xmlWriter.writeXmlVersion("1.0","ISO-8859-1");
            chain.store(xmlWriter,population);
            xmlWriter.close();
            
            fileWriter.close();
            
         } catch (IOException e1) {
             e1.printStackTrace();
         }
	}
	
	public static Population load(String fileName, boolean[][] template, FileTraining training) {

		File file = new File(fileName);
        try {
			Document document = DocumentBuilderFactoryImpl.newInstance().newDocumentBuilder().parse(file);
			return chain.load(document, template, training);

        } catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("Incorrect FileName or not present at the path");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;

		
	}
}
