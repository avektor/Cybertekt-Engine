/* Uses GLSL 3.0 Core */
#version 330 core

uniform mat4 WorldProjectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 vertTexCoord;

out vec2 texCoord;

void main() {
    texCoord = vertTexCoord;
    gl_Position = WorldProjectionMatrix * vec4(position, 1.0);
}
