package es.udc.tic.efernandez.util.cronometer;

public class Cronometer {

	private static long  globalTime= 0;
	private static long partialTime =0;
	private static boolean stopped = true;
	
	private Cronometer() {}
		
	public static void reset(){
		globalTime = 0;
		partialTime = 0;
		stopped = true;
	}
	
	public static void start(){
		stopped = false;
		partialTime = System.currentTimeMillis();
	}
	
	public static void stop(){
		partialTime = System.currentTimeMillis() - partialTime;
		globalTime += partialTime;
		stopped = true;
	}
	
	public static long getGlobalTime(){
		return stopped? globalTime: globalTime + getPartialTime();
	}
	
	public static long getPartialTime(){
		return stopped? partialTime: System.currentTimeMillis() - partialTime;
	}
	
	

}
