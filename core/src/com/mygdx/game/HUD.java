package com.mygdx.game;

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
  public Inventory inventory;

  public HUD(Viewport vp, SpriteBatch sb) {
    stage = new Stage(vp, sb);

    Table table = new Table();
    table.top();
    table.setFillParent(true);

    CreateTexture tx = CreateTexture.getInstance();
    Sprite healthSymbol = new Sprite(tx.heartTexture, 0, 0, 16, 16);
    Sprite healthSymbolHalf = new Sprite(tx.heartTexture, 32, 0, 16, 16);
    Sprite healthSymbolEmpty = new Sprite(tx.heartTexture, 64, 0, 16, 16);
    healthBar = new HealthBar(3, healthSymbol, healthSymbolHalf, healthSymbolEmpty, 10);
    
    Table moneyTable = new Table();
    Label moneyAmount = new Label("0", new LabelStyle(new BitmapFont(), Color.YELLOW));
    moneyTable.add(moneyAmount);
    Image moneySymbol = new Image(new Sprite(tx.coinTexture, 10, 10)); 
    moneyTable.add(moneySymbol).padLeft(2);

    Sprite potionSymbol = new Sprite(tx.potionTexture, 9, 11);
    Sprite emptySlotSymbol = new Sprite(tx.emptySlotTexture, 9, 11);
    inventory = new Inventory(potionSymbol, emptySlotSymbol, 3, 30);

    float spacing = 50f;
    table.add(healthBar);
    table.add(inventory).padLeft(spacing-(potionSymbol.getWidth()*3)).padRight(spacing);//.align(Align.top);//.spaceLeft(spacing-potionSymbol.getWidth());
    table.add(moneyTable);

    stage.addActor(table);
  }

  public void update() {
    healthBar.update();
    inventory.update();
  }
}
