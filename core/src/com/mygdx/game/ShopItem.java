package com.mygdx.game;

import com.mygdx.game.level.objects.Text;

public class ShopItem {

    public int index, amount, cost;
    public String kind;
    public boolean purchased;
    public Text desc;

    public void ShopInventory() {


    }

    public void createItem(int index, String itemKind, int amount, int cost, Text desc) {
        this.index = index;
        this.amount = amount;
        this.cost = cost;
        this.kind = itemKind;
        this.purchased = purchased;
        this.desc = desc;
    }
}
