package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;

public class Candle extends Light{
    public float candX, candY;
    private World world;
    public Body candBody;
    public Fixture obHitbox;
    public int type;

    public Candle(World world, float x, float y, int type) {
        this.world = world;
        this.candX = x;
        this.candY = y;
        this.type = type;
    }

    public Body createCandle() {
        BodyFactory bodyFactory = new BodyFactory();

    //    this.candBody = bodyFactory.createObstacle(world, candX, candY);

        this.candBody = bodyFactory.createCandle(world, candX, candY);

        this.candBody.setUserData("Candle");

        //this.obHitbox.setUserData("Obstacle");

        //potArrayMap.put(obBody, this);

        return this.candBody;
    }

    public static void renderCandle(SpriteBatch batch, Sprite obstacleSprite, float x, float y) {

        batch.draw(obstacleSprite,x - 8f,y - 8f,0,0,16,16,1,1,0);
    }
}
