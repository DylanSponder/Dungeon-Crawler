package com.mygdx.game.box2D;

import com.badlogic.gdx.physics.box2d.*;

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

    public Body createFireBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 10f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4.5f, 5f);
        Fixture fix = body.createFixture(shape, 1.0f);
        shape.dispose();
        fix.setSensor(true);
        return body;
    }

    public Body createColumnBase(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 2.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 1.5f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createPedestal(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 6.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 5.5f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createDoorBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Door");
        return body;
    }

    public Fixture createDoorHitbox(Body body) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);
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

    public static Body createCobweb(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.4f);
        Fixture cobFixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        cobFixture.setSensor(true);
        return body;
    }

    public static Body createPot(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.5f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createPotion(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(5.5f);
        Fixture potionFixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        potionFixture.setSensor(true);
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
        boneShape.setAsBox(5.5f, 3.5f);
        Fixture boneHitbox = boneBody.createFixture(boneShape, 0f);
        boneShape.dispose();
        boneHitbox.setUserData("Bone");
        boneHitbox.setSensor(true);
        return boneBody;
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

    //unused - in case we want the bow to have collision in future
    //TODO - IMPLEMENT AS SHIELD ITEM
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

    public Body createSimpleBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(6f, 6f);
        Fixture playerHitbox = body.createFixture(playerShape, 1.0f);
        body.setUserData("Player");
        playerHitbox.setUserData("PlayerHitbox");
        playerShape.dispose();
        return body;
    }
}
