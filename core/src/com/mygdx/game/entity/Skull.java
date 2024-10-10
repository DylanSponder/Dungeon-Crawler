package com.mygdx.game.entity;

import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Fire;
import com.mygdx.game.level.objects.Room;

import static com.mygdx.game.DungeonCrawler.player;
import static com.mygdx.game.DungeonCrawler.world;

public class Skull {

    public Body skullBody;
    public Fixture skullHitbox;
    public float skullX, skullY;
    public boolean skullCreated;
    public float SKULL_HEALTH;
    public float iFrames = 0.66f;
    public boolean skullIFrame;
    private World world;
    public ShapeRenderer shapeRenderer;
    public Vector2 tmp = new Vector2();
    public Vector2 tmp2 = new Vector2();
    public boolean rayCastable, rayResult;
    public Ray respawnDetectionRay;
    public int room;
    public boolean resurrecting;

    public Skull(World world, float x, float y) {
        this.world = world;
        this.skullX = x;
        this.skullY = y;
        this.SKULL_HEALTH = 1.5f;
        this.skullCreated = false;
        this.skullIFrame = true;
    }

    public Body createSkull(ArrayMap<Body, Skull> skullArrayMap) {

        this.resurrecting = false;

        shapeRenderer = new ShapeRenderer();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                skullIFrame = false;
            }
        }, iFrames);

        BodyFactory bodyFactory = new BodyFactory();

        //this.boneBody = bodyFactory.createSkullBody(DungeonCrawler.world,boneX,boneY);

        this.rayCastable = false;

        this.skullBody = bodyFactory.createSkullBody(world,skullX,skullY);

        this.skullBody.setUserData("Skull");

        this.skullHitbox = bodyFactory.createSkullHitbox(skullBody, 5);

        this.skullCreated = true;

        skullArrayMap.put(skullBody, this);

        return this.skullBody;
    }

    public static void renderSkull(SpriteBatch batch, Sprite skullSprite, float x, float y) {

        batch.draw(skullSprite, x - 8f, y - 7f, 176,64, 16, 16, 1,1 ,0);

    }

    public boolean rayCastSkull(Room room, Fire fire) {

            rayResult = false;

            Vector2 translatedCoords = new Vector2();
            translatedCoords.x = skullBody.getPosition().x;
            translatedCoords.y = skullBody.getPosition().y;

            //Ray<Vector2> ray = new Ray<>(enemyBody.getPosition(),player.playerBody.getPosition());

            respawnDetectionRay = new Ray<>(translatedCoords,fire.fireBody.getPosition());

            //two ways to do this
            //get nearest spawner by checking vector equation coords
            //send a ray to each spawner, if it collides with the spawner radius, ignore.

            world.rayCast((fixture, point, normal, fraction) -> {
                //System.out.println(fixture.getBody().getUserData());
                //System.out.println(fixture.getUserData());
                if (fixture.getBody().getType() == BodyDef.BodyType.StaticBody){
                    //System.out.println("STATIC");

                    if (fixture.getUserData() == "Spawner" || fixture.getBody().getUserData() == "Spawner") {

                        return 0;
                    }
                    else if (fixture.getUserData() == "Fire" || fixture.getBody().getUserData() == "Fire") {
                        rayResult = true;

                        return 1;
                    }

                } else
                if (fixture.getBody().getType() == BodyDef.BodyType.DynamicBody){



                }
                return 0;
            },translatedCoords, fire.fireBody.getPosition());

        return rayResult;
    }
}
