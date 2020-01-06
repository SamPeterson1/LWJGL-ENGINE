#version 330 core

out vec4 fragColor;
in vec2 passTextCoords;
in vec3 faceNormal;
in vec3 toLight;
in vec3 toCamera;
in vec4 shadowCoords;
in float visibility;
in float depth;

uniform int textured;
uniform float reflectivity;
uniform float shineDamping;
uniform sampler2D sampler;
uniform sampler2D shadowMap;
uniform vec3 sunColor;
uniform vec3 sunDirection;
uniform vec3 color;
uniform vec3 skyColor;

const float shadowMapSize = 2048;

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

	vec3 unitNormal = normalize(faceNormal);
	vec3 unitLightVector = normalize(sunDirection);
	vec3 unitCamVector = normalize(toCamera);
	
	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.2);
	vec3 diffuse = brightness * sunColor * shadowLighting;
	
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specular = dot(reflectedLightDirection, unitCamVector);
	specular = max(specular, 0.0);
	float dampedSpecular = pow(specular, shineDamping);
	vec3 finalSpecular = dampedSpecular * sunColor * reflectivity;
	
	vec4 textureColor = texture(sampler, passTextCoords);
	if(textureColor.w < 0.5) {
		discard;
	}
	
	if(textured == 1)
		fragColor = texture(sampler, passTextCoords) * vec4(diffuse, 1.0);
	else
		fragColor = vec4(diffuse, 1.0) * vec4(color, 1.0) + vec4(finalSpecular, 1.0);
	fragColor = mix(vec4(skyColor, 1.0), fragColor, visibility);
	//fragColor = vec4(shadowCoords.w, 0.0, 0.0, 1.0);
}