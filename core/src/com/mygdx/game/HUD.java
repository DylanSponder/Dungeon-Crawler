package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
  public Stage stage;

  Label healthLabel;

  public HUD(Viewport vp, SpriteBatch sb) {
    stage = new Stage(vp, sb);
    Table table = new Table();
    table.top();
    table.setFillParent(true);

    healthLabel = new Label("Hi", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    table.add(healthLabel).expandX().padTop(10);

    stage.addActor(table);
  }
}
