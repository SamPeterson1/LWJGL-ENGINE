#version 330 core 

in vec3 position;
in vec3 normals;
in vec2 textCoords;
out vec2 passTextCoords;
out vec3 faceNormal;
out vec3 toLight;
out float visibility;

const float fogDensity = 0.02;
const float fogGradient = 1.5;

uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

uniform int useFakeLighting;
uniform vec3 lightPosition;

void main(void) {
	
	vec4 worldPosition = t * vec4(position, 1.0);
	toLight = vec3(0.0, 1.0, 0.0) - worldPosition.xyz;
	vec4 posRelativeToCam = v * worldPosition;
	gl_Position = p * posRelativeToCam;
	passTextCoords = textCoords;
	
	faceNormal = vec3(t * vec4(normals, 0.0));
	
	float distance = length(posRelativeToCam.xyz);
	//visibility = exp(-pow((distance*fogDensity), fogGradient));
	visibility = 1.0;
}