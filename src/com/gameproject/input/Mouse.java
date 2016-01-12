package com.gameproject.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.gameproject.window.DisplayManager;

public class Mouse extends GLFWCursorPosCallback{

	public static double x = 0;
	public static double y = 0;
	public static final int LEFT_BUTTON = GLFW_MOUSE_BUTTON_1;
	public static final int RIGHT_BUTTON = GLFW_MOUSE_BUTTON_2;
	
	private static boolean[] buttons = new boolean[15];
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		// TODO Auto-generated method stub
		x = xpos;
		y = ypos;
	}
	
	public static boolean isClicked(int button){
		return buttons[button];
	}
	
	public static void update(){
		if(glfwGetMouseButton(DisplayManager.getActiveDisplay(), LEFT_BUTTON) == GLFW_PRESS)
			buttons[LEFT_BUTTON] = true;
		else if(glfwGetMouseButton(DisplayManager.getActiveDisplay(), LEFT_BUTTON) == GLFW_RELEASE)
			buttons[LEFT_BUTTON] = false;
	}
	
}
