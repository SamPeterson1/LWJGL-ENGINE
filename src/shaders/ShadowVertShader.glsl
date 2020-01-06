#version 330 core 

in vec3 position;
in vec2 textureCoords;

out vec2 passTextCoords;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {

	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	passTextCoords = textureCoords;
	
}
