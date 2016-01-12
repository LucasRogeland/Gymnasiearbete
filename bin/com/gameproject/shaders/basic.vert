#version 330 core

in vec3 position;
in vec2 texCoords;

uniform mat4 pr_matrix;
uniform mat4 trans_matrix;

out vec2 pass_texCoords;

void main(void){

	gl_Position = pr_matrix * trans_matrix * vec4(position.xyz, 1.0);
	pass_texCoords = texCoords;
	
}