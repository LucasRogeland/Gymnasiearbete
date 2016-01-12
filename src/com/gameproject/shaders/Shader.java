package com.gameproject.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.gameproject.math.Matrix4f;
import com.gameproject.math.Vector2f;
import com.gameproject.math.Vector3f;

public abstract class Shader {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>(); 
	private boolean enabled;
	
	public static StaticShader shader;
	
	public Shader(String vertexFile, String fragmentFile){
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}
	
	public static void loadAll(){
		shader = new StaticShader();
	}
	
	public void start(){
		enabled = true;
		GL20.glUseProgram(programID);
	}
	
	public void stop(){
		enabled = false;
		GL20.glUseProgram(0);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attrib, String variableName){
		GL20.glBindAttribLocation(programID, attrib, variableName);
	}
	
	public int getUniform(String name){
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		
		int result = GL20.glGetUniformLocation(programID, name);
		if(result == -1)
			System.out.println("Could not find uniform variable " + name);
		else
			locationCache.put(name, result);
		
		return result;
	}
	
	public void setUniform1i(String name, int value){
		if(!enabled) start();
		GL20.glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value){
		if(!enabled) start();
		GL20.glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, Vector2f vector){
		if(!enabled) start();
		GL20.glUniform2f(getUniform(name), vector.x, vector.y);
	}
	
	public void setUniform2f(String name, Vector3f vector){
		if(!enabled) start();
		GL20.glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix){
		if(!enabled) start();
		GL20.glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void cleanUp(){
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}catch(IOException e){
			System.err.println("Could not read file!");
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID));
			System.err.println("Could not compile shader");
			System.exit(-1);
		}
		return shaderID;
	}
	
}
