#version 330 core 

in vec3 position;
in vec3 normals;
in vec2 textCoords;
out vec2 passTextCoords;
out vec3 faceNormal;
out vec3 toLight[4];
out vec3 vertex;
out float visibility;
out vec3 rawNormals;
out float depth;

const float fogDensity = 0.02;
const float fogGradient = 1.5;

uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

uniform vec3 lightPosition[4];

void main(void) {
	
	vec4 worldPosition = t * vec4(normalize(position)*200, 1.0);
	vec4 posRelativeToCam = v * worldPosition;
	gl_Position = p * posRelativeToCam;
	vertex = position/200;
	depth = gl_Position.z;
	passTextCoords = textCoords;

	faceNormal = vec3(t * vec4(normals, 0.0));
	rawNormals = normals;
	
	for(int i = 0; i < 4; i ++) {
		toLight[i] = lightPosition[i] - worldPosition.xyz;
	}

	float distance = length(posRelativeToCam.xyz);
	visibility = exp(-pow((distance*fogDensity), fogGradient));

}