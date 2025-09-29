package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Arrow;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.entity.behaviours.fsm.drops.Skull;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Eyebeam;
import com.mygdx.game.level.GenerateLevel;
import com.mygdx.game.level.objects.*;

import java.util.Objects;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.mygdx.game.DungeonCrawler.*;
import static com.mygdx.game.level.GenerateLevel.init;

public class GameContactListener implements ContactListener {
    //there is a lot of lazy branches here - most collisions only need to be handled once
    // (static bodies like walls will almost never be the object that is colliding with a dynamic body for instance)
    // many redundancies should be re-written and if-statements made into switches



    @Override
    public void beginContact(Contact contact) {
        Fixture collider = contact.getFixtureA();
        Fixture collidee = contact.getFixtureB();

        String colliderStr = collider.getBody().getUserData().toString();
        String collideeStr = collidee.getBody().getUserData().toString();

        if ((colliderStr == "Sword" && collideeStr == "Fire" && collidee.getUserData() != "Spawner")
        || (collideeStr == "Sword" && colliderStr == "Fire" && collider.getUserData() != "Spawner"))
        {


        }

        //System.out.println(colliderStr + " " + collideeStr);
        if (colliderStr == "Water" && collidee.getUserData() == "PlayerBound") {
            player.swimming = true;
            DungeonCrawler.PLAYER_SPEED_MULTI = 35f;
        }

        if(((colliderStr == "Pot" && collideeStr == "Sword")
                || (colliderStr == "Sword" && collideeStr == "Pot"))
                || ((colliderStr == "Pot" && collideeStr.startsWith("Arrow"))
                || (colliderStr.startsWith("Arrow") && collideeStr == "Pot"))){
            if (collidee.getBody().getUserData() == "Pot") {
                for (Pot p : pots) {
                    if (collidee.getBody() == p.potBody) {
                        int rand = Random.randomInt(3,1);

                            if (p.POT_HEALTH >= 1) {
                                if (rand == 1) {
                                    p.POT_HEALTH--;
                                    p.POT_HEALTH--;
                                if (p.POT_HEALTH <= 0) {

                                    soundController.playSound("PotSmash",5f,4f,0.05f);
                                    brokenPots.add(p);
                                }
                            } else {

                                    soundController.playSound("PotSmash",5f,4f,0.05f);
                                    p.POT_HEALTH--;
                                    p.POT_HEALTH--;
                                    brokenPots.add(p);
                            }
                        }
                    }
                }
            }
        }

        if (((colliderStr.startsWith("Arrow") && (collideeStr == "Enemy" && collidee.getUserData()!="Eyebeam"))
                || ((colliderStr == "Enemy"&& collidee.getUserData()!="Eyebeam") && collideeStr.startsWith("Arrow")))
                ||
                ((colliderStr == "Sword" && (collideeStr == "Enemy" && collidee.getUserData()!="Eyebeam")
                        || ((colliderStr == "Enemy"&& collider.getUserData()!="Eyebeam") && collideeStr == "Sword"))
        )) {
            if (collider.getUserData() != "Proximity" &&
                    collidee.getUserData() != "Proximity") {
                for (Enemy e : enemies) {
                    if (e.enemyBody == collider.getBody()) {

                        String fbData = collidee.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        if (e.vulnerable) {
                            switch (fbData) {
                                case "DownSword":
                                   // e.loseHealth(2);
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY - 50);
                                    }
                                    break;
                                case "UpSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY + 50);
                                    }
                                    break;
                                case "LeftSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX - 50, velY);
                                    }
                                    break;
                                case "RightSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX + 50, velY);
                                    }
                                    break;
                                case "DownArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY - 50);
                                    }
                                    break;
                                case "UpArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY + 50);
                                    }
                                    break;
                                case "LeftArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX - 50, velY);
                                    }
                                    break;
                                case "RightArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX + 50, velY);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            System.out.println(e.ENEMY_HEALTH);
                            if (e.enemyID == 5) {
                                for (BossMinotaur boss1 : bossMinotaurs) {
                                    if (boss1.enemyBody == collider.getBody()) {

                                        boolean hurt = Random.randomBoolean();

                                        if (hurt) {
                                            soundController.playSound("MinoHurt",10f,8f,0.04f);
                                        } else {
                                            soundController.playSound("MinoHurt2",10f,8f,0.04f);
                                        }

                                        if (boss1.ENEMY_HEALTH < boss1.MAX_HEALTH / 2) {
                                            init.roomList.get(player.currentRoom).snuffTorches();
                                            boss1.chargeThreshold = 3;
                                            boss1.defaultSpeed = boss1.enragedSpeed;

                                        //the minotaur speeds up after getting hit for a brief period
                                            /*
                                        if (!boss1.enraged) {
                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    boss1.enemyAI.setMaxLinearSpeed(boss1.enragedSpeed);
                                                    System.out.println(boss1.enemyAI.getMaxLinearSpeed());
                                                    boss1.enraged = true;

                                                    Timer.schedule(new Timer.Task() {
                                                        @Override
                                                        public void run() {
                                                            boss1.enraged = false;
                                                            boss1.enemyAI.setMaxLinearSpeed(20);
                                                        }
                                                    }, boss1.enrageTime);
                                                }
                                            }, 0.5f);
                                            }
                                        */
                                        }
                                    }
                                }
                            }
                        }

                        //e.enemyBody.applyForceToCenter(0,0, true);
                        if (e.enemyID != 5) {
                            e.enemyBody.setLinearVelocity(0, 0);
                        }


                        if (colliderStr.startsWith("Arrow") || collideeStr.startsWith("Arrow")) {
                            soundController.playSound("ArrowHit", 10f,8f,0.1f);
                            if (!arrowBodiesCollided.contains(collidee.getBody()) && e.enemyID != 3) {
                                arrowBodiesCollided.add(collidee.getBody());
                            }
                        }
                        if (colliderStr.startsWith("Sword") || collideeStr.startsWith("Sword")) {
                            soundController.playSound("SwordHit", 10f,8f,0.1f);
                        }



                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(e.enemyBody)) {

                                if (e.enemyID == 1){
                                    for (EnemySkull skull2 : enemySkulls) {
                                        if (skull2.enemyBody == collider.getBody()) {
                                            Skull skull = new Skull(world, collider.getBody().getPosition().x, collider.getBody().getPosition().y);

                                            if (e.inRespawnRange) {
                                                skull.resurrectable = true;
                                            }

                                            skulls.add(skull);
                                            deadEnemyBodies.add(collider.getBody());
                                            soundController.playSound("SkullDeath",9.5f,8f,0.1f);
                                            dyingSkulls.add(skull2);
                                        }
                                    }
                                }
                                else if (e.enemyID == 2) {
                                    for (EnemySpider spider : enemySpiders) {
                                        if (spider.enemyBody == collider.getBody()) {
                                            //deadEnemyBodies.add(collider.getBody());
                                            deadEnemyBodies.add(collider.getBody());
                                            soundController.playSound("SpiderDeath",8.5f,7.5f,0.1f);
                                            dyingSpiders.add(spider);
                                        }
                                    }
                                }
                                else if (e.enemyID == 3) {
                                    for (EnemyGhost ghost : enemyGhosts) {
                                        if (ghost.enemyBody == collider.getBody()) {
                                            deadEnemyBodies.add(collider.getBody());
                                            soundController.playSound("GhostDeath",8.5f,7.5f,0.1f);
                                            dyingGhosts.add(ghost);
                                        }
                                    }
                                }
                                else if (e.enemyID == 4) {
                                    for (EnemyCyclops eye : enemyEyes) {
                                        if (eye.enemyBody == collider.getBody()) {
                                            deadEnemyBodies.add(collider.getBody());
                                            soundController.playSound("SkullDeath",8.5f,7.5f,0.1f);
                                            dyingEyes.add(eye);
                                        }
                                    }
                                }
                                else if (e.enemyID == 5) {

                                    for (BossMinotaur boss1 : bossMinotaurs) {
                                        if (boss1.enemyBody == collider.getBody()) {
                                            deadEnemyBodies.add(collider.getBody());
                                            //soundController.playSound("SkullDeath",8.5f,7.5f,0.1f);
                                            dyingMinotaurs.add(boss1);
                                        }
                                    }
                                }
                            }

                            //skullArrayMap.put();


                            //hud.updateGold(1, true);

                            init.roomList.get(e.room).enemyCounter--;
                            if (init.roomList.get(e.room).enemyCounter < 1) {
                                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);


                                //init.roomList.get(player.currentRoom).doorLocations.get()
                                switch (GenerateLevel.path.get(player.currentRoom+1)){
                                    case 1:
                                        String topLeft = init.roomList.get(player.currentRoom).doorLocations.get("TopLeft");
                                        String[] topLeftXY = topLeft.split(",");
                                        String topLeftX = topLeftXY[0].toString();
                                        String topLeftY = topLeftXY[0].toString();
                                        Compass.calculateAngle(Integer.parseInt(topLeftX), Integer.parseInt(topLeftY));
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        break;
                                }


                                // hud.winRoom();
                                if (player.currentRoom != 10) {
                                    player.roomCleared = true;
                                } else {
                                    player.floorCleared = true;
                                }

                                //DungeonCrawler.roomClear.play();
                                //DungeonCrawler.roomClear.dispose();
                            }

                            break;
                        }
                    } else if (e.enemyBody == collidee.getBody()) {
                        String faData = collider.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        if (e.vulnerable) {
                            switch (faData) {
                                case "DownSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY - 50);
                                    }
                                    break;
                                case "UpSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY + 50);
                                    }
                                    break;
                                case "LeftSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX - 50, velY);
                                    }
                                    break;
                                case "RightSword":
                                    e.loseHealth(2);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX + 50, velY);
                                    }
                                    break;
                                case "DownArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY - 50);
                                    }
                                    break;
                                case "UpArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX, velY + 50);
                                    }
                                    break;
                                case "LeftArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX - 50, velY);
                                    }
                                    break;
                                case "RightArrow":
                                    e.loseHealth(1);
                                    if (e.enemyID != 5) {
                                        e.enemyBody.setLinearVelocity(velX + 50, velY);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            System.out.println(e.ENEMY_HEALTH);
                            if (e.enemyID == 5) {
                                for (BossMinotaur boss1 : bossMinotaurs) {
                                    if (boss1.enemyBody == collidee.getBody()) {

                                        boolean hurt = Random.randomBoolean();

                                        if (hurt) {
                                            soundController.playSound("MinoHurt",10f,8f,0.04f);
                                        } else {
                                            soundController.playSound("MinoHurt2",10f,8f,0.04f);
                                        }

                                        if (boss1.ENEMY_HEALTH < boss1.MAX_HEALTH / 2) {
                                            init.roomList.get(player.currentRoom).snuffTorches();
                                            boss1.chargeThreshold = 3;
                                            boss1.defaultSpeed = boss1.enragedSpeed;

                                        //the minotaur speeds up after getting hit for a brief period
                                         /*
                                        if (!boss1.enraged) {
                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    boss1.enemyAI.setMaxLinearSpeed(boss1.enragedSpeed);
                                                    System.out.println(boss1.enemyAI.getMaxLinearSpeed());
                                                    boss1.enraged = true;

                                                    Timer.schedule(new Timer.Task() {
                                                        @Override
                                                        public void run() {
                                                            boss1.enraged = false;
                                                            boss1.enemyAI.setMaxLinearSpeed(20);
                                                        }
                                                    }, boss1.enrageTime);
                                                }
                                            }, 0.5f);

                                            }
                                        */
                                        }
                                    }
                                }
                            }
                        }


                        if (colliderStr.startsWith("Arrow") || collideeStr.startsWith("Arrow")) {
                            soundController.playSound("ArrowHit", 10f,8f,0.1f);
                            if (!arrowBodiesCollided.contains(collider.getBody())&& e.enemyID != 3) {
                                arrowBodiesCollided.add(collider.getBody());
                            }
                        }
                        if (colliderStr.startsWith("Sword") || collideeStr.startsWith("Sword")) {
                            soundController.playSound("SwordHit", 10f,8f,0.1f);
                        }

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(e.enemyBody)) {

                                if (e.enemyID == 1) {
                                    for (EnemySkull skull2 : enemySkulls) {
                                        if (skull2.enemyBody == collidee.getBody()) {
                                            //deadEnemyBodies.add(collidee.getBody());
                                            Skull skull = new Skull(world, collidee.getBody().getPosition().x, collidee.getBody().getPosition().y);
                                            if (e.inRespawnRange) {
                                                skull.resurrectable = true;
                                            }

                                            skulls.add(skull);
                                            deadEnemyBodies.add(collidee.getBody());
                                            soundController.playSound("SkullDeath",9.5f,8f,0.1f);
                                            dyingSkulls.add(skull2);
                                        }
                                    }
                                } else if (e.enemyID == 2) {
                                    for (EnemySpider spider : enemySpiders) {
                                        if (spider.enemyBody == collidee.getBody()) {
                                            deadEnemyBodies.add(collidee.getBody());
                                            soundController.playSound("SpiderDeath",8.5f,7.5f,0.1f);
                                            dyingSpiders.add(spider);

                                        }
                                    }
                                }
                                else if (e.enemyID == 3) {
                                    for (EnemyGhost ghost : enemyGhosts) {
                                        if (ghost.enemyBody == collidee.getBody()) {
                                            deadEnemyBodies.add(collidee.getBody());
                                            soundController.playSound("GhostDeath",8.5f,7.5f,0.1f);
                                            dyingGhosts.add(ghost);
                                        }
                                    }
                                }
                                else if (e.enemyID == 4) {
                                    for (EnemyCyclops eye : enemyEyes) {
                                        if (eye.enemyBody == collidee.getBody()) {
                                            deadEnemyBodies.add(collidee.getBody());
                                            soundController.playSound("CyclopsDeath",8.5f,7.5f,0.1f);
                                            dyingEyes.add(eye);
                                        }
                                    }
                                }
                                else if (e.enemyID == 5) {
                                    for (BossMinotaur boss1 : bossMinotaurs) {
                                        if (boss1.enemyBody == collidee.getBody()) {
                                            deadEnemyBodies.add(collidee.getBody());
                                            //soundController.playSound("SkullDeath",8.5f,7.5f,0.1f);
                                            dyingMinotaurs.add(boss1);
                                        }
                                    }
                                }
                            }

                            //hud.updateGold(1, true);
                            init.roomList.get(e.room).enemyCounter--;
                            if (init.roomList.get(e.room).enemyCounter < 1) {
                                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
                                player.roomCleared = true;
                                if (init.roomList.get(player.currentRoom).index < GenerateLevel.numRooms-1) {
                                    Compass.showCompass();

                                    switch (GenerateLevel.path.get(player.currentRoom + 1)) {
                                        case 1:
                                            for (Door d : init.roomList.get(player.currentRoom + 1).doors) {
                                                if (Objects.equals(d.doorName, "BottomLeft")) {
                                                    Compass.calculateAngle(d.doorX, d.doorY);
                                                }
                                            }

                                            break;
                                        case 2:
                                            for (Door d : init.roomList.get(player.currentRoom).doors) {
                                                if (Objects.equals(d.doorName, "UpperRight")) {
                                                    Compass.calculateAngle(d.doorX, d.doorY);
                                                }
                                            }
                                            break;
                                        case 3:
                                            for (Door d : init.roomList.get(player.currentRoom + 1).doors) {
                                                if (Objects.equals(d.doorName, "TopLeft")) {
                                                    Compass.calculateAngle(d.doorX, d.doorY);
                                                }
                                            }

                                            break;
                                        case 4:
                                            for (Door d : init.roomList.get(player.currentRoom).doors) {
                                                if (Objects.equals(d.doorName, "UpperLeft")) {
                                                    Compass.calculateAngle(d.doorX, d.doorY);
                                                }
                                            }
                                            break;
                                    }
                                    //DungeonCrawler.roomClear.play();
                                    //DungeonCrawler.roomClear.dispose();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }



        if (((colliderStr.startsWith("Arrow") && collideeStr == "Skull")
                || (colliderStr == "Skull" && collideeStr.startsWith("Arrow")))
                || ((colliderStr == "Sword" && collideeStr == "Skull")
                || (colliderStr == "Skull" && collideeStr == "Sword"))
        ) {

            if (collidee.getBody().getUserData() == "Skull") {
                for (Skull s : skulls) {
                    if (collidee.getBody() == s.skullBody && !s.skullIFrame) {
                        if (s.SKULL_HEALTH > 0) {
                            s.SKULL_HEALTH--;
                            if (s.SKULL_HEALTH <= 0) {
                                brokenSkulls.add(s);
                                soundController.playSound("Skull",8.5f,7.5f,0.1f);
                            }
                            if (colliderStr.startsWith("Arrow")) {
                                if (!arrowBodiesCollided.contains(collider.getBody())) {
                                    arrowBodiesCollided.add(collider.getBody());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (((colliderStr == "Bone" || collideeStr == "Web") && collidee.getUserData() != "Enemy" && collideeStr != "Bone" && collideeStr != "Sword" && !collideeStr.startsWith("Arrow") && !collideeStr.startsWith("Room"))
                || ((collideeStr == "Bone" || collideeStr == "Web") && collider.getUserData() != "Enemy" && colliderStr != "Bone" && colliderStr != "Sword" && !colliderStr.startsWith("Arrow") && !colliderStr.startsWith("Room"))
        ) {

            if (((((collideeStr == "Wall")
                    || collideeStr == "Shield")
                    || collideeStr =="Door")
                    || collideeStr == "Column")
                    && colliderStr =="Bone") {
                if (collideeStr == "Wall" || collideeStr == "Shield") {
                    if (collidee.getBody().getUserData() == "Door") {
                        for (Room r : init.roomList) {
                            for (Door d : r.doors) {
                                if (d.doorBody == collidee.getBody()) {
                                    if (!d.open) {
                                        if (!boneBodiesCollided.contains(collider.getBody())) {
                                            boneBodiesCollided.add(collider.getBody());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        //when the player activates the shield and the new hitbox takes precedent
                    } else if (collideeStr == "Shield") {
                        if (!boneBodiesCollided.contains(collider.getBody())) {
                            boneBodiesCollided.add(collider.getBody());
                        }
                    }
                }
            }
            else if (((((colliderStr == "Wall")
                    || colliderStr == "Door")
                    || colliderStr == "Shield")
                    || colliderStr == "Column")
                    && collideeStr == "Bone") {


                if (colliderStr == "Wall" || colliderStr == "Shield") {

                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                        boneBodiesCollided.add(collidee.getBody());
                    } else {

                    }
                } else if (colliderStr == "Door") {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collider.getBody()) {
                                if (!d.open) {
                                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                                        boneBodiesCollided.add(collidee.getBody());
                                    }
                                }
                            }
                        }
                    }
                }

            }

            if ((((collideeStr == "Wall")
                    || collideeStr == "Shield")
                    || collideeStr =="Door")
                    && colliderStr =="Web") {
                if (collideeStr == "Wall" || collideeStr == "Shield") {
                    if (collidee.getBody().getUserData() == "Door") {
                        for (Room r : init.roomList) {
                            for (Door d : r.doors) {
                                if (d.doorBody == collidee.getBody()) {
                                    if (!d.open) {
                                        if (!webBodiesCollided.contains(collider.getBody())) {
                                            webBodiesCollided.add(collider.getBody());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (collideeStr == "Shield") {
                        if (!boneBodiesCollided.contains(collider.getBody())) {
                            boneBodiesCollided.add(collider.getBody());
                        }
                    }
                }
            }
            else if ((((colliderStr == "Wall")
                    || colliderStr == "Door")
                    || colliderStr == "Shield")
                    && collideeStr == "Web") {

                if (colliderStr == "Wall" || colliderStr == "Shield") {

                    if (!webBodiesCollided.contains(collidee.getBody())) {
                        webBodiesCollided.add(collidee.getBody());
                    } else {

                    }
                } else if (colliderStr == "Door") {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collider.getBody()) {
                                if (!d.open) {
                                    if (!webBodiesCollided.contains(collidee.getBody())) {
                                        webBodiesCollided.add(collidee.getBody());
                                    }
                                }
                            }
                        }
                    }
                }
            }



            else if (collideeStr == "Bone" && colliderStr == "Player") {
                hud.healthBar.loseHealth(0.5f);
                if (collidee.getBody().getUserData() == "Bone") {
             //       player.playerBody.applyLinearImpulse(collider.getBody().getLinearVelocity().x * 500, collider.getBody().getLinearVelocity().y * 500, 0, 0, true);
                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                        boneBodiesCollided.add(collidee.getBody());
                    }
                }

            } else if (colliderStr == "Bone" && collideeStr == "Player") {
                hud.healthBar.loseHealth(0.5f);
            //    player.playerBody.applyLinearImpulse(collidee.getBody().getLinearVelocity().x * 500, collidee.getBody().getLinearVelocity().y * 500, 0, 0, true);
                if (!boneBodiesCollided.contains(collider.getBody())) {
                    boneBodiesCollided.add(collider.getBody());
                }
            }
            else if (collideeStr == "Web" && colliderStr == "Player") {
               // hud.healthBar.LoseHealth(0.5f);
            //        player.playerBody.applyLinearImpulse(collider.getBody().getLinearVelocity().x * 500, collider.getBody().getLinearVelocity().y * 500, 0, 0, true);
                    if (!webBodiesCollided.contains(collidee.getBody())) {
                        webBodiesCollided.add(collidee.getBody());

                }

            } else if (colliderStr == "Web" && collideeStr == "Player") {
              //  hud.healthBar.LoseHealth(0.5f);
            //    player.playerBody.applyLinearImpulse(collidee.getBody().getLinearVelocity().x * 500, collidee.getBody().getLinearVelocity().y * 500, 0, 0, true);
                if (!webBodiesCollided.contains(collider.getBody())) {
                    webBodiesCollided.add(collider.getBody());
                }
            }
        }

            if (((colliderStr == "Player" && collideeStr == "Enemy")
                    || (colliderStr == "Enemy" && collideeStr == "Player"))
            ) {
                //if player enters enemy detection range, attack player
                if ((collider.getUserData() == "Proximity" ||
                        collidee.getUserData() == "Proximity")
                ) {
                    // System.out.println(colliderStr + " + " + collideeStr);
                    for (Enemy e : enemies) {
                        for (EnemySkull e1 : enemySkulls) {
                            if (e1.enemyBody == collider.getBody() || e1.enemyBody == collidee.getBody()) {
                                e1.playerInRange = true;
                            }
                        }
                        for (EnemySpider e2 : enemySpiders) {
                            if (e2.enemyBody == collider.getBody() || e2.enemyBody == collidee.getBody()) {
                                e2.playerInRange = true;
                            }
                        }
                        for (EnemyGhost e3 : enemyGhosts) {
                            if (e3.enemyBody == collider.getBody() || e3.enemyBody == collidee.getBody()) {
                                e3.playerInRange = true;
                            }
                        }
                        for (EnemyCyclops e4 : enemyEyes) {
                            if (e4.enemyBody == collider.getBody() || e4.enemyBody == collidee.getBody()) {
                                e4.playerInRange = true;
                            }
                        }
                    }
                }
            }

        if (collider.getUserData() == "Spawner") {

          //  System.out.println("in range");
            if (collideeStr == "Enemy") {
                if (collidee.getUserData() != "Proximity") {
                 //   System.out.println("not sensor");
                    for (EnemySkull e : enemySkulls) {
                        if (e.enemyBody == collidee.getBody()) {
                            //System.out.println(colliderStr + " " + collideeStr);
                            e.inRespawnRange = true;
                        }
                    }
                }
            }
        } else if (collidee.getUserData() == "Spawner") {

          //  System.out.println("in range");
            if (colliderStr == "Enemy") {
                if (collider.getUserData() != "Proximity") {
                    for (EnemySkull e : enemySkulls) {
                        if (e.enemyBody == collider.getBody()) {
                            //System.out.println(colliderStr + " " + collideeStr);
                            e.inRespawnRange = true;
                        }
                    }
                }
            }
        }

        if (collider.getUserData() == "PlayerBound") {
            if (collidee.getBody().getUserData() == "Potion") {
                for (Potion p : potions) {
                    if (p.potionBody == collidee.getBody()) {
                        if (!(hud.inventory.Capacity == hud.inventory.Size)) {
                            collectedPotions.add(p);
                        }
                    }
                }
            }

            if (collidee.getBody().getUserData() == "Coin") {
                for (Coin c : coins) {
                    if (c.coinBody == collidee.getBody()) {
                        collectedCoins.add(c);
                    }
                }
            }

            if (collidee.getBody().getUserData() == "Heart") {
                for (Heart h : hearts) {
                    if (h.heartBody == collidee.getBody() && hud.healthBar.currentHealth < hud.healthBar.maxHealth) {
                        collectedHearts.add(h);
                        soundController.playSound("PickupHeart", 9f, 8f, 0.08f);
                    }
                }
            }

        }

            switch (colliderStr) {

                case "TrapArea":
                    if (collidee.getUserData() == "PlayerBound") {
                        for (Trap tr : traps) {
                            if (tr.trapArea == collider.getBody()) {
                                if (!tr.active) {
                                    tr.fireArrow(tr.trapX, tr.trapY, tr.type);
                                }
                            }
                        }
                    }
                    break;

                case "Roof":
                    if (collidee.getUserData() == "PlayerBound") {
                        for (Roof r : roofs) {
                            if (r.roofBody == collider.getBody()) {
                                r.visible = false;
                            }
                        }
                }
                break;
                case "Chisel":

                    if (collideeStr == "Obstacle") {
                        for (Obstacle ob : obstacles) {
                            if (collidee.getBody() == ob.obBody) {
                                obstacleBodiesCollected.add(collidee.getBody());
                                soundController.playSound("Chisel",11,10,0.1f);
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        soundController.playSound("Chisel",11,10,0.1f);
                                    }
                                }, 0.2f);
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        soundController.playSound("Chisel",11,10,0.1f);
                                    }
                                }, 0.5f);
                            }
                        }
                    }
                    break;


                case "Bone": {

                    if (collideeStr == "Wall") {
                        if (!boneBodiesCollided.contains(collider.getBody())) {
                            boneBodiesCollided.add(collider.getBody());
                        }
                    }
                    break;
                }
                case "Skull":
                    if (collideeStr == "Skull") {

                    }

                    break;
                case "Column":
                    if (collideeStr.startsWith("Arrow")) {
                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                            arrowBodiesCollided.add(collidee.getBody());
                            break;
                        }
                    } else if (collideeStr == "Bone") {
                        if (!boneBodiesCollided.contains(collidee.getBody())) {
                            boneBodiesCollided.add(collidee.getBody());
                        }
                    }
                    break;
                case "Fire":
                    if (collideeStr.startsWith("Arrow")) {
                        for (Fire f : fires) {
                            if (f.fireBody == collider.getBody()) {
                                if (f.type == 1 || f.type == 3) {
                                    for (Arrow a : arrows) {
                                        if (a.arrowBody == collidee.getBody()) {
                                            if (a.onFire && !f.smoking) {
                                                if (!f.active) {
                                                    soundController.playSound("FireWhoosh",10,7,0.04f);
                                                    f.active = true;
                                                }
                                                f.light.setActive(true);
                                                f.smoking = false;
                                                f.extinguish = true;

                                                if (f.type == 3) {
                                                    f.torchLight.setActive(true);
                                                }
                                            }  else if (f.extinguish) {
                                                a.onFire = true;
                                                f.smoking = true;
                                                f.extinguish = false;
                                            }
                                        }
                                    }
                                } else if (f.extinguish && f.type == 2 && collider.getUserData() != "Spawner") {
                                    f.smoking = true;
                                    f.extinguish = false;

                                }
                            }
                        }
                    }
                    else if (collideeStr == "Sword" || collideeStr == "Lance") {
                        for (Fire f : fires) {
                            if (f.fireBody == collider.getBody()) {
                                if (f.extinguish && (f.type == 1 || f.type == 3)) {
                                    f.smoking = true;
                                    f.extinguish = false;
                                }
                            }
                        }
                    }
                    break;

                case "RafWall":
                    if (collideeStr == "Arrow") {
                        for (RaisedFloor raf : raisedFloors) {
                            if (collider.getBody() == raf.rafBody) {
                                if (!raf.lowered) {
                                    if (!arrowBodiesCollided.contains(collidee.getBody())) {
                                        arrowBodiesCollided.add(collidee.getBody());
                                    }
                                }
                            }
                        }
                    }
                    if (collideeStr == "RafBottom") {
                        collider.setSensor(true);
                    }
                    if ((collidee.getBody().getType() == DynamicBody
                            && (!collidee.isSensor() && collidee.getUserData() != "Player")
                    )|| collidee.getUserData() == "PlayerBound")
                    {
                        if (collider.getUserData() == "Raf") {
                            for (RaisedFloor raf : raisedFloors) {
                                if (raf.rafBody == collider.getBody() && raf.lowered) {
                                    raf.entityColliding = true;
                                }
                            }
                        }
                    }
                    break;
                case "Wall":
                    if (collideeStr == "Arrow") {
                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                            arrowBodiesCollided.add(collidee.getBody());
                        }
                    }
                    if (collideeStr == "Enemy") {
                        for (BossMinotaur b : bossMinotaurs) {
                            if (b.enemyBody == collidee.getBody()) {
                                //maybe make the minotaur stop before chasing the player again if too sudden

                                if (b.stateMachine.getCurrentState() == BossMinotaurState.CHARGE_ATTACK)
                                    b.stunned = true;
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            b.stateMachine.changeState(BossMinotaurState.GO_TO_PLAYER);
                                            b.locked = false;
                                            b.stunned = false;

                                        }
                                    }, 2.2f);
                            }
                        }
                    }
                    break;
                case "Obstacle":
                    if (collideeStr.startsWith("Arrow")) {
                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                            arrowBodiesCollided.add(collidee.getBody());
                        }
                    }
                    break;

                case "Arrow":
                    if (collidee.getUserData() == "PlayerBound") {
                        hud.healthBar.loseHealth(0.5f);
                    }

                    if (((((collideeStr == "Enemy" && collidee.getUserData()!= "EnemyGhost") && collidee.getUserData() != "Proximity") && (collideeStr != "Cobweb" || collideeStr != "Water"))
                            || collideeStr == "Wall")) {
                        if (!arrowBodiesCollided.contains(collider.getBody())) {
                            arrowBodiesCollided.add(collider.getBody());
                        }
                        if (collidee.getUserData() == "EnemySkull") {
                            for (EnemySkull e : enemySkulls) {
                                if (e.enemyAI.getBody() == collidee.getBody()) {
                                    //e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        } else if (collidee.getUserData() == "EnemySpider") {
                            for (EnemySpider e2 : enemySpiders) {
                                if (e2.enemyAI.getBody() == collidee.getBody()) {
                                    //e2.getStateMachine().changeState(EnemySpiderState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        } else if (collidee.getUserData() == "EnemyGhost") {
                            for (EnemyGhost e3 : enemyGhosts) {
                                if (e3.enemyAI.getBody() == collidee.getBody()) {
                                    //e3.getStateMachine().changeState(EnemyGhostState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        } else if (collidee.getUserData() == "EnemyCyclops") {
                            for (EnemyCyclops e4 : enemyEyes) {
                                if (e4.enemyAI.getBody() == collidee.getBody()) {
                                    //e4.getStateMachine().changeState(EnemyCyclopsState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        }



                    } else if (collideeStr == "Fire") {
                        for (Fire f : fires) {
                            if (f.fireBody == collidee.getBody()) {
                                if (f.type == 1 || f.type == 3) {
                                    for (Arrow a : arrows) {
                                        if (a.arrowBody == collider.getBody()) {
                                            if (a.onFire && !f.smoking) {
                                                if (!f.active) {
                                                    soundController.playSound("FireWhoosh",10,7,0.04f);
                                                    f.active = true;
                                                }
                                                f.light.setActive(true);
                                                f.smoking = false;
                                                f.extinguish = true;
                                                if (f.type == 3) {
                                                    f.torchLight.setActive(true);
                                                }
                                            } else if (f.extinguish) {
                                                a.onFire = true;
                                                f.smoking = true;
                                                f.extinguish = false;
                                            }
                                        }
                                    }
                                    //
                                } else if (f.extinguish && f.type == 2 && collidee.getUserData() != "Spawner") {
                                    f.smoking = true;
                                    f.extinguish = false;

                                }
                            }
                        }
                    }
                    break;
                case "Door":
                    if (collideeStr == "Arrow") {
                        for (Room r : init.roomList) {
                            for (Door d : r.doors) {
                                if (d.doorBody == collider.getBody()) {
                                    if (!d.open) {
                                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                                            arrowBodiesCollided.add(collidee.getBody());
                                            break;
                                        }

                                    }
                                }
                            }
                        }
                    }
                    else if (collideeStr == "Enemy") {
                        for (BossMinotaur b : bossMinotaurs) {
                            if (b.enemyBody == collidee.getBody()) {
                                //maybe make the minotaur stop before chasing the player again if too sudden

                                if (b.stateMachine.getCurrentState() == BossMinotaurState.CHARGE_ATTACK)
                                    b.stunned = true;
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            b.locked = false;
                                            b.stunned = false;
                                            b.stateMachine.changeState(BossMinotaurState.GO_TO_PLAYER);
                                        }
                                    }, 2.2f);

                            }
                        }
                    }
                    else if (collideeStr == "Bone") {
                        for (Room r : init.roomList) {
                            for (Door d : r.doors) {
                                if (d.doorBody == collider.getBody()) {
                                    if (!d.open) {
                                        if (!boneBodiesCollided.contains(collidee.getBody())) {
                                            boneBodiesCollided.add(collidee.getBody());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "Sword":
                    if (collideeStr == "Fire" && collidee.getUserData() != "Spawner"){
                        for (Fire f : fires) {
                            if (f.fireBody == collidee.getBody()) {
                                if (f.extinguish && f.type == 1 || f.type == 3) {
                                    f.smoking = true;
                                    f.extinguish = false;
                                }
                            }
                        }
                    }
                    break;
                case "Enemy":
                    if (collidee.getUserData() == "Player" && collider.getUserData() != "Proximity") {
                        for (EnemySkull e : enemySkulls) {
                            hud.healthBar.loseHealth(0.5f);
                            if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()) {
                                e.playerInRange = true;
                                // e.getStateMachine().changeState(EnemyState.GO_TO_PLAYER);
                                //e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                            }
                        }
                    }
                    break;
                case "Player":

                    if ((collideeStr == "Web")) {
                        if (!player.touchingCobweb) {
                            Cobweb web = new Cobweb(world, collider.getBody().getPosition().x - 8,collider.getBody().getPosition().y - 8, false);
                            cobwebs.add(web);
                            player.touchingCobweb = true;
                        }
                        if (!webBodiesCollided.contains(collidee.getBody())) {
                            webBodiesCollided.add(collidee.getBody());
                        }
                        break;
                    }

                    if ((collideeStr == "Cobweb")) {
                        DungeonCrawler.PLAYER_SPEED_MULTI = 15f;
                        player.touchingCobweb = true;
                        break;
                    }

                    if ((collideeStr == "Water")) {
                        //DungeonCrawler.PLAYER_SPEED_MULTI = 25f;
                        player.swimming = true;
                        break;
                    }

                    if (collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity" && collidee.getUserData() == "EnemySkull") {
                        for (EnemySkull e : enemySkulls) {
                            if ((e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody())) {
                                hud.healthBar.loseHealth(0.5f);
                                e.playerInRange = true;
                            }
                        }
                    } else if (collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity" && collidee.getUserData() == "EnemySpider") {
                        for (EnemySpider e : enemySpiders) {
                            if ((e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody())) {
                                hud.healthBar.loseHealth(0.5f);
                                e.playerInRange = true;
                            }
                        }
                    }
                    else if (collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity" && collidee.getUserData() == "EnemyGhost") {
                        for (EnemyGhost e : enemyGhosts) {
                            if ((e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody())) {
                                hud.healthBar.loseHealth(0.5f);
                                e.playerInRange = true;
                            }
                        }
                    }
                    else if (collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity" && (collidee.getUserData() == "EnemyCyclops" || collidee.getBody().getUserData() == "Eyebeam")) {
                        for (EnemyCyclops e : enemyEyes) {
                            if ((e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody())) {
                                e.playerInRange = true;
                            }
                            hud.healthBar.loseHealth(0.5f);
                        }
                    }
                    else if (collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity" && collidee.getUserData() == "BossMinotaur") {

                        if (collideeStr == "Enemy") {
                            for (BossMinotaur b : bossMinotaurs) {
                                if (b.enemyBody == collidee.getBody()) {
                                    //maybe make the minotaur stop before chasing the player again if too sudden
                                    if (b.stateMachine.getCurrentState() == BossMinotaurState.CHARGE_ATTACK)
                                        b.stunned = true;
                                        hud.healthBar.loseHealth(0.5f);
                                        b.playerInRange = true;
                                        //b.stateMachine.changeState(BossMinotaurState.STOP);
                                        b.locked = true;
                                    ArrayMap<Integer, String> temp = new ArrayMap<>();
                                    temp.put(1,"Up");
                                    temp.put(2,"Left");
                                    temp.put(3,"Down");
                                    temp.put(4,"Right");

                                    if (b.facing == "Up") {
                                        b.facing = "Down";
                                        //temp.removeKey(1);
                                        //temp.removeKey(3);
                                    }
                                    if (b.facing == "Left") {
                                        b.facing = "Right";
                                        //temp.removeKey(2);
                                        //temp.removeKey(4);
                                    }
                                    if (b.facing == "Down") {
                                        b.facing = "Up";
                                        //temp.removeKey(3);
                                        //temp.removeKey(1);
                                    }
                                    if (b.facing == "Right") {
                                        b.facing = "Left";
                                        //temp.removeKey(4);
                                        //temp.removeKey(2);
                                    }
                                    /*
                                    int r = Random.randomInt(1,0);
                                    if (r == 0) {
                                        b.facing = temp.getValueAt(0);
                                        System.out.println(temp.getValueAt(0));
                                    }
                                    else if (r == 1) {
                                        b.facing = temp.getValueAt(1);
                                        System.out.println(temp.getValueAt(1));
                                    }
                                    else if (r == 2) {
                                        b.facing = temp.getValueAt(2);
                                        System.out.println(temp.getValueAt(2));
                                    }
                                    */
                                        //b.stateMachine.changeState(BossMinotaurState.CHARGE_ATTACK);
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            b.locked = false;
                                            b.stunned = false;
                                            //b.stateMachine.changeState(BossMinotaurState.GO_TO_PLAYER);
                                            b.stateMachine.changeState(BossMinotaurState.CHARGE_ATTACK);
                                            Timer.schedule(new Timer.Task() {
                                                @Override
                                                public void run() {
                                                    b.locked = false;
                                                    b.stunned = false;
                                                    b.stateMachine.changeState(BossMinotaurState.GO_TO_PLAYER);
                                                    //b.stateMachine.changeState(BossMinotaurState.CHARGE_ATTACK);
                                                }
                                            }, 1.5f);
                                        }
                                    }, 0.5f);
                                }
                            }
                        }
                        /*
                        for (BossMinotaur b : bossMinotaurs) {
                            if ((b.enemyBody == collider.getBody() || b.enemyBody == collidee.getBody())) {


                                ArrayMap<Integer, String> temp = new ArrayMap<>();
                                temp.put(1,"Up");
                                temp.put(2,"Left");
                                temp.put(3,"Down");
                                temp.put(4,"Right");

                                if (b.facing == "Up") {
                                    temp.removeKey(1);
                                }
                                if (b.facing == "Left") {
                                    temp.removeKey(2);
                                }
                                if (b.facing == "Down") {
                                    temp.removeKey(3);
                                }
                                if (b.facing == "Right") {
                                    temp.removeKey(4);
                                }
                                int r = Random.randomInt(2,0);
                                if (r == 0) {
                                    b.facing = temp.getValueAt(0);
                                }
                                else if (r == 1) {
                                    b.facing = temp.getValueAt(1);
                                }
                                else if (r == 2) {
                                    b.facing = temp.getValueAt(2);
                                }
                                b.locked = false;


                                //b.stateMachine.revertToPreviousState();
                                b.chargeTime = 0;
                                //b.stateMachine.changeState(BossMinotaurState.CHARGE_ATTACK);
                            }
                        }

                         */
                    }





                    if (collidee.getUserData() == "ShopRadius") {
                        for (Shopkeeper shop : shopkeepers) {
                            if (collidee.getBody() == shop.shopBody) {
                                shop.message = shop.messages.get(0);
                                shop.message.showing = true;
                            }
                        }
                    } else if (collidee.getUserData() == "ShopSell") {
                        for (Shopkeeper shop : shopkeepers) {
                            if (collidee.getBody() == shop.shopBody) {
                                //shop.message = shop.susMessages.get(1);
                                //shop.message.showing = true;
                               // soundController.playSound("Shop",11,11,0.1f);
                                shop.ListStock();

                                player.buyingStock = true;
                                player.shopkeeper = shop;

                            /*
                            for (int i = 0; i < shop.inventoryText.size(); i++) {
                                shop.inventoryText.get(i).showing = true;
                                shop.inventoryText.get(i).fade = false;
                            }
                             */
                            }
                        }
                    }
                    break;
                    default: {
                        break;
                }
            }

            switch (collideeStr) {
                case "RafWall":
                    if (collideeStr == "Arrow") {
                        for (RaisedFloor raf : raisedFloors) {
                            if (collider.getBody() == raf.rafBody) {
                                if (!raf.lowered) {
                                    if (!arrowBodiesCollided.contains(collidee.getBody())) {
                                        arrowBodiesCollided.add(collidee.getBody());
                                    }
                                }
                            }
                        }
                    }
                    if (colliderStr == "RafBottom") {
                        collidee.setSensor(true);
                    }
                    if ((collider.getBody().getType() == DynamicBody
                            && (!collider.isSensor() && collider.getUserData() != "Player")) || collider.getUserData() == "PlayerBound")
                    {
                        if (collider.getUserData() == "Raf") {
                            for (RaisedFloor raf : raisedFloors) {
                                if (raf.rafBody == collider.getBody() && raf.lowered) {
                                    raf.entityColliding = true;
                                }
                            }
                        }
                    }
                    break;
                case "TrapArea":
                    if (collider.getUserData() == "PlayerBound") {
                        for (Trap tr : traps) {
                            if (tr.trapArea == collidee.getBody()) {
                                if (!tr.active) {
                                    tr.fireArrow(tr.trapX,tr.trapY, tr.type);
                                }
                            }
                        }
                    }
                    break;
                case "Water":
                    if (collider.getUserData() == "PlayerBound") {
                        System.out.println("SEXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXQQQQ");
                        player.swimming = true;
                        break;
                    }
                    break;

                case "Cobweb":
                    if (collider.getUserData() == "PlayerBound") {
                       DungeonCrawler.PLAYER_SPEED_MULTI = 15f;
                        player.touchingCobweb = true;
                       break;
                    }

                    else if ((colliderStr.startsWith("Arrow"))) {
                        for (OrderedMap.Entry<Body, Arrow> arrowEntry : arrowArrayMap.entries()) {
                            Arrow value = arrowEntry.value;
                            if (value.arrowBody == collider.getBody()) {
                                if (value.onFire) {
                                    for (Cobweb cob : cobwebs) {
                                        if (cob.cobBody == collidee.getBody()) {
                                            burnedCobwebs.add(cob);
                                        }
                                    }
                                    if (!arrowBodiesCollided.contains(collider.getBody())) {
                                        //arrowBodiesCollided.add(collider.getBody());
                                    }
                                }

                            }
                        }
                    }
                    break;
                case "Player":

                    if (colliderStr == "Stem") {
                        for (Column C : columns) {
                            if (C.stemBody == collider.getBody()) {

                                C.visible = false;
                            }
                        }
                    }

                    if (colliderStr == "Flag") {
                        for (Flag F : flags) {
                            if (F.flagBody == collider.getBody()) {
                                F.visible = false;
                            }
                        }
                    }
                    if (colliderStr == "Statue") {
                        for (Statue S : statues) {
                            if (S.statueBody == collider.getBody()) {
                                S.visible = false;
                            }
                        }
                    }


                    //TODO: revise - unused
                    if (colliderStr == "Enemy" && collider.getUserData() != "Proximity") {
                        for (EnemySkull e : enemySkulls) {
                            hud.healthBar.loseHealth(0.5f);
                            if (e.enemyBody == collider.getBody() || e.enemyBody == collider.getBody()) {
                                e.playerInRange = true;
                                // e.getStateMachine().changeState(EnemyState.GO_TO_PLAYER);
                                //e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                            }
                        }
                    }
                case "Obstacle":
                    if (colliderStr.startsWith("Arrow")) {
                        if (!arrowBodiesCollided.contains(collider.getBody())) {
                            arrowBodiesCollided.add(collider.getBody());
                            break;
                        }
                    }
                    break;
                case "Arrow":
                    if (collider.getUserData() == "PlayerBound") {
                        hud.healthBar.loseHealth(0.5f);
                    }

                    if (colliderStr.equals("Obstacle")) {
                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                            arrowBodiesCollided.add(collidee.getBody());
                            break;
                        }
                    } else if (colliderStr == "Fire") {
                    for (Fire f : fires) {
                        if (f.fireBody == collider.getBody()) {
                            if (f.type == 1 || f.type == 3) {
                                for (Arrow a : arrows) {
                                    if (a.arrowBody == collidee.getBody()) {
                                        if (a.onFire && !f.smoking) {
                                            if (!f.active) {
                                                soundController.playSound("FireWhoosh",10,7,0.04f);
                                                f.active = true;
                                            }
                                            f.light.setActive(true);
                                            f.smoking = false;
                                            f.extinguish = true;
                                            if (f.type == 3) {
                                                f.torchLight.setActive(true);
                                            }
                                        } else if (f.extinguish) {
                                            a.onFire = true;
                                            f.smoking = true;
                                            f.extinguish = false;
                                        }
                                    }
                                }
                            } else if (f.extinguish && f.type == 2) {
                                f.smoking = true;
                                f.extinguish = false;

                            }
                        }
                    }
                }
                    break;
                case "Roof":
                    if (collider.getUserData() == "PlayerBound") {
                        for (Roof r : roofs) {
                            if (r.roofBody == collidee.getBody()) {
                                r.visible = false;
                            }
                        }
                    }
                    break;
                default: {
                    break;
                }
            }

            if ((collider.getBody().getUserData() == "Door" && collidee.getUserData() == "PlayerBound")
                    || (collider.getUserData() == "PlayerBound" && collidee.getBody().getUserData() == "Door")
                    || ((collider.getBody().getUserData() == "Door" && collidee.getBody().getUserData() == "Enemy")
                    || (collider.getBody().getUserData() == "Enemy" && collidee.getBody().getUserData() == "Door"))
            ) {
                if (collider.getBody().getUserData() == "Door"
                        && (collidee.getUserData() != "Proximity")) {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collider.getBody()) {
                                if (!d.locked) {
                                    d.open = true;

                                    if (collidee.getUserData() == "PlayerBound") {
                                        player.touchingDoor = true;
                                        soundController.playSound("DoorOpen", 8f, 8f, 0.1f);
                                        if (!d.opened) {
                                            Compass.resetCompass();
                                            Compass.hideCompass();
                                        }
                                        d.opened = true;
                                    }
                                } else {
                                    if (collidee.getUserData() == "PlayerBound") {
                                        soundController.playSound("DoorClose", 10, 10, 0.1f);
                                    }
                                }
                            }
                        }
                    }
                }
                else if (collidee.getBody().getUserData() == "Door"
                        && (collider.getUserData() != "Proximity")) {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collider.getBody()) {
                                if (!d.locked) {
                                    d.open = true;
                                    if (collider.getUserData() == "PlayerBound") {
                                        player.touchingDoor = true;
                                        soundController.playSound("DoorOpen", 8f, 8f, 0.1f);
                                        if (!d.opened) {
                                            Compass.resetCompass();
                                            Compass.hideCompass();
                                        }
                                        d.opened = true;
                                    }
                                } else {
                                    if (collider.getUserData() == "PlayerBound") {
                                        soundController.playSound("DoorClose", 10, 10, 0.1f);
                                    }
                                }
                            }
                        }
                    }
                }
            }


            if (collider.getBody().getUserData().toString().startsWith("Room")) {
                // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))

                if (collidee.getUserData() == "PlayerBound") {

                    String[] roomIndexAsString = collider.getBody().getUserData().toString().split("-");
                    player.currentRoom = Integer.parseInt(roomIndexAsString[1]);
                    init.roomList.get(player.currentRoom).isEntered = true;

                    for (EnemySkull e : init.roomList.get(player.currentRoom).enemySkulls) {
                        e.rayCastable = true;
                        e.enemyAI.setMaxLinearSpeed(e.defaultSpeed);
                        e.stateMachine.changeState(EnemySkullState.WANDER);
                        e.active = true;
                    }
                    for (EnemySpider e2 : init.roomList.get(player.currentRoom).enemySpiders) {
                        e2.rayCastable = true;
                        e2.enemyAI.setMaxLinearSpeed(e2.defaultSpeed);
                        e2.stateMachine.changeState(EnemySpiderState.WANDER);
                        e2.active = true;
                    }
                    for (EnemyGhost e3 : init.roomList.get(player.currentRoom).enemyGhosts) {
                        e3.rayCastable = true;
                        e3.enemyAI.setMaxLinearSpeed(e3.defaultSpeed);
                        e3.stateMachine.changeState(EnemyGhostState.WANDER);
                        e3.active = true;
                    }
                    for (EnemyCyclops e4 : init.roomList.get(player.currentRoom).enemyEyes) {
                        e4.rayCastable = true;
                        e4.enemyAI.setMaxLinearSpeed(e4.defaultSpeed);
                        e4.stateMachine.changeState(EnemyCyclopsState.WANDER);
                        e4.active = true;
                    }

                    player.touchingRoom = true;


                    for (Roof r : init.roomList.get(player.currentRoom).roofs) {
                      //  r.visible = false;
                    }
                }



                if (init.roomList.get(player.currentRoom).isShop) {
                    init.roomList.get(player.currentRoom).enemyCounter = 0;
                    init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
                }
            }

        }


    @Override
    public void endContact(Contact contact) {
        Fixture collider = contact.getFixtureA();
        Fixture collidee = contact.getFixtureB();

        String colliderAsString = collider.getBody().getUserData().toString();
        String collideeAsString = collidee.getBody().getUserData().toString();

        if (colliderAsString == "Water" && collidee.getUserData() == "PlayerBound") {
            player.swimming = false;
            DungeonCrawler.PLAYER_SPEED_MULTI = PLAYER_DEFAULT_SPEED;
        }

        switch (colliderAsString) {
            case "RafWall":
                if (collideeAsString == "RafBottom") {
                    collider.setSensor(false);
                }
                if ((collidee.getBody().getType() == DynamicBody
                    && (!collidee.isSensor() && collidee.getUserData() != "Player")
                  )|| collidee.getUserData() == "PlayerBound") {
                    if (collider.getUserData() == "Raf") {
                        for (RaisedFloor raf : raisedFloors) {
                            if (raf.rafBody == collider.getBody()) {
                                raf.entityColliding = false;
                                if (!raf.lowering) {
                                    raf.raiseFloorAfterEntityMoves();
                                }
                            }
                        }
                    }
                }
                break;
            case "Stem":
                if (collidee.getUserData() == "PlayerBound") {

                    for (Column C : columns) {
                        if (C.stemBody == collider.getBody()) {
                            C.visible = true;
                        }
                    }

                    break;
                }
            case "Flag":
                if (collidee.getUserData() == "PlayerBound") {

                    for (Flag F : flags) {
                        if (F.flagBody == collider.getBody()) {
                            F.visible = true;
                        }
                    }

                    break;
                }
            case "Statue":
                if (collidee.getUserData() == "PlayerBound") {

                    for (Statue S : statues) {
                        if (S.statueBody == collider.getBody()) {
                            S.visible = true;
                        }
                    }

                    break;
                }
            case "Cobweb":
                if (collider.getUserData() == "PlayerBound") {
                    DungeonCrawler.PLAYER_SPEED_MULTI = PLAYER_DEFAULT_SPEED;
                    player.touchingCobweb = false;
                    break;
                }
                break;
            case "Water":
                if (collider.getUserData() == "PlayerBound") {
                    player.swimming = false;
                }
                break;
            case "Player":
                if (collidee.getUserData() == "ShopSell") {
                    for (Shopkeeper s : shopkeepers) {
                        s.HideStock();
                    }
                }
                break;
            case "Enemy":
                if (collider.getUserData() != "Proximity") {
                    if (collidee.getUserData() == "Spawner") {
                 //       System.out.println("out of range");
                        for (EnemySkull e : enemySkulls) {
                            if (e.enemyBody == collider.getBody()) {
                                e.inRespawnRange = false;
                            }
                        }
                    }
                break;
            }
            case "Spawner":
                if (collideeAsString == "Enemy") {
                if (collidee.getUserData() != "Proximity") {
                //    System.out.println("out of range");
                        for (EnemySkull e : enemySkulls) {
                            if (e.enemyBody == collidee.getBody()) {
                                e.inRespawnRange = false;
                            }
                        }
                    }
                    break;
                }
                break;
            case "Roof":
                if (collidee.getUserData() == "PlayerBound") {
                    for (Roof r : roofs) {
                        if (r.roofBody == collider.getBody()) {
                            r.visible = true;
                        }
                    }
                }
                break;
            default:
                //System.out.println("Unassessed exit collision");
                //System.out.println(colliderAsString + " " +collideeAsString);
        }

        switch (collideeAsString) {
            case "RafWall":
                if (colliderAsString == "RafBottom") {
                    collidee.setSensor(false);
                }
                if ((collider.getBody().getType() == DynamicBody
                && (!collider.isSensor() && collider.getUserData() != "Player")) || collider.getUserData() == "PlayerBound") {
                    if (collidee.getUserData() == "Raf") {
                        for (RaisedFloor raf : raisedFloors) {
                            if (raf.rafBody == collidee.getBody()) {
                                raf.entityColliding = false;
                                if (!raf.lowering) {
                                    raf.raiseFloorAfterEntityMoves();
                                }
                            }
                        }
                    }
                }
                break;
            case "Cobweb":
                if (collider.getUserData() == "PlayerBound") {
                    DungeonCrawler.PLAYER_SPEED_MULTI = PLAYER_DEFAULT_SPEED;
                    player.touchingCobweb = false;
                    for (Cobweb cob : cobwebs) {
                        if (cob.cobBody == collidee.getBody()) {
                            if (!cob.impassable) {
                                burnedCobwebs.add(cob);
                            }
                        }
                    }
                    break;
                }
            case "Water":
                if (collider.getUserData() == "PlayerBound") {
                    player.swimming = false;
                }
                break;
            case "Roof":
                if (collider.getUserData() == "PlayerBound") {
                    for (Roof r : roofs) {
                        if (r.roofBody.getUserData() == collideeAsString) {
                            r.visible = true;
                        }
                    }
                }
                break;

            default:
                break;
        }


        if (    (colliderAsString == "Player" && collideeAsString == "Enemy")
                ||(colliderAsString == "Enemy" && collideeAsString == "Player")
        ){
            if  (collider.getUserData() == "Proximity"||
                    collidee.getUserData() == "Proximity"){

                    for (EnemySkull e : enemySkulls) {
                        if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                            e.playerInRange = false;
                            e.getStateMachine().changeState(EnemySkullState.WANDER);
                        }
                    }

                    for (EnemySpider e : enemySpiders) {
                        if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                            e.playerInRange = false;
                            e.getStateMachine().changeState(EnemySpiderState.WANDER);
                        }
                    }
                for (EnemyGhost e : enemyGhosts) {
                    if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                        e.playerInRange = false;
                        e.getStateMachine().changeState(EnemyGhostState.WANDER);
                    }
                }
                for (EnemyCyclops e : enemyEyes) {
                    if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                        e.playerInRange = false;
                        for (Fixture fixture : e.enemyBody.getFixtureList()) {

                            if (fixture.getUserData() == "Eyebeam") {

                                if (!eyebeamArrayMap.isEmpty()) {

                                    if (!reversedEyebeamMap) {
                                        eyebeamArrayMap.reverse();
                                        reversedEyebeamMap = true;
                                    }

                                    for (OrderedMap.Entry<Body, Eyebeam> beamEntry : eyebeamArrayMap.entries()) {
                                        Body key = beamEntry.key;
                                        Eyebeam value = beamEntry.value;

                                        if (key == fixture.getBody()) {
                                            value.beamLight.setActive(false);
                                        }
                                    }
                                }
                                eyebeamBodiesCollected.add(fixture.getBody());
                            }
                        }
                        e.getStateMachine().changeState(EnemyCyclopsState.WANDER);
                        }
                    }
                }
            }

        if (collider.getBody().getUserData().toString().startsWith("Room")) {



            if (collidee.getUserData() == "PlayerBound") {

                for (Roof r : init.roomList.get(player.currentRoom).roofs) {
                   // r.visible = true;
                }
                player.touchingRoom = false;
                if (player.currentRoom < GenerateLevel.numRooms){ //TODO: Check for level progress before adding to current room
                        init.roomList.get(player.currentRoom).unlockDoor(world, init.roomList.get(player.currentRoom+1),false);
                }
            }
        }

        if ((collider.getBody().getUserData() == "Door" && collidee.getUserData() == "PlayerBound")
            ||(collider.getUserData() == "PlayerBound" & collidee.getBody().getUserData() == "Door")
                || ((collider.getBody().getUserData() == "Door" && collidee.getBody().getUserData() == "Enemy")
                ||(collider.getBody().getUserData() == "Enemy" && collidee.getBody().getUserData() == "Door"))
        )
        {
            if (collider.getBody().getUserData() == "Door"){
                for (Room r : init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == collider.getBody()) {
                            d.open = false;

                            if (collidee.getUserData() == "PlayerBound") {
                                player.touchingDoor = false;
                                if (!d.locked) {
                                    soundController.playSound("DoorClose", 10, 10, 0.1f);
                                }
                            }
                        }
                    }
                }
            }
            else if (collidee.getBody().getUserData() == "Door"){
                for (Room r : init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == collider.getBody()) {
                            d.open = false;
                            if (collider.getUserData() == "PlayerBound") {
                                player.touchingDoor = false;
                                if (!d.locked) {
                                    soundController.playSound("DoorClose", 10, 10, 0.1f);
                                }
                            }
                        }
                    }
                }
            }

        }

        if (collider.getBody().getUserData() == "Door" && collidee.getUserData() == "PlayerBound"
                || (collider.getBody().getUserData() == "Door" && collidee.getBody().getUserData() == "Door")) {
            //player must be touching a room but not a door
            if (collidee.getUserData() == "PlayerBound" && player.touchingRoom && !player.touchingDoor) {
                if (player.currentRoom != 0) {
                    if (init.roomList.get(player.currentRoom).enemyCounter != 0) {
                        init.roomList.get(player.currentRoom).lockAllDoors(world, init.roomList.get(player.currentRoom), true);
                    }
                }
            }

            for (BossMinotaur b1 : init.roomList.get(player.currentRoom).bossMinotaurs) {
                b1.rayCastable = true;
                b1.enemyAI.setMaxLinearSpeed(b1.defaultSpeed);
                b1.stateMachine.changeState(BossMinotaurState.GO_TO_PLAYER);
                b1.active = true;
            }
        }

        if (colliderAsString.startsWith("Arrow")) {
            if (collideeAsString == "Fire") {
                for (Arrow a : arrows) {
                    if (a.arrowBody == collider.getBody()) {
                        for (Fire f : fires) {
                            if (collidee.getBody() == f.fireBody) {
                                if (f.active) {
                                    if (!a.onFire) {
                                        //a.onFire = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else if (collideeAsString.startsWith("Arrow")) {
            if (colliderAsString == "Fire") {
                for (Arrow a : arrows) {
                    if (a.arrowBody == collidee.getBody()) {
                        for (Fire f : fires) {
                            if (collider.getBody() == f.fireBody) {
                                if (f.active) {
                                    if (!a.onFire) {
                                        //a.onFire = true;
                                    }
                                }
                            }
                        }
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
