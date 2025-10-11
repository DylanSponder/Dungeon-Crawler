package com.mygdx.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.ui.*;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

        import static com.mygdx.game.DungeonCrawler.*;

public class OptionsMenu {

    public static Table optionsMenuContainer;
    public static VerticalGroup optionsMenuGroup;
    public TextButton fullscreenOffButton, fullscreenOnButton, backButton;
    public boolean fullscreen;
    public float volume;
    public Slider slider;

    public OptionsMenu (){

        volume = 100;

        slider = new Slider(0, 100, 1, false, skin);

        // https://libgdx.com/wiki/graphics/2d/scene2d/skin
        //uiskin.atlas, uiskin.json, uiskin.png, default.png and default.fnt all required

        optionsMenuContainer = new Table();
        optionsMenuGroup = new VerticalGroup();
        optionsMenuContainer.setFillParent(true);
/*
        TextButton playButton = new TextButton("RESUME", DungeonCrawler.skin, "default");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                DungeonCrawler.pauseMenuClosed = true;
                // Called when player clicks on Play button
            }
        });
        playButton.getLabelCell().align(Align.right);
        playButton.padLeft(6.5f);
 */
            slider.setValue(100);
            slider.setVisible(true);


            //optionsMenuClosed = true;
            Label off = new Label("FULLSCREEN: OFF", skin);
            Label on = new Label("FULLSCREEN: ON", skin);
            TextButton fullscreenOffButton = new TextButton("", skin, "default");
            fullscreenOffButton.setLabel(off);

            fullscreenOffButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    if (fullscreen) {
                        fullscreen = false;
                        fullscreenOffButton.setLabel(off);
                        Gdx.graphics.setWindowedMode(600,900);
                    } else {
                        fullscreen = true;
                        fullscreenOffButton.setLabel(on);
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    }

                    //Difficulty
                    //Fullscreen
                    //Shaders
                    //Sound
                    // Called when player clicks on Options button
                }
            });
            fullscreenOffButton.padLeft(6.5f);
            optionsMenuGroup.addActor(fullscreenOffButton);

        optionsMenuStage.addActor(slider);
        slider.moveBy(40,50);


        Label easy = new Label("DIFFICULTY: EASY", skin);
        Label normal = new Label("DIFFICULTY: NORMAL", skin);
        Label hard = new Label("DIFFICULTY: HARD", skin);
        TextButton difficultyButton = new TextButton("", skin, "default");
        difficultyButton.setLabel(normal);

        difficultyButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                if (difficultyButton.getLabel().getText().toString().equals("DIFFICULTY: EASY")) {
                    difficultyButton.setLabel(normal);
                } else if ((difficultyButton.getLabel().getText().toString().equals("DIFFICULTY: NORMAL"))) {
                    difficultyButton.setLabel(hard);
                } else if ((difficultyButton.getLabel().getText().toString().equals("DIFFICULTY: HARD"))) {
                    difficultyButton.setLabel(easy);
                }

                //Shaders
                //Sound
                // Called when player clicks on Options button
            }
        });
        difficultyButton.padLeft(6.5f);
        optionsMenuGroup.addActor(difficultyButton);

            /*
            fullscreenOnButton = new TextButton("FULLSCREEN: ON", DungeonCrawler.skin, "default");
            fullscreenOnButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    DungeonCrawler.fullscreen = false;
                    //Difficulty
                    //Fullscreen
                    //Shaders
                    //Sound
                    // Called when player clicks on Options button
                }
            });
            fullscreenOnButton.padLeft(6.5f);
            optionsMenuGroup.addActor(fullscreenOnButton);

             */


        TextButton backButton = new TextButton("BACK", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                DungeonCrawler.optionsMenuClosed = true;
                DungeonCrawler.pauseMenuClosed = false;
                optionsMenuContainer.setVisible(false);
                DungeonCrawler.optionsMenu.slider.setVisible(false);
                // Called when player clicks on Exit button
            }
        });
        backButton.padLeft(6.5f);

        //add all menu buttons to the menu group
        // pauseMenuGroup.addActor(playButton);
        //  pauseMenuGroup.addActor(settingsButton);



        optionsMenuGroup.addActor(backButton);

        OptionsMenu.optionsMenuContainer.add(optionsMenuGroup);

        optionsMenuStage.addActor(OptionsMenu.optionsMenuContainer);
    }


    // public openOptionsMenu() {



    // }
}