package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Inventory extends Table {
  TextureRegionDrawable potion;
  TextureRegionDrawable emptySlot;
  ArrayList<Image> actors;
  int Cooldown;
  int CooldownRemaining;
  boolean WaitingForCooldown = false;
  int Capacity;
  public int Size;

  Inventory(Sprite potionSymbol, Sprite emptySlotSymbol, int capacity, int cooldown) {
    Cooldown = cooldown;
    potion = new TextureRegionDrawable(potionSymbol);
    emptySlot = new TextureRegionDrawable(emptySlotSymbol);
    actors = new ArrayList<Image>();
    Capacity = capacity;
    for (int i = 0; i < Capacity; i++) {
      actors.add(new Image(emptySlot));
      this.add(actors.get(i));
    }
  }

  public void addPotion() {
    if (this.Size < Capacity) {
      this.Size++;
      actors.get(this.Size-1).setDrawable(potion);
    }
  }

  public void usePotion(int id) {
    if (!WaitingForCooldown) {
      if (this.Size > 0) {
        actors.get(this.Size-1).setDrawable(emptySlot);
        this.Size--;
        WaitingForCooldown = true;
      }
    }
  }

  public void update() {
    System.out.println(CooldownRemaining);
    if (WaitingForCooldown && CooldownRemaining > 0) {
      CooldownRemaining--;
    } else {
      CooldownRemaining = Cooldown;
      WaitingForCooldown = false;
    }
  }
}

