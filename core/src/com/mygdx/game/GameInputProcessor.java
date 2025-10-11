package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Arrow;
import com.mygdx.game.level.GenerateLevel;
import com.mygdx.game.level.objects.Potion;
import com.mygdx.game.level.objects.Text;

import static com.mygdx.game.DungeonCrawler.*;


//the game input processor is used to manage all inputs for the game
public class GameInputProcessor implements InputProcessor {

    public CreateAssets tx;
    public BodyFactory bf;
    public float playerRangedAttackSpeedInSeconds = 0.6f,  playerMeleeAttackSpeedInSeconds = 0.5f;
    public float attackCooldown = 0.6f;
    public boolean canAttack = true;
        @Override
        public boolean scrolled(float amountX, float amountY) {
        if (DungeonCrawler.debug) {
            //System.out.println(camera.zoom);

            if ((camera.zoom >= 0.1f && camera.zoom <= 24f)) {
                if (camera.zoom == 24f) {
                    if (amountY < 0f) {
                        camera.zoom += amountY * 0.1f;
                    }
                } else if (camera.zoom == 0.1f) {
                    if (amountY > 0f) {
                        camera.zoom += amountY * 0.1f;
                    }
                } else {
                    camera.zoom += amountY * 0.1f;
                }
            } else if (camera.zoom > 24f) {
                camera.zoom = 24f;
            } else if (camera.zoom < 0.1f) {
                camera.zoom = 0.1f;
            }
        }
        return true;
    }

        public boolean touchDown(int x, int y, int pointer, int button) {
            tx = CreateAssets.getInstance();
            bf = new BodyFactory();

        if (button == 0 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking) && canAttack) {
            //if player presses left mouse attack with the swordBody

            playerMeleeAttacking = true;

            if (moveDown || player.facing == 3) {
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("DownSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(0,-100000,0,0,true);
            } else if (moveUp || player.facing == 1) {
                tx.playerTextureRegion = tx.playerAttackUp;
                tx.playerHead = tx.playerHeadUp;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, 17.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("UpSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(0,100000,0,0,true);
            } else if (moveLeft || player.facing == 4) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                tx.playerHead = tx.playerHeadLeft;
                swordBody = bf.createSwordBody(world, player.playerBody, -15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("LeftSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(-100000,0,0,0,true);
            } else if (moveRight || player.facing == 2) {
                tx.playerTextureRegion = tx.playerAttackRight;
                tx.playerHead = tx.playerHeadRight;
                swordBody = bf.createSwordBody(world, player.playerBody, 15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("RightSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(100000,0,0,0,true);
            } else {
                player.facing = 3;
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("DownSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(0,-100000,0,0,true);
            }

            swordBody.setUserData("Sword");

            //pause player in place while attacking (attacks must be timed correctly!)
            playerPaused = true;
            PLAYER_HORIZONTAL_SPEED = 0;
            PLAYER_VERTICAL_SPEED = 0;
            player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

            boolean swing = Random.randomBoolean();

            if (swing) {
                soundController.playSound("SwordSwing1", 8,6,0.1f);
            } else {
                soundController.playSound("SwordSwing2", 8,6,0.2f);
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    swordBody.destroyFixture(swordHitbox);

                }
            }, 0.2f);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;

                    //reset playerSprite to before the attack input
                    if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
                        tx.playerTextureRegion = tx.playerDown;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                    }

                    playerMeleeAttacking = false;
                }
            }, playerMeleeAttackSpeedInSeconds);
            attackCooldown();
        }

