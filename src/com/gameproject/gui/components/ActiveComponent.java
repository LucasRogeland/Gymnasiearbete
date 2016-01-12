package com.gameproject.gui.components;

public abstract class ActiveComponent extends StaticComponent{

	private boolean selected;
	
	public ActiveComponent(String texture, float x, float y, int w, int h) {
		super(texture, x, y, w, h);
	}

	@Override
	public abstract void update();

	public abstract void selected();
	public abstract void activate();
	public abstract void clicked();
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean b){
		selected = b;
	}
}
