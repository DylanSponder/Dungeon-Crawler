package com.mygdx.game.entity.behaviours.fsm;

import box2dLight.ChainLight;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.utils.BossMinotaurBox2DSteeringEntity;
import com.mygdx.game.entity.utils.ChargeBox2DSteeringEntity;
import com.mygdx.game.level.objects.Text;

import static com.mygdx.game.DungeonCrawler.camera;
import static com.mygdx.game.DungeonCrawler.rayHandler;

public class BossMinotaur extends Enemy {

    public StateMachine<BossMinotaur, BossMinotaurState> stateMachine;
    public int enemyID;
    public BossMinotaurBox2DSteeringEntity enemyAI;
    public String facing;
    public boolean active, enraged, charging, locked;
    public float stateTime, enrageTime, chargeTime;
    public int enragedSpeed, chargingSpeed, MAX_HEALTH, chargeThreshold;
    public Body chargeBody;
    public ChargeBox2DSteeringEntity chargeEntity;

    public BossMinotaur(World world, float x, float y) {
        BodyFactory bodyFactory = new BodyFactory();

        this.shapeRenderer = new ShapeRenderer();

        this.alertMessage = new Text(DungeonCrawler.defaultFont,"!", Color.RED,true,1f,0.0045f,false, false, null, 0);

        this.lostSightMessage = new Text(DungeonCrawler.defaultFont,"?", Color.YELLOW,true,1f,0.0045f,false, false, null, 0);

        this.rayCastable = false;

        this.sightCounter = 0;

        this.alerted = false;

        Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);

        this.ENEMY_HEALTH = 40;

        this.MAX_HEALTH = this.ENEMY_HEALTH;

        this.playerInRange = false;

        this.defaultSpeed = 24;

        this.enragedSpeed = 30;

        this.chargingSpeed = 65;

        this.chargeThreshold = 6;

        this.enrageTime = 2.2f;

        //creates an enemy with a body, hitbox and steering entity
        this.enemyBody = bodyFactory.createSimpleDynamicBody(world, x, y);
        this.enemyDetectionBody = bodyFactory.createSimpleDynamicBody(world, x, y);

        this.enemyHitbox = bodyFactory.createMinotaurHitbox(enemyBody, 5.95f);

        //this.enemyDetectionRadius = bodyFactory.createEnemyDetectionRadius(enemyBody, 100f);

        //enemyDetectionRadius.setSensor(true);

        this.enemyAI = new BossMinotaurBox2DSteeringEntity(enemyBody, 10);
        //playerDetectionRay = new EnemyBox2DSteeringEntity(enemyBody,10);

        stateMachine = new DefaultStateMachine<BossMinotaur, BossMinotaurState>(this, BossMinotaurState.STOP);
        stateMachine.changeState(BossMinotaurState.STOP);
        this.enemyBody.setUserData("Enemy");
        this.enemyHitbox.setUserData("Enemy");

        this.debug = false;

        //this.eyeLight = new ChainLight(rayHandler, 60, new Color(0.3f,0,1f,0.5f),50,1,new float[]{0,0,0,40,20,40,20,0,0,0});

    }

    public Arrive<Vector2> arriveAtPlayer() {
        arriveSB = new Arrive<Vector2>(enemyAI, DungeonCrawler.player.playerB2D)
                .setTimeToTarget(0.03f)
                .setArrivalTolerance(16f)
                .setDecelerationRadius(8f);
        return arriveSB;
    }

    public Arrive<Vector2> chargeAtWall(World world) {
        BodyFactory bodyFactory = new BodyFactory();

        if (this.ENEMY_HEALTH < this.MAX_HEALTH / 2 ) {
            this.enemyAI.setMaxLinearSpeed(this.chargingSpeed + 30);
        } else {
            this.enemyAI.setMaxLinearSpeed(this.chargingSpeed);
        }


        if (this.facing == "Up") {
            chargeBody = bodyFactory.createSimpleStaticBody(world,enemyBody.getPosition().x,enemyBody.getPosition().y + 10000);

            this.chargeEntity = new ChargeBox2DSteeringEntity(chargeBody, 1);

            arriveSB = new Arrive<Vector2>(enemyAI,chargeEntity)
                    .setTimeToTarget(0.03f)
                    .setArrivalTolerance(16f)
                    .setDecelerationRadius(8f);
        }
        if (this.facing == "Down") {
            chargeBody = bodyFactory.createSimpleStaticBody(world,enemyBody.getPosition().x,enemyBody.getPosition().y - 10000);

            this.chargeEntity = new ChargeBox2DSteeringEntity(chargeBody, 1);

            arriveSB = new Arrive<Vector2>(enemyAI,chargeEntity)
                    .setTimeToTarget(0.03f)
                    .setArrivalTolerance(16f)
                    .setDecelerationRadius(8f);
        }
        if (this.facing == "Left") {
            chargeBody = bodyFactory.createSimpleStaticBody(world,enemyBody.getPosition().x - 10000,enemyBody.getPosition().y);

            this.chargeEntity = new ChargeBox2DSteeringEntity(chargeBody, 1);

            arriveSB = new Arrive<Vector2>(enemyAI,chargeEntity)
                    .setTimeToTarget(0.03f)
                    .setArrivalTolerance(16f)
                    .setDecelerationRadius(8f);
        }
        if (this.facing == "Right") {
            chargeBody = bodyFactory.createSimpleStaticBody(world,enemyBody.getPosition().x + 10000,enemyBody.getPosition().y);

            this.chargeEntity = new ChargeBox2DSteeringEntity(chargeBody, 1);

            arriveSB = new Arrive<Vector2>(enemyAI,chargeEntity)
                    .setTimeToTarget(0.03f)
                    .setArrivalTolerance(16f)
                    .setDecelerationRadius(8f);
        }
        return arriveSB;
    }

    public Face<Vector2> facePlayer() {
        faceSB = new Face<Vector2>(enemyAI, DungeonCrawler.player.playerB2D)
                .setTimeToTarget(0.03f);
        return faceSB;
    }

    public void update (float delta) {


        float x = this.enemyAI.getLinearVelocity().x;
        float y = this.enemyAI.getLinearVelocity().y;

        float xA = Math.abs(x);
        float yA = Math.abs(y);

        if (!this.locked) {
            if (xA > yA) {
                if (x < 0) {
                    this.facing = "Left";
                } else {
                    this.facing = "Right";
                }
            } else {
                if (y < 0) {
                    this.facing = "Down";
                } else {
                    this.facing = "Up";
                }
            }
        }



        stateMachine.update();
    }

    public StateMachine<BossMinotaur, BossMinotaurState> getStateMachine () {
        return stateMachine;
    }

    public static void renderMinotaur(SpriteBatch batch, TextureRegion minotaurSprite, float x, float y) {

        batch.draw(minotaurSprite,x,y);

    }

}
