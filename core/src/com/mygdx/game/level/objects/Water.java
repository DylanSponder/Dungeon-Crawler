package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;

public class Water {

    public float waterX, waterY;
    private World world;
    public int type;
    public float stateTime;
    public Body waterBody;
    public Fixture waterFixture;
    public boolean bodyCreated;

    public Water(World world, float x, float y, int type) {
        this.world = world;
        this.waterX = x;
        this.waterY = y;
        this.type = type;
        this.stateTime = 0;
    }
    public static void setUserData(Body body, Fixture fixture) {

        body.setUserData("Water");
        fixture.setUserData("Water");

    }

    public Body createWaterBody() {

        BodyFactory bodyFactory = new BodyFactory();

        this.waterBody = bodyFactory.createWater(world, waterX, waterY);

        this.waterBody.setUserData("Water");

        return this.waterBody;
    }

    public static Body createModularWaterBody(Water water, int width, int height) {

        BodyFactory bodyFactory = new BodyFactory();

        water.waterBody = bodyFactory.createModularWaterBody(DungeonCrawler.world, water.waterX, water.waterY, width, height);

        water.waterBody.setUserData("Water");

        return water.waterBody;
    }



    public static void renderWater(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, int type) {// int alpha

        if (type == 1) {
            batch.setColor(1, 1, 1, 0.51f);
        } else if (type == 2) {
            batch.setColor(1, 1, 1, 0.4f);
        }

        batch.draw(tex, x, y, width, height);
    }
}
