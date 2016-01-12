package com.gameproject.gui;

public class GuiManager {

	private static Gui gui;
	
	public static void loadGui(Gui _gui){
		gui = _gui;
	}
	
	public static void tick(){
		update();
		render();
	}
	
	private static void render(){
		gui.render();
	}
	
	private static void update(){
		gui.update();
	}
	
	public static Gui getGui(){
		return gui;
	}
}
