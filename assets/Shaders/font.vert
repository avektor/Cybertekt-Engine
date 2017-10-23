uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrix;
uniform float m_TexWidth, m_TexHeight;
varying vec4 position;

attribute vec3 inPosition;
attribute vec2 inTexCoord;

varying vec2 texCoords;

//Font Vertex Shader
void main(){
    texCoords = inTexCoord;
    #ifdef HAS_CLIP_BOUNDS
        position = g_WorldViewMatrix * vec4(inPosition, 1.0);
    #endif
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}
