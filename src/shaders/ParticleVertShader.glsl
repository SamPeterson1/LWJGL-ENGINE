#version 330 core 

in vec2 position;

in vec4 texOffsets;
in float blendFactor;
in mat4 t;

out vec2 textCoords1;
out vec2 textCoords2;
out float blend;
out float fadeOut;

uniform mat4 p;
uniform mat4 v;

void main(void) {

	
	vec2 textCoords = position + vec2(1, 1);
	textCoords /= 8;
	
	textCoords1 = textCoords + texOffsets.xy;
	textCoords2 = textCoords + texOffsets.zw;
	fadeOut = 1.0;
	blend = blendFactor;
	gl_Position = p * v * t * vec4(position, 0.0, 1.0);

	
}