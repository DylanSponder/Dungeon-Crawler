package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class ColumnBase {

    private Fixture baseHitbox;
    private Body baseBody;
    public float columnX, columnY;
    public World world;
    public Body columnBody;
    public boolean visible;
    public int type; //1-9 types

    public ColumnBase(World world, float x, float y, int type, boolean solid) {
        this.world = world;
        this.columnX = x;
        this.columnY = y;
        this.visible = false;
        this.type = type;

    }
    public Body createColumnBase(Body body){
        this.baseBody = body;
        return body;
    }
}