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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
  public Stage stage;
  int topPadding;
  public int health;
  Image heart1;
  Image heart2;
  Image heart3;

  public HUD(Viewport vp, SpriteBatch sb, Image healthSymbol, Image moneySymbol) {
    health = 3;
    stage = new Stage(vp, sb);
    Table table = new Table();
    Table healthTable = new Table();
    Table moneyTable = new Table();
    table.top();
    table.setFillParent(true);

    CreateTexture tx = CreateTexture.getInstance();
    heart1 = new Image(new Sprite(tx.heartTexture, 16, 16));
    heart2 = new Image(new Sprite(tx.heartTexture, 16, 16));
    heart3 = new Image(new Sprite(tx.heartTexture, 16, 16));
    // healthTable.add(healthSymbol);
    healthTable.add(heart1);
    healthTable.add(heart2);
    healthTable.add(heart3);

    Label moneyAmount = new Label("0", new LabelStyle(new BitmapFont(), Color.YELLOW));
    moneyTable.add(moneyAmount).padRight(2);
    moneyTable.add(moneySymbol);
    table.add(healthTable).padRight(150);
    table.add(moneyTable);

    stage.addActor(table);
  }

  public void update() {
    CreateTexture tx = CreateTexture.getInstance();
    if (health == 2) {
      heart3.setDrawable(new TextureRegionDrawable(tx.enemySprite));
    }
    if (health == 1) {
      heart2.setDrawable(new TextureRegionDrawable(tx.enemySprite));
    }
    if (health == 0) {
      heart1.setDrawable(new TextureRegionDrawable(tx.enemySprite));
    }
  }
}
