package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
  public Stage stage;
  int topPadding;
  public HealthBar healthBar;

  public HUD(Viewport vp, SpriteBatch sb, Image moneySymbol) {
    stage = new Stage(vp, sb);
    Table table = new Table();

    CreateTexture tx = CreateTexture.getInstance();
    Sprite healthSymbol = new Sprite(tx.heartTexture, 0, 0, 16, 16);
    Sprite healthSymbolHalf = new Sprite(tx.heartTexture, 32, 0, 16, 16);
    Sprite healthSymbolEmpty = new Sprite(tx.heartTexture, 64, 0, 16, 16);
    healthBar = new HealthBar(3, healthSymbol, healthSymbolHalf, healthSymbolEmpty, 60);

    Table moneyTable = new Table();
    table.top();
    table.setFillParent(true);
    
    Label moneyAmount = new Label("0", new LabelStyle(new BitmapFont(), Color.YELLOW));
    moneyTable.add(moneyAmount).padRight(2);
    moneyTable.add(moneySymbol);

    table.add(healthBar).padRight(130);
    table.add(moneyTable);

    stage.addActor(table);
  }

  public void update() {
    healthBar.update();
  }
}
