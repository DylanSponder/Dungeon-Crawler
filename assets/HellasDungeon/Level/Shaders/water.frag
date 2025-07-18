
const float PI = 3.1415926535897932;

//speed
uniform float speed = 0.02;
uniform float speed_x = 0.03;
uniform float speed_y = 0.03;

// refraction
uniform float emboss = 0.50;
uniform float intensity = 1.0;
uniform int steps = 8;
uniform float frequency = 6.0;
uniform int angle = 7; // better when a prime

// reflection
uniform float delta = 60.;
uniform float gain = 700.;

  float v_color(vec2 coord,float time)
  {
    float delta_theta = 2.0 * PI / float(angle);
    float v_color = 0.0;
    float theta = 0.0;
    for (int i = 0; i < steps; i++)
    {
      vec2 adjc = coord;
      theta = delta_theta*float(i);
      adjc.x += cos(theta)*time*speed + time * speed_x;
      adjc.y -= sin(theta)*time*speed - time * speed_y;
      v_color = v_color + cos( (adjc.x*cos(theta) - adjc.y*sin(theta))*frequency)*intensity;
    }

    return cos(v_color);
  }

void main( out vec4 fragColor, in vec2 fragCoord )
{
    float time = iTime*1.3;

vec2 p = (fragCoord.xy) / iResolution.xy, c1 = p, c2 = p;
float cc1 = col(c1,time);

c2.x += iResolution.x/delta;
float dx = emboss*(cc1-col(c2,time))/delta;

c2.x = p.x;
c2.y += iResolution.y/delta;
float dy = emboss*(cc1-col(c2,time))/delta;

c1.x += dx*2.;
c1.y = -(c1.y+dy*2.);


vec4 v_color = texture(iChannel0,c1);
gl_FragColor = v_color;
}