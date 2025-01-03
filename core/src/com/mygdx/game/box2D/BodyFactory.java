package com.mygdx.game.box2D;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.DungeonCrawler.chiselHitbox;

public class BodyFactory {

    public Body createWall(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createRoofHitbox(World world, float x, float y, int type, int ext) {
        PolygonShape shape = new PolygonShape();
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        switch (type) {
            case 0:
                shape.setAsBox(16, 16);
                bodyDef.position.set(x ,y);
                break;
            case 1:
                shape.setAsBox(26, 24 + (ext * 8));
                bodyDef.position.set(x + 40 ,y - (16 + (ext * 8)));
                break;
            case 2:
                shape.setAsBox(43, 32 + (ext * 8));
                bodyDef.position.set(x + 56,y - (24 + (ext * 8)));

                break;
            case 3:
                shape.setAsBox(62, 48 + (ext * 8));
                bodyDef.position.set(x + 72,y - (40 + (ext * 8)));

                break;
            case 4:
                shape.setAsBox(16, 8 + (ext * 8));
                bodyDef.position.set(x,y);

                break;
            case 5:
                shape.setAsBox(16, 8 + (ext * 8));
                bodyDef.position.set(x,y);

                break;
        }
        body = world.createBody(bodyDef);
        //shape.setAsBox(8, 8);
        Fixture fix = body.createFixture(shape, 1.0f);
        fix.setSensor(true);
        fix.setUserData("Roof");
        shape.dispose();
        return body;
    }

    public Body createFireBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 10.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4.5f, 5.5f);
        Fixture fix = body.createFixture(shape, 1.0f);
        shape.dispose();
        fix.setSensor(true);
        return body;
    }

    public Body createTrapArea(World world, float x, float y, int direction) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        switch (direction) {
            case 1:
                shape.setAsBox(4f, 12f);
                bodyDef.position.set(x + 8, y - 12);
                break;
            case 2:
                shape.setAsBox(12f, 4f);
                bodyDef.position.set(x - 12, y - 8);
                break;
            case 3:
                shape.setAsBox(4f, 12f);
                bodyDef.position.set(x - 8, y + 28);
                break;
            case 4:
                shape.setAsBox(12f, 4f);
                bodyDef.position.set(x + 28, y + 8);
                break;
        }
        body = world.createBody(bodyDef);
        Fixture fix = body.createFixture(shape, 1.0f);
        fix.setSensor(true);
        shape.dispose();
        return body;
    }

    public Body createColumnBase(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 2.03f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 1.96f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createPedestal(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8.02f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 7.95f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createDoorBody(World world, float x, float y, boolean upDown) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        if (upDown) {
            bodyDef.position.set(x + 8, y);

        } else {
            bodyDef.position.set(x + 16, y + 8);
        }

        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Door");
        return body;
    }

    public Fixture createDoorHitbox(Body body, boolean upDown) {
        PolygonShape shape = new PolygonShape();
        if (upDown) {
            shape.setAsBox(8, 16);
        } else {
            shape.setAsBox(16, 8);
        }
        Fixture fixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        fixture.setSensor(true);
        return fixture;
    }

    public Body createLockBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Lock");
        return body;
    }

    public static Body createWallTurn(World world, float x, float y, float offsetX, float offsetY) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + offsetX, y + offsetY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(16f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createObstacle(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.4f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createCandle(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.4f);
        Fixture candFix = body.createFixture(shape, 1.0f);
        candFix.setSensor(true);
        shape.dispose();
        return body;
    }

    public static Body createCobweb(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8f);
        Fixture cobFixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        cobFixture.setSensor(true);
        return body;
    }

    public static Body createImpassableCobweb(World world, float x, float y) {
        Body body2;
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(x + 8f, y + 8f);
        bodyDef2.fixedRotation = true;
        body2 = world.createBody(bodyDef2);
        CircleShape shape2 = new CircleShape();
        shape2.setRadius(4f);
        Fixture cobFixture2 = body2.createFixture(shape2, 1.0f);
        shape2.dispose();
        return body2;
    }

    public static Body createPot(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createPotion(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(5.5f);
        Fixture potionFixture = body.createFixture(shape, 1.0f);
        shape.dispose();
       // potionFixture.setSensor(true);
        return body;
    }

    public static Body createCoin(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(3.5f);
        Fixture potionFixture = body.createFixture(shape, 1.0f);
        shape.dispose();
       // potionFixture.setSensor(true);
        return body;
    }

    public Body createBoneBody(World world, Body skull, float x, float y) {
        Body boneBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(skull.getPosition().x, skull.getPosition().y);
        bodyDef.fixedRotation = false;
        boneBody = world.createBody(bodyDef);
        PolygonShape boneShape = new PolygonShape();
        boneShape.setAsBox(5f, 2.5f);
        //was 5.5, 3.5
        Fixture boneHitbox = boneBody.createFixture(boneShape, 0f);
        boneShape.dispose();
        boneHitbox.setUserData("Bone");
        boneHitbox.setSensor(true);
        return boneBody;
    }

    public Body createWebBody(World world, Body web, float x, float y, float angle) {
        Body webBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(web.getPosition().x, web.getPosition().y);
        bodyDef.angle = angle;
        bodyDef.fixedRotation = true;
        webBody = world.createBody(bodyDef);
        CircleShape webShape = new CircleShape();
        //webShape.setAsBox(8f, 8f);
        webShape.setRadius(8f);
        Fixture webHitbox = webBody.createFixture(webShape, 0f);
        webShape.dispose();
        webHitbox.setUserData("Bone");
        webHitbox.setSensor(true);
        return webBody;
    }

    public Body createSwordBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createSwordHitbox(Body sword, boolean r){
        PolygonShape swordShape = new PolygonShape();
        if (r){
            swordShape.setAsBox(5.5f, 2.5f);
        }
        else {
            swordShape.setAsBox(2.5f, 5.5f);
        }
        Fixture swordHitbox = sword.createFixture(swordShape, 1.0f);
        swordShape.dispose();
        swordHitbox.setUserData("Sword");
        swordHitbox.isSensor();
        return swordHitbox;
    }

    public Body createShieldBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
       // bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createShieldHitbox(Body shield,boolean r){
        PolygonShape shieldShape = new PolygonShape();
        if (r){
            shieldShape.setAsBox(2.5f, 9f);
        }
        else {
            shieldShape.setAsBox(9f, 2.5f);
        }
        Fixture shieldHitbox = shield.createFixture(shieldShape, 1.0f);
        shieldShape.dispose();
        return shieldHitbox;
    }

    public Body createChiselBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
        // bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createChiselHitbox(Body chisel,boolean r){
        PolygonShape chiselShape = new PolygonShape();
        if (r){
            chiselShape.setAsBox(6f, 2.5f);
        }
        else {
            chiselShape.setAsBox(2.5f, 6f);
        }
        Fixture chiselHitbox = chisel.createFixture(chiselShape, 1.0f);
        chiselShape.dispose();
        chiselHitbox.setSensor(true);
        return chiselHitbox;
    }

    public Body createSimpleDynamicBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Body createSimpleStaticBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }


    public Body createSkullBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createSkullHitbox(Body body, float r) {
        CircleShape skullShape = new CircleShape();
        skullShape.setRadius(r);
        Fixture skullHitbox = body.createFixture(skullShape, 1.0f);
        skullShape.dispose();
        skullHitbox.setUserData("SkullHitbox");
        skullHitbox.setSensor(true);
        return skullHitbox;
    }

    public Fixture createEnemyHitbox(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyHitbox.setUserData("EnemyHitbox");
        return enemyHitbox;
    }

    public Fixture createEnemyDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyDetectionHitbox = body.createFixture(enemyShape, 0.8f);
        enemyShape.dispose();
        enemyDetectionHitbox.setUserData("Proximity");
        enemyDetectionHitbox.setSensor(true);
        return enemyDetectionHitbox;
    }

    public Fixture createSpawnerDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture spawner = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        spawner.setUserData("Spawner");
        spawner.setSensor(true);
        return spawner;
    }

    public Body createShopBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Body createTutorialBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        //bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createShopHitbox(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        // PolygonShape enemyShape = new PolygonShape();
        // enemyShape.setAsBox(x, y);
        Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyHitbox.setUserData("ShopHitbox");
        return enemyHitbox;
    }

    public Fixture createShopDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyDetectionHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyDetectionHitbox.setUserData("ShopSell");
        enemyDetectionHitbox.isSensor();
        return enemyDetectionHitbox;
    }

    public static Fixture createRoom(int roomIndex, World world, int roomX, int roomY, int h, int w){
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(roomX, roomY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape roomShape = new PolygonShape();
        roomShape.setAsBox(w, h);
        Fixture roomHitbox = body.createFixture(roomShape, 1.0f);
        roomShape.dispose();
        body.setUserData("Room-"+roomIndex);
        roomHitbox.setUserData("Room-"+roomIndex);
        //roomHitbox.isSensor();
        roomHitbox.setSensor(true);
        return roomHitbox;
    }


    public Body createPlayerBody(World world, float playerX, float playerY) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerX, playerY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape playerShape1 = new PolygonShape();
        PolygonShape playerShape2 = new PolygonShape();
        PolygonShape playerShape3 = new PolygonShape();
        CircleShape playerCornerShape1 = new CircleShape();
        CircleShape playerCornerShape2 = new CircleShape();
        CircleShape playerCornerShape3 = new CircleShape();
        CircleShape playerCornerShape4 = new CircleShape();
        playerCornerShape1.setRadius(3);
        playerCornerShape2.setRadius(3);
        playerCornerShape3.setRadius(3);
        playerCornerShape4.setRadius(3);
        Vector2 vec1 = new Vector2(playerCornerShape1.getPosition().x+3,playerCornerShape1.getPosition().y+3);
        Vector2 vec2 = new Vector2(playerCornerShape1.getPosition().x-3,playerCornerShape1.getPosition().y-3);
        Vector2 vec3 = new Vector2(playerCornerShape1.getPosition().x-3,playerCornerShape1.getPosition().y+3);
        Vector2 vec4 = new Vector2(playerCornerShape1.getPosition().x+3,playerCornerShape1.getPosition().y-3);
        playerCornerShape1.setPosition(vec1);
        playerCornerShape2.setPosition(vec2);
        playerCornerShape3.setPosition(vec3);
        playerCornerShape4.setPosition(vec4);

        playerShape1.setAsBox(3f, 5.98f);
        playerShape2.setAsBox(5.98f, 3f);

        //player bounds for room door locking
        playerShape3.setAsBox(6.5f, 6.5f);

        Fixture playerHitbox = body.createFixture(playerShape1, 1.0f);
        Fixture playerHitbox2 = body.createFixture(playerShape2, 1.0f);

        Fixture playerBoundHitbox = body.createFixture(playerShape3, 1.0f);
        playerBoundHitbox.setSensor(true);

        Fixture playerCornerHitbox1 = body.createFixture(playerCornerShape1,1.0f);
        Fixture playerCornerHitbox2 = body.createFixture(playerCornerShape2,1.0f);
        Fixture playerCornerHitbox3 = body.createFixture(playerCornerShape3,1.0f);
        Fixture playerCornerHitbox4 = body.createFixture(playerCornerShape4,1.0f);

        body.setUserData("Player");
        playerCornerHitbox1.setUserData("PlayerHitbox");
        playerCornerHitbox2.setUserData("PlayerHitbox");
        playerCornerHitbox3.setUserData("PlayerHitbox");
        playerCornerHitbox4.setUserData("PlayerHitbox");
        playerHitbox.setUserData("PlayerHitbox");
        playerHitbox2.setUserData("PlayerHitbox");
        playerBoundHitbox.setUserData("PlayerBound");
        playerCornerShape1.dispose();
        playerCornerShape2.dispose();
        playerCornerShape3.dispose();
        playerCornerShape4.dispose();
        playerShape1.dispose();
        playerShape2.dispose();
        playerShape3.dispose();
        return body;
    }
}
