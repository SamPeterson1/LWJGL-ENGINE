#version 330 core
out vec4 fragColor;

in vec2 passTextCoords;
uniform sampler2D modelTexture;

void main(void) {
	fragColor = vec4(1.0);
	if(texture(modelTexture, passTextCoords).w < 0.5) discard;
}