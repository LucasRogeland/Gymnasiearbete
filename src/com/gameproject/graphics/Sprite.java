package com.gameproject.graphics;

public class Sprite {

	private int vaoID;
	private int vertexCount;
	
	public Sprite(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public Sprite(float x, float y, int w, int h){
		float[] vert = {
				0,	h,	0, //V0
				0,	0,	0, //V1
				w,	0,	0, //V2
				w,	h,	0  //V3
				
		};
		
		float[] tcs = {
				0,1,  
				0,0,
				1,0,
				1,1
		};
		
		int[] ind = {
				0,1,2,
				2,3,0
		};

		Sprite s = Loader.loadSprite(vert, tcs, ind);
		this.vaoID = s.getVaoID();
		this.vertexCount = s.getVertexCount();
	}
	
	public int getVaoID(){
		return vaoID;
	}
	
	public int getVertexCount(){
		return vertexCount;
	}
	
}
