package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class OilLamp extends Light{
    public float lampX, lampY;
    private World world;
    public Body lampBody, candFlameBody;
    public Fixture obHitbox;
    public int type;

    public OilLamp(World world, float x, float y, int type) {
        this.world = world;
        this.lampX = x;
        this.lampY = y;
        this.type = type;
    }

    public Body createLamp() {
        BodyFactory bodyFactory = new BodyFactory();

        this.lampBody = bodyFactory.createCandle(world, lampX, lampY);

        this.lampBody.setUserData("Lamp");

        return this.lampBody;
    }

    public static void renderLamp(SpriteBatch batch, Sprite obstacleSprite, float x, float y) {

        batch.draw(obstacleSprite,x - 8f,y - 8f,0,0,16,16,1,1,0);
    }
}