        //if player presses right mouse attack with a bow
        if (button == 1 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking) && canAttack) {

            playerRangedAttacking = true;

            arrowBody = null;

            if (moveDown || player.facing == 3) {
                playerDirection = "Down";
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("DownArrow");
                arrowBody.setLinearVelocity(0, -400f);
                player.playerBody.applyForce(0,150000,0,0,true);
            } else if (moveUp || player.facing == 1) {
                playerDirection = "Up";
                tx.playerTextureRegion = tx.playerAttackUp;
                tx.playerHead = tx.playerHeadUp;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("UpArrow");
                arrowBody.setLinearVelocity(0, 400f);
                player.playerBody.applyForce(0,-150000,0,0,true);
            } else if (moveLeft || player.facing == 4) {
                playerDirection = "Left";
                tx.playerTextureRegion = tx.playerAttackLeft;
                tx.playerHead = tx.playerHeadLeft;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
                arrowHitbox.setUserData("LeftArrow");
                arrowBody.setLinearVelocity(-400f, 0);
                player.playerBody.applyForce(150000,0,0,0,true);
            } else if (moveRight || player.facing == 2) {
                playerDirection = "Right";
                tx.playerTextureRegion = tx.playerAttackRight;
                tx.playerHead = tx.playerHeadRight;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x + 14f, player.playerBody.getPosition().y+1);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
                arrowHitbox.setUserData("RightArrow");
                arrowBody.setLinearVelocity(400f, 0);
                player.playerBody.applyForce(-150000,0,0,0,true);
            }
            //only triggers if the player hasn't moved at all yet - player starts facing down
            else {
                playerDirection = "Down";
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("DownArrow");
                arrowBody.setLinearVelocity(0, -400f);
                player.playerBody.applyForce(0,150000,0,0,true);
            }
            //pause player in place while attacking (attacks must be timed correctly!)
            arrowBody.setUserData("Arrow");
            if (player.hasGreekFire){
                arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, true));
                //player.greekFireUses--;
            } else {
                arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, false));
            }
            arrowArrayMap.put(arrowBody, arrow);

            soundController.playSound("BowAttack3", 10,6,0.1f);

            playerPaused = true;
            PLAYER_HORIZONTAL_SPEED = 0;
            PLAYER_VERTICAL_SPEED = 0;
            player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;
                    //reset playerSprite to before the attack input
                    if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
                        tx.playerTextureRegion = tx.playerDown;
                        tx.playerHead = tx.playerHeadDown;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                        tx.playerHead = tx.playerHeadUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                        tx.playerHead = tx.playerHeadLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                        tx.playerHead = tx.playerHeadRight;
                    }
                    playerRangedAttacking = false;
                }
            }, playerRangedAttackSpeedInSeconds);
            attackCooldown();
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    public boolean keyDown(int keycode) {

        tx = CreateAssets.getInstance();
        bf = new BodyFactory();

        if (debug) {

            if (keycode == Input.Keys.NUM_1) {
                rayHandler.setAmbientLight(0f, 0f, 0f, 1f);
            }
            if (keycode == Input.Keys.NUM_2) {
                rayHandler.setAmbientLight(0f, 0f, 0f, 0.025f);
            }
            if (keycode == Input.Keys.NUM_3) {
                hud.updateGold(20,true);
            }
            if (keycode == Input.Keys.NUM_4) {
                DungeonCrawler.PLAYER_SPEED_MULTI = PLAYER_DEFAULT_SPEED + 100;
            }
            if (keycode == Input.Keys.NUM_5) {
                DungeonCrawler.PLAYER_SPEED_MULTI = PLAYER_DEFAULT_SPEED;
            }
            if (keycode == Input.Keys.NUM_9) {
                hud.inventory.addPotion();
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && optionsMenuClosed) {
            pauseMenuClosed = false;
            Gdx.input.setInputProcessor(pauseMenuStage);
           // Gdx.graphics.setWindowedMode(1800, 1000);
        }



        if (player.buyingStock){

            if (keycode == Input.Keys.NUM_1) {
                ShopItem item = (ShopItem) player.shopkeeper.inventory.get(0);

                int money = Integer.parseInt(hud.totalGoldAsString);

                if (money >= item.cost && !item.purchased) {

                    item.purchased = true;

                    soundController.playSound("Buy",10,10,0.1f);

                    switch (item.kind) {

                        case "WINE": {
                            Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
                            potion.createPotion(potionArrayMap,rayHandler);
                            potions.add(potion);
                            potionArrayMap.put(potion.potionBody, potion);
                            break;
                        }
                        case "SHIELD": {
                            player.hasShield = true;
                            hud.addItem(2);
                            break;
                        }
                        case "GREEK FIRE": {
                            player.hasGreekFire = true;
                            hud.addItem(4);
                            break;
                        }
                        case "TORCH": {
                            player.hasTorch = true;
                            hud.addItem(1);
                            break;
                        }
                        case "CHISEL": {
                            player.hasChisel = true;
                            hud.addItem(3);
                            break;
                        }
                        case "BELT": {
                            //hud.inventory.Capacity = hud.inventory.Capacity + 2;
                            hud.inventory.changeCapacity(1, true);
                            hud.addItem(5);
                            break;
                        }
                    }

                    //int moneyAfterPurchase = money - item.cost;

                    hud.updateGold(item.cost, false);

                    //item.amount--;

                    player.shopkeeper.inventoryText.remove(0);
                    player.shopkeeper.inventoryText.remove(0);

                    Text t1 = player.shopkeeper.Stock(item.kind, item.index);
                    Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
                }
            }

            if (keycode == Input.Keys.NUM_2) {
                ShopItem firstItem = (ShopItem) player.shopkeeper.inventory.get(0);
                ShopItem item = (ShopItem) player.shopkeeper.inventory.get(1);

                int money = Integer.parseInt(hud.totalGoldAsString);

                if (money >= item.cost && !item.purchased) {

                    item.purchased = true;

                    soundController.playSound("Buy",10,10,0.1f);

                    switch (item.kind) {

                        case "WINE": {
                            //System.out.println("THIS IS A TEST TO SEE IF POTIONS CAN BE ADDED VIA THE HUD");
                            //hud.inventory.addPotion();
                            Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
                            potion.createPotion(potionArrayMap,rayHandler);
                            potions.add(potion);
                            potionArrayMap.put(potion.potionBody, potion);
                            break;

                        }
                        case "SHIELD": {
                            player.hasShield = true;
                            hud.addItem(2);
                            break;
                        }
                        case "GREEK FIRE": {
                            player.hasGreekFire = true;
                            hud.addItem(4);
                            break;
                        }
                        case "TORCH": {
                            player.hasTorch = true;
                            hud.addItem(1);
                            break;
                        }
                        case "CHISEL": {
                            player.hasChisel = true;
                            hud.addItem(3);
                            break;
                        }
                        case "BELT": {
                            //hud.inventory.Capacity = hud.inventory.Capacity + 2;
                            hud.inventory.changeCapacity(1, true);
                            hud.addItem(5);
                            break;
                        }
                    }
                    //int moneyAfterPurchase = money - item.cost;

                    hud.updateGold(item.cost, false);

                    //item.amount--;

                    //	x--;
                    if (firstItem.purchased) {
                        player.shopkeeper.inventoryText.remove(0);
                        player.shopkeeper.inventoryText.remove(0);
                    }
                    else {
                        player.shopkeeper.inventoryText.remove(2);
                        player.shopkeeper.inventoryText.remove(2);
                    }


                    Text t1 = player.shopkeeper.Stock(item.kind, item.index);
                    Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
                }
            }

            if (keycode == Input.Keys.NUM_3) {
                ShopItem firstItem = (ShopItem) player.shopkeeper.inventory.get(0);
                ShopItem secondItem = (ShopItem) player.shopkeeper.inventory.get(1);
                ShopItem item = (ShopItem) player.shopkeeper.inventory.get(2);

                int money = Integer.parseInt(hud.totalGoldAsString);

                if (money >= item.cost && !item.purchased) {
                    item.purchased = true;

                    soundController.playSound("Buy",10,10,0.1f);

                    switch (item.kind) {

                        case "WINE": {
                            Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
                            potion.createPotion(potionArrayMap,rayHandler);
                            potions.add(potion);
                            potionArrayMap.put(potion.potionBody, potion);
                            break;
                        }
                        case "SHIELD": {
                            player.hasShield = true;
                            hud.addItem(2);
                            break;
                        }
                        case "GREEK FIRE": {
                            player.hasGreekFire = true;
                            hud.addItem(4);
                            break;
                        }
                        case "TORCH": {
                            player.hasTorch = true;
                            hud.addItem(1);
                            break;
                        }
                        case "CHISEL": {
                            player.hasChisel = true;
                            hud.addItem(3);
                            break;
                        }
                        case "BELT": {
                            //hud.inventory.Capacity = hud.inventory.Capacity + 2;
                            hud.inventory.changeCapacity(1, true);
                            hud.addItem(5);
                            break;
                        }
                    }
                    //int moneyAfterPurchase = money - item.cost;

                    hud.updateGold(item.cost, false);

                    //item.amount--;

                    //	x--;

                    if (firstItem.purchased) {
                        player.shopkeeper.inventoryText.remove(2);
                        player.shopkeeper.inventoryText.remove(2);
                    }
                    else if (secondItem.purchased) {
                        player.shopkeeper.inventoryText.remove(2);
                        player.shopkeeper.inventoryText.remove(2);
                    }
                    else {
                        player.shopkeeper.inventoryText.remove(4);
                        player.shopkeeper.inventoryText.remove(4);
                    }

                    Text t1 = player.shopkeeper.Stock(item.kind, item.index);
                    Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
                }
            }
        }
        // Use potion
        if (keycode == Input.Keys.E) {
            if (hud.inventory.Size > 0) {
                hud.inventory.usePotion(1);
            }
        }

        if (keycode == Input.Keys.P) {
            if (!debug) {
                debug = true;
            } else {
                debug = false;
            }
        }


        if ((keycode == 31 && player.hasChisel) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking && !playerUsingChisel)) {
            float playerChiselUseSpeedInSeconds = 0.85f;
            playerUsingChisel = true;
            if (moveDown || player.facing == 3) {
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                chiselBody = bf.createChiselBody(world, player.playerBody, -2f, -9.5f);
                chiselHitbox = bf.createChiselHitbox(chiselBody, false);
                chiselHitbox.setUserData("Chisel");
                chiselHitbox.setSensor(true);
            }
            else if (moveUp || player.facing == 1) {
                tx.playerTextureRegion = tx.playerAttackUp;
                tx.playerHead = tx.playerHeadUp;
                chiselBody = bf.createChiselBody(world, player.playerBody, -3f, 12.5f);
                chiselHitbox = bf.createChiselHitbox(chiselBody, false);
                chiselHitbox.setUserData("Chisel");
                chiselHitbox.setSensor(true);
            }
            else if (moveLeft || player.facing == 4) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                tx.playerHead = tx.playerHeadLeft;
                chiselBody = bf.createChiselBody(world, player.playerBody, -11.5f, -2f);
                chiselHitbox = bf.createChiselHitbox(chiselBody, true);
                chiselHitbox.setUserData("Chisel");
                chiselHitbox.setSensor(true);
            }
            else if (moveRight || player.facing == 2) {
                tx.playerTextureRegion = tx.playerAttackRight;
                tx.playerHead = tx.playerHeadRight;
                chiselBody = bf.createChiselBody(world, player.playerBody, 12.5f, -2f);
                chiselHitbox = bf.createChiselHitbox(chiselBody, true);
                chiselHitbox.setUserData("Chisel");
                chiselHitbox.setSensor(true);
            }

            chiselBody.setUserData("Chisel");

            //pause player in place while attacking (attacks must be timed correctly!)
            playerPaused = true;
            PLAYER_HORIZONTAL_SPEED = 0;
            PLAYER_VERTICAL_SPEED = 0;
            player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);
            


            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;
                    chiselBody.destroyFixture(chiselHitbox);

                    //reset playerSprite to before the attack input
                    if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
                        tx.playerTextureRegion = tx.playerDown;
                        tx.playerHead = tx.playerHeadDown;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                        tx.playerHead = tx.playerHeadUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                        tx.playerHead = tx.playerHeadLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                        tx.playerHead = tx.playerHeadRight;
                    }

                    playerUsingChisel = false;
                }
            }, playerChiselUseSpeedInSeconds);

        }

        if ((keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking && !playerUsingChisel) && player.hasShield) {
            float playerShieldAttackSpeedInSeconds = 0.85f;
            //0.85
            playerShieldAttacking = true;

            if (moveDown || player.facing == 3) {
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                shieldBody = bf.createShieldBody(world, player.playerBody, -2f, -9.5f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, false);
                shieldHitbox.setUserData("DownShield");
            }
            else if (moveUp || player.facing == 1) {
                tx.playerTextureRegion = tx.playerAttackUp;
                tx.playerHead = tx.playerHeadUp;
                shieldBody = bf.createShieldBody(world, player.playerBody, -3f, 12.5f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, false);
                shieldHitbox.setUserData("UpShield");
            }
            else if (moveLeft || player.facing == 4) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                tx.playerHead = tx.playerHeadLeft;
                shieldBody = bf.createShieldBody(world, player.playerBody, -11.5f, -2f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, true);
                shieldHitbox.setUserData("LeftShield");
            }
            else if (moveRight || player.facing == 2) {
                tx.playerTextureRegion = tx.playerAttackRight;
                tx.playerHead = tx.playerHeadRight;
                shieldBody = bf.createShieldBody(world, player.playerBody, 12.5f, -2f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, true);
                shieldHitbox.setUserData("RightShield");
            }

            shieldBody.setUserData("Shield");

            //pause player in place while attacking (attacks must be timed correctly!)
            playerPaused = true;
            PLAYER_HORIZONTAL_SPEED = 0;
            PLAYER_VERTICAL_SPEED = 0;
            player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;
                    shieldBody.destroyFixture(shieldHitbox);

                    //reset playerSprite to before the attack input
                    if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
                        tx.playerTextureRegion = tx.playerDown;
                        tx.playerHead = tx.playerHeadDown;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                        tx.playerHead = tx.playerHeadUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                        tx.playerHead = tx.playerHeadLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                        tx.playerHead = tx.playerHeadRight;
                    }

                    playerShieldAttacking = false;
                }
            }, playerShieldAttackSpeedInSeconds);
        }

        //pressing '6' shows some debug data
        if (keycode == 13) {
            //System.out.println("PLAYER X: " + player.playerBody.getPosition().x);
            //System.out.println(" PLAYER Y: " + player.playerBody.getPosition().y);
            System.out.println("Current room X" + GenerateLevel.init.roomList.get(player.currentRoom).x1);
            System.out.println("Current room Y" +GenerateLevel.init.roomList.get(player.currentRoom).y1);
            System.out.println("Current room door locations" +GenerateLevel.init.roomList.get(player.currentRoom).doorLocations);
            System.out.println("Next room door locations" +GenerateLevel.init.roomList.get(player.currentRoom + 1).doorLocations);
        }

        //if player presses space attack with the swordBody
        if (((keycode == 62)) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking) && canAttack) {

            playerMeleeAttacking = true;

            if (moveDown || player.facing == 3) {
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("DownSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(0,-100000,0,0,true);
            } else if (moveUp || player.facing == 1) {
                tx.playerTextureRegion = tx.playerAttackUp;
                tx.playerHead = tx.playerHeadUp;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, 17.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("UpSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(0,100000,0,0,true);
            } else if (moveLeft || player.facing == 4) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                tx.playerHead = tx.playerHeadLeft;
                swordBody = bf.createSwordBody(world, player.playerBody, -15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("LeftSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(-100000,0,0,0,true);
            } else if (moveRight || player.facing == 2) {
                tx.playerTextureRegion = tx.playerAttackRight;
                tx.playerHead = tx.playerHeadRight;
                swordBody = bf.createSwordBody(world, player.playerBody, 15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("RightSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(100000,0,0,0,true);
            } else {
                player.facing = 3;
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, player.facing);
                swordHitbox.setUserData("DownSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(0,-100000,0,0,true);
            }

            swordBody.setUserData("Sword");

            //pause player in place while attacking (attacks must be timed correctly!)
            playerPaused = true;
            PLAYER_HORIZONTAL_SPEED = 0;
            PLAYER_VERTICAL_SPEED = 0;
            player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

            boolean swing = Random.randomBoolean();

            if (swing) {
                soundController.playSound("SwordSwing1", 8,6,0.1f);
            } else {
                soundController.playSound("SwordSwing2", 8,6,0.2f);
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    swordBody.destroyFixture(swordHitbox);

                }
            }, 0.2f);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;

                    //reset playerSprite to before the attack input
                    if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
                        tx.playerTextureRegion = tx.playerDown;
                        tx.playerHead = tx.playerHeadDown;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                        tx.playerHead = tx.playerHeadUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                        tx.playerHead = tx.playerHeadLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                        tx.playerHead = tx.playerHeadRight;
                    }

                    playerMeleeAttacking = false;
                }
            }, playerMeleeAttackSpeedInSeconds);
            attackCooldown();
        }

        //if player presses enter attack with a bow
        if (keycode == 66 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking) && canAttack) {

            stateTime2 = 0f;
            playerRangedAttacking = true;

            arrowBody = null;

            if (moveDown || player.facing == 3) {
                playerDirection = "Down";
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("DownArrow");
                arrowBody.setLinearVelocity(0, -400f);

                player.playerBody.applyForce(0,150000,0,0,true);
            } else if (moveUp || player.facing == 1) {
                playerDirection = "Up";
                tx.playerTextureRegion = tx.playerAttackUp;
                tx.playerHead = tx.playerHeadUp;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("UpArrow");
                arrowBody.setLinearVelocity(0, 400f);

                player.playerBody.applyForce(0,-150000,0,0,true);

            } else if (moveLeft || player.facing == 4) {
                playerDirection = "Left";
                tx.playerTextureRegion = tx.playerAttackLeft;
                tx.playerHead = tx.playerHeadLeft;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
                arrowHitbox.setUserData("LeftArrow");
                arrowBody.setLinearVelocity(-400f, 0);

                player.playerBody.applyForce(150000,0,0,0,true);

            } else if (moveRight || player.facing == 2) {
                playerDirection = "Right";
                tx.playerTextureRegion = tx.playerAttackRight;
                tx.playerHead = tx.playerHeadRight;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x + 14f, player.playerBody.getPosition().y+1);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
                arrowHitbox.setUserData("RightArrow");
                arrowBody.setLinearVelocity(400f, 0);

                player.playerBody.applyForce(-150000,0,0,0,true);
            }
            //only triggers if the player hasn't moved at all yet - player starts facing down
            else {
                playerDirection = "Down";
                tx.playerTextureRegion = tx.playerAttackDown;
                tx.playerHead = tx.playerHeadDown;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("DownArrow");
                arrowBody.setLinearVelocity(0, -400f);

                player.playerBody.applyForce(0,150000,0,0,true);
            }
            //pause player in place while attacking (attacks must be timed correctly!)

            arrowBody.setUserData("Arrow");
            if (player.hasGreekFire){

                arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, true));
            } else {
                arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, false));
            }
            arrowArrayMap.put(arrowBody, arrow);

            soundController.playSound("BowAttack3", 10,6,0.1f);


            playerPaused = true;
            PLAYER_HORIZONTAL_SPEED = 0;
            PLAYER_VERTICAL_SPEED = 0;
            player.playerBody.setLinearVelocity(PLAYER_HORIZONTAL_SPEED, PLAYER_VERTICAL_SPEED);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;
                    //reset playerSprite to before the attack input
                    if (tx.playerTextureRegion.equals(tx.playerAttackDown)) {
                        tx.playerTextureRegion = tx.playerDown;
                        tx.playerHead = tx.playerHeadDown;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                        tx.playerHead = tx.playerHeadUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                        tx.playerHead = tx.playerHeadLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                        tx.playerHead = tx.playerHeadRight;
                    }
                    playerRangedAttacking = false;
                }
            }, playerRangedAttackSpeedInSeconds);
            attackCooldown();
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }


    public void attackCooldown() {
            canAttack = false;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                canAttack = true;
            }
        }, attackCooldown);
    }
}

