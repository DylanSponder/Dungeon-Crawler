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

public class Coin {

    public float coinX, coinY;
    private World world;
    public Body coinBody;
    public Fixture coinHitbox;
    public boolean coinCreated;
    public PointLight coinLight;

    public Coin(World world, float x, float y) {
        this.world = world;
        this.coinX = x;
        this.coinY = y;
        this.coinCreated = false;
    }

    public Body createCoin(ArrayMap<Body, Coin> coinArrayMap, RayHandler rayHandler) {

        BodyFactory bodyFactory = new BodyFactory();

        this.coinBody = bodyFactory.createCoin(world, coinX, coinY);

        this.coinBody.setUserData("Coin");

        coinArrayMap.put(coinBody, this);

        this.coinCreated = true;

        Color coinLightColor = new Color(Color.GOLD);
        coinLightColor.a = 0.3f;

        this.coinLight = new PointLight(rayHandler, 100, coinLightColor, 25, coinX, coinY);
        this.coinLight.setXray(true);

        return this.coinBody;
    }


    public static void renderCoin(SpriteBatch batch, Sprite coinSprite, float x, float y) {

        CreateAssets tx = CreateAssets.getInstance();

        batch.draw(coinSprite,x - 3.5f,y - 3.5f,0,0,7,7,1,1,0);
    }


}
