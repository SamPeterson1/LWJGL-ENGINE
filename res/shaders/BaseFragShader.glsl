#version 330 core

out vec4 fragColor;
in vec2 passTextCoords;
in vec3 faceNormal;
in vec3 toLight;
in float visibility;

uniform int textured;
uniform sampler2D sampler;
uniform vec3 sunColor;
uniform vec3 sunDirection;
uniform vec3 attenuation;
uniform vec3 lightColor;
uniform vec3 color;
uniform vec3 skyColor;

const vec3 ambientLight = vec3(1.0, 1.0, 1.0);
const float ambientLightIntensity = 0.2;

void main(void) {

	vec4 textureColor = texture(sampler, passTextCoords);
	if(textureColor.a < 0.8) discard;
	
	float d = length(toLight);
	
	float attenuationFactor = attenuation.x + (d*attenuation.y) + (d*d*attenuation.z);
	vec3 toLightNormal = normalize(toLight);
	vec3 faceNormalf = normalize(faceNormal);
	float dotProduct = dot(toLightNormal, faceNormalf);
	//float pointLighting = min(1.0, max(0.0, dotProduct));
	//pointLighting ;
	
	vec3 diffuse = dotProduct * vec3(1.0, 1.0, 1.0);
	fragColor = vec4(diffuse, 1.0);
	
}