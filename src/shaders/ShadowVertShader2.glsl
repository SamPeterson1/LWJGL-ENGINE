#version 330 core 

in vec3 position;
in vec2 textureCoords;

out vec2 passTextCoords;
out float depth;
out vec3 lightPos;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {
	
	vec4 posRelativeToCam = viewMatrix * transformationMatrix * vec4(position, 1.0);
	vec4 pos = projectionMatrix * posRelativeToCam;
	pos.xy *= -1;
	lightPos = vec3(viewMatrix[3][0], viewMatrix[3][1], viewMatrix[3][2]);
	depth = length(posRelativeToCam)/100.0;
	gl_Position = pos;
	passTextCoords = textureCoords;
	
}
