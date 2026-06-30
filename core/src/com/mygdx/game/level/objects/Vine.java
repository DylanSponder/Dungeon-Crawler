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

public class Vine {

    public ShaderProgram vineShader;
    public String fragmentShader;
    public String vertexShader;
    public FrameBuffer fbo;
    public float time;

    public float vineX, vineY;
    private World world;
    public Body vineBody;
    public Fixture vineHitbox;

    public boolean visible, loweredAlpha;
    public int type;
    public float alpha;


    public Vine(World world, float x, float y) {
        this.world = world;
        this.vineX = x;
        this.vineY = y;
        this.visible = true;
        this.alpha = 100;
        this.time = Random.randomInt(1000,1);

    }

    public void createVineHitbox(float vineX, float vineY, World world) {
        BodyFactory bodyFactory = new BodyFactory();

        vineBody = bodyFactory.createVineHitbox(world, vineX, vineY);
        vineBody.setUserData("Vine");
    }

    public static void renderVine(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, Vine v, float alpha) {// int alpha

        batch.setColor(1, 1, 1, alpha / 100);
        batch.draw(tex, x, y, width, height);
    }

}
