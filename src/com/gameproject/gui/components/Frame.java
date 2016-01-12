package com.gameproject.gui.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gameproject.gameobject.Entity;

public class Frame extends Entity{

	//Lists of all the frames and static and active components
	private Map<String, Frame> frames;
	private Map<String, StaticComponent> staticComps;
	private Map<String, ActiveComponent> activeComps;
	
	//Create a new frame with the specified texture, position and size
	public Frame(String tex,float x, float y, int width, int height) {
		super(tex,x, y, width, height);
		frames = new HashMap<>();
		staticComps = new HashMap<>();
		activeComps = new HashMap<>();
	}
	
	//Add a frame to the frame
	public void addComponent(String id, Frame comp){
		comp.move(getX(),getY());
		frames.put(id, comp);
	}
	
	//Add a static component to the frame
	public void addComponent(String id,StaticComponent comp){
		comp.move(getX(),getY());
		if(comp.getClass() == ActiveComponent.class)
			activeComps.put(id, (ActiveComponent) comp);
		else
			staticComps.put(id, comp);
	}

	//Update all the frames and components of the frame
	public void update(boolean clicked, double x, double y) {
		
		//Update the frames within the frame
		for(Entry<String, Frame> entry : frames.entrySet()) {
			Frame comp = entry.getValue(); 
			comp.update(clicked, x, y);
		}
		
		//Update the static components within the frame
		for(Entry<String, StaticComponent> entry : staticComps.entrySet()) {
			StaticComponent comp = entry.getValue(); 
			comp.update();
		}
		
		//Update the active components within the frame
		for(Entry<String, ActiveComponent> entry : activeComps.entrySet()) {
		    ActiveComponent comp = entry.getValue();
		    if(clicked){
				if(x > comp.getX() && x < comp.getX() + comp.getWidth() && y > comp.getY() && y < comp.getY() + comp.getHeight())
					comp.clicked();
				else
					comp.setSelected(false);
			}
			comp.update();
		}
	}
	
	//Render all the frames and components within this frame
	public void renderAll(){
		//Render this frame
		super.render();
		
		//Render the frames within the frame
		for(Entry<String, Frame> entry : frames.entrySet()) {
		    Frame comp = entry.getValue(); 
			comp.renderAll();
		}
		
		//Render the static components within this frame
		for(Entry<String, StaticComponent> entry : staticComps.entrySet()) {
		    StaticComponent comp = entry.getValue(); 
			comp.render();
		}
		
		//Render the active components within this frame
		for(Entry<String, ActiveComponent> entry : activeComps.entrySet()) {
		    ActiveComponent comp = entry.getValue(); 
			comp.render();
		}
	}

}
