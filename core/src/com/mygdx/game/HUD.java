package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
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
  public Stage subStage;
  int topPadding;
  public HealthBar healthBar;
  public Inventory inventory;
  private Label moneyAmount;
  private Table moneyTable;
  private Image moneySymbol;
  public int totalGold;
  public String totalGoldAsString;
  public Label winWords, startWords, roomWords;
  private float hudFade;
  //private CreateTexture tx;

  public HUD(Viewport vp, SpriteBatch sb) {
    stage = new Stage(vp, sb);
    subStage = new Stage(vp, sb);

    totalGold = 12;
    totalGoldAsString = String.valueOf(totalGold);

    hudFade = 1f;

    Table table = new Table();
    table.top();
    table.setFillParent(true);

    CreateAssets tx = CreateAssets.getInstance();
    Sprite healthSymbol = new Sprite(tx.heartHUDTexture, 0, 0, 16, 16);
    Sprite healthSymbolHalf = new Sprite(tx.heartHUDTexture, 32, 0, 16, 16);
    Sprite healthSymbolEmpty = new Sprite(tx.heartHUDTexture, 64, 0, 16, 16);
    //Health slots
    healthBar = new HealthBar(3f, healthSymbol, healthSymbolHalf, healthSymbolEmpty, 80);
    
    moneyTable = new Table();
    moneyAmount = new Label(totalGoldAsString, new LabelStyle(DungeonCrawler.defaultFont, Color.YELLOW));
    moneyTable.add(moneyAmount).padTop(25);
    moneySymbol = new Image(new Sprite(tx.coinTexture, 10, 10));
    moneyTable.add(moneySymbol).padBottom(0);

    Sprite potionSymbol = new Sprite(tx.potionItemTexture, 9, 11);
    Sprite emptySlotSymbol = new Sprite(tx.emptySlotTexture, 9, 11);
    //Potion slots
    inventory = new Inventory(potionSymbol, emptySlotSymbol, 1, 150);

    float spacing = 50f;
    table.add(healthBar);
    table.add(inventory).padLeft(spacing-(potionSymbol.getWidth()*3)).padRight(spacing);//.align(Align.top);//.spaceLeft(spacing-potionSymbol.getWidth());
    table.add(moneyTable);

    stage.addActor(table);
  }

  public void startLevel() {
    subStage.clear();
    Table startTable = new Table();
    startTable.center();
    startTable.setFillParent(true);
    startWords = new Label("j CLAY CATACOMBS j", new LabelStyle(DungeonCrawler.defaultFont, Color.WHITE));
    startTable.add(startWords);
    subStage.addActor(startTable);
  }

  public void winRoom() {
    subStage.clear();
    Table winTable = new Table();
    winTable.center();
    winTable.setFillParent(true);
    Color color = new Color(1,1,1,1);
    roomWords = new Label("ROOM CLEARED!", new LabelStyle(DungeonCrawler.defaultFont, color));
    winTable.add(roomWords);
    subStage.addActor(winTable);
  }

  public void winLevel() {
    subStage.clear();
    Table winTable = new Table();
    winTable.center();
    winTable.setFillParent(true);
    Color color = new Color(1,1,1,1);
    winWords = new Label("LEVEL CLEARED!", new LabelStyle(DungeonCrawler.defaultFont, color));
    winTable.add(winWords);
    subStage.addActor(winTable);

  }
  public void fadeHUD(Label words) {
          if (hudFade <= 0) {
            words.clear();
            hudFade = 1;
            if (DungeonCrawler.player.floorCleared) {
              DungeonCrawler.player.floorCleared = false;
            } else if (DungeonCrawler.player.roomCleared) {
              DungeonCrawler.player.roomCleared = false;
            }
            subStage.clear();
          } else {
            hudFade = hudFade - 0.0045f;
            words.setColor(1f,1f,1f,hudFade);
          }
      }

  public void updateGold(int gold, boolean add) {
    CreateAssets tx = CreateAssets.getInstance();

    moneyTable.clear();
     if (add) {
       totalGold = totalGold + gold;
     } else {
       totalGold = totalGold - gold;
     }

    totalGoldAsString  = String.valueOf(totalGold);
    moneyAmount = new Label(totalGoldAsString, new LabelStyle(DungeonCrawler.defaultFont, Color.YELLOW));
    moneyTable.add(moneyAmount).padTop(25);
    moneySymbol = new Image(new Sprite(tx.coinTexture, 10, 10));
    moneyTable.add(moneySymbol).padBottom(0);
  }

  public void update() {
    healthBar.update();
    inventory.update();
  }
}
