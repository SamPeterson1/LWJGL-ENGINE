#version 330 core
out vec4 fragColor;

uniform samplerCube sampler;
uniform vec3 fogColor;
in vec3 textureCoords;

const int size = 512;
const float lower = 0.1;
const float upper = 0.4;


void main(void) {
	
	if(textureCoords.y < size * lower)
		fragColor = vec4(fogColor, 1.0);
	else if(textureCoords.y < size * upper)
		fragColor = mix(texture(sampler, textureCoords), vec4(fogColor, 1.0), 1-(textureCoords.y-lower*size)/(upper*size - lower*size));
	else
		fragColor = texture(sampler, textureCoords);
}