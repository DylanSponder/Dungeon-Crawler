package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DungeonCrawler;

public class Item {

    public int index;
    public Sprite itemSprite;
    public Text listing;
    public float X, Y;

    public Item(int index, String kind, float posX, float posY) {
      this.index = index;
      this.X = posX;
      this.Y = posY;
      switch (kind) {
        case "POTION": {
          String msg = String.valueOf(index) + ". Potion";
          Text t = new Text(DungeonCrawler.defaultFont, msg, Color.WHITE, false, posX, posY, false);
          this.listing = t;
        }
      }
    }
}
