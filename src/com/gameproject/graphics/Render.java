package com.gameproject.graphics;

import java.awt.Font;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.gameproject.gameobject.Entity;

public class Render {
	
	public static void init(){
		
		GL.createCapabilities(true);
		
	}
	
	public static void render(Entity entity){
		Sprite sprite = entity.getSprite();
		Texture texture = entity.getTexture();
		GL30.glBindVertexArray(sprite.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindVertexArray(0);
	}
	
	
	public static void drawText(String s, float x ,float y, Color color){
		Font font = new Font("Times New Roman", Font.BOLD, 24);
		TrueTypeFont tff = new TrueTypeFont(font, false);
		tff.drawString(x, y, s, color);		
	}
	
	public static void clear(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void setClearColor(float r, float g, float b, float a){
		GL11.glClearColor(r, g, b, a);
	}
	
}
