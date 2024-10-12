package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateAssets;
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
    public boolean scaledText;

    public Shopkeeper(World world, float x, float y, Text shopMessage) {

        BodyFactory bodyFactory = new BodyFactory();

        this.scaledText = false;

        this.inventory = new ArrayList<Text>();
        this.shopBody = bodyFactory.createShopBody(world, x, y);
        this.posX = x;
        this.posY = y;
        this.shopDetectionBody = bodyFactory.createShopBody(world, x, y);
        this.shopSellBody = bodyFactory.createSimpleBody(world, x, y);

        this.shopHitbox = bodyFactory.createShopHitbox(shopBody, 7.5f);

        this.shopDetectionRadius = bodyFactory.createShopDetectionRadius(shopBody, 70);
        this.shopDetectionRadius.setSensor(true);

        this.shopSellRadius = bodyFactory.createShopDetectionRadius(shopBody, 35);
        this.shopSellRadius.setSensor(true);

        this.message = shopMessage;
        this.messages = new ArrayList<>();

        this.shopBody.setUserData("Shopkeeper");
        this.shopDetectionRadius.setUserData("ShopRadius");
    }

    public Text Stock(String kind, int index, int amount, int cost) {
        final CreateAssets tx = CreateAssets.getInstance();

        switch (kind) {
            case "POTION": {
                System.out.println("POTION ADDED TO THE SHOPKEEPER INVENTORY");
                String msg = (index + 1) + ".  POTION " + "(" + amount + " LEFT)";
                Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false,100, 0.1f, false, true, tx.potionSprite, 9f);
                //Text t2 = new Text(DungeonCrawler.defaultFont, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinSprite, 8f);
               // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                //this.inventory.add(t2);
                break;
            }
            case "SHIELD": {
                System.out.println("SHIELD ADDED TO THE SHOPKEEPER INVENTORY");
                String msg = (index + 1) + ". SHIELD " + "(" + amount + " LEFT)";
                Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false,100, 0.1f, false, false,  tx.coinSprite, 15f);
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
            case "GREEKFIRE": {
                System.out.println("GREEK FIRE ADDED TO THE SHOPKEEPER INVENTORY");
                String msg = (index + 1) + ". GREEK FIRE " + "(" + amount + " LEFT)";
                Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false,100, 0.1f, false, false,  tx.coinSprite, 15f);
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
        }
        return inventory.get(index);
     // this.inventory.add(item);
    }

    public Text DescribeStock(String kind, int index, int amount, int cost) {
        final CreateAssets tx = CreateAssets.getInstance();

        switch (kind) {
            case "POTION": {
                System.out.println("POTION DESCRIPTION ADDED TO A POTION");
                String desc = "  " + cost + " HEALS 1.5 HEARTS";
                Text t = new Text(DungeonCrawler.defaultFont, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinSprite, 0.5f);
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
            case "SHIELD": {
                System.out.println("SHIELD DESCRIPTION ADDED TO A SHIELD");
                String desc = "  " + cost + " abcdefghij";
                Text t = new Text(DungeonCrawler.defaultFont, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinSprite, 0.5f);
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
            case "GREEKFIRE": {
                System.out.println("GREEK FIRE DESCRIPTION ADDED TO A GREEK FIRE");
                String desc = "  " + cost + " FIRE ARROW X 10";
                Text t = new Text(DungeonCrawler.defaultFont, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinSprite, 0.5f);
                // DungeonCrawler.messages.add(t);
                //this.inventory.add()
                this.inventory.add(t);
                break;
            }
        }
        return inventory.get(index);
    }

    public void ListStock() {
      for (int i = 0; i < inventory.size(); i++) {
          if (!this.scaledText) {
              int ihalf = i / 2;

              if (ihalf % 2 != 0) {
                  //this.inventory.get(i).textY = inventory.get(i).textY - i * 12;
                  this.inventory.get(i).textY = inventory.get(i).textY - (i * 12) - 24;
                  this.inventory.get(i).showing = true;
              } else {
                  this.inventory.get(i).textY = inventory.get(i).textY - (i * 12) - 12;
                  this.inventory.get(i).showing = true;
              }
          } else {
              int ihalf = i / 2;

              if (ihalf % 2 != 0) {
                  this.inventory.get(i).showing = true;
                  //this.inventory.get(i).textY = (inventory.get(i).textY);
              } else {
                  //this.inventory.get(i).textY = inventory.get(i).textY - i * 12;
                  this.inventory.get(i).showing = true;
              }



          }
          if (i == inventory.size() - 1) {
              this.scaledText = true;
          }
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
