#ifdef GL_ES
    #define PRECISION mediump
        precision PRECISION float;
        precision PRECISION int;
    #else
        #define PRECISION
    #endif

    varying vec2 v_texCoords;
    uniform sampler2D u_texture;
    uniform float u_speed;
    uniform float u_verticalDensity;
    uniform float u_swayIntensity;
    uniform float u_time;
    uniform float u_fixedBasePosY;
    uniform float u_alpha;

    void main() {
        vec2 uv = v_texCoords;

        // Putting it all together
        float u_offsetX = sin(uv.y * u_verticalDensity + u_time * u_speed) * u_swayIntensity;

        // Offsetting the u/x coord.
        uv.x += u_offsetX * (uv.y - u_fixedBasePosY);

        vec4 rgba_texture = texture2D(u_texture, uv);

        vec4 v_color = vec4(1,1,1,1);
        v_color.a = u_alpha;

        gl_FragColor = v_color * rgba_texture;
    }