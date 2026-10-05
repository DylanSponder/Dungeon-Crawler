package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Random;
import com.mygdx.game.box2D.BodyFactory;

public class VasePlant {

    public ShaderProgram vasePlantShader;
    public String fragmentShader;
    public String vertexShader;
    public FrameBuffer fbo;
    public float time;

    public float vasePlantX, vasePlantY;
    private World world;
    public Body vasePlantBody;
    public Fixture vasePlantHitbox;

    public boolean visible, loweredAlpha;
    public int type;
    public float alpha;


    public VasePlant(World world, float x, float y, int type) {
        this.world = world;
        this.vasePlantX = x;
        this.vasePlantY = y;
        this.visible = true;
        this.alpha = 100;
        this.time = Random.randomInt(1000,1);
        this.type = type;
    }

    public void createVasePlantHitbox(float vasePlantX, float vasePlantY, World world) {
        BodyFactory bodyFactory = new BodyFactory();

        vasePlantBody = bodyFactory.createVasePlantHitbox(world, vasePlantX, vasePlantY);
        vasePlantBody.setUserData("VasePlant");
    }

    public static void renderVasePlant(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, VasePlant vp, float alpha) {// int alpha

        batch.setColor(1, 1, 1, alpha / 100);
        batch.draw(tex, x, y, width, height);
    }

}
