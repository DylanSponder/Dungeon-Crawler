package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Random;

public class Wave {

    public ShaderProgram waveShader;
    public String fragmentShader;
    public String vertexShader;
    public FrameBuffer fbo;
    public float time;

    public float waveX, waveY;
    private World world;
    public Body flagBody;
    public float stateTime;

    public boolean visible, loweredAlpha;
    public int type;
    public float alpha;

    public Wave(World world, float x, float y) {
        this.stateTime = 0;
        this.world = world;
        this.waveX = x;
        this.waveY = y;
        this.visible = true;
        this.alpha = 100;
        this.time = Random.randomInt(2,1);

    }

    public static void renderWave(SpriteBatch batch, TextureRegion currentFrame, float x, float y, int width, int height) {// int alpha

        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(currentFrame, x, y, width, height);
    }

}