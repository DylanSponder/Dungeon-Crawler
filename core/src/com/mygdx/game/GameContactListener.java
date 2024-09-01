package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.behaviours.fsm.Enemy;
import com.mygdx.game.entity.behaviours.fsm.EnemyState;
import com.mygdx.game.entity.Skull;
import com.mygdx.game.level.objects.Door;
import com.mygdx.game.level.GenerateLevel;
import com.mygdx.game.level.objects.Pot;
import com.mygdx.game.level.objects.Room;
import com.mygdx.game.CreateSound;

import static com.mygdx.game.DungeonCrawler.*;

public class GameContactListener implements ContactListener {
    //there is a lot of lazy branches here - most collisions only need to be handled once
    // (static bodies like walls will never be the object that is colliding with a dynamic body for instance)
    // many redundancies should be re-written and if-statements made into switches

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if ((fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Arrow")
                || (fa.getBody().getUserData() == "Wall" && fb.getBody().getUserData() == "Arrow")
                || (fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Wall")
                || (fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Door")
                || (fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Arrow")
                || (fa.getBody().getUserData() == "Pot" && fb.getBody().getUserData() == "Arrow")
                || (fa.getBody().getUserData() == "Arrow" && fb.getBody().getUserData() == "Pot")
        ) {
            if (fa.getBody().getUserData() == "Enemy" && fa.getUserData() != "Proximity"
                    || fa.getBody().getUserData() == "Wall"
                    || fa.getBody().getUserData() == "Pot") {
                if (!arrowBodiesCollided.contains(fb.getBody())) {
                    arrowBodiesCollided.add(fb.getBody());
                }
            } else if (fb.getBody().getUserData() == "Enemy" && fb.getUserData() != "Proximity"
                    || fb.getBody().getUserData() == "Wall"
                    || fb.getBody().getUserData() == "Pot") {
                if (!arrowBodiesCollided.contains(fa.getBody())) {
                    arrowBodiesCollided.add(fa.getBody());
                }
            } else if (fa.getBody().getUserData() == "Door") {
                for (Room r : GenerateLevel.init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fa.getBody()) {
                            if (!d.open) {
                                if (!arrowBodiesCollided.contains(fb.getBody())) {
                                    arrowBodiesCollided.add(fb.getBody());
                                }
                            }
                        }
                    }
                }
            } else if (fb.getBody().getUserData() == "Door") {
                for (Room r : GenerateLevel.init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fb.getBody()) {
                            if (!d.open) {
                                if (!arrowBodiesCollided.contains(fa.getBody())) {
                                    arrowBodiesCollided.add(fa.getBody());
                                }
                            }
                        }
                    }
                }
            }

            if (fa.getBody().getUserData() == "Arrow" && fb.getUserData() == "EnemyHitbox"
                    || fb.getBody().getUserData() == "Arrow" && fa.getUserData() == "EnemyHitbox") {
                for (Enemy e : enemies) {
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()) {
                        e.getStateMachine().changeState(EnemyState.ATTACK);
                    }
                }
            }

        }

        if ((fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Player")
                || (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Door")
                || ((fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Door"))
        ) {
            if (fa.getBody().getUserData() == "Door"
                    && (fb.getUserData() != "Proximity")) {
                for (Room r : GenerateLevel.init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fa.getBody()) {
                            if (!d.locked) {
                                d.open = true;
                            }
                        }
                    }
                }
            }
            if (fb.getBody().getUserData() == "Door"
                    && (fa.getUserData() != "Proximity")) {
                for (Room r : GenerateLevel.init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fa.getBody()) {
                            if (!d.locked) {
                                d.open = true;
                            }
                        }
                    }
                }
            }
        }

        //bone branch needs to be revisited - faulty logic is causing bones not to get destroyed somewhere here
        if (((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player"))
                || ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Bone")
                || (fa.getBody().getUserData() == "Bone" && fb.getBody().getUserData() == "Player"))
        ) {
            //if player enters enemy detection range, attack player
            if (fa.getUserData() == "Proximity" ||
                    fb.getUserData() == "Proximity") {
                for (Enemy e : enemies) {
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()) {
                        e.getStateMachine().changeState(EnemyState.ATTACK);
                    }
                }
            } else {
                hud.healthBar.LoseHealth(0.5f);
                if (fa.getBody().getUserData() == "Bone") {
                    player.playerBody.applyLinearImpulse(fa.getBody().getLinearVelocity().x * 100, fa.getBody().getLinearVelocity().y * 100, 0, 0, true);
                    if (!boneBodiesCollided.contains(fa.getBody())) {
                        boneBodiesCollided.add(fa.getBody());
                    }
                } else if (fb.getBody().getUserData() == "Bone") {
                    player.playerBody.applyLinearImpulse(fb.getBody().getLinearVelocity().x * 100, fb.getBody().getLinearVelocity().y * 100, 0, 0, true);
                    if (!boneBodiesCollided.contains(fb.getBody())) {
                        boneBodiesCollided.add(fb.getBody());
                    }
                    //TODO: fix
                } else if (fa.getBody().getUserData() == "Enemy") {
                    player.playerBody.applyLinearImpulse(fa.getBody().getLinearVelocity().x * 50, fa.getBody().getLinearVelocity().y * 50, 0, 0, true);
                    fa.getBody().applyLinearImpulse(-fb.getBody().getLinearVelocity().x * 2, -fb.getBody().getLinearVelocity().y * 2, 0, 0, true);

                } else if (fb.getBody().getUserData() == "Enemy") {
                    player.playerBody.applyLinearImpulse(fb.getBody().getLinearVelocity().x * 50, fb.getBody().getLinearVelocity().y * 50, 0, 0, true);

                    if (fa.getBody().getLinearVelocity().x < 10 && fa.getBody().getLinearVelocity().y < 10) {
                        fa.getBody().applyLinearImpulse(-fb.getBody().getLinearVelocity().x, -fb.getBody().getLinearVelocity().y + 150, 0, 0, true);
                    } else {
                        fa.getBody().applyLinearImpulse(-fb.getBody().getLinearVelocity().x + 150, -fb.getBody().getLinearVelocity().y + 150, 0, 0, true);
                    }
                }
            }
        }

        if ((fa.getBody().getUserData() == "Bone" && fb.getUserData() != "Proximity" && fb.getBody().getUserData() != "Bone" && fb.getBody().getUserData() != "Sword" && !fb.getBody().getUserData().toString().startsWith("Arrow"))
                || (fb.getBody().getUserData() == "Bone" && fa.getUserData() != "Proximity" && fa.getBody().getUserData() != "Bone" && fa.getBody().getUserData() != "Sword" && !fa.getBody().getUserData().toString().startsWith("Arrow"))
        ) {
            if ((((fa.getBody().getUserData() == "Enemy" && fa.getUserData() != "Proximity")
                    || fa.getBody().getUserData() == "Wall")
                    || fa.getBody().getUserData() == "Door")
                    && fb.getBody().getUserData() == "Bone") {

                if (fa.getBody().getUserData() == "Door"){
                    for (Room r : GenerateLevel.init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == fa.getBody()) {
                                if (!d.open) {
                                    if (!boneBodiesCollided.contains(fb.getBody())) {
                                        boneBodiesCollided.add(fb.getBody());
                                    }
                                }
                            }
                        }
                    }
                }

            } else if ((((fb.getBody().getUserData() == "Enemy" && fb.getUserData() != "Proximity")
                        || fb.getBody().getUserData() == "Wall")
                        || fb.getBody().getUserData() == "Door")
                        && fa.getBody().getUserData() == "Bone") {

                if (fb.getBody().getUserData() == "Door"){
                    for (Room r : GenerateLevel.init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == fb.getBody()) {
                                if (!d.open) {
                                    if (!boneBodiesCollided.contains(fa.getBody())) {
                                        boneBodiesCollided.add(fa.getBody());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (fa.getBody().getUserData().toString().startsWith("Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))
            String[] roomIndexAsString = fa.getBody().getUserData().toString().split("-");
            player.currentRoom = Integer.parseInt(roomIndexAsString[1]);
            if (fb.getBody().getUserData() == "Player") {
                player.touchingRoom = true;
            }
        }

        if(((fa.getBody().getUserData() == "Pot" && fb.getBody().getUserData() == "Sword")
                || (fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Pot"))
                || ((fa.getBody().getUserData() == "Pot" && fb.getBody().getUserData().toString().startsWith("Arrow"))
                || (fa.getBody().getUserData().toString().startsWith("Arrow") && fb.getBody().getUserData() == "Pot"))){
            if (fb.getBody().getUserData() == "Pot") {
                for (Pot p : pots) {
                    if (fb.getBody() == p.potBody) {
                        if (p.POT_HEALTH >= 1) {
                            p.POT_HEALTH--;
                            if (p.POT_HEALTH <= 0) {
                                brokenPots.add(p);
                            }
                        }
                    }
                }
            }
        }

        if ((fa.getBody().getUserData().toString().startsWith("Arrow") && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData().toString().startsWith("Arrow"))
                ||
                ((fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Sword"))
        ) {
            if (fa.getUserData() != "Proximity" &&
                    fb.getUserData() != "Proximity") {
                for (Enemy e : enemies) {
                    if (e.enemyBody == fa.getBody()) {

                        String fbData = fb.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (fbData) {
                            case "DownSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY - 120);
                                break;
                            case "UpSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY + 120);
                                break;
                            case "LeftSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX - 120, velY);
                                break;
                            case "RightSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX + 120, velY);
                                break;
                            case "DownArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY - 70);
                                break;
                            case "UpArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY + 70);
                                break;
                            case "LeftArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX - 70, velY);
                                break;
                            case "RightArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX + 70, velY);
                                break;
                            default:
                                break;
                        }
                        //e.enemyBody.applyForceToCenter(0,0, true);
                        e.enemyBody.setLinearVelocity(0, 0);
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

                            GenerateLevel.init.roomList.get(e.room).enemyCounter--;
                            if (GenerateLevel.init.roomList.get(e.room).enemyCounter < 1) {
                                GenerateLevel.init.roomList.get(player.currentRoom).unlockAllDoors(world, GenerateLevel.init.roomList.get(player.currentRoom), false);
                                DungeonCrawler.roomClear.play();
                                DungeonCrawler.roomClear.dispose();
                            }

                            break;
                        }
                    } else if (e.enemyBody == fb.getBody()) {
                        String faData = fa.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (faData) {
                            case "DownSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY - 120);
                                break;
                            case "UpSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY + 120);
                                break;
                            case "LeftSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX - 120, velY);
                                break;
                            case "RightSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX + 120, velY);
                                break;
                            case "DownArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY - 70);
                                break;
                            case "UpArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY + 70);
                                break;
                            case "LeftArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX - 70, velY);
                                break;
                            case "RightArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX + 70, velY);
                                break;
                            default:
                                break;
                        }

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(fb.getBody())) {
                                deadEnemyBodies.add(fb.getBody());
                            }
                            enemySkulls.add(new Skull(world, fb.getBody().getPosition().x, fb.getBody().getPosition().y));
                            e.getStateMachine().changeState(EnemyState.DIE);
                            hud.updateGold(1);
                            GenerateLevel.init.roomList.get(e.room).enemyCounter--;
                            if (GenerateLevel.init.roomList.get(e.room).enemyCounter < 1) {
                                GenerateLevel.init.roomList.get(player.currentRoom).unlockAllDoors(world, GenerateLevel.init.roomList.get(player.currentRoom), false);
                                DungeonCrawler.roomClear.play();
                                DungeonCrawler.roomClear.dispose();
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (((fa.getBody().getUserData().toString().startsWith("Arrow") && fb.getBody().getUserData() == "Skull")
            || (fa.getBody().getUserData() == "Skull" && fb.getBody().getUserData().toString().startsWith("Arrow")))
            || ((fa.getBody().getUserData() == "Sword" && fb.getBody().getUserData() == "Skull")
            || (fa.getBody().getUserData() == "Skull" && fb.getBody().getUserData() == "Sword"))
        ) {
            if (fb.getBody().getUserData() == "Skull") {
                for (Skull s : enemySkulls) {
                    if (fb.getBody() == s.skullBody && !s.skullIFrame) {
                        if (s.SKULL_HEALTH > 0) {
                            s.SKULL_HEALTH--;
                            if (s.SKULL_HEALTH <= 0) {
                                brokenSkulls.add(s);
                            }
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

        if (fa.getBody().getUserData().toString().startsWith("Room")) {
            if (fb.getBody().getUserData() == "Player") {
                player.touchingRoom = false;
                if (player.currentRoom <= 9){
                        GenerateLevel.init.roomList.get(player.currentRoom).unlockDoor(world, GenerateLevel.init.roomList.get(player.currentRoom+1),false);
                }
            }
        }



        if ((fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Player")
            ||(fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Door")
                || ((fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Door"))
        )
        {
            if (fa.getBody().getUserData() == "Door"){
                for (Room r : GenerateLevel.init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fa.getBody()) {
                            d.open = false;
                        }
                    }
                }

            }
            if (fb.getBody().getUserData() == "Door"){
                for (Room r : GenerateLevel.init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fa.getBody()) {
                            d.open = false;
                        }
                    }
                }
            }
        }

        if (fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Player"
                || (fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Door")) {
            //player must be touching a room but not a door
            if (fb.getBody().getUserData() == "Player" && player.touchingRoom) {
                if (player.currentRoom != 0) {
                    if (GenerateLevel.init.roomList.get(player.currentRoom).enemyCounter != 0) {
                        GenerateLevel.init.roomList.get(player.currentRoom).lockAllDoors(world, GenerateLevel.init.roomList.get(player.currentRoom), true);
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