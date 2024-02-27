package com.mygdx.game.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Player {
    public int PLAYER_HEALTH = 12;

    public static Box2DSteeringEntity playerB2D;
    public float PLAYER_X = 0f, PLAYER_Y = 0f;

    public Body playerBody;

    public Player() {
        PLAYER_HEALTH = 12;
    }

    public Body createPlayer(World world, float PLAYER_X, float PLAYER_Y){
        BodyFactory bf = new BodyFactory();

        playerBody = bf.createPlayerBody(world, PLAYER_X, PLAYER_Y);
        playerB2D = new Box2DSteeringEntity(playerBody,10);

        playerBody.setUserData("Player");

        return playerBody;
    }
}
