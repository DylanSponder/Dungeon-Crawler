package com.mygdx.game;

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

    public Body createEnemy(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape enemyShape = new PolygonShape();
        enemyShape.setAsBox(6f, 5f);
        Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        return body;
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

    //code example to change the player hitbox - previously tested but currently unused
    //if hitbox needs to be accessed in DungeonCrawler, create separate method to capture hitbox (see createSwordHitbox)
			/*
			player.destroyFixture(playerHitbox);
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(7,7);
			player.createFixture(shape,1.0f);
			*/
}
