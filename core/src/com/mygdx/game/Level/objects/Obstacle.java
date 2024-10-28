package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;

public class Obstacle {
    public float obX, obY;
    private World world;
    public Body obBody;
    public Fixture obHitbox;
    public int type;

    public Obstacle(World world, float x, float y, int type) {
        this.world = world;
        this.obX = x;
        this.obY = y;
        this.type = type;
    }

    public Body createObstacle() {
        BodyFactory bodyFactory = new BodyFactory();

        this.obBody = bodyFactory.createObstacle(world, obX, obY);

        this.obBody.setUserData("Obstacle");

        //this.obHitbox.setUserData("Obstacle");

        //potArrayMap.put(obBody, this);

        return this.obBody;
    }
}
