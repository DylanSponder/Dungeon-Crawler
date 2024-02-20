package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HealthBar extends Table {
  float maxHealth;
  public float currentHealth;
  TextureRegionDrawable healthSymbol;
  TextureRegionDrawable healthSymbolHalf;
  TextureRegionDrawable healthSymbolEmpty;
  ArrayList<Image> actors;

  public void LoseHealth(float health) {
    float result = currentHealth - health;
    if (result > -1) {
      System.out.println("Not: "+result);
      currentHealth = result;
    }
  }

  HealthBar(float capacity, Sprite symbol, Sprite symbolHalf, Sprite symbolEmpty) {
    maxHealth = capacity;
    currentHealth = maxHealth;
    healthSymbol = new TextureRegionDrawable(symbol);
    healthSymbolHalf = new TextureRegionDrawable(symbolHalf);
    healthSymbolEmpty = new TextureRegionDrawable(symbolEmpty);
    actors = new ArrayList<Image>();

    for (int i = 0; i < currentHealth; i++) {
      actors.add(i, new Image(healthSymbol));
      this.add(actors.get(i));
    }
  }

  public void update() {
    // Set last Actor to healthSymbolHalf if playerHealth is fractional
    if ((int) currentHealth != currentHealth) {
      actors.get((int) currentHealth).setDrawable(healthSymbolHalf);
    }
    for (int i = actors.size()-1; i >= currentHealth; i--) {
      actors.get(i).setDrawable(healthSymbolEmpty);
    }
  }
}
