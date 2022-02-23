package util;

public class MathOperation {

	
	public static Long clamp(Long value, Long lowerLimit, Long upperLimit){
		Long toReturn = value;
		
		toReturn = (value < lowerLimit)? lowerLimit : value;
		toReturn = (value > upperLimit)? upperLimit : value;	
		
		return toReturn;
	}
	
	public static Double clamp(Double value, Double lowerLimit, Double upperLimit){
		Double toReturn = value;
		
		toReturn = (value < lowerLimit)? lowerLimit : value;
		toReturn = (value > upperLimit)? upperLimit : value;	
		
		return toReturn;
	}
	
	public static double NormalizeDouble(double inf, double sup, double data){
		
		double toReturn;
		
			toReturn = (data -inf)/(sup-inf);
		
		return toReturn;
	}
}
