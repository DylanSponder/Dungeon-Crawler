package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;

public class Skull {

    public Body skullBody;
    public Fixture skullHitbox;
    public float skullX, skullY;
    //public World world;

    public Skull(World world, float x, float y) {

        //this.world = world;
      //  DungeonCrawler.enemySkulls.add(this);
        this.skullX = x;
        this.skullY = y;
    }

    public void createSkull() {
        BodyFactory bodyFactory = new BodyFactory();

        //this.skullBody = bodyFactory.createSkullBody(DungeonCrawler.world,skullX,skullY);

        this.skullBody = bodyFactory.createSkullBody(DungeonCrawler.world,skullX,skullY);

        this.skullHitbox = bodyFactory.createSkullHitbox(skullBody, 8);

        this.skullBody.setUserData("DeadEnemy");
    }
}
