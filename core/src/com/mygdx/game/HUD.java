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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.level.CreateCell;
import sun.tools.jconsole.Tab;

public class HUD {
  public Stage stage;
  int topPadding;
  public HealthBar healthBar;
  public Inventory inventory;
  private Label moneyAmount;
  private Table moneyTable;
  private Image moneySymbol;
  public int totalGold;
  public String totalGoldAsString;
  //private CreateTexture tx;

  public HUD(Viewport vp, SpriteBatch sb) {
    stage = new Stage(vp, sb);

    totalGold = 0;
    totalGoldAsString = String.valueOf(totalGold);

    Table table = new Table();
    table.top();
    table.setFillParent(true);

    CreateTexture tx = CreateTexture.getInstance();
    Sprite healthSymbol = new Sprite(tx.heartTexture, 0, 0, 16, 16);
    Sprite healthSymbolHalf = new Sprite(tx.heartTexture, 32, 0, 16, 16);
    Sprite healthSymbolEmpty = new Sprite(tx.heartTexture, 64, 0, 16, 16);
    healthBar = new HealthBar(3, healthSymbol, healthSymbolHalf, healthSymbolEmpty, 10);
    
    moneyTable = new Table();
    moneyAmount = new Label(totalGoldAsString, new LabelStyle(new BitmapFont(), Color.YELLOW));
    moneyTable.add(moneyAmount);
    moneySymbol = new Image(new Sprite(tx.coinTexture, 10, 10));
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

  public void winnerWinnerChickenDinner() {
    stage.clear();
    Table winTable = new Table();
    winTable.center();
    winTable.setFillParent(true);
    Label winWords = new Label("YOU WIN BRODIE!!!!!!!!!!", new LabelStyle(new BitmapFont(), Color.YELLOW));
    winTable.add(winWords);
    stage.addActor(winTable);
  }

  public void updateGold(int gold) {
    CreateTexture tx = CreateTexture.getInstance();

    moneyTable.clear();

    totalGold = totalGold + gold;
    totalGoldAsString  = String.valueOf(totalGold);
    moneyAmount = new Label(totalGoldAsString, new LabelStyle(new BitmapFont(), Color.YELLOW));
    moneyTable.add(moneyAmount);
    moneySymbol = new Image(new Sprite(tx.coinTexture, 10, 10));
    moneyTable.add(moneySymbol).padLeft(2);
  }

  public void update() {
    healthBar.update();
    inventory.update();
  }
}
