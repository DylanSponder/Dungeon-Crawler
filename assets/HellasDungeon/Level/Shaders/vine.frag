#ifdef GL_ES
    #define PRECISION mediump
        precision PRECISION float;
        precision PRECISION int;
    #else
        #define PRECISION
    #endif


varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float u_time;
uniform float u_alpha;

void main() {
	vec2 uv = v_texCoords;
    
    	uv += cos(u_time*6.0*vec2(0.1, 0.1) + uv*1.0)*0.04;

	vec4 rgba_texture = texture2D(u_texture, uv);

	vec4 v_color = vec4(1,1,1,1);
	v_color.a = u_alpha;
    
	gl_FragColor = v_color * rgba_texture;
}