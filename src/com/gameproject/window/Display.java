package com.gameproject.window;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;

import com.gameproject.input.Keyboard;
import com.gameproject.input.Mouse;

public class Display {

	private long 				  window;
    private GLFWKeyCallback 	  keyCallback;
    private GLFWCursorPosCallback mouse;
    private String 				  title;
	private int 				  width;
	private int 				  height;
	private boolean 			  fullscreen;
	
	public Display(String title, int width, int height, boolean fullscreen){
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		init();
	}
	
	private void init(){
		
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resize able
        
        if(fullscreen){
        	window = glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), NULL);
        	if ( window == NULL )
        		throw new RuntimeException("Failed to create the GLFW window");
        }else{
        	window = glfwCreateWindow(width, height, title, NULL, NULL);
        	if ( window == NULL )
        		throw new RuntimeException("Failed to create the GLFW window");
        	
        	 // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            // Center our window
            glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
            );
        }
        
        glfwSetKeyCallback(window, keyCallback = new Keyboard());
        glfwSetCursorPosCallback(window, mouse = new Mouse());
        
       
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
 
        // Make the window visible
        glfwShowWindow(window);
	}
	
	public void update(){
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	
	public void setTitle(String nTitle){
		title = nTitle;
		glfwSetWindowTitle(window, title);
	}
	
	public void destroy(){
		glfwDestroyWindow(window);
		keyCallback.release();
		mouse.release();
	}
	
	public long getDisplay(){
		return window;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
}
