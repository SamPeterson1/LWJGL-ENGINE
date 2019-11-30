#version 330 core 

in vec3 position;
in vec2 textCoords;
out vec2 passTextCoords;

uniform mat4 transformationMatrix;

void main(void) {
	
	gl_Position = transformationMatrix * vec4(position, 1.0);
	passTextCoords = textCoords;
	
}