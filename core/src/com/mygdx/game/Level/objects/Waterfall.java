package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Waterfall {

    public float waterfX, waterfY;
    private World world;

    public Waterfall(World world, float x, float y) {
        this.world = world;
        this.waterfX = x;
        this.waterfY = y;
    }

    public static void renderWater(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height) {// int alpha

        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(tex, x, y, width, height);

    }
}
