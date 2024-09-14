package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Text;

import java.util.ArrayList;

public class Shopkeeper {
    public Body shopBody, shopDetectionBody, shopSellBody;
    public Fixture shopHitbox;
    public Fixture shopDetectionRadius, shopSellRadius;
    public Text shopMessage;
    public ArrayList<Text> shopMessages;

    public Shopkeeper(World world, float x, float y, Text shopMessage) {

        BodyFactory bodyFactory = new BodyFactory();

        this.shopBody = bodyFactory.createShopBody(world, x, y);
        this.shopDetectionBody = bodyFactory.createShopBody(world, x, y);
        this.shopSellBody = bodyFactory.createSimpleBody(world, x, y);

        this.shopHitbox = bodyFactory.createShopHitbox(shopBody, 7.5f);

        this.shopDetectionRadius = bodyFactory.createShopDetectionRadius(shopBody, 70);
        this.shopDetectionRadius.setSensor(true);

        this.shopSellRadius = bodyFactory.createShopDetectionRadius(shopBody, 40);
        this.shopSellRadius.setSensor(true);

        this.shopMessage = shopMessage;
        this.shopMessages = new ArrayList<>();

        this.shopBody.setUserData("Shopkeeper");
        this.shopDetectionRadius.setUserData("ShopRadius");
    }
}
