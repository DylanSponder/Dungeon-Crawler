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
	float y = 1.0 - uv.y;

	float u_offsetX = sin(y * u_verticalDensity + u_time * u_speed) * u_swayIntensity;

	uv.x += u_offsetX * (y - u_fixedBasePosY);

        vec4 v_color = vec4(1,1,1,u_alpha);

	gl_FragColor = texture2D(u_texture, uv) * v_color;

    }