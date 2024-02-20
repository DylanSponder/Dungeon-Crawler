package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.Player;

public class ListenerClass implements ContactListener {

    Player player;
    DungeonCrawler game = new DungeonCrawler();
  //  int PLAYER_HEALTH = player.PLAYER_HEALTH;
   // int ENEMY_HEALTH = game.ENEMY_HEALTH;

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void beginContact(Contact contact) {

    }
}