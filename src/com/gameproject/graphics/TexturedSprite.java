package com.gameproject.graphics;

public class TexturedSprite {

	private Sprite sprite;
	private Texture texture;
	
	public TexturedSprite(Sprite sprite, Texture texture){
		this.sprite = sprite;
		this.texture = texture;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Texture getTexture(){
		return texture;
	}
}
