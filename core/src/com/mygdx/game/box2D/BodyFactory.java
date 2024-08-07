package com.mygdx.game.box2D;

import com.badlogic.gdx.math.Circle;
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

    public Body createDoorBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createDoorHitbox(Body body) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);
        Fixture fixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        return fixture;
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
        bodyDef.position.set(x + 8f, y + 8f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.5f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
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

    public Fixture createSwordHitbox(Body sword,boolean r){
        PolygonShape swordShape = new PolygonShape();
        if (r){
            swordShape.setAsBox(5f, 2.5f);
        }
        else {
            swordShape.setAsBox(2.5f, 5f);
        }
        Fixture swordHitbox = sword.createFixture(swordShape, 1.0f);
        swordShape.dispose();
        swordHitbox.setUserData("Sword");
        swordHitbox.isSensor();
        return swordHitbox;
    }

    //unused - in case we want the bow to have collision in future
    public Body createBowBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createBowHitbox(Body bow,boolean r){
        PolygonShape bowShape = new PolygonShape();
        if (r){
            bowShape.setAsBox(7f, 3.5f);
        }
        else {
            bowShape.setAsBox(3.5f, 7f);
        }
        Fixture bowHitbox = bow.createFixture(bowShape, 1.0f);
        bowShape.dispose();
        return bowHitbox;
    }

    public Body createEnemyBody(World world, float x, float y) {
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
        skullHitbox.setUserData("EnemyHitbox");
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
        Fixture enemyDetectionHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyDetectionHitbox.setUserData("Proximity");
        enemyDetectionHitbox.isSensor();
        return enemyDetectionHitbox;
    }

    public Body createShopBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
        enemyHitbox.setUserData("EnemyHitbox");
        return enemyHitbox;
    }

    public Fixture createShopDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyDetectionHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyDetectionHitbox.setUserData("Proximity");
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
        roomHitbox.isSensor();
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
        playerShape.setAsBox(6f, 5f);
        Fixture playerHitbox = body.createFixture(playerShape, 1.0f);
        playerHitbox.setUserData("PlayerHitbox");
        playerShape.dispose();
        return body;
    }
}
