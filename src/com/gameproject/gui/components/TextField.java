package com.gameproject.gui.components;

public class TextField extends ActiveComponent{

	//The text that the user has entered(or will enter)
	private String text = "";
	
	//creates a new textfield
	public TextField(String texture, float x, float y, int w, int h) {
		super(texture, x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	//Update the text field
	@Override
	public void update(){
		if(isSelected()){
			selected();
		}
	}
	
	//Perform this if the text field is selected by the user 
	public void selected(){
		System.out.println("selected");
	}
	
	//Get the text
	public String getText(){
		return text;
	}
	
	//If the text field was activated (the user press enter)
	public void activate(){
		System.out.println(text);
		text = "";
	}
	
	@Override
	public void clicked() {
		setSelected(true);
	}

}
