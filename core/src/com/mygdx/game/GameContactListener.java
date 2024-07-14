package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.behaviours.fsm.Enemy;
import com.mygdx.game.entity.behaviours.fsm.EnemyState;
import com.mygdx.game.entity.behaviours.fsm.Skull;
import com.mygdx.game.level.GenerateLevel;

import java.util.Iterator;

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
        if (    ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player"))
                || ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Bone")
                ||(fa.getBody().getUserData() == "Bone" && fb.getBody().getUserData() == "Player"))

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

                    hud.healthBar.LoseHealth(0.5f);

            }
        }

        if (fa.getBody().getUserData().toString().startsWith("Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))
            if (fb.getBody().getUserData() == "Player") {
                System.out.println("Player has entered/touched a room");
                String[] roomIndexAsString =  fa.getBody().getUserData().toString().split("-");
                int roomIndex = Integer.parseInt(roomIndexAsString[1]);
                player.currentRoom = roomIndex;
            }
        }

        if ((fa.getBody().getUserData().toString().startsWith("Arrow") && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData().toString().startsWith("Arrow"))
                ||
                ((fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Enemy")
                        ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Sword"))
        ) {
            if(fa.getUserData() != "Proximity" &&
                    fb.getUserData() != "Proximity"){
                for (Enemy e : enemies) {
                    if (e.enemyBody == fa.getBody()) {

                        String fbData = fb.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (fbData) {
                            case "DownSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY-120);
                                break;
                            case "UpSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY+120);
                                break;
                            case "LeftSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX-120, velY);
                                break;
                            case "RightSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX+120, velY);
                                break;
                            case "DownArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY-45);
                                break;
                            case "UpArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY+45);
                                break;
                            case "LeftArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX-45, velY);
                                break;
                            case "RightArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX+45, velY);
                                break;
                            default:
                                break;
                        }
                        //e.enemyBody.applyForceToCenter(0,0, true);
                        e.enemyBody.setLinearVelocity(0,0);
                        System.out.println(e.enemyBody.getLinearVelocity());

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(fa.getBody())) {
                                //arrowBodiesCollided.add(fa.getBody());
                                deadEnemyBodies.add(fa.getBody());
                            }
                            enemySkulls.add(new Skull(world, fa.getBody().getPosition().x, fa.getBody().getPosition().y));
                            //skullArrayMap.put();
                            e.getStateMachine().changeState(EnemyState.DIE);
                            hud.updateGold(1);
                            /*
                            GenerateLevel.init.roomList.get(player.currentRoom).enemyCounter--;
                            if (GenerateLevel.init.roomList.get(player.currentRoom).enemyCounter == 0){
                                GenerateLevel.init.roomList.get(player.currentRoom).unlockDoor(world, GenerateLevel.init.roomList.get(player.currentRoom));
                                System.out.println("All enemies in this room are dead!");
                            }
                             */
                            break;
                        }
                    } else if (e.enemyBody == fb.getBody()) {
                        String faData = fa.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (faData) {
                            case "DownSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY-120);
                                break;
                            case "UpSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY+120);
                                break;
                            case "LeftSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX-120, velY);
                                break;
                            case "RightSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX+120, velY);
                                break;
                            case "DownArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY-45);
                                break;
                            case "UpArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY+45);
                                break;
                            case "LeftArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX-45, velY);
                                break;
                            case "RightArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX+45, velY);
                                break;
                            default:
                                break;
                        }

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(fb.getBody())) {
                                //arrowBodiesCollided.add(fa.getBody());
                                deadEnemyBodies.add(fb.getBody());
                            }
                            enemySkulls.add(new Skull(world, fb.getBody().getPosition().x, fb.getBody().getPosition().y));
                            e.getStateMachine().changeState(EnemyState.DIE);
                            hud.updateGold(1);
                            GenerateLevel.init.roomList.get(player.currentRoom).enemyCounter--;
                            if (GenerateLevel.init.roomList.get(player.currentRoom).enemyCounter == 0){
                                GenerateLevel.init.roomList.get(player.currentRoom).unlockDoor(world, GenerateLevel.init.roomList.get(player.currentRoom));
                                System.out.println("All enemies in this room are dead!");
                            }

                            break;
                        }
                    }
                }
            }
        }
            if (((fa.getBody().getUserData().toString().startsWith("Arrow") && fb.getBody().getUserData() == "Skull")
                    ||(fa.getBody().getUserData() == "Skull" && fb.getBody().getUserData().toString().startsWith("Arrow")))
                    ||
                    ((fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Skull")
                            ||(fa.getBody().getUserData() == "Skull" && fb.getBody().getUserData() == "Sword"))
            ) {
            if (fa.getBody().getUserData() == "Skull") {
                Iterator<Skull> skullIt = enemySkulls.iterator();
                if (skullIt.hasNext()) {
                    Skull skull = skullIt.next();
                if (skull.skullBody == fa.getBody()){
                    skull.SKULL_HEALTH--;
                }
                if (skull.SKULL_HEALTH <= 0) {
                    if (!brokenSkullBodies.contains(fa.getBody())) {
                        brokenSkullBodies.add(fa.getBody());
                    }
                }
            }
            } else
                if (fb.getBody().getUserData() == "Skull") {
                Iterator<Skull> skullIt = enemySkulls.iterator();
                if (skullIt.hasNext()) {
                    Skull skull = skullIt.next();
                    if (skull.skullBody == fb.getBody()){
                        skull.SKULL_HEALTH--;
                    }
                    if (skull.SKULL_HEALTH <= 0) {
                        if (!brokenSkullBodies.contains(fb.getBody())) {
                            brokenSkullBodies.add(fb.getBody());
                        }
                    }
                }
            }
            //CreateBone x 3
            //TODO: Assign random orientation to each bone and random speed between two values

            //Bone b = new Bone(world, fa.getBody(), fb.getBody().getPosition().x, fb.getBody().getPosition().y);
            // s.createSkull();
            //enemySkulls.add(s);

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