#version 330 core

out vec4 fragColor;
in vec2 passTextCoords;
in vec3 faceNormal;
in vec3 toLight;

uniform sampler2D sampler;
uniform vec3 lightColor;
uniform vec3 color;

void main(void) {
	
	vec3 unitNormal = normalize(faceNormal);
	vec3 unitLightVector = normalize(toLight);
	
	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.2);
	vec3 diffuse = brightness * lightColor;
	
	fragColor = vec4(diffuse, 1.0) * vec4(color, 1.0);
}