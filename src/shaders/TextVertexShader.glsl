#version 420

in vec3 position;
in vec2 textCoords;

out vec2 passTextCoords;

void main(void) {
	
	gl_Position = vec4(position, 1.0);
	passTextCoords = textCoords;
	
}