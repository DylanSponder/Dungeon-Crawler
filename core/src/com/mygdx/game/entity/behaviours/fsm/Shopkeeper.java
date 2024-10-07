package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Text;

import java.util.ArrayList;

public class Shopkeeper {
    public Body shopBody, shopDetectionBody, shopSellBody;
    public Fixture shopHitbox;
    public Fixture shopDetectionRadius, shopSellRadius;
    public Text message, listing;
    public ArrayList<Text> messages;
    public ArrayList<Text> inventory;
    public float posX, posY;

    public Shopkeeper(World world, float x, float y, Text shopMessage) {

        BodyFactory bodyFactory = new BodyFactory();

        this.inventory = new ArrayList<Text>();
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

    public Text Stock(String kind, int index, int amount) {
      //Integer amount = this.inventory.size();
      // TODO Check precedence if buggy
      //Item item = new Item(amount, kind, this.shopBody.getPosition().x, this.shopBody.getPosition().y);
        switch (kind) {
            case "POTION": {
                System.out.println("POTION ADDED TO THE SHOPKEEPER INVENTORY");
                String msg = index + " BUY POTION HERE " + amount;
                Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false,100, 0.1f, false);
                t.textX = this.posX;
                t.textY = this.posY;
               // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
            case "SHIELD": {
                System.out.println("SHIELD ADDED TO THE SHOPKEEPER INVENTORY");
                String msg = "BUY SHIELD HERE";
                Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false,100, 0.1f, false);
                t.textX = this.posX;
                t.textY = this.posY;
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
            case "GREEKFIRE": {
                System.out.println("GREEKFIRE ADDED TO THE SHOPKEEPER INVENTORY");
                String msg = "BUY GREEKFIRE HERE";
                Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false,100, 0.1f, false);
                t.textX = this.posX;
                t.textY = this.posY;
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
        }
        return inventory.get(0);
     // this.inventory.add(item);
    }

    public void ListStock() {
      for (int i = 0; i < inventory.size(); i++) {
        this.inventory.get(i).textY = (inventory.get(i).textY) + i * 16;
        this.inventory.get(i).showing = true;
      }
    }

    public void HideStock() {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.get(i).showing = false;
        }
    }

    public void OrganiseStock() {

    }

    public void BuyItem(Integer itemNum) {
      // Remove(inventory, itemNum)
    }

    public void Remove(String kind) {
      // TODO Determine whether items stop showing / get destroyed when no longer referencing
      // OrganiseStock
    }
}
