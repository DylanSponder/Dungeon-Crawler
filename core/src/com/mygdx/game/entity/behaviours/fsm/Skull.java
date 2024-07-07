package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;

import static com.mygdx.game.DungeonCrawler.enemySkulls;

public class Skull {

    public Body skullBody;
    public Fixture skullHitbox;
    public float skullX, skullY;
    public boolean skullCreated;
    public int SKULL_HEALTH;
    //public World world;

    public Skull(World world, float x, float y) {
        //this.world = world;
      //  DungeonCrawler.enemySkulls.add(this);
        this.skullX = x;
        this.skullY = y;
        this.SKULL_HEALTH = 2;
        this.skullCreated = false;
    }

    public void createSkull() {
        BodyFactory bodyFactory = new BodyFactory();

        //this.boneBody = bodyFactory.createSkullBody(DungeonCrawler.world,boneX,boneY);

        this.skullBody = bodyFactory.createSkullBody(DungeonCrawler.world,skullX,skullY);

        this.skullBody.setUserData("Skull");

        this.skullHitbox = bodyFactory.createSkullHitbox(skullBody, 5);

        this.skullCreated = true;
    }

    public void removeSkull(Body body) {

    }
}
