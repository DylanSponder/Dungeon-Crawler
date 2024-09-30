package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entity.behaviours.fsm.EnemySkull;
import com.mygdx.game.entity.behaviours.fsm.EnemySkullState;
import com.mygdx.game.entity.Skull;
import com.mygdx.game.entity.behaviours.fsm.Shopkeeper;
import com.mygdx.game.level.objects.*;

import static com.mygdx.game.DungeonCrawler.*;
import static com.mygdx.game.level.GenerateLevel.init;

public class GameContactListener implements ContactListener {
    //there is a lot of lazy branches here - most collisions only need to be handled once
    // (static bodies like walls will never be the object that is colliding with a dynamic body for instance)
    // many redundancies should be re-written and if-statements made into switches

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        String faAsString = fa.getBody().getUserData().toString();
        String fbAsString = fb.getBody().getUserData().toString();

        //System.out.println(fbAsString);

        //TODO: finish switch statement - ~40% done
        switch (faAsString) {
            case "Skull":
                /*
                if (fbAsString == "Skull") {
                    //TODO Yeah this doesn't work. Bodies don't actually collide with other bodies when they are added to the world
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
                break;
            case "Fire":
                //System.out.println(faAsString + " " + fbAsString);
                if ((fb.getUserData() != "Proximity" && fb.getBody().getUserData() != "Enemy")
                        && fb.getBody().getUserData() != "Player") {
                    for (Fire f : fires) {
                        if (f.extinguish) {
                            if (f.fireBody == fa.getBody()) {

                                f.smoking = true;
                                f.extinguish = false;
                            }
                        }
                    }
                }
                break;
            case "Wall":
                if (fbAsString == "Arrow"){
                    if (!arrowBodiesCollided.contains(fb.getBody())) {
                        arrowBodiesCollided.add(fb.getBody());
                        break;
                    }
                }
                break;

            case "Arrow":
                //System.out.println(fa.getBody().getUserData() + " " + fb.getBody().getUserData());
                //System.out.println(fa.getUserData() + " " + fb.getUserData());

                if (((fb.getBody().getUserData() == "Enemy" && fb.getUserData() != "Proximity")
                        || fb.getBody().getUserData() == "Wall")) {
                    if (!arrowBodiesCollided.contains(fa.getBody())) {
                        arrowBodiesCollided.add(fa.getBody());
                    }
                    for (EnemySkull e : enemies) {
                        if (e.enemyBody == fb.getBody()) {
                            e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                            break;
                        }
                    }
                }
                break;
            case "Door":
                if (fbAsString == "Arrow") {
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == fa.getBody()) {
                                if (!d.open) {
                                    if (!arrowBodiesCollided.contains(fb.getBody())) {
                                        arrowBodiesCollided.add(fb.getBody());
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
                //fb.getBody().getUserData() == "Player"
                //fb.getBody().getUserData() == "Enemy"
                break;
            case "Sword":
                break;
            case "Enemy":
                /*
                if (fb.getBody().getUserData() == "Arrow") {
                    for (EnemySkull e : enemies) {
                        if (e.enemyBody == fa.getBody()) {
                            e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                            break;
                        }
                        if (!arrowBodiesCollided.contains(fb.getBody())) {
                            arrowBodiesCollided.add(fb.getBody());
                            break;
                        }

                    }

                }
                break;

                 */
                break;
            case "Player":
                if (fb.getBody().getUserData() == "Potion"){
                    for (Potion p : potions) {
                        if (p.potionBody == fb.getBody()){
                            collectedPotions.add(p);
                        }
                    }
                } else if (fb.getUserData() == "ShopRadius"){
                    for (Shopkeeper shop : shopkeepers){
                        if (fb.getBody() == shop.shopBody){
                            shop.shopMessage = shop.shopMessages.get(0);
                            shop.shopMessage.showing = true;
                        }
                    }
                }
                else if (fb.getUserData() == "ShopSell"){
                    for (Shopkeeper shop : shopkeepers){
                        if (fb.getBody() == shop.shopBody){
                            shop.shopMessage = shop.shopMessages.get(1);
                            shop.shopMessage.showing = true;
                        }
                    }
                }
                break;
        }

