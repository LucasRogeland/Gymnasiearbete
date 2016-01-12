package com.gameproject.util;

public class Clock {

	private static boolean paused = false;
	public static double lastFrame = 0;
	public static double totalTime = 0;
	public static float delta = 0;
	public static float multiplier  = 1;
	
	public static long getTime(){
		return System.currentTimeMillis();
	}
	
	public static void update(){
		if(lastFrame == 0)
			lastFrame = getTime();
		
		delta = getDelta();
		totalTime += delta;
	}
	
	public static float getDelta(){
		double currentTime = getTime();
		float delta = (float)(currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	
	public static float Delta(){
		if(paused)
			return 0;
		else
			return delta * multiplier;
	}
	
	public static double TotalTime(){
		return totalTime/1000;
	}
	
	public static double Multiplier(){
		return multiplier;
	}
	
	public static void ChangeMultiplier(int change){
		if(multiplier + change < -1 && multiplier + change > 7){
			
		}else{
			multiplier +=change;
		}
	}
	
	public static void pause(){
		if(paused)
			paused = false;
		else
			paused = true;
	}
	
}
