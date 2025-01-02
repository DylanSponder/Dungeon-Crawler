package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.OrderedMap;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Arrow;
import com.mygdx.game.entity.behaviours.fsm.*;
import com.mygdx.game.entity.behaviours.fsm.drops.Skull;
import com.mygdx.game.level.objects.*;

import static com.mygdx.game.DungeonCrawler.*;
import static com.mygdx.game.level.GenerateLevel.init;

public class GameContactListener implements ContactListener {
    //there is a lot of lazy branches here - most collisions only need to be handled once
    // (static bodies like walls will never be the object that is colliding with a dynamic body for instance)
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

                                    soundController.playSound("PotSmash",5f,4f,0.1f);
                                    brokenPots.add(p);
                                }
                            } else {

                                    soundController.playSound("PotSmash",5f,4f,0.1f);
                                    p.POT_HEALTH--;
                                    p.POT_HEALTH--;
                                    brokenPots.add(p);
                            }
                        }
                    }
                }
            }
        }

        if ((colliderStr.startsWith("Arrow") && collideeStr == "Enemy")
                || (colliderStr == "Enemy" && collideeStr.startsWith("Arrow"))
                ||
                ((colliderStr == "Sword" && collideeStr == "Enemy")
                        || (colliderStr == "Enemy" && collideeStr == "Sword"))
        ) {
            if (collider.getUserData() != "Proximity" &&
                    collidee.getUserData() != "Proximity") {
                for (Enemy e : enemies) {
                    if (e.enemyBody == collider.getBody()) {

                        String fbData = collidee.getUserData().toString();
                        float velX = e.enemyBody.getLinearVelocity().x;
                        float velY = e.enemyBody.getLinearVelocity().y;
                        switch (fbData) {
                            case "DownSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY - 50);
                                break;
                            case "UpSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY + 50);
                                break;
                            case "LeftSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX - 50, velY);
                                break;
                            case "RightSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX + 50, velY);
                                break;
                            case "DownArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY - 85);
                                break;
                            case "UpArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY + 85);
                                break;
                            case "LeftArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX - 85, velY);
                                break;
                            case "RightArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX + 85, velY);
                                break;
                            default:
                                break;
                        }
                        //e.enemyBody.applyForceToCenter(0,0, true);
                        e.enemyBody.setLinearVelocity(0, 0);

                        if (colliderStr.startsWith("Arrow") || collideeStr.startsWith("Arrow")) {
                            soundController.playSound("ArrowHit", 10f,8f,0.1f);
                        }



                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(e.enemyBody)) {
                                //arrowBodiesCollided.add(fa.getBody());



                                System.out.println(e.enemyID);

                                if (e.enemyID == 1){
                                    for (EnemySkull skull2 : enemySkulls) {
                                        if (skull2.enemyBody == collider.getBody()) {
                                            Skull skull = new Skull(world, collider.getBody().getPosition().x, collider.getBody().getPosition().y);

                                            if (e.inRespawnRange) {
                                                skull.resurrectable = true;
                                            }

                                            skulls.add(skull);
                                            deadEnemyBodies.add(collider.getBody());
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
                                        if (ghost.enemyBody == collidee.getBody()) {
                                            deadEnemyBodies.add(collidee.getBody());
                                            soundController.playSound("GhostDeath",8.5f,7.5f,0.1f);
                                            dyingGhosts.add(ghost);
                                        }
                                    }
                                }
                            }

                            //skullArrayMap.put();


                            hud.updateGold(1, true);

                            init.roomList.get(e.room).enemyCounter--;
                            if (init.roomList.get(e.room).enemyCounter < 1) {
                                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
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
                        switch (faData) {
                            case "DownSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY - 50);
                                break;
                            case "UpSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX, velY + 50);
                                break;
                            case "LeftSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX - 50, velY);
                                break;
                            case "RightSword":
                                e.ENEMY_HEALTH = e.ENEMY_HEALTH - 2;
                                e.enemyBody.setLinearVelocity(velX + 50, velY);
                                break;
                            case "DownArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY - 85);
                                break;
                            case "UpArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX, velY + 85);
                                break;
                            case "LeftArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX - 85, velY);
                                break;
                            case "RightArrow":
                                e.ENEMY_HEALTH--;
                                e.enemyBody.setLinearVelocity(velX + 85, velY);
                                break;
                            default:
                                break;
                        }

                        if (colliderStr.startsWith("Arrow") || collideeStr.startsWith("Arrow")) {
                            soundController.playSound("ArrowHit", 10f,8f,0.1f);
                        }

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(e.enemyBody)) {

                                System.out.println(e.enemyID);

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
                            }

                            hud.updateGold(1, true);
                            init.roomList.get(e.room).enemyCounter--;
                            if (init.roomList.get(e.room).enemyCounter < 1) {
                                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
                                player.roomCleared = true;
                                //DungeonCrawler.roomClear.play();
                                //DungeonCrawler.roomClear.dispose();
                            }
                            break;
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
                        }
                    }
                }
            }
        }

        if (((colliderStr == "Bone" || collideeStr == "Web") && collidee.getUserData() != "Enemy" && collideeStr != "Bone" && collideeStr != "Sword" && !collideeStr.startsWith("Arrow") && !collideeStr.startsWith("Room"))
                || ((collideeStr == "Bone" || collideeStr == "Web") && collider.getUserData() != "Enemy" && colliderStr != "Bone" && colliderStr != "Sword" && !colliderStr.startsWith("Arrow") && !colliderStr.startsWith("Room"))
        ) {

            if ((((collideeStr == "Wall")
                    || collideeStr == "Shield")
                    || collideeStr =="Door")
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
                                        if (!webBodiesCollected.contains(collider.getBody())) {
                                            webBodiesCollected.add(collider.getBody());
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

                    if (!webBodiesCollected.contains(collidee.getBody())) {
                        webBodiesCollected.add(collidee.getBody());
                    } else {

                    }
                } else if (colliderStr == "Door") {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collider.getBody()) {
                                if (!d.open) {
                                    if (!webBodiesCollected.contains(collidee.getBody())) {
                                        webBodiesCollected.add(collidee.getBody());
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
                    player.playerBody.applyLinearImpulse(collider.getBody().getLinearVelocity().x * 500, collider.getBody().getLinearVelocity().y * 500, 0, 0, true);
                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                        boneBodiesCollided.add(collidee.getBody());
                    }
                }

            } else if (colliderStr == "Bone" && collideeStr == "Player") {
                hud.healthBar.loseHealth(0.5f);
                player.playerBody.applyLinearImpulse(collidee.getBody().getLinearVelocity().x * 500, collidee.getBody().getLinearVelocity().y * 500, 0, 0, true);
                if (!boneBodiesCollided.contains(collider.getBody())) {
                    boneBodiesCollided.add(collider.getBody());
                }
            }
            else if (collideeStr == "Web" && colliderStr == "Player") {
               // hud.healthBar.LoseHealth(0.5f);
                if (collidee.getBody().getUserData() == "Bone") {
                    player.playerBody.applyLinearImpulse(collider.getBody().getLinearVelocity().x * 500, collider.getBody().getLinearVelocity().y * 500, 0, 0, true);
                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                        boneBodiesCollided.add(collidee.getBody());
                    }
                }

            } else if (colliderStr == "Web" && collideeStr == "Player") {
              //  hud.healthBar.LoseHealth(0.5f);
                player.playerBody.applyLinearImpulse(collidee.getBody().getLinearVelocity().x * 500, collidee.getBody().getLinearVelocity().y * 500, 0, 0, true);
                if (!boneBodiesCollided.contains(collider.getBody())) {
                    boneBodiesCollided.add(collider.getBody());
                }
            }
                           /*else if (collider.getBody().getUserData() == "Enemy") {
                    player.playerBody.applyLinearImpulse(collider.getBody().getLinearVelocity().x * 50, collider.getBody().getLinearVelocity().y * 50, 0, 0, true);
                    collider.getBody().applyLinearImpulse(-collidee.getBody().getLinearVelocity().x * 2, -collidee.getBody().getLinearVelocity().y * 2, 0, 0, true);

                } else if (collidee.getBody().getUserData() == "Enemy") {
                    player.playerBody.applyLinearImpulse(collidee.getBody().getLinearVelocity().x * 50, collidee.getBody().getLinearVelocity().y * 50, 0, 0, true);

                    if (collider.getBody().getLinearVelocity().x < 10 && collider.getBody().getLinearVelocity().y < 10) {
                        collider.getBody().applyLinearImpulse(-collidee.getBody().getLinearVelocity().x, -collidee.getBody().getLinearVelocity().y + 150, 0, 0, true);
                    } else {
                        collider.getBody().applyLinearImpulse(-collidee.getBody().getLinearVelocity().x + 150, -collidee.getBody().getLinearVelocity().y + 150, 0, 0, true);
                        }
                    }

                 */
        }


            //bone branch needs to be revisited - faulty logic is causing bones not to get destroyed somewhere here
            //split into two if-statements
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
                            System.out.println(colliderStr + " " + collideeStr);
                            e.inRespawnRange = true;
                        }
                    }
                }
            }
        } else if (collidee.getUserData() == "Spawner") {

          //  System.out.println("in range");
            if (colliderStr == "Enemy") {
                if (collider.getUserData() != "Proximity") {
                  //  System.out.println("not sensor");
                    for (EnemySkull e : enemySkulls) {
                        if (e.enemyBody == collider.getBody()) {
                            System.out.println(colliderStr + " " + collideeStr);
                            e.inRespawnRange = true;
                        }
                    }
                }
            }
        }




            //TODO: finish switch statement - ~40% done
            switch (colliderStr) {

                case "TrapArea":
                    if (collidee.getUserData() == "PlayerBound") {
                        for (Trap tr : traps) {
                            if (tr.trapArea == collider.getBody()) {
                                if (!tr.active) {
                                    tr.fireArrow(tr.trapX, tr.trapY);
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
                /*
                if (fbAsString == "Skull") {
                    //TODO Yeah this doesn't work. Bodies don't actually collide with other bodies when they are added to the world
                    //TODO Use b2::Contact instead
                    // check for coordinates instead
                    if (!deadEnemyBodies.contains(fa.getBody())) {
                        //arrowBodiesCollided.add(fa.getBody());
                        deadEnemyBodies.add(fa.getBody());
                    }
                    if (!deadEnemyBodies.contains(fb.getBody())) {
                        //arrowBodiesCollided.add(fa.getBody());
                        deadEnemyBodies.add(fb.getBody());
                    }

                    //SkullPile.createSkullPile

                }
                */
                    break;
                case "Column":
                    if (collideeStr.startsWith("Arrow")) {
                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                            arrowBodiesCollided.add(collidee.getBody());
                            break;
                        }
                    }
                    break;
                case "Fire":

                    if (collideeStr.startsWith("Arrow")) {
                        for (Fire f : fires) {
                            if (f.fireBody == collider.getBody()) {
                                if (f.extinguish && f.type == 1) {
                                    f.smoking = true;
                                    f.extinguish = false;
                                    for (Arrow a : arrows) {
                                        if (a.arrowBody == collidee.getBody()) {
                                            if (a.onFire) {

                                                if (!f.active) {
                                                  //  f.active = true;
                                                }
                                            } else {
                                                a.onFire = true;
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
                                if (f.extinguish && f.type == 1) {
                                    f.smoking = true;
                                    f.extinguish = false;
                                }
                            }
                        }
                    }
                    /*else if ((collidee.getUserData() != "Proximity" && collideeStr != "Enemy")
                            && collideeStr != "Player"
                            && collidee.getUserData() != "Spawner"
                            && collideeStr != "Bone"
                    ) {

                    }

                     */
                    break;
                case "Wall":
                    if (collideeStr == "Arrow") {
                        if (!arrowBodiesCollided.contains(collidee.getBody())) {
                            arrowBodiesCollided.add(collidee.getBody());
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

                    if ((((collideeStr == "Enemy" && collidee.getUserData() != "Proximity") && collideeStr != "Cobweb")
                            || collideeStr == "Wall")) {
                        if (!arrowBodiesCollided.contains(collider.getBody())) {
                            arrowBodiesCollided.add(collider.getBody());
                        }
                        if (collidee.getUserData() == "EnemySkull") {
                            for (EnemySkull e : enemySkulls) {
                                if (e.enemyAI.getBody() == collidee.getBody()) {
                                    e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        } else if (collidee.getUserData() == "EnemySpider") {
                            for (EnemySpider e2 : enemySpiders) {
                                if (e2.enemyAI.getBody() == collidee.getBody()) {
                                    e2.getStateMachine().changeState(EnemySpiderState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        } else if (collidee.getUserData() == "EnemyGhost") {
                            for (EnemyGhost e3 : enemyGhosts) {
                                if (e3.enemyAI.getBody() == collidee.getBody()) {
                                    e3.getStateMachine().changeState(EnemyGhostState.GO_TO_PLAYER);
                                    break;
                                }
                            }
                        }

                        //TODO COPY FURTHER ENEMY CODE HERE


                    } else if (collideeStr == "Fire") {
                        for (Fire f : fires) {
                            if (f.fireBody == collidee.getBody()) {
                                if (f.extinguish && f.type == 1) {
                                    f.smoking = true;
                                    f.extinguish = false;
                                    for (Arrow a : arrows) {
                                        if (a.arrowBody == collider.getBody()) {
                                            if (a.onFire) {

                                                if (!f.active) {
                                                    f.active = true;
                                                }
                                            } else {
                                                a.onFire = true;
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
                                if (f.extinguish && f.type == 1) {
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
                        webBodiesCollected.add(collidee.getBody());
                        break;
                    }

                    if ((collideeStr == "Cobweb")) {
                        DungeonCrawler.PLAYER_SPEED_MULTI = 15f;
                        player.touchingCobweb = true;
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



                    if (collidee.getBody().getUserData() == "Potion") {
                        for (Potion p : potions) {
                            if (p.potionBody == collidee.getBody()) {
                                if (!(hud.inventory.Capacity == hud.inventory.Size)) {
                                    collectedPotions.add(p);

                                } else {


                                }

                            }
                        }
                    } else if (collidee.getUserData() == "ShopRadius") {
                        for (Shopkeeper shop : shopkeepers) {
                            if (collidee.getBody() == shop.shopBody) {
                                shop.message = shop.messages.get(0);
                                shop.message.showing = true;
                            }
                        }
                    } else if (collidee.getUserData() == "ShopSell") {
                        for (Shopkeeper shop : shopkeepers) {
                            if (collidee.getBody() == shop.shopBody) {
                                //shop.message = shop.messages.get(1);
                                //shop.message.showing = true;

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

                case "TrapArea":
                    if (collider.getUserData() == "PlayerBound") {
                        for (Trap tr : traps) {
                            if (tr.trapArea == collidee.getBody()) {
                                if (!tr.active) {
                                    tr.fireArrow(tr.trapX,tr.trapY);
                                }
                            }
                        }
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
                            if (f.extinguish && f.type == 1) {
                                f.smoking = true;
                                f.extinguish = false;
                                for (Arrow a : arrows) {
                                    if (a.arrowBody == collidee.getBody()) {
                                        if (a.onFire) {

                                            if (!f.active) {
                                                f.active = true;
                                            }
                                        } else {
                                            a.onFire = true;
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

            if ((collider.getBody().getUserData() == "Door" && collidee.getBody().getUserData() == "Player")
                    || (collider.getBody().getUserData() == "Player" && collidee.getBody().getUserData() == "Door")
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
                                    if (collideeStr == "Player") {
                                        System.out.println("TEST");
                                        player.touchingDoor = true;

                                    }
                                }
                            }
                        }
                    }
                }
                if (collidee.getBody().getUserData() == "Door"
                        && (collider.getUserData() != "Proximity")) {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collider.getBody()) {
                                if (!d.locked) {
                                    d.open = true;
                                    if (colliderStr == "Player") {
                                        player.touchingDoor = true;

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
                    for (EnemySkull e : init.roomList.get(player.currentRoom).enemySkulls) {
                        e.rayCastable = true;
                    }
                    for (EnemySpider e2 : init.roomList.get(player.currentRoom).enemySpiders) {
                        e2.rayCastable = true;
                    }
                    for (EnemyGhost e3 : init.roomList.get(player.currentRoom).enemyGhosts) {
                        e3.rayCastable = true;
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
            /*
        } else if (fb.getBody().getUserData().toString().startsWith("Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))
            String[] roomIndexAsString = fb.getBody().getUserData().toString().split("-");
            player.currentRoom = Integer.parseInt(roomIndexAsString[1]);

            if (fa.getBody().getUserData() == "Player") {
                player.touchingRoom = true;
            }
            if (init.roomList.get(player.currentRoom).isShop) {
                init.roomList.get(player.currentRoom).enemyCounter = 0;
                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
            }

             */
            }

        }


    @Override
    public void endContact(Contact contact) {
        Fixture collider = contact.getFixtureA();
        Fixture collidee = contact.getFixtureB();

        String colliderAsString = collider.getBody().getUserData().toString();
        String collideeAsString = collidee.getBody().getUserData().toString();

        switch (colliderAsString) {
            case "Cobweb":
                if (collider.getUserData() == "PlayerBound") {
                    DungeonCrawler.PLAYER_SPEED_MULTI = 60f;
                    player.touchingCobweb = false;
                    break;
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
            case "Cobweb":
                if (collider.getUserData() == "PlayerBound") {
                    DungeonCrawler.PLAYER_SPEED_MULTI = 60f;
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

                    for (EnemySkull e : enemySkulls){
                        if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                            e.playerInRange = false;
                            e.getStateMachine().changeState(EnemySkullState.WANDER);
                        }
                    }

                    for (EnemySpider e : enemySpiders){
                        if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                            e.playerInRange = false;
                            e.getStateMachine().changeState(EnemySpiderState.WANDER);
                        }
                    }
                for (EnemyGhost e : enemyGhosts){
                    if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()){
                        e.playerInRange = false;
                        e.getStateMachine().changeState(EnemyGhostState.WANDER);
                    }
                }
                }
            }

        if (collider.getBody().getUserData().toString().startsWith("Room")) {



            if (collidee.getUserData() == "PlayerBound") {

                System.out.println(player.currentRoom);

                for (Roof r : init.roomList.get(player.currentRoom).roofs) {
                   // r.visible = true;
                }
                player.touchingRoom = false;
                if (player.currentRoom <= 9){ //TODO: Check for level progress before adding to current room
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
                            }
                        }
                    }
                }
            }
            if (collidee.getBody().getUserData() == "Door"){
                for (Room r : init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == collider.getBody()) {
                            d.open = false;
                            if (collider.getUserData() == "PlayerBound") {
                                player.touchingDoor = false;
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
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
