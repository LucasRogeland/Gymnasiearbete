package com.gameproject.gameobject;

import com.gameproject.graphics.Render;
import com.gameproject.graphics.Sprite;
import com.gameproject.graphics.Texture;
import com.gameproject.graphics.TexturedSprite;
import com.gameproject.math.Matrix4f;
import com.gameproject.math.Vector3f;
import com.gameproject.shaders.Shader;

public class Entity {

	private float x;
	private float y;
	private int width;
	private int height;
	
	private TexturedSprite sprite;
	
	public Entity(String texture){
		this(texture, 0,0, 64, 64);
	}
	
	public Entity(String texture, float x, float y, int w, int h){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		
		
		sprite = new TexturedSprite(new Sprite(x,y,w,h), new Texture(texture));
	}
	
	
	public void render(){
		Shader.shader.setUniformMat4f("trans_matrix", Matrix4f.translate(new Vector3f(x,y,0)));
		Render.render(this);
	}

	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
	public Sprite getSprite(){
		return sprite.getSprite();
	}
	
	public Texture getTexture(){
		return sprite.getTexture();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
}
