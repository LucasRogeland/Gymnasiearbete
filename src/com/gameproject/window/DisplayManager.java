package com.gameproject.window;


import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

import org.lwjgl.glfw.GLFWErrorCallback;


public class DisplayManager {

	private static Display 			 avtiveDisplay;
	private static GLFWErrorCallback errorCallback;
   
    
	public static void create(String title, int width, int height, boolean fullscreen){
		if(avtiveDisplay == null)
			avtiveDisplay = new Display(title, width, height, fullscreen);
		else
			System.out.println("display allready created");
	}
	
	public static void init(){
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		//If glfw is not initialized, initialize it; 
		if ( glfwInit() != GLFW_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
	}
	
	public static void update(){
		avtiveDisplay.update();
	}
	
	public static void setTitle(String title){
		avtiveDisplay.setTitle(title);
	}
	
	public static void setVsync(boolean vsync){
		if(vsync){
			glfwSwapInterval(1);
		}else{
			glfwSwapInterval(0);
		}
	}
	
	public static long getActiveDisplay(){
		return avtiveDisplay.getDisplay();
	}
	
	public static int getWidth(){
		return avtiveDisplay.getWidth();
	}
	
	public static int getHeight(){
		return avtiveDisplay.getHeight();
	}
	
	public static void destroy(){
		avtiveDisplay.destroy();
		errorCallback.release();
	}
}
