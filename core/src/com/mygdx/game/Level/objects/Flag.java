package com.mygdx.game.level.objects;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;

import java.awt.*;

public class Flag {

    public ShaderProgram flagShader;
    public String fragmentShader;
    public String vertexShader;
    public FrameBuffer fbo;
    public float time;

    public float flagX, flagY;
    private World world;
    public Body flagBody;
    public Fixture flagHitbox;

    public boolean visible, loweredAlpha;
    public int type;
    public float alpha;




    public Flag(World world, float x, float y) {
        this.world = world;
        this.flagX = x;
        this.flagY = y;
        this.visible = true;
        this.alpha = 100;
        this.time = 0;

        BodyFactory bodyFactory = new BodyFactory();

        flagBody = bodyFactory.createSimpleStaticBody(world, x, y);


        //this.ruined = ruined;

        //this.flagBody.setUserData("Flag");
     //   this.coinCreated = false;
    }
        /*
        time = 0;
        vertexShader = Gdx.files.internal("");
        fragmentShader = Gdx.files.internal("");
        flagShader = new ShaderProgram(vertexShader, fragmentShader);
        //flagShader.pedantic = false;
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);


         */



    public static void renderFlag(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, float alpha, Flag f) {// int alpha

        batch.setColor(1, 1, 1, 100);
        batch.draw(tex, x, y, width, height);
    }

    /*
    void mainImage( out vec4 fragColor, in vec2 fragCoord )
    {
        vec2 uv = fragCoord.xy / iResolution.xy;

        // Flip
        uv.y *= -1.0;

        // Represents the v/y coord(0 to 1) that will not sway
        float fixedBasePosY = -1.0;

        // Configs to get the sway right
        float speed = 1.5;
        float verticalDensity = 1.0;
        float swayIntensity = 0.04;

        // Putting it all together
        float offsetX = sin(uv.y * verticalDensity + iTime * speed) * swayIntensity;

        // Offsetting the u/x coord.
        uv.x += offsetX * (uv.y - fixedBasePosY);

        fragColor = texture(iChannel1, uv);
    }
     */


}
