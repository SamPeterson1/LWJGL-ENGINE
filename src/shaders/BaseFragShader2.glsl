#version 330 core

out vec4 fragColor;
in vec2 passTextCoords;
in vec3 faceNormal;
in vec3 toLight[4];
in float visibility;

uniform int textured;
uniform sampler2D sampler;
uniform vec3 sunColor;
uniform vec3 sunDirection;
uniform vec3 attenuation[4];
uniform vec3 lightColor[4];
uniform vec3 color;
uniform vec3 skyColor;

const vec3 ambientLight = vec3(1.0, 1.0, 1.0);
const float ambientLightIntensity = 0.2;

void main(void) {

	vec4 textureColor = texture(sampler, passTextCoords);
	if(textureColor.w < 0.8) discard;
	
	vec3 toSunNormal = normalize(sunDirection);
	vec3 faceNormal = normalize(faceNormal);
	float dotProduct = dot(toSunNormal, faceNormal);
	float sunLighting = max(0.0, min(dotProduct, 1.0));
	
	vec3 sumDiffuse = sunLighting * sunColor;
	
	for(int i = 0; i < 4; i ++) {
		float d = length(toLight[i]);
		float attenuationFactor = attenuation[i].x + (d*attenuation[i].y) + (d*d*attenuation[i].z);
		vec3 toLightNormal = normalize(toLight[i]);
		float dotProduct2 = dot(toLightNormal, faceNormal);
		float pointLighting = max(0.0, min(dotProduct2, 1.0));
		if(attenuationFactor != 0) {
			pointLighting /= attenuationFactor;
			sumDiffuse += pointLighting * lightColor[i];
		}
	}

	vec3 diffuse = textureColor.rgb * sumDiffuse;
	fragColor = vec4(diffuse, 1.0);
	
}