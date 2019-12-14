#version 330 core

uniform sampler2D sampler;
in vec2 textCoords1;
in vec2 textCoords2;
in float blend;
in float fadeOut;

void main(void) {
	
	
	vec4 color1 = texture(sampler, textCoords1);
	vec4 color2 = texture(sampler, textCoords2);
	vec4 combined = mix(color1, color2, blend);
	if(combined.w > 0.5) combined.w = fadeOut;
	gl_FragColor = combined; 
	
}