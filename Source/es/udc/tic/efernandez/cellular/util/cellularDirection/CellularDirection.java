package es.udc.tic.efernandez.cellular.util.cellularDirection;

import java.awt.Point;



public class CellularDirection {

	public final static int OMNIDIRECTION = 8;
	
	public final static int UP = 0;
	
	public final static int DOWN = 1;
	
	public final static int LEFT = 2;
	
	public final static int RIGHT = 3;
	
	public final static int UP_LEFT = 4;
	
	public final static int UP_RIGHT = 5;
	
	public final static int DOWN_LEFT = 6;
	
	public final static int DOWN_RIGHT = 7;
	
	public final static int NUMBERDIRECTIONS = 8;
	
	public final static int NONVALID = -1;
	
	private CellularDirection(){}
	
	public static int direction(Point origin,Point  destiny){
		
		int direction;
		
		int difference_Y = destiny.y - origin.y;
		int difference_X = destiny.x -origin.x ;
		
		if(difference_Y > 0)
			if(difference_X >0)
				direction = UP_RIGHT;
			else if (difference_X <0)
				direction = UP_LEFT;
			else
			direction = UP;
		else if(difference_Y < 0)
			if(difference_X > 0)
				direction = DOWN_RIGHT;
			else if(difference_X < 0)
				direction = DOWN_LEFT;
			else
				direction = DOWN;
		else
			if (difference_X > 0)
				direction = RIGHT;
			else
				direction = LEFT;
		
		return direction;
	}
	
	public static int oposite(int direction){
		int toReturn;
		switch(direction){
		case UP: 
			toReturn = DOWN;
			break;
		case DOWN:
			toReturn = UP;
			break;
		case RIGHT:
			toReturn = LEFT;
			break;
		case LEFT:
			toReturn = RIGHT;
			break;
		case DOWN_LEFT:
			toReturn = UP_RIGHT;
			break;
		case DOWN_RIGHT:
			toReturn = UP_LEFT;
			break;
		case UP_LEFT:
			toReturn = DOWN_RIGHT;
			break;
		case UP_RIGHT:
			toReturn = DOWN_LEFT;
			break;
		case OMNIDIRECTION:
			toReturn = OMNIDIRECTION;
			break;
		default:
			toReturn = NONVALID;
			break;
		}
		return toReturn;
		
	}

	public static int desplacementX(int direction){
		int desplacement = 0;
		switch(direction){
		case UP:
		case DOWN:
			desplacement = 0;
			break;
		case LEFT:
		case DOWN_LEFT:
		case UP_LEFT:
			desplacement = -1;
			break;
		case RIGHT:
		case DOWN_RIGHT:
		case UP_RIGHT:
			desplacement = 1;
			break;
		default:
			desplacement = 0;
			break;
		}
		return desplacement;
	}
	
	public static int desplacementY(int direction){
		int desplacement = 0;
		switch(direction){
		case RIGHT:
		case LEFT:
			desplacement = 0;
			break;
		case UP:
		case UP_RIGHT:
		case UP_LEFT:
			desplacement = -1;
			break;
		case DOWN:
		case DOWN_RIGHT:
		case DOWN_LEFT:
			desplacement = 1;
			break;
		default:
			desplacement = 0;
			break;
		}
		return desplacement;
	}

}
