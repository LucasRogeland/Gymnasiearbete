package com.gameproject.gui.components;

import com.gameproject.gameobject.Entity;

public abstract class StaticComponent extends Entity{

	//This is most definitely necessary...
	public StaticComponent(String texture, float x, float y, int w, int h) {
		super(texture, x, y, w, h);
		
	}
	
	public abstract void update();	
}
