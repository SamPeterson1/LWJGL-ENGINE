#version 330 core 

in vec3 position;
in vec3 normals;
uniform vec3 lightPosition;
in vec2 textCoords;
out vec2 passTextCoords;
out vec3 faceNormal;
out vec3 toLight;

uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

void main(void) {
	
	vec4 worldPosition = t * vec4(position, 1.0);
	gl_Position = p * v * worldPosition;
	passTextCoords = textCoords;
	
	faceNormal = vec3(t * vec4(normals, 0.0));
	toLight = lightPosition - worldPosition.xyz;
	
}