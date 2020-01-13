#version 330 core
out vec4 fragColor;

uniform samplerCube sampler;
uniform vec3 fogColor;
in vec3 textureCoords;

const int size = 512;
const float lower = 0.1;
const float upper = 0.4;


void main(void) {
	
	fragColor = texture(sampler, textureCoords);
}