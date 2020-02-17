#version 330 core
out vec4 fragColor;

in vec2 fragCoord;
in vec2 passTextCoords;
uniform sampler2D sampler;
uniform vec4 color;
uniform float depth;
uniform int textured;
uniform int hasBackground;

void main(void) {
	
	
	if(textured == 0) {
		fragColor = color;
	} else {
		vec4 texColor = texture(sampler, passTextCoords);
		fragColor = texColor;
	}
	
	gl_FragDepth = depth;
}