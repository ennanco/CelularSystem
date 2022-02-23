package es.udc.tic.efernandez.cellularsimulator.model.cellular.xml;

import es.udc.tic.efernandez.cellular.util.globalconfiguration.GlobalConfiguration;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.xml.AbstractGenChainProcessor;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.DNAVariable;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;
import es.udc.tic.efernandez.util.binaryString.BinaryStringOperations;
import es.udc.tic.efernandez.util.binaryString.NonValidSequenceException;

public class DNAVariableChainProcessor extends AbstractGenChainProcessor {

	public DNAVariableChainProcessor(AbstractGenChainProcessor nextLink) {
		super(nextLink);
	}

	protected boolean canProcess(Variable variable) {
		return variable instanceof DNAVariable;
	}

	protected String executeLink(Variable variable) {
	   	DNAVariable concreteVariable = (DNAVariable) variable;
        String[] activePart = concreteVariable.getActivePart();
        String toReturn = "";
        String temp="";
        
        if(concreteVariable.getGeneSequences().equals(GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING)))
            temp += " (Apoptosis)";
        if(concreteVariable.getGeneSequences().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_E)))
            temp += " (Grow E)";
        if(concreteVariable.getGeneSequences().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N)))
            temp += " (Grow N)";
        if(concreteVariable.getGeneSequences().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_W)))
            temp += " (Grow W)";
        if(concreteVariable.getGeneSequences().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_S)))
            temp += " (Grow S)";
        
        
        try{
            toReturn += "\ngen\n\tsequence="+BinaryStringOperations.convert(concreteVariable.getGeneSequences())+
            temp // si es de crecimiento o de apoptosis.
            +"\n\tactivePercentage="+ concreteVariable.getActivePercentage()+
            "\n\tconstitutive="+concreteVariable.getConstitutive();
            for(int i = 0; i < activePart.length;i++){
                toReturn += "\n\tactivePart="+BinaryStringOperations.convert(activePart[i]);
            }
        }catch(NonValidSequenceException e){
            e.printStackTrace();
        }
        return toReturn;
 	}

}
