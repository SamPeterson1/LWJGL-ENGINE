#version 330 core 

in vec3 position;

uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

void main(void) {
	
	gl_Position = p * v * t * vec4(position, 1.0);
	
}