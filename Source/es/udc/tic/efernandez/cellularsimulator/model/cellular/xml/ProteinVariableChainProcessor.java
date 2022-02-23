package es.udc.tic.efernandez.cellularsimulator.model.cellular.xml;

import es.udc.tic.efernandez.cellular.util.globalconfiguration.GlobalConfiguration;
import es.udc.tic.efernandez.cellularsimulator.model.cellular.xml.AbstractGenChainProcessor;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.ProteinVariable;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;

public class ProteinVariableChainProcessor extends AbstractGenChainProcessor {

	public ProteinVariableChainProcessor(AbstractGenChainProcessor nextLink) {
		super(nextLink);
	}

	protected boolean canProcess(Variable variable) {
		return variable instanceof ProteinVariable;
	}

	protected String executeLink(Variable variable) {
		String toReturn ="";
		ProteinVariable proteinVariable = (ProteinVariable) variable;
		if(proteinVariable.isPromotor()){
			toReturn += "Promotor = "+proteinVariable.getSequence()+" Percentage = " + proteinVariable.getPercentage()+"\n";
		}else {
			toReturn += 
						 "Constitutive = " + proteinVariable.isConstitutive()+"\n"
						+ "Sequence = " + proteinVariable.getSequence();
			
	        if(proteinVariable.getSequence().equals(GlobalConfiguration.getProtein(GlobalConfiguration.APOPTOSIS_STRING)))
	            toReturn += " (Apoptosis)";
	        if(proteinVariable.getSequence().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_E)))
	            toReturn += " (Grow E)";
	        if(proteinVariable.getSequence().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_N)))
	            toReturn += " (Grow N)";
	        if(proteinVariable.getSequence().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_W)))
	            toReturn += " (Grow W)";
	        if(proteinVariable.getSequence().equals(GlobalConfiguration.getProtein(GlobalConfiguration.GROWN_STRING_S)))
	            toReturn += " (Grow S)";
	        
	        toReturn += "\n\n";

		}
		return toReturn;
	}

}
