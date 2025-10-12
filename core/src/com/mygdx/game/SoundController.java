package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public class SoundController {

    final CreateAssets tx = CreateAssets.getInstance();
    public float finalVolume;

    //the Sound Controller is responsible for playing Sounds at the correct pitch and volume
    //TODO integrate with a menu volume slider

    public void playSound(String sound, float pitchMax, float pitchMin, float volume) {
        finalVolume = volume * (DungeonCrawler.optionsMenu.volume / 100);

        System.out.println(finalVolume);

        switch (sound) {
            case "PotSmash":
                Sound potSmash = tx.potBreaking;
                long id = potSmash.play();
                float randall1 = Random.randomFloat(pitchMax,pitchMin);
                potSmash.setPitch(id,randall1/10);
                potSmash.setVolume(id,finalVolume);
                break;
            case "Skull":
                Sound skullSmash = tx.skullBreaking;
                long id2 = skullSmash.play();
                float randall2 = Random.randomFloat(pitchMax,pitchMin);
                skullSmash.setPitch(id2,randall2/10);
                skullSmash.setVolume(id2,finalVolume);
                break;
            case "Bone":
                Sound boneSmash = tx.boneBreaking;
                long id3 = boneSmash.play();
                float randall3 = Random.randomFloat(pitchMax,pitchMin);
                boneSmash.setPitch(id3,randall3/10);
                boneSmash.setVolume(id3,finalVolume);
                break;
            case "SpiderAttack":
                Sound spiderAttack = tx.spiderAttack;
                long id4 = spiderAttack.play();
                float randall4 = Random.randomFloat(pitchMax,pitchMin);
                spiderAttack.setPitch(id4,randall4/10);
                spiderAttack.setVolume(id4,finalVolume);
                break;
            case "SpiderDeath":
                Sound spiderDeath = tx.spiderDeath;
                long id5 = spiderDeath.play();
                float randall5 = Random.randomFloat(pitchMax,pitchMin);
                spiderDeath.setPitch(id5,randall5/10);
                spiderDeath.setVolume(id5,finalVolume);
                break;
            case "ArrowHit":
                Sound arrowHit = tx.arrowHit;
                long id6 = arrowHit.play();
                float randall6 = Random.randomFloat(pitchMax,pitchMin);
                arrowHit.setPitch(id6,randall6/10);
                arrowHit.setVolume(id6,finalVolume);
                break;
            case "SwordSwing1":
                Sound swordSwing = tx.swordSwing;
                long id7 = swordSwing.play();
                float randall7 = Random.randomFloat(pitchMax,pitchMin);
                swordSwing.setPitch(id7,randall7/10);
                swordSwing.setVolume(id7,finalVolume);
                break;
            case "SwordSwing2":
                Sound swordSwing2 = tx.swordSwing2;
                long id8 = swordSwing2.play();
                float randall8 = Random.randomFloat(pitchMax,pitchMin);
                swordSwing2.setPitch(id8,randall8/10);
                swordSwing2.setVolume(id8,finalVolume);
                break;
                //currently unused
            case "BowAttack":
                Sound bowAttack1 = tx.bowAttack;
                long id9 = bowAttack1.play();
                float randall9 = Random.randomFloat(pitchMax,pitchMin);
                bowAttack1.setPitch(id9,randall9/10);
                bowAttack1.setVolume(id9,finalVolume);
                break;
            case "Whoosh":
                Sound bowAttack2 = tx.whoosh;
                long id10 = bowAttack2.play();
                float randall10 = Random.randomFloat(pitchMax,pitchMin);
                bowAttack2.setPitch(id10,randall10/10);
                bowAttack2.setVolume(id10,finalVolume);
                break;
            case "BowAttack3":
                Sound bowAttack3 = tx.bowAttack3;
                long id11 = bowAttack3.play();
                float randall11 = Random.randomFloat(pitchMax,pitchMin);
                bowAttack3.setPitch(id11,randall11/10);
                bowAttack3.setVolume(id11,finalVolume);
                break;
            case "TrapOpens":
                Sound trapOpening = tx.trapOpening;
                long id12 = trapOpening.play();
                float randall12 = Random.randomFloat(pitchMax,pitchMin);
                trapOpening.setPitch(id12,randall12/10);
                trapOpening.setVolume(id12,finalVolume);
                break;
            case "TrapCloses":
                Sound trapClosing = tx.trapClosing;
                long id13 = trapClosing.play();
                float randall13 = Random.randomFloat(pitchMax,pitchMin);
                trapClosing.setPitch(id13,randall13/10);
                trapClosing.setVolume(id13,finalVolume);
                break;
            case "GhostDeath":
                Sound ghostDeath = tx.ghostDeath;
                long id14 = ghostDeath.play();
                float randall14 = Random.randomFloat(pitchMax,pitchMin);
                ghostDeath.setPitch(id14,randall14/10);
                ghostDeath.setVolume(id14,finalVolume);
                break;
            case "PlayerHurt":
                Sound playerHurt = tx.playerHurt;
                long id15 = playerHurt.play();
                float randall15 = Random.randomFloat(pitchMax,pitchMin);
                playerHurt.setPitch(id15, randall15/10);
                playerHurt.setVolume(id15,finalVolume);
                break;
            case "DoorOpen":
                Sound doorOpening = tx.doorOpening;
                long id16 = doorOpening.play();
                float randall16 = Random.randomFloat(pitchMax,pitchMin);
                doorOpening.setPitch(id16, randall16/10);
                doorOpening.setVolume(id16,finalVolume);
                break;
            case "DoorClose":
                Sound doorClosing = tx.doorClosing;
                long id17 = doorClosing.play();
                float randall17 = Random.randomFloat(pitchMax,pitchMin);
                doorClosing.setPitch(id17, randall17/10);
                doorClosing.setVolume(id17,finalVolume);
                break;
            case "Footstep1":
                Sound footstep1 = tx.footstep1;
                long id18 = footstep1.play();
                float randall18 = Random.randomFloat(pitchMax,pitchMin);
                footstep1.setPitch(id18, randall18/10);
                footstep1.setVolume(id18,finalVolume);
                break;
            case "Footstep2":
                Sound footstep2 = tx.footstep2;
                long id19 = footstep2.play();
                float randall19 = Random.randomFloat(pitchMax,pitchMin);
                footstep2.setPitch(id19, randall19/10);
                footstep2.setVolume(id19,finalVolume);
                break;
            case "Footstep3":
                Sound footstep3 = tx.footstep3;
                long id20 = footstep3.play();
                float randall20 = Random.randomFloat(pitchMax,pitchMin);
                footstep3.setPitch(id20, randall20/10);
                footstep3.setVolume(id20,finalVolume);
                break;
            case "Coin":
                Sound coin = tx.coin;
                long id21 = coin.play();
                float randall21 = Random.randomFloat(pitchMax,pitchMin);
                coin.setPitch(id21, randall21/10);
                coin.setVolume(id21,finalVolume);
                break;
            case "Buy":
                Sound buy = tx.buy;
                long id22 = buy.play();
                float randall22 = Random.randomFloat(pitchMax,pitchMin);
                buy.setPitch(id22, randall22/10);
                buy.setVolume(id22,finalVolume);
                break;
            case "Shop":
                Sound shop = tx.shop;
                long id23 = shop.play();
                float randall23 = Random.randomFloat(pitchMax,pitchMin);
                shop.setPitch(id23, randall23/10);
                shop.setVolume(id23,finalVolume);
                break;
            case "Chisel":
                Sound chiselUsed = tx.chiselUsed;
                long id24 = chiselUsed.play();
                float randall24 = Random.randomFloat(pitchMax,pitchMin);
                chiselUsed.setPitch(id24, randall24/10);
                chiselUsed.setVolume(id24,finalVolume);
                break;
            case "SkullDeath":
                Sound skullDeath = tx.skullDeath;
                long id25 = skullDeath.play();
                float randall25 = Random.randomFloat(pitchMax,pitchMin);
                skullDeath.setPitch(id25, randall25/10);
                skullDeath.setVolume(id25,finalVolume);
                break;
            case "SwordHit":
                Sound swordHit = tx.swordHit;
                long id26 = swordHit.play();
                float randall26 = Random.randomFloat(pitchMax,pitchMin);
                swordHit.setPitch(id26, randall26/10);
                swordHit.setVolume(id26,finalVolume);
                break;
            case "WineDrink":
                Sound potionDrink = tx.wineDrink;
                long id27 = potionDrink.play();
                float randall27 = Random.randomFloat(pitchMax,pitchMin);
                potionDrink.setPitch(id27, randall27/10);
                potionDrink.setVolume(id27,finalVolume);
                break;
            case "PickupHeart":
                Sound heartPickup = tx.heartPickup;
                long id28 = heartPickup.play();
                float randall28 = Random.randomFloat(pitchMax,pitchMin);
                heartPickup.setPitch(id28, randall28/10);
                heartPickup.setVolume(id28,finalVolume);
                break;
            case "PickupWine":
                Sound winePickup = tx.winePickup;
                long id29 = winePickup.play();
                float randall29 = Random.randomFloat(pitchMax,pitchMin);
                winePickup.setPitch(id29, randall29/10);
                winePickup.setVolume(id29,finalVolume);
                break;
            case "EyebeamAttack":
                Sound eyebeamAttack = tx.eyebeamAttack;
                long id30 = eyebeamAttack.play();
                float randall30 = Random.randomFloat(pitchMax,pitchMin);
                eyebeamAttack.setPitch(id30, randall30/10);
                eyebeamAttack.setVolume(id30,finalVolume);
                break;
            case "CyclopsDeath":
                Sound cyclopsDeath = tx.cyclopsDeath;
                long id31 = cyclopsDeath.play();
                float randall31 = Random.randomFloat(pitchMax,pitchMin);
                cyclopsDeath.setPitch(id31, randall31/10);
                cyclopsDeath.setVolume(id31,finalVolume);
                break;
            case "MinoHurt":
                Sound minoHurt = tx.minoHurt;
                long id32 = minoHurt.play();
                float randall32 = Random.randomFloat(pitchMax,pitchMin);
                minoHurt.setPitch(id32, randall32/10);
                minoHurt.setVolume(id32,finalVolume);
                break;
            case "MinoHurt2":
                Sound minoHurt2 = tx.minoHurt2;
                long id33 = minoHurt2.play();
                float randall33 = Random.randomFloat(pitchMax,pitchMin);
                minoHurt2.setPitch(id33, randall33/10);
                minoHurt2.setVolume(id33,finalVolume);
                break;
            case "MinoCharge":
                Sound minoCharge = tx.minoCharge;
                long id34 = minoCharge.play();
                float randall34 = Random.randomFloat(pitchMax,pitchMin);
                minoCharge.setPitch(id34, randall34/10);
                minoCharge.setVolume(id34,finalVolume);
                break;
            case "MinoCharge2":
                Sound minoCharge2 = tx.minoCharge2;
                long id35 = minoCharge2.play();
                float randall35 = Random.randomFloat(pitchMax,pitchMin);
                minoCharge2.setPitch(id35, randall35/10);
                minoCharge2.setVolume(id35,finalVolume);
                break;
            case "FireWhoosh":
                Sound fireWhoosh = tx.fireWhoosh;
                long id36 = fireWhoosh.play();
                float randall36 = Random.randomFloat(pitchMax,pitchMin);
                fireWhoosh.setPitch(id36, randall36/10);
                fireWhoosh.setVolume(id36,finalVolume);
                break;
        }
    }
}
