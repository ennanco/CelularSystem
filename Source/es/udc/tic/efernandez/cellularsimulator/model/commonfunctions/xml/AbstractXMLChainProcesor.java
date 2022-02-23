package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.xml;

import java.io.IOException;

import org.w3c.dom.Document;

import com.generationjava.io.xml.XmlWriter;

import es.udc.tic.efernandez.cellularsimulator.model.util.pattern.FileTraining;
import es.udc.tic.efernandez.geneticalgorithm.population.Population;

public abstract class AbstractXMLChainProcesor {

	protected AbstractXMLChainProcesor nextLink = null;
	
	AbstractXMLChainProcesor(AbstractXMLChainProcesor nextLink){
		this.nextLink = nextLink;
	}
	
	public void store(XmlWriter xmlWriter, Population population) throws IOException{
		if(canProcess(population)){
			executeWrite(xmlWriter, population);
		}else if(nextLink != null){
			nextLink.store(xmlWriter, population);
		}//else
	}
	
	public Population load(Document document, boolean [][] template, FileTraining training){
		if(canProcess(document)){
			return executeRead(document,template, training);
		}else if(nextLink != null){
			return nextLink.load(document, template, training);
		}//else
		return null;
		
	}
	
	protected abstract Population executeRead(Document document, boolean [][] template, FileTraining training);
	
	protected abstract void executeWrite(XmlWriter xmlWriter, Population population) throws IOException;
	
	protected abstract boolean canProcess(Document document);
	
	protected abstract boolean canProcess(Population population);
}
