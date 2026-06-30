package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entity.behaviours.fsm.BossMinotaur;

import static com.mygdx.game.DungeonCrawler.*;

public class HUD {
  public Stage stage;
  public Stage subStage;
  public Stage menuStage;
  public Group itemGroup, compassGroup;
  int topPadding;
  public HealthBar healthBar;
  public BossHealthbar bossHealthbar;
  public PotionSlotInventory inventory;
  public VerticalGroup itemVerticalGroup, compassVerticalGroupSpacer, compassVerticalGroup;
  private Label moneyAmount, bossName;
  private Table moneyTable;
    private Table itemTable;
    private static Table bossNameTable;
    private static Table bossHealthTable;
  private Container compassSpacerContainer, compassContainer;
  public static RotateByAction rotateArrow;
  public static RepeatAction repeatAction;
  public static Image moneySymbol, compassImage, compassArrowImage;
  public int totalGold;
  public String totalGoldAsString;
  public Label winWords, startWords, roomWords;
  private float hudFade;
  public static TextureRegionDrawable compassDrawable, compassArrowDrawable;
  public static TextureRegionDrawable torchSlot, beltSlot, shieldSlot, greekfireSlot, chiselSlot, wingsSlot;
  public static TextureRegionDrawable torchItem, beltItem, shieldItem, greekfireItem, chiselItem, wingsItem;
  public static Image torchSlotImage, beltSlotImage, shieldSlotImage, greekfireSlotImage, chiselSlotImage, wingsSlotImage;
  public static Image torchItemImage, beltItemImage, shieldItemImage, greekfireItemImage, chiselItemImage, wingsItemImage;
  //private CreateTexture tx;

  public HUD(Viewport vp, SpriteBatch sb) {
    stage = new Stage(vp, sb);
    subStage = new Stage(vp, sb);

    totalGold = 0;
    totalGoldAsString = String.valueOf(totalGold);

    hudFade = 1f;

    Table table = new Table();
    table.top();
    table.setFillParent(true);

    itemGroup = new Group();
    itemTable = new Table();
    //create the items interface
    itemVerticalGroup = new VerticalGroup();
    itemVerticalGroup.addActor(itemTable);
    itemVerticalGroup.columnLeft();
    itemVerticalGroup.padTop(15);
    itemVerticalGroup.padRight(30);
    itemGroup.addActor(itemVerticalGroup);


    CreateAssets tx = CreateAssets.getInstance();

    //create empty item slot icons of all the items the player can collect
    torchSlot = new TextureRegionDrawable(tx.torchSlotSprite);
    torchSlotImage = new Image(torchSlot);
    itemVerticalGroup.addActor(torchSlotImage);

    shieldSlot = new TextureRegionDrawable(tx.shieldSlotSprite);
    shieldSlotImage = new Image(shieldSlot);
    itemVerticalGroup.addActor(shieldSlotImage);

    chiselSlot = new TextureRegionDrawable(tx.chiselSlotSprite);
    chiselSlotImage = new Image(chiselSlot);
    itemVerticalGroup.addActor(chiselSlotImage);

    greekfireSlot = new TextureRegionDrawable(tx.greekfireSlotSprite);
    greekfireSlotImage = new Image(greekfireSlot);
    itemVerticalGroup.addActor(greekfireSlotImage);

    beltSlot = new TextureRegionDrawable(tx.beltSlotSprite);
    beltSlotImage = new Image(beltSlot);
    itemVerticalGroup.addActor(beltSlotImage);

    wingsSlot = new TextureRegionDrawable(tx.wingsSlotSprite);
    wingsSlotImage = new Image(wingsSlot);
    itemVerticalGroup.addActor(wingsSlotImage);

    table.add(itemGroup);

    Sprite healthSymbol = new Sprite(tx.heartHUDTexture, 0, 0, 16, 16);
    Sprite healthSymbolHalf = new Sprite(tx.heartHUDTexture, 32, 0, 16, 16);
    Sprite healthSymbolEmpty = new Sprite(tx.heartHUDTexture, 64, 0, 16, 16);
    //Health slots
    healthBar = new HealthBar(3f, healthSymbol, healthSymbolHalf, healthSymbolEmpty, 60);

    //bossHealthbar = bossMinotaurs.get(0).minoHealthbar;
    
    moneyTable = new Table();
    moneyAmount = new Label(totalGoldAsString, new LabelStyle(DungeonCrawler.defaultFont2, Color.GOLD));
    moneyTable.add(moneyAmount).padTop(5);
    moneySymbol = new Image(new Sprite(tx.coinHUDTexture, 12, 12));
    moneyTable.add(moneySymbol).padBottom(0);

    Sprite potionSymbol = new Sprite(tx.potionHUDTexture, 11, 13);
    Sprite emptySlotSymbol = new Sprite(tx.potionSlotTexture, 9, 11);
    //Potion slots
    inventory = new PotionSlotInventory(potionSymbol, emptySlotSymbol, 1, 150);

    float spacing = 50f;
    table.add(healthBar);
    table.add(inventory).padLeft(spacing-(potionSymbol.getWidth()*3)).padRight(spacing);//.align(Align.top);//.spaceLeft(spacing-potionSymbol.getWidth());
    table.add(moneyTable);

    //create the boss healtbar with name above

      bossName = new Label("MINOTAUR", new LabelStyle(DungeonCrawler.defaultFont2, Color.WHITE));
      bossNameTable = new Table();
      bossNameTable.bottom();
      bossNameTable.padBottom(4);
      bossNameTable.setFillParent(true);
      bossNameTable.add(bossName);

      bossHealthTable = new Table();
      bossHealthTable.bottom();
      bossHealthTable.padRight(14);
      bossHealthTable.setFillParent(true);
      bossHealthTable.add(bossHealthbar);

    //create the compass that guides players to the exit door for that room

    compassGroup = new Group();
    compassContainer = new Container();
    compassSpacerContainer = new Container();
    compassVerticalGroupSpacer = new VerticalGroup();
    compassVerticalGroup = new VerticalGroup();

    compassVerticalGroup.columnRight();
    compassVerticalGroup.padTop(55);
    compassVerticalGroup.padBottom(0);
    compassVerticalGroup.padLeft(25);

    compassVerticalGroupSpacer.columnRight();
    compassVerticalGroupSpacer.padTop(0);
    compassVerticalGroupSpacer.padBottom(0);
    compassVerticalGroupSpacer.padLeft(25);

    compassDrawable = new TextureRegionDrawable(tx.compassSprite);
    compassImage = new Image(compassDrawable);

    compassArrowDrawable = new TextureRegionDrawable(tx.compassArrowSprite);
    compassArrowImage = new Image(compassArrowDrawable);

    compassVerticalGroupSpacer.addActor(compassSpacerContainer);
    compassVerticalGroup.addActor(compassContainer);
    compassVerticalGroup.addActor(compassImage);
    compassVerticalGroup.addActor(compassArrowImage);

    compassGroup.addActor(compassVerticalGroupSpacer);
    compassGroup.addActor(compassVerticalGroup);

    compassArrowImage.setOrigin(compassArrowImage.getWidth()/2,compassArrowImage.getHeight()/2);

    MoveToAction moveToCompass = new MoveToAction();
    moveToCompass.setPosition(compassImage.getX() + compassImage.getWidth()/2 - compassImage.getWidth()/3 + 1.5f,compassImage.getY() - 55 - compassImage.getHeight() + 2.5f);

    rotateArrow = new RotateByAction();
    rotateArrow.setAmount(1);

    repeatAction = new RepeatAction();

    compassArrowImage.addAction(moveToCompass);

    SpriteBatch batch = new SpriteBatch();

    table.add(compassGroup);

    pauseMenuStage.addActor(table);
    optionsMenuStage.addActor(table);




    bossHealthbarStage.clear();

  }

