package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.behaviours.fsm.Enemy;
import com.mygdx.game.entity.behaviours.fsm.EnemyState;

import static com.mygdx.game.DungeonCrawler.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //System.out.println(fa.getBody().getUserData()+" was hit with "+fb.getBody().getUserData());

        if ((fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Arrow")
                ||(fa.getBody().getUserData() == "Wall" && fb.getBody().getUserData() == "Arrow")
                ||(fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Wall")
        ){
            if (fa.getBody().getUserData() == "Enemy" && fa.getUserData() != "Proximity"
                    || fa.getBody().getUserData() == "Wall"){
                if (!arrowBodiesCollided.contains(fb.getBody())) {
                    arrowBodiesCollided.add(fb.getBody());
                }
            }
            else if (fb.getBody().getUserData() == "Enemy" && fb.getUserData() != "Proximity"
                    || fb.getBody().getUserData() == "Wall") {
                if (!arrowBodiesCollided.contains(fa.getBody())) {
                    arrowBodiesCollided.add(fa.getBody());
                }
            }

            if (fa.getBody().getUserData() == "Arrow" && fb.getUserData() == "EnemyHitbox"
                    || fb.getBody().getUserData() == "Arrow" && fa.getUserData() == "EnemyHitbox")
            {
                for (Enemy e : enemies){
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()){
                        e.getStateMachine().changeState(EnemyState.ATTACK);
                    }
                }
            }

        }
        if ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
        ){
            if(fa.getUserData() == "Proximity"||
                    fb.getUserData() == "Proximity"){
                for (Enemy e : enemies){
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()){
                        e.getStateMachine().changeState(EnemyState.ATTACK);
                    }
                }
            }
            else {
                if (fa.getBody().getUserData() == "Player"){
                    if (hud.healthBar.currentHealth < 2) {
                        hud.healthBar.GainHealth(1.0f);
                    } else {
                        hud.healthBar.LoseHealth(0.5f);
                    }
                }
            }
        }

        //if two rooms contact during room generation, abort and choose a new direction
        if ((fa.getUserData() == "Room") || (fa.getUserData() == "Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))
            System.out.println("Player has left or entered a room");
        }


    }
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
        ){
            if(fa.getUserData() == "Proximity"||
                    fb.getUserData() == "Proximity"){
                for (Enemy e : enemies){
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()){
                        e.getStateMachine().changeState(EnemyState.WANDER);
                    }
                }
            }
        }
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}