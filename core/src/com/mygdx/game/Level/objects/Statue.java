package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Statue {

    public World world;
    public float statueX, statueY;
    public Body statueBody, statuePedBody;
    public Fixture statueHitbox;
    public boolean visible, loweredAlpha;
    public int type;
    public float alpha;

    public Statue(World world, float x, float y, int type) {
        this.world = world;
        this.statueX = x;
        this.statueY = y;
        this.visible = true;
        this.alpha = 100;
        this.type = type;
    }

    public void createStatueHitbox(float statueX, float statueY, World world) {
        BodyFactory bodyFactory = new BodyFactory();

        statueBody = bodyFactory.createStatueHitbox(world, statueX, statueY);
        statueBody.setUserData("Statue");
    }

    public void createStatuePedestalHitbox(float statueX, float statueY, World world) {
        BodyFactory bodyFactory = new BodyFactory();

        statuePedBody = bodyFactory.createStatuePedestalHitbox(world, statueX, statueY);
        statuePedBody.setUserData("Wall");
    }

    public static void renderStatue(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, Statue s, float alpha) {// int alpha

        batch.setColor(1, 1, 1, alpha/100);
        batch.draw(tex, x, y, width, height);
    }
}
