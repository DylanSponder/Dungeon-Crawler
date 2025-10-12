package com.mygdx.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.InputListener;
        import com.badlogic.gdx.scenes.scene2d.ui.*;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

        import static com.mygdx.game.DungeonCrawler.*;

public class OptionsMenu {

    public static Table optionsMenuContainer;
    public static VerticalGroup optionsMenuGroup;
    public TextButton fullscreenOffButton, fullscreenOnButton, backButton;
    public boolean fullscreen, volumeSliding;
    public float volume;
    public Slider slider;
    public Label fullscreenLabel, difficultyLabel, volumeLabel;

    public OptionsMenu (){

        volume = 100;



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


        slider = new Slider(0, 100, 2, false, skin);
        slider.setValue(100);
        slider.setVisible(true);

        Label volume = new Label("VOLUME:", skin);
        TextButton volumeButton = new TextButton("", skin, "default");
        volumeButton.setLabel(volume);
        slider.moveBy(90,9);
        //slider.setFillParent(true);
        slider.setSize(100,10);

        volumeButton.addActor(slider);

        slider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // This method is called when a touch/mouse button is pressed down on the slider.
                // Return true to indicate that the event was handled and subsequent touchDragged/touchUp events should be received.
                System.out.println("Slider touchDown!");
                volumeSliding = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // This method is called when a touch/mouse button is released on the slider.
                System.out.println("Slider touchUp!");
                volumeSliding = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                // This method is called when a touch/mouse is dragged over the slider.
                System.out.println("Slider touchDragged!");
            }
        });

       //

        volumeButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println(event.getButton() + "CCCCCCCCCCCCCCCCCCCCCCCCCC");
                /*
                bad and cursed don't use
                if (slider.getValue() != 100 && !volumeSliding) {
                    slider.setValue(100);
                }  else if (slider.getValue() == 100) {
                    slider.setValue(0);
                }

                 */
            }
        });
        volumeButton.padRight(112f);
        volumeButton.padLeft(6.5f);
        optionsMenuGroup.addActor(volumeButton);

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