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
  int numberOfActors;

  public void LoseHealth(float health) {
    if (vulnerable) {
      float result = currentHealth - health;
      if (result < 0) {
        currentHealth = 0;
      } else {
        currentHealth = result;
      }
      vulnerable = false;
    }
  }

  public void GainHealth(float health) {
    float result = currentHealth + health;
    if (result > maxHealth) {
      currentHealth = maxHealth;
    } else {
      currentHealth = result;
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
    numberOfActors = actors.size();
  }

  public void update() {
    for (int i = 0; i < numberOfActors; i++) {
      //System.out.println("health: "+currentHealth);
      if (i < currentHealth) {
        actors.get(i).setDrawable(healthSymbol);
      }
      if (i > currentHealth-1) {
        actors.get(i).setDrawable(healthSymbolEmpty);
      }
      if (i == (int) currentHealth && (int) currentHealth != currentHealth && currentHealth > 0) {
        actors.get(i).setDrawable(healthSymbolHalf);
      }
    }
    //System.out.println(remainingIFrames);
    if (remainingIFrames == 0) {
      remainingIFrames = initialIFrames;
      vulnerable = true;
    } else if (!vulnerable) {
      remainingIFrames--;
    }
  }
}
