#version 330 core 

layout (location = 0) in vec3 position;
layout (location = 2) in vec3 normals;
uniform vec3 lightPosition;
layout (location = 1) in vec2 textCoords;
out vec2 passTextCoords;
out vec3 f;
out vec3 toLight;

uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

void main(void) {
	
	vec4 worldPosition = p * v * t *vec4(position, 1.0);
	gl_Position = worldPosition;
	passTextCoords = textCoords;
	
	f = vec3(t * vec4(normals, 1.0));
	toLight = lightPosition - worldPosition.xyz;
	
}