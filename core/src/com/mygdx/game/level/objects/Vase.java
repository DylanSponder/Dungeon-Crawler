package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Vase {

    public float vaseX, vaseY;
    private World world;
    public Body vaseBody;
    public boolean visible, loweredAlpha;
    public int type;
    public float alpha;

    public Vase(World world, float x, float y) {
        this.world = world;
        this.vaseX = x;
        this.vaseY = y;
        this.alpha = 100;
        this.visible = true;
    }

    public Body createVase() {
        BodyFactory bodyFactory = new BodyFactory();

        this.vaseBody = bodyFactory.createTorchBody(world, vaseX, vaseY,1);

        this.vaseBody.setUserData("Vase");

        return this.vaseBody;
    }

    public static void renderVase(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, Vase s, float alpha) {

        batch.setColor(1, 1, 1, alpha/100);
        batch.draw(tex, x, y, width, height);
    }
}