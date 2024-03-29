#version 400 core

in vec2 passTextCoords;
uniform sampler2D sampler;
uniform sampler2D shadowMap;
uniform vec3 color;

out vec4 fragColor;

void main(void) { 
	
	vec4 texColor = texture(sampler, passTextCoords);
	
    fragColor = texColor * vec4(color, 1.0);
    
}