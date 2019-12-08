#version 330 core
out vec4 fragColor;

in vec2 fragCoord;
in vec2 passTextCoords;
uniform sampler2D sampler;
uniform vec3 color;
uniform float depth;
uniform int textured;
uniform int hasBackground;

void main(void) {
	
	
	if(textured == 0) {
		fragColor = vec4(color, 1.0);
	} else {
		vec4 texColor = texture(sampler, passTextCoords);
		fragColor = texColor;
		if(texColor.w < 0.5) {
			if(hasBackground == 1) {
				fragColor = vec4(color, 1.0);
			} else {
			 	discard;
			}
		}
	}
	
	gl_FragDepth = depth;
}