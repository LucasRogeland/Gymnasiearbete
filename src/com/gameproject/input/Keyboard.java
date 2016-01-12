package com.gameproject.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.gameproject.window.DisplayManager;


public class Keyboard extends GLFWKeyCallback{
	
	private static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
		if(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE)
			glfwSetWindowShouldClose(DisplayManager.getActiveDisplay(), 1);;
	}
	
	public static boolean isKeyDown(int keyCode){
		return keys[keyCode];
	}

	
}
