#version 330 core 

in vec3 position;
uniform mat4 transformationMatrix;

void main(void) {
	
	gl_Position = transformationMatrix * vec4(position, 1.0);
	
}