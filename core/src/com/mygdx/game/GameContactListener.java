package com.mygdx.game;

import com.badlogic.gdx.ai.StandaloneFileSystem;
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
        Fixture collider = contact.getFixtureA();
        Fixture collidee = contact.getFixtureB();

        String colliderStr = collider.getBody().getUserData().toString();
        String collideeStr = collidee.getBody().getUserData().toString();

        //System.out.println(fbAsString);

        //TODO: finish switch statement - ~40% done
        switch (colliderStr) {
            case "Skull":
                if (collideeStr == "Skull") {
                    System.out.println("WAWAWAWAWAWAWAWAWAWAWAWA");
                }
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
                if (collideeStr == "Arrow"){
                    if (!arrowBodiesCollided.contains(collidee.getBody())) {
                        arrowBodiesCollided.add(collidee.getBody());
                        break;
                    }
                }
                break;
            case "Fire":
                //System.out.println(faAsString + " " + fbAsString);
                if ((collidee.getUserData() != "Proximity" && collidee.getBody().getUserData() != "Enemy")
                        && collidee.getBody().getUserData() != "Player"
                        && collidee.getBody().getUserData() != "Bone"
                ) {
                    System.out.println("THIS IS A TEST FOR RESPAWNING ENEMIES" + collideeStr);
                    for (Fire f : fires) {
                        if (f.extinguish) {
                            if (f.fireBody == collider.getBody()) {

                                f.smoking = true;
                                f.extinguish = false;
                            }
                        }
                    }
                }
                break;
            case "Wall":
                if (collideeStr == "Arrow"){
                    if (!arrowBodiesCollided.contains(collidee.getBody())) {
                        arrowBodiesCollided.add(collidee.getBody());
                        break;
                    }
                }
                break;

            case "Arrow":
                //System.out.println(fa.getBody().getUserData() + " " + fb.getBody().getUserData());
                //System.out.println(fa.getUserData() + " " + fb.getUserData());

                if (((collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity")
                        || collidee.getBody().getUserData() == "Wall")) {
                    if (!arrowBodiesCollided.contains(collider.getBody())) {
                        arrowBodiesCollided.add(collider.getBody());
                    }
                    for (EnemySkull e : enemies) {
                        if (e.enemyAI.getBody() == collidee.getBody()) {
                            e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                            break;
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
                if (collidee.getBody().getUserData() == "Potion"){
                    for (Potion p : potions) {
                        if (p.potionBody == collidee.getBody()){
                            collectedPotions.add(p);
                        }
                    }
                } else if (collidee.getUserData() == "ShopRadius"){
                    for (Shopkeeper shop : shopkeepers){
                        if (collidee.getBody() == shop.shopBody){
                            shop.message = shop.messages.get(0);
                            shop.message.showing = true;
                        }
                    }
                }
                else if (collidee.getUserData() == "ShopSell"){
                    for (Shopkeeper shop : shopkeepers){
                        if (collidee.getBody() == shop.shopBody){
                            //shop.message = shop.messages.get(1);
                            //shop.message.showing = true;

                            shop.ListStock();

                            System.out.println("HEY THE SHOP SELL RADIUS WORKS");

                            /*
                            for (int i = 0; i < shop.inventory.size(); i++) {
                                shop.inventory.get(i).showing = true;
                                shop.inventory.get(i).fade = false;
                            }


                             */



                        }
                    }
                }
                break;
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
                            }
                        }
                    }
                }
            }
        }

        //bone branch needs to be revisited - faulty logic is causing bones not to get destroyed somewhere here
        //split into two if-statements
        if (((collider.getBody().getUserData() == "Player" && collidee.getBody().getUserData() == "Enemy")
                || (collider.getBody().getUserData() == "Enemy" && collidee.getBody().getUserData() == "Player"))
                || ((collider.getBody().getUserData() == "Player" && collidee.getBody().getUserData() == "Bone")
                || (collider.getBody().getUserData() == "Bone" && collidee.getBody().getUserData() == "Player"))
        ) {
            //if player enters enemy detection range, attack player
            if (collider.getUserData() == "Proximity" ||
                    collidee.getUserData() == "Proximity") {
                for (EnemySkull e : enemies) {
                    if (e.enemyBody == collider.getBody() || e.enemyBody == collidee.getBody()) {
                        e.playerInRange = true;
                       // e.getStateMachine().changeState(EnemyState.GO_TO_PLAYER);
                        //e.getStateMachine().changeState(EnemySkullState.GO_TO_PLAYER);
                    }
                }
            } else {
                hud.healthBar.LoseHealth(0.5f);
                if (collider.getBody().getUserData() == "Bone") {
                    player.playerBody.applyLinearImpulse(collider.getBody().getLinearVelocity().x * 100, collider.getBody().getLinearVelocity().y * 100, 0, 0, true);
                    if (!boneBodiesCollided.contains(collider.getBody())) {
                        boneBodiesCollided.add(collider.getBody());
                    }
                } else if (collidee.getBody().getUserData() == "Bone") {
                    player.playerBody.applyLinearImpulse(collidee.getBody().getLinearVelocity().x * 100, collidee.getBody().getLinearVelocity().y * 100, 0, 0, true);
                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                        boneBodiesCollided.add(collidee.getBody());
                    }
                    //TODO: fix
                } else if (collider.getBody().getUserData() == "Enemy") {
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
            }
        }

        if ((collider.getBody().getUserData() == "Bone" && collidee.getUserData() != "Proximity" && collidee.getBody().getUserData() != "Bone" && collidee.getBody().getUserData() != "Sword" && !collidee.getBody().getUserData().toString().startsWith("Arrow"))
                || (collidee.getBody().getUserData() == "Bone" && collider.getUserData() != "Proximity" && collider.getBody().getUserData() != "Bone" && collider.getBody().getUserData() != "Sword" && !collider.getBody().getUserData().toString().startsWith("Arrow"))
        ) {
            if ((((collider.getBody().getUserData() == "Enemy" && collider.getUserData() != "Proximity")
                    || collider.getBody().getUserData() == "Wall")
                    || collider.getBody().getUserData() == "Door")
                    && collidee.getBody().getUserData() == "Bone") {

                if (collider.getBody().getUserData() == "Door"){
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
                else if (collider.getBody().getUserData() == "Wall"){
                    if (!boneBodiesCollided.contains(collidee.getBody())) {
                        boneBodiesCollided.add(collidee.getBody());
                    }
                }

            } else if ((((collidee.getBody().getUserData() == "Enemy" && collidee.getUserData() != "Proximity")
                        || collidee.getBody().getUserData() == "Wall")
                        || collidee.getBody().getUserData() == "Door")
                        && collider.getBody().getUserData() == "Bone") {

                if (collidee.getBody().getUserData() == "Door"){
                    for (Room r : init.roomList) {
                        for (Door d : r.doors) {
                            if (d.doorBody == collidee.getBody()) {
                                if (!d.open) {
                                    if (!boneBodiesCollided.contains(collider.getBody())) {
                                        boneBodiesCollided.add(collider.getBody());
                                    }
                                }
                            }
                        }
                    }
                }
                else if (collider.getBody().getUserData() == "Wall"){
                    if (!boneBodiesCollided.contains(collider.getBody())) {
                        boneBodiesCollided.add(collider.getBody());
                    }
                }
            }
        }

        if (collider.getBody().getUserData().toString().startsWith("Room")) {
            // && (fb.getUserData() != "Wall" || fb.getUserData() !="Enemy" || fb.getUserData() != "Player"))

            if (collidee.getBody().getUserData() == "Player") {
                String[] roomIndexAsString = collider.getBody().getUserData().toString().split("-");
                player.currentRoom = Integer.parseInt(roomIndexAsString[1]);
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

        if(((collider.getBody().getUserData() == "Pot" && collidee.getBody().getUserData() == "Sword")
                || (collider.getBody().getUserData() == "Sword" && collidee.getBody().getUserData() == "Pot"))
                || ((collider.getBody().getUserData() == "Pot" && collidee.getBody().getUserData().toString().startsWith("Arrow"))
                || (collider.getBody().getUserData().toString().startsWith("Arrow") && collidee.getBody().getUserData() == "Pot"))){
            if (collidee.getBody().getUserData() == "Pot") {
                for (Pot p : pots) {
                    if (collidee.getBody() == p.potBody) {
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

        if ((collider.getBody().getUserData().toString().startsWith("Arrow") && collidee.getBody().getUserData() == "Enemy")
                || (collider.getBody().getUserData() == "Enemy" && collidee.getBody().getUserData().toString().startsWith("Arrow"))
                ||
                ((collider.getBody().getUserData() == "Sword" && collidee.getBody().getUserData() == "Enemy")
                || (collider.getBody().getUserData() == "Enemy" && collidee.getBody().getUserData() == "Sword"))
        ) {
            if (collider.getUserData() != "Proximity" &&
                    collidee.getUserData() != "Proximity") {
                for (EnemySkull e : enemies) {
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
                        System.out.println(e.enemyBody.getLinearVelocity());

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(collider.getBody())) {
                                //arrowBodiesCollided.add(fa.getBody());
                                deadEnemyBodies.add(collider.getBody());
                            }
                            enemySkulls.add(new Skull(world, collider.getBody().getPosition().x, collider.getBody().getPosition().y));
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

                        if (e.ENEMY_HEALTH < 1) {
                            if (!deadEnemyBodies.contains(collidee.getBody())) {
                                deadEnemyBodies.add(collidee.getBody());
                            }
                            enemySkulls.add(new Skull(world, collidee.getBody().getPosition().x, collidee.getBody().getPosition().y));
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
        if (((collider.getBody().getUserData().toString().startsWith("Arrow") && collidee.getBody().getUserData() == "Skull")
            || (collider.getBody().getUserData() == "Skull" && collidee.getBody().getUserData().toString().startsWith("Arrow")))
            || ((collider.getBody().getUserData() == "Sword" && collidee.getBody().getUserData() == "Skull")
            || (collider.getBody().getUserData() == "Skull" && collidee.getBody().getUserData() == "Sword"))
        ) {
            if (collidee.getBody().getUserData() == "Skull") {
                for (Skull s : enemySkulls) {
                    if (collidee.getBody() == s.skullBody && !s.skullIFrame) {
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

        switch (faAsString) {
            case "Player":
                if (fb.getUserData() == "ShopSell") {
                    for (Shopkeeper s : shopkeepers) {
                        System.out.println("HEY THIS WORKS");
                        s.HideStock();
                    }
                }
                break;
        }

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
