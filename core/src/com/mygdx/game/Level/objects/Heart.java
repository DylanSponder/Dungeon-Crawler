package com.mygdx.game.level.objects;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;

public class Heart {
    public float heartX, heartY;
    private World world;
    public Body heartBody;
    public Fixture heartHitbox;
    public boolean heartCreated;
    public int type;
    public PointLight heartLight;

    public Heart(World world, float x, float y, int type) {
        this.type = type;
        this.world = world;
        this.heartX = x;
        this.heartY = y;
        this.heartCreated = false;
    }

    public Body createHeart(ArrayMap<Body, Heart> heartArrayMap, RayHandler rayHandler) {
            //creates and activates the pots hitbox for collisions
            BodyFactory bodyFactory = new BodyFactory();

            this.heartBody = bodyFactory.createPotion(world, heartX, heartY);

            this.heartBody.setUserData("Heart");

            heartArrayMap.put(heartBody, this);

            this.heartCreated = true;

            Color heartLightColor = new Color(Color.SCARLET);
            heartLightColor.a = 0.7f;

            this.heartLight = new PointLight(rayHandler, 100, heartLightColor, 25, heartX, heartY);
            this.heartLight.setXray(true);

            return this.heartBody;
    }


    public static void renderHeart(SpriteBatch batch, Sprite heartSprite, float x, float y, int type) {

        CreateAssets tx = CreateAssets.getInstance();

        batch.draw(heartSprite,x - 4f,y - 5f,0,0,9,8,1,1,0);
    }

}
