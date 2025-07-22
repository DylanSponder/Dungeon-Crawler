package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Random;

public class Water {

    public float time;

    public float waterX, waterY;
    private World world;

    public Water(World world, float x, float y) {
        this.world = world;
        this.waterX = x;
        this.waterY = y;
        this.time = Random.randomInt(2,1);
    }

    public static void renderWater(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height) {// int alpha

        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(tex, x, y, width, height);
    }

}