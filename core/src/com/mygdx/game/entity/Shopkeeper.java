package com.mygdx.game.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;

public class Shopkeeper {
    public Body shopBody, shopDetectionBody;
    public Fixture shopHitbox;
    public Fixture shopDetectionRadius;

    public Shopkeeper(World world, float x, float y) {

        BodyFactory bodyFactory = new BodyFactory();

        shopBody = bodyFactory.createShopBody(world, x, y);
        shopDetectionBody = bodyFactory.createEnemyBody(world, x, y);
        shopHitbox = bodyFactory.createShopHitbox(shopBody, 7.5f);

        shopDetectionRadius = bodyFactory.createShopDetectionRadius(shopBody, 100);
        shopDetectionRadius.setSensor(true);

        this.shopBody.setUserData("Shopkeeper");
    }
}
