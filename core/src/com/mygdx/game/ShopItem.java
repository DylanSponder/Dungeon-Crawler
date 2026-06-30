package com.mygdx.game;

import com.mygdx.game.level.objects.DisplayText;

public class ShopItem {

    public int index, amount, cost;
    public String kind;
    public boolean purchased;
    public DisplayText desc;

    public void ShopInventory() {


    }

    public void createItem(int index, String itemKind, int amount, int cost, DisplayText desc) {
        this.index = index;
        this.amount = amount;
        this.cost = cost;
        this.kind = itemKind;
        this.purchased = purchased;
        this.desc = desc;
        //whether or not this item should only appear once in shopkeeper inventories
        //this.replace = replace;
    }
}
