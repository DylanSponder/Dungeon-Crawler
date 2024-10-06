package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.DungeonCrawler;

import static com.mygdx.game.DungeonCrawler.messages;

public class Item {

    public int index;
    public Sprite itemSprite;
    public Text listing;
    public float X, Y;

    public Item(int index, String kind, float posX, float posY) {
      this.index = index;
      this.X = posX;
      this.Y = posY;

    }
}
