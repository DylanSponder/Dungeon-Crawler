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

public class GameInputProcessor implements InputProcessor {

    public CreateAssets tx;
    public BodyFactory bf;
        @Override
        public boolean scrolled(float amountX, float amountY) {
        if (DungeonCrawler.debug) {
            //camera zoom should be between 0.3 and 1.3 - may be changed during testing
            if ((camera.zoom >= 0.3f && camera.zoom <= 24f)) {
                if (camera.zoom == 24f) {
                    if (amountY < 0f) {
                        camera.zoom += amountY * 0.1f;
                    }
                } else if (camera.zoom == 0.3f) {
                    if (amountY > 0f) {
                        camera.zoom += amountY * 0.1f;
                    }
                } else {
                    camera.zoom += amountY * 0.1f;
                }
            } else if (camera.zoom > 24f) {
                camera.zoom = 24f;
            } else if (camera.zoom < 0.3f) {
                camera.zoom = 0.3f;
            }
        }
        return true;
    }

        public boolean touchDown(int x, int y, int pointer, int button) {
            tx = CreateAssets.getInstance();
            bf = new BodyFactory();

        if (button == 0 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
            //if player presses left mouse attack with the swordBody
            float playerMeleeAttackSpeedInSeconds = 0.40f;
            playerMeleeAttacking = true;

            if (tx.playerTextureRegion.equals(tx.playerDown)) {
                tx.playerTextureRegion = tx.playerAttackDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, false);
                swordHitbox.setUserData("DownSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(0,-100000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerUp) || leanUp) {
                tx.playerTextureRegion = tx.playerAttackUp;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, 17.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, false);
                swordHitbox.setUserData("UpSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(0,100000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerLeft)) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                swordBody = bf.createSwordBody(world, player.playerBody, -15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, true);
                swordHitbox.setUserData("LeftSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(-100000,0,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerRight)) {
                tx.playerTextureRegion = tx.playerAttackRight;
                swordBody = bf.createSwordBody(world, player.playerBody, 15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, true);
                swordHitbox.setUserData("RightSword");
                swordHitbox.setSensor(true);
                player.playerBody.applyForce(100000,0,0,0,true);
            } else {
                tx.playerSprite = tx.playerAttackDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, false);
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
            //CreateSound.slash.play();
            //roomClear.play();
            //swordSlash.setLooping(true);
            //swordSlash.play();
            //swordSlash.setVolume(1f);
            //swordSlash.dispose();

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;
                    swordBody.destroyFixture(swordHitbox);

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
        }

        //if player presses right mouse attack with a bow
        if (button == 1 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
            float playerRangedAttackSpeedInSeconds = 0.50f;
            playerRangedAttacking = true;

            if (tx.playerTextureRegion.equals(tx.playerDown)) {
                playerDirection = "Down";
                tx.playerTextureRegion = tx.playerAttackDown;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("DownArrow");
                arrowBody.setLinearVelocity(0, -400f);
                player.playerBody.applyForce(0,150000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerUp) || leanUp) {
                playerDirection = "Up";
                tx.playerTextureRegion = tx.playerAttackUp;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("UpArrow");
                arrowBody.setLinearVelocity(0, 400f);
                player.playerBody.applyForce(0,-150000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerLeft)) {
                playerDirection = "Left";
                tx.playerTextureRegion = tx.playerAttackLeft;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
                arrowHitbox.setUserData("LeftArrow");
                arrowBody.setLinearVelocity(-400f, 0);
                player.playerBody.applyForce(150000,0,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerRight)) {
                playerDirection = "Right";
                tx.playerTextureRegion = tx.playerAttackRight;
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
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                    }
                    playerRangedAttacking = false;
                }
            }, playerRangedAttackSpeedInSeconds);
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

