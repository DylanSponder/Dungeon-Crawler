package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Potion {
    public float potionX, potionY;
    private World world;
    public Body potionBody;
    public Fixture potHitbox;
    public boolean potionCreated;

    public Potion(World world, float x, float y) {
        this.world = world;
        this.potionX = x;
        this.potionY = y;
        this.potionCreated = false;
    }
    /*
    public Body createPotion() {



    }
     */
}
