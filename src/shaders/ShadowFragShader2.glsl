#version 330 core
out vec4 fragColor;

in vec2 passTextCoords;
in vec3 lightPosition;
in vec3 lightPos;
in float depth;
uniform sampler2D modelTexture;

void main(void) {
	gl_FragDepth = depth;
	fragColor = vec4(1.0);
}