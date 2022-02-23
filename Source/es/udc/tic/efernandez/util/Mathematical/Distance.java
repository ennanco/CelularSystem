package es.udc.tic.efernandez.util.Mathematical;

import java.awt.Point;

public class Distance {

	private Distance(){}
	
	public static double euclideanDistance(Point p1,Point p2){
		// sqrt ( x^2 + y^2)
		return Math.sqrt(Math.pow((p1.x-p2.x),2.0D)+Math.pow((p1.y-p2.y),2.0D));
	}
	
	public static int manhattanDistance(Point p1, Point p2){
		// |x1-x2|+|y1-y2|
		return Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y);
	}
	
	public static int chebyshevDistance(Point p1, Point p2){
		// square distance
		return Math.max(Math.abs(p1.x - p2.x),Math.abs(p1.y - p2.y));
	}
}
