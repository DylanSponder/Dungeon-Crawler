package com.mygdx.game.entity;

import com.badlogic.gdx.physics.box2d.*;

public class CreateBody {

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

    public static Body createWallTurn(World world, float x, float y, float offsetX, float offsetY) {
      //  world = new World(new Vector2(0, 0f), false);
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

    public Body createSword(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createSwordHitbox(Body sword,boolean r){
        PolygonShape swordShape = new PolygonShape();
        if (r){
            swordShape.setAsBox(6f, 2.5f);
        }
        else {
            swordShape.setAsBox(2.5f, 6f);
        }
        Fixture swordHitbox = sword.createFixture(swordShape, 1.0f);
        swordShape.dispose();
        return swordHitbox;
    }

    public Body createBow(World world, Body player, float x, float y) {
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

    public Body createArrow(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createArrowHitbox(Body arrow,boolean r){
        PolygonShape arrowShape = new PolygonShape();
        if (r){
            arrowShape.setAsBox(2.5f, 6.5f);
        }
        else {
            arrowShape.setAsBox(6.5f, 2.5f);
        }
        Fixture arrowHitbox = arrow.createFixture(arrowShape, 1.0f);
        arrowShape.dispose();
        return arrowHitbox;
    }

    public Body createEnemy(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createEnemyHitbox(Body body){
        PolygonShape enemyShape = new PolygonShape();
        enemyShape.setAsBox(6f, 5f);
        Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        return enemyHitbox;
    }

    public Body createPlayer(World world, float playerX, float playerY) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerX, playerY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(6f, 5f);
        Fixture playerHitbox = body.createFixture(playerShape, 1.0f);
        playerShape.dispose();
        return body;
    }
}
