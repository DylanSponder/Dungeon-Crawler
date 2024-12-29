package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public class SoundController {

    final CreateAssets tx = CreateAssets.getInstance();

    public SoundController() {


    }

    public void playSound(String sound, float pitchMax, float pitchMin, float volume) {


        switch (sound) {
            case "PotSmash":
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

            case "Bone":
                Sound boneSmash = tx.boneBreaking;
                long id3 = boneSmash.play();
                float randall3 = Random.randomFloat(pitchMax,pitchMin);
                boneSmash.setPitch(id3,randall3/10);
                boneSmash.setVolume(id3,volume);
                break;
            case "SpiderAttack":
                Sound spiderAttack = tx.spiderAttack;
                long id4 = spiderAttack.play();
                float randall4 = Random.randomFloat(pitchMax,pitchMin);
                spiderAttack.setPitch(id4,randall4/10);
                spiderAttack.setVolume(id4,volume);
                break;
            case "SpiderDeath":
                Sound spiderDeath = tx.spiderDeath;
                long id5 = spiderDeath.play();
                float randall5 = Random.randomFloat(pitchMax,pitchMin);
                spiderDeath.setPitch(id5,randall5/10);
                spiderDeath.setVolume(id5,volume);
                break;
            case "ArrowHit":
                Sound arrowHit = tx.arrowHit;
                long id6 = arrowHit.play();
                float randall6 = Random.randomFloat(pitchMax,pitchMin);
                arrowHit.setPitch(id6,randall6/10);
                arrowHit.setVolume(id6,volume);
                break;
            case "SwordSwing1":
                Sound swordSwing = tx.swordSwing;
                long id7 = swordSwing.play();
                float randall7 = Random.randomFloat(pitchMax,pitchMin);
                swordSwing.setPitch(id7,randall7/10);
                swordSwing.setVolume(id7,volume);
                break;
            case "SwordSwing2":
                Sound swordSwing2 = tx.swordSwing2;
                long id8 = swordSwing2.play();
                float randall8 = Random.randomFloat(pitchMax,pitchMin);
                swordSwing2.setPitch(id8,randall8/10);
                swordSwing2.setVolume(id8,volume);
                break;
            case "BowAttack1":
                Sound bowAttack1 = tx.bowAttack;
                long id9 = bowAttack1.play();
                float randall9 = Random.randomFloat(pitchMax,pitchMin);
                bowAttack1.setPitch(id9,randall9/10);
                bowAttack1.setVolume(id9,volume);
                break;
            case "BowAttack2":
                Sound bowAttack2 = tx.bowAttack2;
                long id10 = bowAttack2.play();
                float randall10 = Random.randomFloat(pitchMax,pitchMin);
                bowAttack2.setPitch(id10,randall10/10);
                bowAttack2.setVolume(id10,volume);
                break;
            case "BowAttack3":
                Sound bowAttack3 = tx.bowAttack3;
                long id11 = bowAttack3.play();
                float randall11 = Random.randomFloat(pitchMax,pitchMin);
                bowAttack3.setPitch(id11,randall11/10);
                bowAttack3.setVolume(id11,volume);
                break;
            case "TrapOpens":
                Sound trapOpening = tx.trapOpening;
                long id12 = trapOpening.play();
                float randall12 = Random.randomFloat(pitchMax,pitchMin);
                trapOpening.setPitch(id12,randall12/10);
                trapOpening.setVolume(id12,volume);
                break;
            case "TrapCloses":
                Sound trapClosing = tx.trapClosing;
                long id13 = trapClosing.play();
                float randall13 = Random.randomFloat(pitchMax,pitchMin);
                trapClosing.setPitch(id13,randall13/10);
                trapClosing.setVolume(id13,volume);
                break;
            case "GhostDeath":
                Sound ghostDeath = tx.ghostDeath;
                long id14 = ghostDeath.play();
                float randall14 = Random.randomFloat(pitchMax,pitchMin);
                ghostDeath.setPitch(id14,randall14/10);
                ghostDeath.setVolume(id14,volume);
                break;
            case "PlayerHurt":
                Sound playerHurt = tx.playerHurt;
                long id15 = playerHurt.play();
                float randall15 = Random.randomFloat(pitchMax,pitchMin);
                playerHurt.setPitch(id15, randall15/10);
                playerHurt.setVolume(id15,volume);
                break;


        }
    }
}
