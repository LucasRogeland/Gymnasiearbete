package com.gameproject.game;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.opengl.GL11;

import com.gameproject.game.StateManager.GameState;
import com.gameproject.graphics.Loader;
import com.gameproject.graphics.Render;
import com.gameproject.gui.Gui;
import com.gameproject.gui.GuiManager;
import com.gameproject.gui.components.TextField;
import com.gameproject.input.Mouse;
import com.gameproject.level.Level;
import com.gameproject.math.Matrix4f;
import com.gameproject.net.Client;
import com.gameproject.shaders.Shader;
import com.gameproject.util.Clock;
import com.gameproject.window.DisplayManager;

public class Game {
	
	private Level level;
	
	public Game(){
		init();
	}
	
	public void start(){
		gameLoop();
		close();
	}
	
	//Initialize the stuff (Client, Display, Renderer, StateManager, Shaders)
	private void init(){
		
		//Start the client
		Client.start();
		
		//Initialize the main display
		DisplayManager.init();
		DisplayManager.create("Display", 1920, 1080, false);
		DisplayManager.setTitle("Klockan tickar");
		DisplayManager.setVsync(false);
		
		//Set the game to load the main menu
		StateManager.setState(GameState.MainMenu);
		
		//Initialize the renderer
		Render.init();
		Render.setClearColor(.2f, .5f, 0, 1);
		
		//Print the openGL version
		System.out.println("OpenGL " + GL11.glGetString(GL11.GL_VERSION));
		
		//Load the shaders
		Shader.loadAll();
	}
	
	//Load the Game specific data(Levels, Gui...) and start the loop
	private void gameLoop(){
		
		level = new Level("brickwall");
		
		Gui gui = new Gui();
		gui.addComponent("txt",new TextField("txt", 20, 30, 350,50));
		GuiManager.loadGui(gui);
		System.out.println(DisplayManager.getWidth() + "  " + DisplayManager.getHeight());
		
		//Set the display mode of the game IE View Port
		Matrix4f pr_matrix = Matrix4f.orthographic(0.0f, DisplayManager.getWidth(), DisplayManager.getHeight(), 0,-1.0f, 1.0f);
		Shader.shader.setUniformMat4f("pr_matrix", pr_matrix);
		
		//The game loop 
		while(glfwWindowShouldClose(DisplayManager.getActiveDisplay()) == GLFW_FALSE){
			//Render.setClearColor(.5f*Clock.Delta(), .2f*Clock.Delta(), 0, 1);
			Render.clear();
			Clock.update();
		
			//Update and render the game based on the current gamestate
			switch(StateManager.getState()){
			case Game:
				break;
			case Pause:
				break;
			case MainMenu:
				Mouse.update();
				Shader.shader.start();
				level.update();
				level.render();
				GuiManager.tick();
				Shader.shader.stop();
				break;
			}
			DisplayManager.update();
		}
	}
	
	//Clean up when we are done
	private void close(){
		DisplayManager.destroy();
		Loader.cleanUp();
		Shader.shader.cleanUp();
		glfwTerminate();
		Client.requestClose();
		System.exit(0);
	}
	
}
