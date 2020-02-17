#version 330 core 

in vec3 position;
in vec3 normals;
in vec2 textCoords;
out vec2 passTextCoords;
out vec3 faceNormal;
out vec3 toLight[4];
out vec4 shadowCoords;
out float visibility;
out float depth;
out vec3 toShadowLight;

const float fogDensity = 0;
const float fogGradient = 1.5;

uniform vec3 lightPosition[4];

uniform mat4 shadowMapP;
uniform mat4 shadowMapV;
uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

const float fov = 70.0f;
const float zFar = 1000.0f;
const float zNear = 0.01f;
const float aspect = 1440.0f/1080.0f;


void main(void) {
	
	vec4 worldPosition = t*vec4(position, 1.0);
	shadowCoords = shadowMapP * shadowMapV * worldPosition * 0.5 + 0.5;
	vec4 posRelativeToCam = v*worldPosition;
	gl_Position = p * posRelativeToCam;
	passTextCoords = textCoords * 40;
	faceNormal = vec3(t * vec4(normals, 0.0));
	
	toShadowLight = vec3(50, 5, 50) - worldPosition.xyz;
	
	for(int i = 0; i < lightPosition.length; i ++) {
		toLight[i] = lightPosition[i] - worldPosition.xyz;
	}
	//float distance = length(posRelativeToCam.xyz);
	//visibility = exp(-pow((distance*fogDensity), fogGradient));
	visibility = 1;
}