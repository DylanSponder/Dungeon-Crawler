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
import com.mygdx.game.CreateTexture;
import com.mygdx.game.box2D.BodyFactory;

public class Potion {
    public float potionX, potionY;
    private World world;
    public Body potionBody;
    public Fixture potHitbox;
    public boolean potionCreated;
    public int type;
    public PointLight potionLight;

    public Potion(World world, float x, float y, int type) {
        this.type = type;
        this.world = world;
        this.potionX = x;
        this.potionY = y;
        this.potionCreated = false;
    }

    public Body createPotion(ArrayMap<Body, Potion> potionArrayMap, RayHandler rayHandler) {
            //creates and activates the pots hitbox for collisions
            BodyFactory bodyFactory = new BodyFactory();

            this.potionBody = bodyFactory.createPotion(world, potionX, potionY);

            this.potionBody.setUserData("Potion");

            potionArrayMap.put(potionBody, this);

            this.potionCreated = true;

            Color potionLightColor = new Color(Color.SCARLET);
            potionLightColor.a = 0.4f;

            this.potionLight = new PointLight(rayHandler, 100, potionLightColor, 40, potionX, potionY);
            this.potionLight.setXray(true);

            return this.potionBody;
    }


    public static void renderPotion(SpriteBatch batch, Sprite potionSprite, float x, float y) {

        CreateTexture tx = CreateTexture.getInstance();

        batch.draw(potionSprite,x - 4.5f,y - 5.5f,0,0,9,11,1,1,0);
    }

}
