#version 330 core 

in vec2 position;

in vec4 texOffsets;
in float blendFactor;
in mat4 t;
in float fadeOut;

out vec2 textCoords1;
out vec2 textCoords2;
out float blend;
out float passFadeOut;

uniform mat4 p;
uniform mat4 v;
uniform int atlasRows;

void main(void) {

	
	vec2 textCoords = position + vec2(1, 1);
	textCoords /= atlasRows * 2.0;
	
	textCoords1 = textCoords + texOffsets.xy;
	textCoords2 = textCoords + texOffsets.zw;
	passFadeOut = fadeOut;
	blend = blendFactor;
	gl_Position = p * v * t * vec4(position, 0.0, 1.0);

	
}