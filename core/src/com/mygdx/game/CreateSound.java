package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class CreateSound {

    public static Music roomClear, slash;
    //Sound swordSlash = Gdx.audio.newSound(Gdx.files.internal("NinjaAdventure/Sounds/Game/Sword.wav"));

    public void createSound() {
        roomClear = Gdx.audio.newMusic(Gdx.files.internal("NinjaAdventure/Sounds/Menu/Accept.wav"));
        slash = Gdx.audio.newMusic(Gdx.files.internal("Sounds/slash.mp3"));
    }

}
