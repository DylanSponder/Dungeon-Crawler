package com.mygdx.game.level.objects;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;

public class Cobweb {
    public float cobX, cobY;
    private World world;
    public Body cobBody, innerCobBody;
    public Fixture cobHitbox;
    public boolean cobCreated;

    // Cobwebs are solid and block entities
// can be set alight and then burned/destroyed by fire arrows that collide with fire from columns and pedestals (torches?)
// The Player and Enemies can send attacks through (if sighted)
//

    public Cobweb(World world, float x, float y) {
        this.world = world;
        this.cobX = x;
        this.cobY = y;
        this.cobCreated = false;
    }

    public Body createCobweb(ArrayMap<Body, Cobweb> cobArrayMap) {

        BodyFactory bodyFactory = new BodyFactory();

        this.cobBody = bodyFactory.createCobweb(world, cobX, cobY);


        this.innerCobBody = bodyFactory.createImpassableCobweb(world, cobX, cobY);


        this.cobBody.setUserData("Cobweb");

        this.innerCobBody.setUserData("InnerCobweb");

        cobArrayMap.put(cobBody, this);

        this.cobCreated = true;

        return this.cobBody;
    }

    public static void renderCobweb(SpriteBatch batch, Sprite potionSprite, float x, float y) {

        CreateAssets tx = CreateAssets.getInstance();

        batch.draw(tx.cobwebSprite,x - 8f,y - 8f,0,0,16,16,1,1,0);
    }
}
