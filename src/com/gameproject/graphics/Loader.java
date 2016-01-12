package com.gameproject.graphics;


import static com.gameproject.util.BufferUtils.createFloatBuffer;
import static com.gameproject.util.BufferUtils.createIntBuffer;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {

	private static List<Integer> vaos 	   = new ArrayList<Integer>();
	private static List<Integer> vbos 	   = new ArrayList<Integer>();
	private static Map<String ,Integer> textures = new HashMap<>();
	
	public static Sprite loadSprite(float[] positions,float[] textureCoords, int[] indices){
		int vaoID = createVao();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVao();
		return new Sprite(vaoID, indices.length);
	}
	
	public static int getTexture(String fileName){
		if(textures.containsKey(fileName))
			return textures.get(fileName);
		return loadTexture(fileName);
	}
	
	private static int loadTexture(String fileName){
		int[] pixels = null;
		int width = 0;
		int height = 0;
		try{
			BufferedImage image = ImageIO.read(new FileInputStream( "res/"+fileName+".png"));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		}catch(IOException e){
			e.printStackTrace();
		}
		int[] data = new int[width * height];
		for(int i = 0; i < width * height; i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int tex = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, createIntBuffer(data));
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textures.put(fileName, tex);
		return tex;
	}
	
	private static int createVao(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void storeDataInAttributeList(int attribNumber,int coordSize,  float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = createIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private static void unbindVao(){
		GL30.glBindVertexArray(0);
	}
	
	public static void cleanUp(){
		for(int vao : vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(Entry<String, Integer> entry : textures.entrySet()){
			GL11.glDeleteTextures(entry.getValue());
		}
		textures.clear();
	}
}
