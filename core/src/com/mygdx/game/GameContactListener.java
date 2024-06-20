package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.entity.Arrow;
import com.mygdx.game.entity.behaviours.fsm.Enemy;
import com.mygdx.game.entity.behaviours.fsm.EnemyState;

import static com.mygdx.game.DungeonCrawler.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //System.out.println(fa.getBody().getUserData()+" was hit with "+fb.getBody().getUserData());

        if (    (fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Enemy")
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
        if (    (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
        ){
            if( fa.getUserData() == "Proximity"||
                    fb.getUserData() == "Proximity"){
                for (Enemy e : enemies){
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()){
                        e.getStateMachine().changeState(EnemyState.ATTACK);
                    }
                }
            }
            else {
                if (fa.getBody().getUserData() == "Player"){
                    hud.healthBar.LoseHealth(0.5f);
                }
            }
        }

        //if two rooms contact during room generation, abort and choose a new direction
        if (    (fa.getUserData() == "Room") || (fa.getUserData() == "Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))
            //System.out.println("Entity has left or entered a room");
        }

        if ((fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData().toString().startsWith("Arrow"))
                ||
                ((fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Enemy")
                        ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Sword"))
        ) {
            if(fa.getUserData() != "Proximity" &&
                    fb.getUserData() != "Proximity"){
                for (Enemy e : enemies) {
                    if (e.enemyBody == fa.getBody()) {
                        e.ENEMY_HEALTH--;
                        String fbData = fb.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (fbData) {
                            case "Down":
                                e.enemyBody.setLinearVelocity(velX, velY-40);
                                break;
                            case "Up":
                                e.enemyBody.setLinearVelocity(velX, velY+40);
                                break;
                            case "Left":
                                e.enemyBody.setLinearVelocity(velX-40, velY);
                                break;
                            case "Right":
                                e.enemyBody.setLinearVelocity(velX+40, velY);
                                break;
                            default:
                                break;
                        }
                        //e.enemyBody.applyForceToCenter(0,0, true);
                        e.enemyBody.setLinearVelocity(0,0);
                       System.out.println(e.enemyBody.getLinearVelocity());

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemies.contains(fa.getBody())) {
                                //arrowBodiesCollided.add(fa.getBody());
                                deadEnemies.add(fa.getBody());

                            }
                            e.getStateMachine().changeState(EnemyState.DIE);
                            hud.updateGold(1);

                            break;
                        }
                    } else if (e.enemyBody == fb.getBody()) {
                        e.ENEMY_HEALTH--;
                        String faData = fa.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (faData) {
                            case "Down":
                                e.enemyBody.setLinearVelocity(velX, velY-40);
                                break;
                            case "Up":
                                e.enemyBody.setLinearVelocity(velX, velY+40);
                                break;
                            case "Left":
                                e.enemyBody.setLinearVelocity(velX-40, velY);
                                break;
                            case "Right":
                                e.enemyBody.setLinearVelocity(velX+40, velY);
                                break;
                            default:
                                break;
                        }

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemies.contains(fb.getBody())) {
                                //arrowBodiesCollided.add(fa.getBody());
                                deadEnemies.add(fb.getBody());
                            }
                            e.getStateMachine().changeState(EnemyState.DIE);
                            hud.updateGold(1);
                            break;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (    (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
        ){
            if  (fa.getUserData() == "Proximity"||
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
