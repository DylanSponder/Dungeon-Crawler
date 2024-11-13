package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public class SoundController {

    final CreateAssets tx = CreateAssets.getInstance();

    public SoundController() {


    }

    public void playSound(String sound, float pitchMax, float pitchMin, float volume) {


        switch (sound) {
            case "Pot":
                Sound potSmash = tx.potBreaking;
                long id = potSmash.play();
                float randall1 = Random.randomFloat(pitchMax,pitchMin);
                potSmash.setPitch(id,randall1/10);
                potSmash.setVolume(id,volume);

                break;

            case "Skull":
                Sound skullSmash = tx.skullBreaking;
                long id2 = skullSmash.play();
                float randall2 = Random.randomFloat(pitchMax,pitchMin);
                skullSmash.setPitch(id2,randall2/10);
                skullSmash.setVolume(id2,volume);
                break;

        }







    }
}
