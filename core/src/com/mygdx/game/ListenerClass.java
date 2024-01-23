package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class ListenerClass implements ContactListener {
    DungeonCrawler game = new DungeonCrawler();
    int PLAYER_HEALTH = game.PLAYER_HEALTH;
    int ENEMY_HEALTH = game.ENEMY_HEALTH;
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
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        System.out.println(fa.getBody().getUserData() + " has collided with " + fb.getBody().getUserData());

        //check if player and enemy collide
        if ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")) {
            System.out.println("Ouch!");
            //subtract 1 or more health depending on the attack/enemy
            PLAYER_HEALTH -= 1;
            System.out.println(PLAYER_HEALTH);
            if (PLAYER_HEALTH == 0) {
                System.out.println("You died!");
            }

            //generate blood particle
        }

        //check if sword and enemy collide
        if ((fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Enemy")
        ||(fb.getBody().getUserData() == "Sword" && fa.getBody().getUserData() == "Enemy")) {
            ENEMY_HEALTH -= 1;
            System.out.println(fa.getBody().getUserData()+" was hit with "+fb.getBody().getUserData());
            System.out.println(ENEMY_HEALTH);
            if (ENEMY_HEALTH == 0) {
                System.out.println("Enemy killed!");
                game.ENEMY_HEALTH = 0;
            }
        }
    }
}