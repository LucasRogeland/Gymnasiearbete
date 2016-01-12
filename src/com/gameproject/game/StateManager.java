package com.gameproject.game;

public class StateManager {
	
	//Game states
	public static enum GameState{
		MainMenu, Game, Pause;
	}
	
	//Current Game state
	private static GameState state;

	//Set current state
	public static void setState(GameState _state){
		state = _state;
	}
	
	//Get current state
	public static GameState getState(){
		return state;
	}
}