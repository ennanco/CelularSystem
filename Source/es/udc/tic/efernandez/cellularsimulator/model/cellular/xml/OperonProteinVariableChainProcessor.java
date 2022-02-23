package es.udc.tic.efernandez.cellularsimulator.model.cellular.xml;

import es.udc.tic.efernandez.cellular.util.globalconfiguration.GlobalConfiguration;
import es.udc.tic.efernandez.cellularsimulator.model.geneticalgorithm.variable.OperonProteinVariable;
import es.udc.tic.efernandez.geneticalgorithm.variable.Variable;

public class OperonProteinVariableChainProcessor extends
		AbstractGenChainProcessor {

	public OperonProteinVariableChainProcessor(
			AbstractGenChainProcessor nextLink) {
		super(nextLink);
	}

	@Override
	protected boolean canProcess(Variable variable) {
		return variable instanceof OperonProteinVariable;
	}

	@Override
	protected String executeLink(Variable variable) {
		String toReturn ="";
		OperonProteinVariable proteinVariable = (OperonProteinVariable) variable;
		if(proteinVariable.isPromotor()){
			toReturn += "Promotor = "+proteinVariable.getSequence()+" Percentage = " + proteinVariable.getPercentage()+"\n";
		} else if(proteinVariable.isOperon()){
			toReturn+= 
					 "OperonConstitutive = " + proteinVariable.isConstitutive()+"\n"
					+ "OperonLength = "+ proteinVariable.getOperonLength()+"\n"
					+ "OPERON\n\n";
		} else {
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
