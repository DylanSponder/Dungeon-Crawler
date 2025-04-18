package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import static com.mygdx.game.DungeonCrawler.menuStage;

public class PauseMenu {

    public static Table menuContainer;
    public static VerticalGroup menuGroup;


    public PauseMenu (){

        // https://libgdx.com/wiki/graphics/2d/scene2d/skin
        //uiskin.atlas, uiskin.json, uiskin.png, default.png and default.fnt all required

        menuContainer = new Table();
        menuGroup = new VerticalGroup();
        menuContainer.setFillParent(true);

        TextButton playButton = new TextButton("RESUME", DungeonCrawler.skin, "default");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                DungeonCrawler.menuClosed = true;
                // Called when player clicks on Play button
            }
        });
        playButton.getLabelCell().align(Align.right);
        playButton.padLeft(6.5f);

        TextButton settingsButton = new TextButton("OPTIONS", DungeonCrawler.skin, "default");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                DungeonCrawler.menuClosed = true;
                // Called when player clicks on Options button
            }
        });
        settingsButton.padLeft(6.5f);

        TextButton exitButton = new TextButton("QUIT", DungeonCrawler.skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
                // Called when player clicks on Exit button
            }
        });
        exitButton.padLeft(6.5f);

        //add all menu buttons to the menu group
        menuGroup.addActor(playButton);
        menuGroup.addActor(settingsButton);
        menuGroup.addActor(exitButton);

        menuContainer.add(menuGroup);
        menuStage.addActor(menuContainer);

    }


   // public openOptionsMenu() {



   // }
}
