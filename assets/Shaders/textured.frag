/* Solid Color Fragment Shader */

/* Uses GLSL 3.0 Core Version */
#version 330 core

/* Base Color */
uniform vec4 Color;

/* Color Map Texture */
uniform sampler2D ColorMap;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    /* fragColor = texture(ColorMap, texCoord) * Color; */
    fragColor = Color;
}
