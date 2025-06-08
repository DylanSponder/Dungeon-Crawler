package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
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
    public ArrayList<Text> inventoryText;
    public ArrayMap inventory;
    public float posX, posY;
    public boolean scaledText;

    public Shopkeeper(World world, float x, float y, Text shopMessage) {

        BodyFactory bodyFactory = new BodyFactory();

        this.scaledText = false;

        this.inventory = new ArrayMap();

        this.inventoryText = new ArrayList<Text>();
        this.shopBody = bodyFactory.createShopBody(world, x, y);
        this.posX = x;
        this.posY = y;
        this.shopDetectionBody = bodyFactory.createShopBody(world, x, y);
        this.shopSellBody = bodyFactory.createSimpleDynamicBody(world, x, y);

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

    public Text Stock(String kind, int index) {
        final CreateAssets tx = CreateAssets.getInstance();

        switch (kind) {
            case "WINE": {
                String msg = (index + 1) + ".   WINE";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, true, tx.potionItemSprite, 6.5f);
                this.inventoryText.add(t);
                break;
            }
            case "SHIELD": {
                String msg = (index + 1) + ".   SHIELD";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, true,  tx.shieldItemSprite, 5f);
                this.inventoryText.add(t);
                break;
            }
            case "GREEK FIRE": {
                String msg = (index + 1) + ".   GREEK FIRE";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, true,  tx.greekfireItemSprite, 1.75f);
                this.inventoryText.add(t);
                break;
            }
            case "TORCH": {
                String msg = (index + 1) + ".   TORCH";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, true,  tx.torchItemSprite, 5f);
                this.inventoryText.add(t);
                break;
            }
            case "BELT": {
                String msg = (index + 1) + ".   BACCHUS BELT";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, true,  tx.beltItemSprite, 2.5f);
                this.inventoryText.add(t);
                break;
            }
            case "CHISEL": {
                String msg = (index + 1) + ".   CHISEL";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, true,  tx.chiselItemSprite, 3.25f);
                this.inventoryText.add(t);
                break;
            }
            case "LANCE": {
                String msg = (index + 1) + ".   LANCE";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, false,  tx.coinHUDSprite, 6f);
                this.inventoryText.add(t);
                break;
            }
            case "HELM": {
                String msg = (index + 1) + ".   HELM OF HADES";
                Text t = new Text(DungeonCrawler.defaultFont2, msg, Color.WHITE, false,100, 0.1f, false, false,  tx.coinHUDSprite, 6f);
                this.inventoryText.add(t);
                break;
            }
        }
        return inventoryText.get(index);
     // this.inventoryText.add(item);
    }

    public Text DescribeStock(String kind, int index, int cost) {
        final CreateAssets tx = CreateAssets.getInstance();

        switch (kind) {
            case "WINE": {
                String desc = "[  " + cost + "] HEAL 1.5 HEARTS (E)";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -1f);
                this.inventoryText.add(t);
                break;
            }
            case "SHIELD": {
                String desc = "[  " + cost + "] BLOCK (SHIFT)";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -1f);
                this.inventoryText.add(t);
                break;
            }
            case "GREEK FIRE": {
                String desc = "[  " + cost + "] FLAMING ARROWS";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -0.5f);
                this.inventoryText.add(t);
                break;
            }
            case "TORCH": {
                String desc = "[  " + cost + "] MORE LIGHT";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -0.5f);
                this.inventoryText.add(t);
                break;
            }
            case "BELT": {
                String desc = "[  " + cost + "] SPACE + 1 (WINE)";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -0.5f);
                this.inventoryText.add(t);
                break;
            }
            case "CHISEL": {
                String desc = "[  " + cost + "] BREAK ROCKS (C)";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -0.5f);
                this.inventoryText.add(t);
                break;
            }
            case "LANCE": {
                String desc = "  " + cost + " MELEE RANGE UP";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -0.5f);
                this.inventoryText.add(t);
                break;
            }
            case "HELM": {
                String desc = "  " + cost + " INVISIBILITY (10 SECONDS PER ROOM)";
                Text t = new Text(DungeonCrawler.defaultFont2, desc, Color.WHITE, false,100, 0.1f, false, true, tx.coinHUDSprite, -0.5f);
                this.inventoryText.add(t);
                break;
            }
        }
        return inventoryText.get(index);
    }

    public void ListStock() {
      for (int i = 0; i < inventoryText.size(); i++) {
          if (!this.scaledText) {
              int ihalf = i / 2;

              if (i >= 4) {
                  this.inventoryText.get(i).textY = inventoryText.get(i).textY - (i * 12) - 15;
                  this.inventoryText.get(i).showing = true;
              }
              else if (i >= 2) {
                  //this.inventoryText.get(i).textY = inventoryText.get(i).textY - i * 12;
                  this.inventoryText.get(i).textY = inventoryText.get(i).textY - (i * 12) - 10;
                  this.inventoryText.get(i).showing = true;
              } else {
                  this.inventoryText.get(i).textY = inventoryText.get(i).textY - (i * 12) - 5;
                  this.inventoryText.get(i).showing = true;
              }
          } else {
              int ihalf = i / 2;

              if (ihalf % 2 != 0) {
                  this.inventoryText.get(i).showing = true;
                  //this.inventoryText.get(i).textY = (inventoryText.get(i).textY);
              } else {
                  //this.inventoryText.get(i).textY = inventoryText.get(i).textY - i * 12;
                  this.inventoryText.get(i).showing = true;
              }



          }
          if (i == inventoryText.size() - 1) {
              this.scaledText = true;
          }
      }
    }

    public void HideStock() {
        for (int i = 0; i < inventoryText.size(); i++) {
            this.inventoryText.get(i).showing = false;
        }
    }

    public void OrganiseStock() {

    }

    public void BuyItem(Integer itemNum) {
      // Remove(inventoryText, itemNum)
    }

    public void Remove(String kind) {
      // TODO Determine whether items stop showing / get destroyed when no longer referencing
      // OrganiseStock
    }
}
