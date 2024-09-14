package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.World;

public class ColumnTop {
    public boolean visible;
    public float columnX, columnY;
    public World world;
   // public Body columnBody;
    public int type; //1-9 types
    public boolean solid;

    public ColumnTop(World world, float x, float y, int type, boolean solid) {
        this.world = world;
        this.columnX = x;
        this.columnY = y;
        this.visible = false;
        this.type = type;
        this.solid = solid;
    }
}
