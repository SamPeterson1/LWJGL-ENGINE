#version 330 core
out vec4 fragColor;

in vec2 fragCoord;
in vec2 passTextCoords;
uniform sampler2D sampler;
uniform vec3 color;
uniform float depth;
uniform int textured;

void main(void) {
	
	if(textured == 0) {
		fragColor = vec4(color, 1.0);
	} else {
		vec4 texColor = texture(sampler, passTextCoords);
		if(texColor.w < 0.5) discard;
		fragColor = texture(sampler, passTextCoords);
	}
	
	gl_FragDepth = depth;
}