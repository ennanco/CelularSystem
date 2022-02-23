package es.udc.tic.efernandez.cellularsimulator.model.commonfunctions.parametersmanagerxml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.generationjava.io.xml.PrettyPrinterXmlWriter;
import com.generationjava.io.xml.SimpleXmlWriter;
import com.generationjava.io.xml.XmlWriter;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

import es.udc.tic.efernandez.cellularsimulator.model.util.ParametersMap;

public class ParametersManagerXML {

	
	
public static void store(String name,ParametersMap parameters){
		
		int index =name.lastIndexOf(".");
		name = ( index >= 0 && name.substring(index).toLowerCase().equals(".prm"))? name : name+".prm";
		
		File file = new File(name);
		XmlWriter xmlWriter;
		HashMap parametersMap = parameters.getParameters();

        try {
            boolean created = file.createNewFile();
            if(!created){
                file.delete();
                file.createNewFile();
            }
            
            FileWriter fileWriter = new FileWriter(file);
            
            xmlWriter = new PrettyPrinterXmlWriter(new SimpleXmlWriter(fileWriter));
            
            xmlWriter.writeXmlVersion("1.0","ISO-8859-1");
            xmlWriter.writeEntity("parameters");
            	xmlWriter.writeAttribute("protein_lower_limit", parametersMap.get(ParametersMap.PROTEIN_LOWER_LIMIT));
            	xmlWriter.writeAttribute("protein_high_limit", parametersMap.get(ParametersMap.PROTEIN_HIGH_LIMIT));
            	xmlWriter.writeAttribute("protein_apoptosis_limit", parametersMap.get(ParametersMap.PROTEIN_APOPTOSIS_LIMIT));
            	xmlWriter.writeAttribute("apoptosis_string", parametersMap.get(ParametersMap.APOPTOSIS_STRING));
            	xmlWriter.writeAttribute("grown_string_w", parametersMap.get(ParametersMap.GROWN_STRING_W));
            	xmlWriter.writeAttribute("grown_string_n", parametersMap.get(ParametersMap.GROWN_STRING_N));
            	xmlWriter.writeAttribute("grown_string_e", parametersMap.get(ParametersMap.GROWN_STRING_E));
            	xmlWriter.writeAttribute("grown_string_s", parametersMap.get(ParametersMap.GROWN_STRING_S));
            	xmlWriter.writeAttribute("number_variables", parametersMap.get(ParametersMap.NUMBER_VARIABLES));
            	xmlWriter.writeAttribute("number_individuals", parametersMap.get(ParametersMap.NUMBER_INDIVIDUALS));
            	xmlWriter.writeAttribute("number_generations", parametersMap.get(ParametersMap.NUMBER_GENERATIONS));
            	xmlWriter.writeAttribute("minimization_number_parameter", parametersMap.get(ParametersMap.MINIMIZATION_NUMBER_PARAMETER));
            	xmlWriter.writeAttribute("crossover_percentage", parametersMap.get(ParametersMap.CROSSOVER_PERCENTAGE));
            	xmlWriter.writeAttribute("mutation_percentage", parametersMap.get(ParametersMap.MUTATION_PERCENTAGE));
            	xmlWriter.writeAttribute("evaluation_dimension_row", parametersMap.get(ParametersMap.EVALUATION_DIMENSION_ROW));
            	xmlWriter.writeAttribute("evaluation_dimension_column", parametersMap.get(ParametersMap.EVALUATION_DIMENSION_COLUMN));
            	xmlWriter.writeAttribute("evaluation_num_iteration", parametersMap.get(ParametersMap.EVALUATION_NUM_ITERATION));
            	xmlWriter.writeAttribute("active_part_max_proteine", parametersMap.get(ParametersMap.ACTIVE_PART_MAX_PROTEINE));
            	xmlWriter.writeAttribute("max_number_of_proteine", parametersMap.get(ParametersMap.MAX_NUMBER_OF_PROTEINE));
            	xmlWriter.writeAttribute("protein_min_length", parametersMap.get(ParametersMap.PROTEINE_MIN_LENGTH));
            xmlWriter.endEntity();
            xmlWriter.close();
            
            fileWriter.close();
            
         } catch (IOException e1) {
             e1.printStackTrace();
         }
	}
	
	public static HashMap load(String fileName) {

		File file = new File(fileName);
		HashMap map = new HashMap();
        try {
			Document document = DocumentBuilderFactoryImpl.newInstance().newDocumentBuilder().parse(file);
			
			NodeList individuals = document.getElementsByTagName("parameters");
			Element individual  = (individuals.getLength() >0 )?
                    (Element) individuals.item(0):null;
            if( individual == null) 
            	return null;
            map.put(ParametersMap.PROTEIN_LOWER_LIMIT,new Float(individual.getAttribute("protein_lower_limit")));
            map.put(ParametersMap.PROTEIN_HIGH_LIMIT,new Float(individual.getAttribute("protein_high_limit")));
            map.put(ParametersMap.PROTEIN_APOPTOSIS_LIMIT,new Float(individual.getAttribute("protein_apoptosis_limit")));
            map.put(ParametersMap.APOPTOSIS_STRING,individual.getAttribute("apoptosis_string"));
            map.put(ParametersMap.GROWN_STRING_W,individual.getAttribute("grown_string_w"));
            map.put(ParametersMap.GROWN_STRING_N,individual.getAttribute("grown_string_n"));
            map.put(ParametersMap.GROWN_STRING_E,individual.getAttribute("grown_string_e"));
            map.put(ParametersMap.GROWN_STRING_S,individual.getAttribute("grown_string_s"));
            map.put(ParametersMap.NUMBER_VARIABLES,new Integer(individual.getAttribute("number_variables")));
            map.put(ParametersMap.NUMBER_INDIVIDUALS, new Integer(individual.getAttribute("number_individuals")));
            map.put(ParametersMap.NUMBER_GENERATIONS, new Integer(individual.getAttribute("number_generations")));
            map.put(ParametersMap.MINIMIZATION_NUMBER_PARAMETER,new Float(individual.getAttribute("minimization_number_parameter")));
            map.put(ParametersMap.CROSSOVER_PERCENTAGE,new Float(individual.getAttribute("crossover_percentage")));
            map.put(ParametersMap.MUTATION_PERCENTAGE,new Float(individual.getAttribute("mutation_percentage")));
            map.put(ParametersMap.EVALUATION_DIMENSION_ROW,new Integer(individual.getAttribute("evaluation_dimension_row")));
            map.put(ParametersMap.EVALUATION_DIMENSION_COLUMN,new Integer(individual.getAttribute("evaluation_dimension_column")));
            map.put(ParametersMap.EVALUATION_NUM_ITERATION,new Integer(individual.getAttribute("evaluation_num_iteration")));
            map.put(ParametersMap.ACTIVE_PART_MAX_PROTEINE,new Integer(individual.getAttribute("active_part_max_proteine")));
            map.put(ParametersMap.MAX_NUMBER_OF_PROTEINE,new Integer(individual.getAttribute("max_number_of_proteine")));
            map.put(ParametersMap.PROTEINE_MIN_LENGTH,new Integer(individual.getAttribute("protein_min_length")));

        } catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return map;

		
	}
}
