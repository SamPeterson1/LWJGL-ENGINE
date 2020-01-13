#version 330 core

out vec4 fragColor;
in vec2 passTextCoords;
in vec3 faceNormal;
in vec3 toLight[4];
in vec3 toCamera;
in vec4 shadowCoords;
in float visibility;
in float depth;

uniform sampler2D sampler;
uniform sampler2D shadowMap;
uniform samplerCube depthMap;
uniform vec3 sunColor;
uniform vec3 sunDirection;
uniform vec3 lightColor[4];
uniform vec3 skyColor;
uniform vec3 attenuation[4];

const float shadowMapSize = 2048;
const vec3 ambientLight = vec3(0.2);
const float ambientLightIntensity = 0.2;

void main(void) {
	
	float shadowLighting = 1;
	vec2 shadowTexCoords = shadowCoords.xy;
	if(shadowTexCoords.y < 1 && shadowTexCoords.x < 1 && shadowTexCoords.y > 0 && shadowTexCoords.x > 0) {

		int inShadow = 0;
		for(int i = -1; i < 1; i ++) {
			for(int ii = -1; ii < 1; ii ++) { 
				vec2 offsetTexCoords = shadowTexCoords;
				offsetTexCoords.x += i/shadowMapSize;
				offsetTexCoords.y += ii/shadowMapSize;
				float objectNearestLight = texture(shadowMap, offsetTexCoords).r;
				if(objectNearestLight < shadowCoords.z) {
					inShadow ++;
				}
			}
		}
		shadowLighting = 1.0 - inShadow*0.6/4.0;
	}
	
	vec3 toSunNormal = normalize(sunDirection);
	float sunDotProduct = dot(toSunNormal, faceNormal);
	float sunLighting = max(0.0, sunDotProduct);
	
	vec3 totalDiffuse = sunColor * sunLighting;
	
	for(int i = 0; i < 4; i ++) {
		float d = length(toLight[i]);
		float attenuationFactor = attenuation[i].x + (d*attenuation[i].y) + (d*d*attenuation[i].z);
		vec3 toLightNormal = normalize(toLight[i]);
		vec3 faceNormal = normalize(faceNormal);
		float dotProduct = dot(toLightNormal, faceNormal);
		float pointLighting = min(1.0, max(0.0, dotProduct));
		if(attenuationFactor != 0) {
			pointLighting /= attenuationFactor;
			totalDiffuse += lightColor[i] * pointLighting;
		}
	}

	vec3 textureColor = texture(sampler, passTextCoords).rgb;
	vec3 diffuse = totalDiffuse;
	diffuse *= textureColor * shadowLighting;
	fragColor = vec4(mix(skyColor, diffuse, visibility), 1.0);
	fragColor = texture(depthMap, 
}