  public static void showBossBar(String bossName) {

      bossHealthbarStage.addActor(bossHealthTable);
      bossHealthbarStage.addActor(bossNameTable);
  }

    public static void clearBossBar() {

        bossHealthbarStage.clear();
    }

  public void addItem(int type) {

    CreateAssets tx = CreateAssets.getInstance();

    //we create a new Image with the texture of the item
    //then swap it with its respective slot
    //and finally just delete the slot image

    switch (type) {
      case 1:
        torchItem = new TextureRegionDrawable(tx.torchItemSprite);
        torchItemImage = new Image(torchItem);
        itemVerticalGroup.addActor(torchItemImage);
        itemVerticalGroup.swapActor(torchSlotImage, torchItemImage);
        itemVerticalGroup.removeActor(torchSlotImage);
        break;
      case 2:
        shieldItem = new TextureRegionDrawable(tx.shieldItemSprite);
        shieldItemImage = new Image(shieldItem);
        itemVerticalGroup.addActor(shieldItemImage);
        itemVerticalGroup.swapActor(shieldSlotImage, shieldItemImage);
        itemVerticalGroup.removeActor(shieldSlotImage);
        break;
      case 3:
        chiselItem = new TextureRegionDrawable(tx.chiselItemSprite);
        chiselItemImage = new Image(chiselItem);
        itemVerticalGroup.addActor(chiselItemImage);
        itemVerticalGroup.swapActor(chiselSlotImage, chiselItemImage);
        itemVerticalGroup.removeActor(chiselSlotImage);
        break;
      case 4:
        greekfireItem = new TextureRegionDrawable(tx.greekfireItemSprite);
        greekfireItemImage = new Image(greekfireItem);
        itemVerticalGroup.addActor(greekfireItemImage);
        itemVerticalGroup.swapActor(greekfireSlotImage, greekfireItemImage);
        itemVerticalGroup.removeActor(greekfireSlotImage);
        break;
      case 5:
        beltItem = new TextureRegionDrawable(tx.beltItemSprite);
        beltItemImage = new Image(beltItem);
        itemVerticalGroup.addActor(beltItemImage);
        itemVerticalGroup.swapActor(beltSlotImage, beltItemImage);
        itemVerticalGroup.removeActor(beltSlotImage);
        break;
        case 6:
            wingsItem = new TextureRegionDrawable(tx.wingsItemSprite);
            wingsItemImage = new Image(wingsItem);
            itemVerticalGroup.addActor(wingsItemImage);
            itemVerticalGroup.swapActor(wingsSlotImage, wingsItemImage);
            itemVerticalGroup.removeActor(wingsSlotImage);
            break;
    }
  }

  public void startLevel() {
    subStage.clear();
    Table startTable = new Table();
    startTable.center();
    startTable.setFillParent(true);
    if (modifiersAllowed && DungeonCrawler.curse.enabled) {
        startWords = new Label(curse.message.message, new LabelStyle(DungeonCrawler.defaultFont, curse.message.color));
    } else {
        startWords = new Label("Ω HELLAS DUNGEON Ω", new LabelStyle(DungeonCrawler.defaultFont, Color.WHITE));
    }

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

    Compass.showCompass();
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
            hudFade = hudFade - 0.0025f;
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
    moneyAmount = new Label(totalGoldAsString, new LabelStyle(DungeonCrawler.defaultFont2, Color.GOLD));
    moneyTable.add(moneyAmount).padTop(5);
    moneySymbol = new Image(new Sprite(tx.coinHUDTexture, 12, 12));
    moneyTable.add(moneySymbol).padBottom(0);
  }

  public void update() {
    healthBar.update();
    inventory.update();
  }
}
