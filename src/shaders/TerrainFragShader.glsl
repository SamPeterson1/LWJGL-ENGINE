#version 330 core

out vec4 fragColor;
in vec2 passTextCoords;
in vec3 faceNormal;
in vec3 toLight;
in vec3 toCamera;
in float visibility;

uniform int textured;
uniform float reflectivity;
uniform float shineDamping;
uniform sampler2D sampler;
uniform vec3 lightColor;
uniform vec3 color;
uniform vec3 skyColor;

void main(void) {
	
	//fragColor = texture(sampler, passTextCoords)
	
	vec3 unitNormal = normalize(faceNormal);
	vec3 unitLightVector = normalize(toLight);
	vec3 unitCamVector = normalize(toCamera);
	
	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.2);
	vec3 diffuse = brightness * lightColor;
	
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specular = dot(reflectedLightDirection, unitCamVector);
	specular = max(specular, 0.0);
	float dampedSpecular = pow(specular, shineDamping);
	vec3 finalSpecular = dampedSpecular * lightColor * reflectivity;
	
	vec4 textureColor = texture(sampler, passTextCoords);
	if(textureColor.w < 0.5) {
		discard;
	}
	
	if(textured == 1)
		fragColor = texture(sampler, passTextCoords) * vec4(diffuse, 1.0);
	else
		fragColor = vec4(diffuse, 1.0) * vec4(color, 1.0) + vec4(finalSpecular, 1.0);
	fragColor = mix(vec4(skyColor, 1.0), fragColor, visibility);
}