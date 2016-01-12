package com.gameproject.shaders;

public class StaticShader extends Shader{

	private static final String VERTEX_FILE = "src/com/gameproject/shaders/basic.vert";
	private static final String FRAGMENT_FILE = "src/com/gameproject/shaders/basic.frag";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
	}

}
