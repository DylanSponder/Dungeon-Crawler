package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class ColumnStem {
    public boolean visible;
    public float columnX, columnY;
    public World world;
    public Body columnBody;
    public int type; //1-9 types

    public ColumnStem(World world, float x, float y, int type, boolean solid) {
        this.world = world;
        this.columnX = x;
        this.columnY = y;
        this.visible = false;
        this.type = type;
    }
}
