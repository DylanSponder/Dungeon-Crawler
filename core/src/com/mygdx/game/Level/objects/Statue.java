package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.World;

public class Statue {

    public World world;
    public float statueX, statueY;


    public Statue(World world, float x, float y, int type) {
        this.world = world;
        this.statueX = x;
        this.statueY = y;
    }
}
