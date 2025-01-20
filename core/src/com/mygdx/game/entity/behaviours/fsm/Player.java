package com.mygdx.game.entity.behaviours.fsm;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Random;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.utils.PlayerBox2DSteeringEntity;

public class Player {
    public int PLAYER_HEALTH = 12;

    public static PlayerBox2DSteeringEntity playerB2D;
    public float PLAYER_X = 0f, PLAYER_Y = 0f;

    public static Body playerBody;
    public Shopkeeper shopkeeper;
    public int currentRoom;
    public int facing;
    public boolean touchingRoom, touchingDoor, touchingCobweb;
    public boolean midAnimationFrame;
    public boolean hasGreekFire, hasShield, hasTorch, torchApplied, hasChisel;
    public int greekFireUses;
    public boolean floorCleared, roomCleared, playerInput;
    public float stateTime, timeSinceMoved;
    public PointLight playerLight;

    public Fixture playerDetectionFixture;
    public boolean buyingStock;

    public Player() {
        PLAYER_HEALTH = 12;
        midAnimationFrame = false;
        hasGreekFire = false;
        hasShield = false;
        hasTorch = false;
        torchApplied = false;
        hasChisel = false;
    }

    public Body createPlayer(World world, float PLAYER_X, float PLAYER_Y, RayHandler rayHandler){
        BodyFactory bf = new BodyFactory();

        floorCleared = false;

        this.playerBody = bf.createPlayerBody(world, PLAYER_X, PLAYER_Y);

        this.playerLight = new PointLight(rayHandler, 1000, new Color(0.25f, 0.20f, 0, 0.55f), 45, PLAYER_X, PLAYER_Y);
        this.playerLight.attachToBody(this.playerBody);
        this.playerLight.setSoftnessLength(65);

        this.playerB2D = new PlayerBox2DSteeringEntity(playerBody,10);

        this.playerBody.setUserData("Player");

        return playerBody;
    }

    public static void renderPlayer(SpriteBatch batch, TextureRegion playerSprite, float x, float y) {

        batch.draw(playerSprite,x,y);

    }
}
