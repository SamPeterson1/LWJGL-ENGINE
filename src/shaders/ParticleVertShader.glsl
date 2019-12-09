#version 330 core 

in vec2 position;

out vec2 textCoords1;
out vec2 textCoords2;
out float blend;
out float fadeOut;

uniform vec2 textOffset1;
uniform vec2 textOffset2;
uniform vec3 textCoordInfo;

uniform mat4 p;
uniform mat4 v;
uniform mat4 t;

void main(void) {
	
	vec2 textCoords = position + vec2(1, 1);
	
	textCoords /= textCoordInfo.x * 2;
	
	textCoords1 = textCoords + textOffset1;
	textCoords2 = textCoords + textOffset2;
	fadeOut = textCoordInfo.z;
	blend = textCoordInfo.y;
	gl_Position = p * v * t * vec4(position, 0.0, 1.0);
	
}