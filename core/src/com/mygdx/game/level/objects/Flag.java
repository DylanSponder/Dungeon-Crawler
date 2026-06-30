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
import com.mygdx.game.Random;
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
        this.time = Random.randomInt(2,1);

    }

    public void createFlagHitbox(float flagX, float flagY, World world) {
        BodyFactory bodyFactory = new BodyFactory();

        flagBody = bodyFactory.createFlagHitbox(world, flagX, flagY);
        flagBody.setUserData("Flag");
    }

    public static void renderFlag(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, Flag f, float alpha) {// int alpha

        batch.setColor(1, 1, 1, alpha / 100);
        batch.draw(tex, x, y, width, height);
    }

}
