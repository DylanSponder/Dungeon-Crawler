package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import static com.mygdx.game.DungeonCrawler.optionsMenuStage;
import static com.mygdx.game.DungeonCrawler.pauseMenuStage;
import static com.mygdx.game.DungeonCrawler.bossHealthbarStage;
import static com.mygdx.game.OptionsMenu.optionsMenuContainer;

public class PauseMenu {

    public static Table pauseMenuContainer;
    public static VerticalGroup pauseMenuGroup;


    public PauseMenu (){

        // https://libgdx.com/wiki/graphics/2d/scene2d/skin
        //uiskin.atlas, uiskin.json, uiskin.png, default.png and default.fnt all required
        pauseMenuContainer = new Table();
        pauseMenuGroup = new VerticalGroup();
        pauseMenuContainer.setFillParent(true);

        TextButton playButton = new TextButton("RESUME", DungeonCrawler.skin, "default");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                DungeonCrawler.pauseMenuClosed = true;
                DungeonCrawler.optionsMenuClosed = true;
                // Called when player clicks on Play button
            }
        });
        playButton.getLabelCell().align(Align.right);
        playButton.padLeft(6.5f);

        TextButton settingsButton = new TextButton("OPTIONS", DungeonCrawler.skin, "default");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                DungeonCrawler.pauseMenuClosed = true;
                DungeonCrawler.optionsMenuClosed = false;
                Gdx.input.setInputProcessor(optionsMenuStage);
                DungeonCrawler.optionsMenu.slider.setVisible(true);
                pauseMenuContainer.setVisible(false);
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
        pauseMenuGroup.addActor(playButton);
        pauseMenuGroup.addActor(settingsButton);
        pauseMenuGroup.addActor(exitButton);

        PauseMenu.pauseMenuContainer.add(pauseMenuGroup);
        pauseMenuStage.addActor(PauseMenu.pauseMenuContainer);

    }


    // public openOptionsMenu() {



    // }
}
