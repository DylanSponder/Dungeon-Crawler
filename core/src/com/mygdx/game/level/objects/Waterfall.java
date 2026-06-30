package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Random;
import com.mygdx.game.box2D.BodyFactory;

public class Waterfall {

    public float waterfallX, waterfallY;
    private World world;
    public Body waterfallBody;
    public float time;
    public float alpha;

    public Waterfall(World world, float x, float y) {
        this.world = world;
        this.waterfallX = x;
        this.waterfallY = y;
        this.time = Random.randomInt(1,1);
        this.alpha = 50;
    }

    public Body createWaterfall() {
        BodyFactory bodyFactory = new BodyFactory();

        this.waterfallBody = bodyFactory.createTorchBody(world, waterfallX, waterfallY,1);

        this.waterfallBody.setUserData("Waterfall");

        return this.waterfallBody;
    }

    public static void renderWater(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, float alpha) {// int alpha

        batch.setColor(1, 1, 1, alpha / 100);
        batch.draw(tex, x, y, width, height);

    }
}
