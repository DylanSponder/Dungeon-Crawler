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
  int initialIFrames;
  int remainingIFrames;
  boolean vulnerable;

  public void LoseHealth(float health) {
    if (vulnerable) {
      float result = currentHealth - health;
      if (result < -1) {
        currentHealth = 0;
      } else {
        currentHealth = result;
      }
      vulnerable = false;
    }
  }

  HealthBar(float capacity, Sprite symbol, Sprite symbolHalf, Sprite symbolEmpty, int totalIFrames) {
    vulnerable = true;
    initialIFrames = totalIFrames;
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
    for (int i = actors.size()-1; i >= currentHealth && i > -1; i--) {
      actors.get(i).setDrawable(healthSymbolEmpty);
    }
    if (remainingIFrames == 0) {
      remainingIFrames = initialIFrames;
      vulnerable = true;
    } else {
      remainingIFrames--;
    }
  }
}
