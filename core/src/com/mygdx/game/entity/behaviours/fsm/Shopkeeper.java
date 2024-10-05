package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Item;
import com.mygdx.game.level.objects.Text;

import java.util.ArrayList;

public class Shopkeeper {
    public Body shopBody, shopDetectionBody, shopSellBody;
    public Fixture shopHitbox;
    public Fixture shopDetectionRadius, shopSellRadius;
    public Text message;
    public ArrayList<Text> messages;
    public ArrayList<Item> inventory;
    public float posX, posY;

    public Shopkeeper(World world, float x, float y, Text shopMessage) {

        BodyFactory bodyFactory = new BodyFactory();

        this.inventory = new ArrayList<Item>();
        this.shopBody = bodyFactory.createShopBody(world, x, y);
        this.posX = x;
        this.posY = y;
        this.shopDetectionBody = bodyFactory.createShopBody(world, x, y);
        this.shopSellBody = bodyFactory.createSimpleBody(world, x, y);

        this.shopHitbox = bodyFactory.createShopHitbox(shopBody, 7.5f);

        this.shopDetectionRadius = bodyFactory.createShopDetectionRadius(shopBody, 70);
        this.shopDetectionRadius.setSensor(true);

        this.shopSellRadius = bodyFactory.createShopDetectionRadius(shopBody, 40);
        this.shopSellRadius.setSensor(true);

        this.message = shopMessage;
        this.messages = new ArrayList<>();

        this.shopBody.setUserData("Shopkeeper");
        this.shopDetectionRadius.setUserData("ShopRadius");
    }

    public void Stock(String kind) {
      Integer amount = inventory.size();
      // TODO Check precedence if buggy
      Item item = new Item(amount, kind, posX, posY+amount*16);
      inventory.add(item);
    }

    public void ListStock() {
      for (int i = 0; i < inventory.size(); i++) {
        inventory.get(i).listing.showing = true;
      }
    }

    public void OrganiseStock() {}

    public void BuyItem(Integer itemNum) {
      // Remove(inventory, itemNum)
    }

    public void Remove(String kind) {
      // TODO Determine whether items stop showing / get destroyed when no longer referencing
      // OrganiseStock
    }
}
