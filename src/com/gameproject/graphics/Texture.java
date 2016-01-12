package com.gameproject.graphics;

public class Texture {

	private int textureID;
	
	public Texture(String fileName){
		this.textureID = Loader.getTexture(fileName);
	}
	
	public int getId(){
		return textureID;
	}
	
}
