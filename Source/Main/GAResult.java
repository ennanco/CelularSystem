package Main;

public class GAResult {
	
	
	private double minTrainTrainValue;
	private double minTrainTestValue;
	private String minTrainTrainSerie;
	private String minTrainTestSerie;
	private String minTrainIndividual;
	private double minTestTrainValue;
	private double minTestTestValue;
	private String minTestTrainSerie;
	private String minTestTestSerie;
	private String minTestIndividual;
	private double mean;
	private double std;

	
	public GAResult(double minTrainTrainValue, double minTrainTestValue,
			String minTrainTrainSerie, String minTrainTestSerie,
			String minTrainIndividual, double minTestTrainValue,
			double minTestTestValue, String minTestTrainSerie,
			String minTestTestSerie, String minTestIndividual, 
			double mean, double std) {
		this.minTrainTrainValue = minTrainTrainValue;
		this.minTrainTestValue = minTrainTestValue;
		this.minTrainTrainSerie = minTrainTrainSerie;
		this.minTrainTestSerie = minTrainTestSerie;
		this.minTrainIndividual = minTrainIndividual;
		this.minTestTrainValue = minTestTrainValue;
		this.minTestTestValue = minTestTestValue;
		this.minTestTrainSerie = minTestTrainSerie;
		this.minTestTestSerie = minTestTestSerie;
		this.minTestIndividual = minTestIndividual;
		this.mean = mean;
		this.std = std;	
	}


	public double getMinTrainTrainValue() {
		return minTrainTrainValue;
	}


	public double getMinTrainTestValue() {
		return minTrainTestValue;
	}


	public String getMinTrainTrainSerie() {
		return minTrainTrainSerie;
	}


	public String getMinTrainTestSerie() {
		return minTrainTestSerie;
	}


	public String getMinTrainIndividual() {
		return minTrainIndividual;
	}


	public double getMinTestTrainValue() {
		return minTestTrainValue;
	}


	public double getMinTestTestValue() {
		return minTestTestValue;
	}


	public String getMinTestTrainSerie() {
		return minTestTrainSerie;
	}


	public String getMinTestTestSerie() {
		return minTestTestSerie;
	}


	public String getMinTestIndividual() {
		return minTestIndividual;
	}


	public double getMean() {
		return mean;
	}


	public double getStd() {
		return std;
	}



}
