package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;

public class Block {
    public float blX, blY;
    private World world;
    public Body blBody;
    public Fixture blHitbox;
    public int type;
    public boolean blCreated;

    public Block(World world, float x, float y, int type) {
        this.world = world;
        this.blX = x;
        this.blY = y;
        this.type = type;
        this.blCreated = false;
    }

    public Body createObstacle() {
        BodyFactory bodyFactory = new BodyFactory();

        this.blBody = bodyFactory.createObstacle(world, blX, blY);

        this.blBody.setUserData("Wall");

        this.blCreated = true;

        //this.obHitbox.setUserData("Obstacle");

        //potArrayMap.put(obBody, this);

        return this.blBody;
    }
}