            // (For Debugging) Add potion
            if (keycode == Input.Keys.NUM_9) {
                hud.inventory.addPotion();
            }
        }
				/*

					// (For Debugging) Damage player
					if (keycode == Keys.NUM_0) {
						hud.healthBar.LoseHealth(0.5f);
					}

					if (keycode == Keys.NUM_2) {
						camera.zoom = 1f;
					}

					if (keycode == 10) {
						camera.zoom = 10f;
					}
				}
				else
				*/


        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {

            menuClosed = false;

         //   Gdx.graphics.setWindowedMode(1280, 720);
        }



        if (player.buyingStock){

            if (keycode == Input.Keys.NUM_1) {
                //String amount = player.shopkeeper.inventory.get(1).toString();
                //int x = Integer.parseInt(amount);
                //System.out.println(x);

                ShopItem item = (ShopItem) player.shopkeeper.inventory.get(0);

                int money = Integer.parseInt(hud.totalGoldAsString);

                if (money >= item.cost && !item.purchased) {

                    System.out.println(item.kind + "<- THE TYPE OF ITEM");

                    item.purchased = true;
                    switch (item.kind) {

                        case "KYKEON": {
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

                            break;

                        }
                        case "GREEK FIRE": {
                            player.hasGreekFire = true;
                            //player.greekFireUses = 10;
                            break;
                        }
                        case "TORCH": {
                            player.hasTorch = true;
                            break;
                        }
                        case "BELT": {
                            //hud.inventory.Capacity = hud.inventory.Capacity + 2;
                            hud.inventory.changeCapacity(1, true);
                            break;
                        }
                    }

                    //int moneyAfterPurchase = money - item.cost;

                    hud.updateGold(item.cost, false);

                    System.out.println(item.kind);
                    System.out.println(item.index);
                    //item.amount--;

                    player.shopkeeper.inventoryText.remove(0);
                    player.shopkeeper.inventoryText.remove(0);

                    Text t1 = player.shopkeeper.Stock(item.kind, item.index);
                    Text t2 = player.shopkeeper.DescribeStock(item.kind,item.index,item.cost);
                }
            }

            if (keycode == Input.Keys.NUM_2) {
                //String amount = player.shopkeeper.inventory.get(1).toString();
                //int x = Integer.parseInt(amount);
                //System.out.println(x);
                ShopItem firstItem = (ShopItem) player.shopkeeper.inventory.get(0);
                ShopItem item = (ShopItem) player.shopkeeper.inventory.get(1);

                int money = Integer.parseInt(hud.totalGoldAsString);

                if (money >= item.cost && !item.purchased) {
                    item.purchased = true;

                    switch (item.kind) {

                        case "KYKEON": {
                            //System.out.println("THIS IS A TEST TO SEE IF POTIONS CAN BE ADDED VIA THE HUD");
                            //hud.inventory.addPotion();
                            Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
                            potion.createPotion(potionArrayMap,rayHandler);
                            potions.add(potion);
                            potionArrayMap.put(potion.potionBody, potion);
                            break;

                        }
                        case "SHIELD": {
                            //TODO: add shield item
                            player.hasShield = true;

                            break;

                        }
                        case "GREEK FIRE": {
                            player.hasGreekFire = true;
                            //player.greekFireUses = 30;
                            break;
                        }
                        case "TORCH": {
                            player.hasTorch = true;
                            break;
                        }
                        case "BELT": {
                            //hud.inventory.Capacity = hud.inventory.Capacity + 2;
                            hud.inventory.changeCapacity(1, true);
                            break;
                        }
                    }
                    //int moneyAfterPurchase = money - item.cost;

                    hud.updateGold(item.cost, false);

                    System.out.println(item.kind);
                    System.out.println(item.index);
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
                //String amount = player.shopkeeper.inventory.get(1).toString();
                //int x = Integer.parseInt(amount);
                //System.out.println(x);
                ShopItem firstItem = (ShopItem) player.shopkeeper.inventory.get(0);
                ShopItem secondItem = (ShopItem) player.shopkeeper.inventory.get(1);
                ShopItem item = (ShopItem) player.shopkeeper.inventory.get(2);

                int money = Integer.parseInt(hud.totalGoldAsString);

                if (money >= item.cost && !item.purchased) {
                    item.purchased = true;

                    switch (item.kind) {

                        case "KYKEON": {
                            //System.out.println("THIS IS A TEST TO SEE IF POTIONS CAN BE ADDED VIA THE HUD");
                            //hud.inventory.addPotion();
                            Potion potion = new Potion(world, player.shopkeeper.posX, player.shopkeeper.posY+ 16,1);
                            potion.createPotion(potionArrayMap,rayHandler);
                            potions.add(potion);
                            potionArrayMap.put(potion.potionBody, potion);
                            break;

                        }
                        case "SHIELD": {
                            //TODO: add shield item
                            player.hasShield = true;


                            break;

                        }
                        case "GREEK FIRE": {
                            player.hasGreekFire = true;
                            //player.greekFireUses = 30;
                            break;
                        }
                        case "TORCH": {
                            player.hasTorch = true;
                            //playerLight.setXray(true);
                            break;
                        }
                        case "BELT": {
                            //hud.inventory.Capacity = hud.inventory.Capacity + 2;
                            hud.inventory.changeCapacity(1, true);
                            break;
                        }
                    }
                    //int moneyAfterPurchase = money - item.cost;

                    hud.updateGold(item.cost, false);

                    System.out.println(item.kind);
                    System.out.println(item.index);
                    //item.amount--;

                    //	x--;
                    if (firstItem.purchased && !secondItem.purchased) {
                        player.shopkeeper.inventoryText.remove(2);
                        player.shopkeeper.inventoryText.remove(2);
                    }
                    else if (secondItem.purchased && !firstItem.purchased) {
                        player.shopkeeper.inventoryText.remove(0);
                        player.shopkeeper.inventoryText.remove(0);
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
                hud.healthBar.GainHealth(1.5f);
            }
        }

        if (keycode == Input.Keys.P) {
            if (!debug) {
                debug = true;
            } else {
                debug = false;
            }
        }

        if ((keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking) && player.hasShield) {
            float playerShieldAttackSpeedInSeconds = 0.85f;
            playerShieldAttacking = true;

            if (tx.playerTextureRegion.equals(tx.playerDown) || leanDown) {
                tx.playerTextureRegion = tx.playerAttackDown;
                shieldBody = bf.createShieldBody(world, player.playerBody, -2f, -9.5f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, false);
                shieldHitbox.setUserData("DownShield");
            }
            if (tx.playerTextureRegion.equals(tx.playerUp) || leanUp) {
                tx.playerTextureRegion = tx.playerAttackUp;
                shieldBody = bf.createShieldBody(world, player.playerBody, -3f, 12.5f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, false);
                shieldHitbox.setUserData("UpShield");
            }
            if (tx.playerTextureRegion.equals(tx.playerLeft)) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                shieldBody = bf.createShieldBody(world, player.playerBody, -11.5f, -2f);
                shieldHitbox = bf.createShieldHitbox(shieldBody, true);
                shieldHitbox.setUserData("LeftShield");
            }
            if (tx.playerTextureRegion.equals(tx.playerRight)) {
                tx.playerTextureRegion = tx.playerAttackRight;
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
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                    }

                    playerShieldAttacking = false;
                }
            }, playerShieldAttackSpeedInSeconds);
        }

        //pressing '6' shows some debug data
        if (keycode == 13) {
            //System.out.println("PLAYER X: " + player.playerBody.getPosition().x);
            //System.out.println(" PLAYER Y: " + player.playerBody.getPosition().y);
            System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).x1);
            System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).y1);
            System.out.println(GenerateLevel.init.roomList.get(player.currentRoom).doorLocations);
            System.out.println(GenerateLevel.init.roomList.get(player.currentRoom + 1).doorLocations);
        }

        //if player presses space attack with the swordBody
        if (((keycode == 62)) && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
            float playerMeleeAttackSpeedInSeconds = 0.40f;
            playerMeleeAttacking = true;

            if (tx.playerTextureRegion.equals(tx.playerDown)) {
                tx.playerTextureRegion = tx.playerAttackDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, false);
                swordHitbox.setUserData("DownSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(0,-100000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerUp) || leanUp) {
                tx.playerTextureRegion = tx.playerAttackUp;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, 17.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, false);
                swordHitbox.setUserData("UpSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(0,100000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerLeft)) {
                tx.playerTextureRegion = tx.playerAttackLeft;
                swordBody = bf.createSwordBody(world, player.playerBody, -15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, true);
                swordHitbox.setUserData("LeftSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(-100000,0,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerRight)) {
                tx.playerTextureRegion = tx.playerAttackRight;
                swordBody = bf.createSwordBody(world, player.playerBody, 15.5f, -1.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, true);
                swordHitbox.setUserData("RightSword");
                swordHitbox.setSensor(true);

                player.playerBody.applyForce(100000,0,0,0,true);
            } else {
                tx.playerSprite = tx.playerAttackDown;
                swordBody = bf.createSwordBody(world, player.playerBody, -2.5f, -11.5f);
                swordHitbox = bf.createSwordHitbox(swordBody, false);
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
            //CreateSound.slash.play();
            //roomClear.play();
            //swordSlash.setLooping(true);
            //swordSlash.play();
            //swordSlash.setVolume(1f);
            //swordSlash.dispose();

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //resume player movement after a short delay and remove swordBody hitbox
                    playerPaused = false;
                    swordBody.destroyFixture(swordHitbox);

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
        }

        //if player presses enter attack with a bow
        if (keycode == 66 && (!playerMeleeAttacking && !playerRangedAttacking && !playerShieldAttacking)) {
            float playerRangedAttackSpeedInSeconds = 0.50f;
            stateTime2 = 0f;
            playerRangedAttacking = true;

            if (tx.playerTextureRegion.equals(tx.playerDown)) {
                playerDirection = "Down";
                tx.playerTextureRegion = tx.playerAttackDown;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y - 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("DownArrow");
                arrowBody.setLinearVelocity(0, -400f);

                player.playerBody.applyForce(0,150000,0,0,true);
            } else if (tx.playerTextureRegion.equals(tx.playerUp) || leanUp) {
                playerDirection = "Up";
                tx.playerTextureRegion = tx.playerAttackUp;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 2f, player.playerBody.getPosition().y + 14f);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, true);
                arrowHitbox.setUserData("UpArrow");
                arrowBody.setLinearVelocity(0, 400f);

                player.playerBody.applyForce(0,-150000,0,0,true);

            } else if (tx.playerTextureRegion.equals(tx.playerLeft)) {
                playerDirection = "Left";
                tx.playerTextureRegion = tx.playerAttackLeft;
                arrowBody = Arrow.createArrowBody(world, player.playerBody.getPosition().x - 14f, player.playerBody.getPosition().y+1);
                arrowHitbox = Arrow.createArrowHitbox(arrowBody, false);
                arrowHitbox.setUserData("LeftArrow");
                arrowBody.setLinearVelocity(-400f, 0);

                player.playerBody.applyForce(150000,0,0,0,true);

            } else if (tx.playerTextureRegion.equals(tx.playerRight)) {
                playerDirection = "Right";
                tx.playerTextureRegion = tx.playerAttackRight;
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
                player.greekFireUses--;
            } else {
                arrows.add(arrow = new Arrow(arrowBody, playerDirection, 0f, false));
            }
            arrowArrayMap.put(arrowBody, arrow);

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
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackUp)) {
                        tx.playerTextureRegion = tx.playerUp;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackLeft)) {
                        tx.playerTextureRegion = tx.playerLeft;
                    } else if (tx.playerTextureRegion.equals(tx.playerAttackRight)) {
                        tx.playerTextureRegion = tx.playerRight;
                    }
                    playerRangedAttacking = false;
                }
            }, playerRangedAttackSpeedInSeconds);
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
}
