package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HealthBar extends Table {
  float maxHealth;
  float currentHealth;
  Sprite healthSymbol;
  Sprite healthSymbolHalf;
  Sprite healthSymbolEmpty;
  ArrayList<Image> actors;

  HealthBar(float capacity, Sprite symbol, Sprite symbolHalf, Sprite symbolEmpty) {
    maxHealth = capacity;
    currentHealth = maxHealth;
    healthSymbol = symbol;
    healthSymbolHalf = symbolHalf;
    healthSymbolEmpty = symbolEmpty;
    actors = new ArrayList<Image>();

    for (int i = 0; i < currentHealth; i++) {
      actors.add(i, new Image(healthSymbol));
      this.add(actors.get(i));
    }
  }

  public void update(float playerHealth) {
    if (currentHealth != playerHealth) {
      for (int i = actors.size()-1; i > -1; i--) {
        actors.get(i).setDrawable(new TextureRegionDrawable(healthSymbolEmpty));
      }
      // Set last Actor to healthSymbolHalf if playerHealth is fractional
      // if ((int) playerHealth != playerHealth) {
      //   actors.get(actors.size()-1).setDrawable(new TextureRegionDrawable(healthSymbolHalf));
      // }
    }
    currentHealth = playerHealth;
  }
}
