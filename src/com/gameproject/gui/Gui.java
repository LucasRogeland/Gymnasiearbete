package com.gameproject.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gameproject.gui.components.ActiveComponent;
import com.gameproject.gui.components.Frame;
import com.gameproject.gui.components.StaticComponent;
import com.gameproject.input.Mouse;

public class Gui{
	
	//Lists of all the frames and static and active components
	private Map<String, Frame> frames;
	private Map<String, StaticComponent> staticComps;
	private Map<String, ActiveComponent> activeComps;
	
	//Create a new gui
	public Gui(){
		frames = new HashMap<>();
		staticComps = new HashMap<>();
		activeComps = new HashMap<>();
	}
	
	//Add a frame which stores components
	public void addComponent(String id,Frame comp){
		if(!activeComps.containsKey(id) && !frames.containsKey(id) && !staticComps.containsKey(id))
			frames.put(id, comp);
	}
	
	//Add a static component
	public void addComponent(String id, StaticComponent comp){
		if(!activeComps.containsKey(id) && !frames.containsKey(id) && !staticComps.containsKey(id)){
			if(comp.getClass() == ActiveComponent.class)
				activeComps.put(id,(ActiveComponent) comp);
			else
				staticComps.put(id, comp);
		}
	}
	
	//Remove a component
	public void removeComponent(String id){
		if(frames.containsKey(id))
			frames.remove(id);
		else if(activeComps.containsKey(id))
			activeComps.remove(id);
		else if(staticComps.containsKey(id))
			staticComps.remove(id);
	}
	
	//Update all the components 
	protected void update(){
		boolean clicked = false;
		
		//Get the x and y position of the mouse
		double x = Mouse.x;
		double y = Mouse.y;
		
		//If the  mouse button is pressed clicked = true
		if(Mouse.isClicked(Mouse.LEFT_BUTTON))
			clicked = true;
		
		//Update all the frames and the components they contain
		for(Entry<String, Frame> entry : frames.entrySet()) {
			Frame comp = entry.getValue(); 
			comp.update(clicked, x, y);
		}
		
		//Update all the static components
		for(Entry<String, StaticComponent> entry : staticComps.entrySet()) {
			StaticComponent comp = entry.getValue(); 
			comp.update();
		}
		
		//Check if any of the active components have been clicked
		//and update all the active components
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
	
	//Render all the components
	protected void render(){
		//Render all the frames and the components the contain
		for(Entry<String, Frame> entry : frames.entrySet()) {
		    Frame comp = entry.getValue(); 
			comp.renderAll();
		}
		//Render all the static components
		for(Entry<String, StaticComponent> entry : staticComps.entrySet()) {
		    StaticComponent comp = entry.getValue(); 
			comp.render();
		}
		
		//Render all the active components
		for(Entry<String, ActiveComponent> entry : activeComps.entrySet()) {
		    ActiveComponent comp = entry.getValue(); 
			comp.render();
		}
	}

}
