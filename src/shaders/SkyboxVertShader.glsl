#version 330 core 

in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
out vec3 textureCoords;

void main(void) {
	
	mat4 newViewMatrix = viewMatrix;
	
	newViewMatrix[3][0] = 0;
	newViewMatrix[3][1] = 0;
	newViewMatrix[3][2] = 0;
	
	gl_Position = projectionMatrix * newViewMatrix * vec4(position, 1.0);
	textureCoords = position;
}
