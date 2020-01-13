#version 330 core 

in vec3 position;
in vec2 textureCoords;

out vec2 passTextCoords;
out float depth;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {
	
	vec4 pos = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	gl_Position = pos;
	depth = 0.5;
	passTextCoords = textureCoords;
	
}
