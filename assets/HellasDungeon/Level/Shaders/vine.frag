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
uniform float u_speed;
uniform float u_alpha;
uniform float u_swayIntensity;

void main() {
	vec2 uv = v_texCoords;
    
    	uv += cos(u_time*6.0*vec2(u_speed, u_speed) + uv*0.0)*u_swayIntensity;

	vec4 rgba_texture = texture2D(u_texture, uv);

	vec4 v_color = vec4(1,1,1,1);
	v_color.a = u_alpha;
    
	gl_FragColor = v_color * rgba_texture;
}