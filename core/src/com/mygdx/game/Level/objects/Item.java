package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item {

    public int index;
    public Sprite itemSprite;
    public Text message;


    public Item(int index, Text message) {
        this.index = index;
        this.message = message;

    }
}
