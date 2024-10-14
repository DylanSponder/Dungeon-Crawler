package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Cobweb {
    public float cobX, cobY;
    private World world;
    public Body cobBody;
    public Fixture cobHitbox;
    public boolean cobCreated;
    public int type;

    public Cobweb(World world, float x, float y, int type) {
        this.type = type;
        this.world = world;
        this.cobX = x;
        this.cobY = y;
        this.cobCreated = false;
    }
}
