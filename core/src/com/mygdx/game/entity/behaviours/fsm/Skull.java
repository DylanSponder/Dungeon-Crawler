package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.Arrow;

import static com.mygdx.game.DungeonCrawler.enemySkulls;

public class Skull {

    public Body skullBody;
    public Fixture skullHitbox;
    public float skullX, skullY;
    public boolean skullCreated;
    public float SKULL_HEALTH;
    public float iFrames = 1f;
    public boolean skullIFrame;
    private World world;

    public Skull(World world, float x, float y) {
        this.world = world;
        this.skullX = x;
        this.skullY = y;
        this.SKULL_HEALTH = 1.5f;
        this.skullCreated = false;
        this.skullIFrame = true;
    }

    public Body createSkull(ArrayMap<Body, Skull> skullArrayMap) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                skullIFrame = false;
            }
        }, iFrames);

        BodyFactory bodyFactory = new BodyFactory();

        //this.boneBody = bodyFactory.createSkullBody(DungeonCrawler.world,boneX,boneY);

        this.skullBody = bodyFactory.createSkullBody(world,skullX,skullY);

        this.skullBody.setUserData("Skull");

        this.skullHitbox = bodyFactory.createSkullHitbox(skullBody, 5);

        this.skullCreated = true;

        skullArrayMap.put(skullBody, this);

        return this.skullBody;
    }

    public static void  renderSkull(SpriteBatch batch, Sprite skullSprite, float x, float y) {

        batch.draw(skullSprite, x - 8f, y - 7f, 176,64, 16, 16, 1,1 ,0);

    }
}
