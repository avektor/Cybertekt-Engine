uniform vec4 m_Color;
uniform sampler2D m_DistMap;

varying vec2 texCoords;
varying vec4 position;

#ifdef HAS_CLIP_BOUNDS
	uniform vec4 m_Clipping;
#endif

varying float oneU, oneV;

float contour(in float d, in float w) {
    return smoothstep(0.5 - w, 0.5 + w, d);
}

float samp(in vec2 uv, float w) {
    return contour(texture2D(m_DistMap, uv).a, w);
}

float aastep(float threshold, float value) {
    float afWidth = 0.7 * length(vec2(dFdx(value), dFdy(value)));
    return smoothstep(threshold-afWidth, threshold+afWidth, value);
}

//Font Fragment Shader
void main(void){

    //Discard fragment if its position is outside of the clip boundaries.
    #ifdef HAS_CLIP_BOUNDS
        if (position.x < m_Clipping.x || position.x > m_Clipping.z || position.y < m_Clipping.y || position.y > m_Clipping.w) {
            discard;
        }
    #endif

    // Retrieve Distance value from the texture.
    vec2 uv = texCoords.xy;
    float dist = texture2D(m_DistMap, uv).a;

    // fwidth helps keep outlines a constant width irrespective of scaling.
    float width = fwidth(dist);

    // Basic Version
    //float alpha = smoothstep(0.5 - width, 0.5 + width, dist);

    // Super-sampling - 4 extra points (Comment out to disable).

    float alpha = contour(dist, width);
    float dscale = 0.707; // Half of 1/sqrt2 - this can be modified.
    vec2 duv = dscale * (dFdx(uv) + dFdy(uv));
    vec4 box = vec4(uv-duv, uv+duv);

    float asum = samp(box.xy, width) + samp(box.zw, width) + samp(box.xw, width) + samp(box.zy, width);
    alpha = (alpha + 0.5 * asum) / 3.0;

    //Super Sampling Complete

    vec4 color = vec4(vec3(alpha), alpha);

    #ifdef HAS_COLOR
        color *= m_Color;
    #endif

    gl_FragColor = color;
}