        if ((fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Player")
                || (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Door")
                || ((fa.getBody().getUserData() == "Door" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Door"))
        ) {
            if (fa.getBody().getUserData() == "Door"
                    && (fb.getUserData() != "Proximity")) {
                for (Room r : init.roomList) {
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
                for (Room r : init.roomList) {
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
        //split into two if-statements
        if (((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                || (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player"))
                || ((fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Bone")
                || (fa.getBody().getUserData() == "Bone" && fb.getBody().getUserData() == "Player"))
        ) {
            //if player enters enemy detection range, attack player
            if (fa.getUserData() == "Proximity" ||
                    fb.getUserData() == "Proximity") {
                for (EnemySkull e : enemies) {
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()) {
                        e.playerInRange = true;
                       // e.getStateMachine().changeState(EnemyState.GO_TO_PLAYER);
                        //e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
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
                    for (Room r : init.roomList) {
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
                else if (fa.getBody().getUserData() == "Wall"){
                    if (!boneBodiesCollided.contains(fb.getBody())) {
                        boneBodiesCollided.add(fb.getBody());
                    }
                }

            } else if ((((fb.getBody().getUserData() == "Enemy" && fb.getUserData() != "Proximity")
                        || fb.getBody().getUserData() == "Wall")
                        || fb.getBody().getUserData() == "Door")
                        && fa.getBody().getUserData() == "Bone") {

                if (fb.getBody().getUserData() == "Door"){
                    for (Room r : init.roomList) {
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
                else if (fa.getBody().getUserData() == "Wall"){
                    if (!boneBodiesCollided.contains(fa.getBody())) {
                        boneBodiesCollided.add(fa.getBody());
                    }
                }
            }
        }

        if (fa.getBody().getUserData().toString().startsWith("Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))
            String[] roomIndexAsString = fa.getBody().getUserData().toString().split("-");
            player.currentRoom = Integer.parseInt(roomIndexAsString[1]);
            if (fb.getBody().getUserData() == "Player") {
                for (EnemySkull e : init.roomList.get(player.currentRoom).enemies) {
                    e.rayCastable = true;
                }
                player.touchingRoom = true;
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
                for (EnemySkull e : enemies) {
                    if (e.enemyBody == fa.getBody()) {

                        String fbData = fb.getUserData().toString();
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
                        System.out.println(e.enemyBody.getLinearVelocity());

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(fa.getBody())) {
                                //arrowBodiesCollided.add(fa.getBody());
                                deadEnemyBodies.add(fa.getBody());
                            }
                            enemySkulls.add(new Skull(world, fa.getBody().getPosition().x, fa.getBody().getPosition().y));
                            //skullArrayMap.put();
                            e.getStateMachine().changeState(EnemySkullState.DIE);
                            hud.updateGold(1);

                            init.roomList.get(e.room).enemyCounter--;
                            if (init.roomList.get(e.room).enemyCounter < 1) {
                                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
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

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(fb.getBody())) {
                                deadEnemyBodies.add(fb.getBody());
                            }
                            enemySkulls.add(new Skull(world, fb.getBody().getPosition().x, fb.getBody().getPosition().y));
                            e.getStateMachine().changeState(EnemySkullState.DIE);
                            hud.updateGold(1);
                            init.roomList.get(e.room).enemyCounter--;
                            if (init.roomList.get(e.room).enemyCounter < 1) {
                                init.roomList.get(player.currentRoom).unlockAllDoors(world, init.roomList.get(player.currentRoom), false);
                                //DungeonCrawler.roomClear.play();
                                //DungeonCrawler.roomClear.dispose();
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

        String faAsString = fa.getBody().getUserData().toString();
        String fbAsString = fb.getBody().getUserData().toString();

        if (    (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Enemy")
                ||(fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "Player")
        ){
            if  (fa.getUserData() == "Proximity"||
                    fb.getUserData() == "Proximity"){
                for (EnemySkull e : enemies){
                    if (e.enemyBody == fa.getBody() || e.enemyBody == fb.getBody()){
                        e.playerInRange = false;
                        e.getStateMachine().changeState(EnemySkullState.WANDER);
                    }
                }
            }
        }

        if (fa.getBody().getUserData().toString().startsWith("Room")) {
            if (fb.getBody().getUserData() == "Player") {
                player.touchingRoom = false;
                if (player.currentRoom <= 9){
                        init.roomList.get(player.currentRoom).unlockDoor(world, init.roomList.get(player.currentRoom+1),false);
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
                for (Room r : init.roomList) {
                    for (Door d : r.doors) {
                        if (d.doorBody == fa.getBody()) {
                            d.open = false;
                        }
                    }
                }

            }
            if (fb.getBody().getUserData() == "Door"){
                for (Room r : init.roomList) {
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