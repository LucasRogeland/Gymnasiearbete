package com.gameproject.level;

import com.gameproject.gameobject.Entity;
import com.gameproject.util.Clock;

public class Level {

	private final Entity BG;
	
	public Level(String bg){
	
		BG = new Entity(bg, 200, 200, 256, 256);
		
	}
	
	public void update(){
		BG.move(.3f * Clock.Delta(), 0.3f * Clock.Delta());
	}
	
	public void render(){
		BG.render();
	}
	
}
