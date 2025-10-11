package com.mygdx.game.entity.behaviours.fsm;

import box2dLight.ChainLight;
import box2dLight.PointLight;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.utils.EnemyCyclopsBox2DSteeringEntity;
import com.mygdx.game.entity.utils.EnemySkullBox2DSteeringEntity;
import com.mygdx.game.entity.utils.EnemyBox2DRaycastCollisionDetector;
import com.mygdx.game.level.objects.Text;
// import jdk.internal.jshell.tool.StopDetectingInputStream;

import static com.mygdx.game.DungeonCrawler.*;

public class EnemyCyclops extends Enemy {
    public StateMachine<EnemyCyclops, EnemyCyclopsState> stateMachine;
    public int enemyID;
    public EnemyCyclopsBox2DSteeringEntity enemyAI;
    public ChainLight eyeLight;
    public String facing;
    public boolean firingBeam, canTurn, turnOff, active, locked;
    public float turnDelay;
    public Body beamBody;

    public EnemyCyclops(World world, float x, float y) {
        BodyFactory bodyFactory = new BodyFactory();

        this.shapeRenderer = new ShapeRenderer();

        this.alertMessage = new Text(DungeonCrawler.defaultFont3,"!", Color.RED,true,1f,0.0045f,false, false, null, 0);

        this.lostSightMessage = new Text(DungeonCrawler.defaultFont4,"?", Color.YELLOW,true,1f,0.0045f,false, false, null, 0);

        this.rayCastable = false;

        this.sightCounter = 0;

        this.alerted = false;

        Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);

        this.ENEMY_HEALTH = 3;

        this.playerInRange = false;

        this.turnDelay = 0.1f;
        this.canTurn = true;

        //creates an enemy with a body, hitbox and steering entity
        this.enemyBody = bodyFactory.createSimpleDynamicBody(world, x, y);
        this.enemyDetectionBody = bodyFactory.createSimpleDynamicBody(world, x, y);

        this.enemyHitbox = bodyFactory.createEnemyHitbox(enemyBody, 5.95f);

        this.enemyDetectionRadius = bodyFactory.createEnemyDetectionRadius(enemyBody, 100f);

        //enemyDetectionRadius.setSensor(true);

        this.enemyAI = new EnemyCyclopsBox2DSteeringEntity(enemyBody, 10);
        //playerDetectionRay = new EnemyBox2DSteeringEntity(enemyBody,10);

        stateMachine = new DefaultStateMachine<EnemyCyclops, EnemyCyclopsState>(this, EnemyCyclopsState.WANDER);
        stateMachine.changeState(EnemyCyclopsState.STOP);
        this.enemyBody.setUserData("Enemy");
        this.enemyHitbox.setUserData("EnemyCyclops");

        this.debug = false;

        this.eyeLight = new ChainLight(rayHandler, 60, new Color(0.3f,0,1f,0.5f),50,1,new float[]{0,0,0,40,20,40,20,0,0,0});

    }

    /*
    public void showAlertMessage() {
        alertMessage.textX = this.enemyAI.getBody().getPosition().x;
        alertMessage.textY = this.enemyAI.getBody().getPosition().y;
    }
    */

    public static void renderEyebeam(SpriteBatch batch, TextureRegion currentFrame, float x, float y, boolean upDown) {

        if (upDown) {
            batch.draw(currentFrame, x, y,0,0,64,8,1,1,180);

        } else {
            batch.draw(currentFrame, x, y);
        }
    }
    public Wander<Vector2> wander(EnemyCyclopsBox2DSteeringEntity owner, float wanderOrientation) {
        wanderSB = new Wander<Vector2>(owner)
                .setFaceEnabled(false)
                //.setAlignTolerance(0.001f)
                .setDecelerationRadius(5)
                .setTimeToTarget(0.2f)
                .setWanderOffset(3)
                .setWanderOrientation(wanderOrientation)
                .setWanderRadius(1.5f)
                .setWanderRate(MathUtils.PI2 * 4)
                .setLimiter(new LinearAccelerationLimiter(80));
        debug = true;

        wanderCenter = wanderSB.getWanderCenter();

        BlendedSteering blendedSteering = blendSteering(wanderSB,avoidObstacle(), 1,4);
        enemyAI.setBehaviour(blendedSteering);

        return wanderSB;
    }

    public Seek<Vector2> seekPlayer() {
        seekSB = new Seek<Vector2>(enemyAI, DungeonCrawler.player.playerB2D);
        return seekSB;
    }

    public Arrive<Vector2> arriveAtPlayer() {
        arriveSB = new Arrive<Vector2>(enemyAI, DungeonCrawler.player.playerB2D)
                .setTimeToTarget(0.03f)
                .setArrivalTolerance(16f)
                .setDecelerationRadius(8f);
        return arriveSB;
    }

    public RaycastObstacleAvoidance avoidObstacle(){

        RayConfigurationBase<Vector2>[] localRayConfigurations = new RayConfigurationBase[] {
                new CentralRayWithWhiskersConfiguration<Vector2>(enemyAI, 15f,
                        10f, 15 * MathUtils.degreesToRadians)};
        rayConfigurations = localRayConfigurations;

        RaycastCollisionDetector<Vector2> raycastCollisionDetector = new EnemyBox2DRaycastCollisionDetector(DungeonCrawler.world);
        raycastObstacleAvoidanceSB = new RaycastObstacleAvoidance<Vector2>(enemyAI, rayConfigurations[0],
                raycastCollisionDetector, 200);

        return raycastObstacleAvoidanceSB;
    }

    public RaycastObstacleAvoidance detectPlayer(){
        if (this.rayCastable) {

            Vector2 translatedCoords = new Vector2();
            translatedCoords.x = this.enemyAI.getPosition().x;
            translatedCoords.y = this.enemyAI.getPosition().y;

            //Ray<Vector2> ray = new Ray<>(enemyBody.getPosition(),player.playerBody.getPosition());

            playerDetectionRay = new Ray<>(translatedCoords,player.playerBody.getPosition());


            world.rayCast((fixture, point, normal, fraction) -> {

                        boolean sighted = false;

                        if (fixture.getBody().getType() == BodyDef.BodyType.StaticBody && fixture.getBody().getUserData() != "Skull" && fixture.getBody().getUserData() != "Fire"
                                && fixture.getBody().getUserData() != "Candle"
                                && fixture.getBody().getUserData() != "Cobweb"
                                && fixture.getBody().getUserData() != "Roof"
                                && fixture.getBody().getUserData() != "TrapArea"
                                && fixture.getBody().getUserData() != "Water"
                                && fixture.getBody().getUserData() != "Stem"
                                && fixture.getBody().getUserData() != "Statue"
                                && fixture.getBody().getUserData() != "Pit") {
                            //sighted = true;
                            //System.out.println(fixture.getBody().getUserData());
                            sightCounter = 0;
                            playerSighted = false;
                            return 0;
                        } else if (fixture.getBody().getType() == BodyDef.BodyType.DynamicBody && !fixture.isSensor() && fixture.getBody().getUserData() != "Enemy") {
                            playerSighted = false;
                            if (!firingBeam) {
                                this.getStateMachine().changeState(EnemyCyclopsState.WANDER);
                            }

                            //this.enemyAI.setBehaviour();
                            if (fixture.getBody().getUserData() != "Player"
                                    && fixture.getUserData() != "Proximity"
                                    && fixture.getUserData() != "EnemyHitbox"
                                    && fixture.getUserData() != "Bone"
                                //&& !fixture.isSensor()
                            ) {
                                //System.out.println("NOT A PLAYER BUT DYNAMIC");
                                return 0;

                            }

                            else if (fixture.getBody().getUserData() == "Player") {

                                sightCounter++;
                                //number of successful ray hits on the player - more for slower detection
                                if (sightCounter > 5) {
                                    //System.out.println(fraction);

                                    //enemy has seen the player and will reach appropriate distance
                                    playerSighted = true;
                                    return 1;
                                }
                                return 1;
                            }
                            return 1;
                        }
                        else {
                            return 1;
                        }
                    },
                    translatedCoords, player.playerBody.getPosition());
        }
        return raycastPlayerDetectionSB;
    }


    public BlendedSteering blendSteering(SteeringBehavior behaviour, SteeringBehavior behaviour2, float weight1, float weight2) {

        BlendedSteering<Vector2> blendedSteeringSB = new BlendedSteering<Vector2>(enemyAI);
        blendedSteeringSB
                .add(behaviour,weight1)
                .add(avoidObstacle(),weight2);

        return blendedSteeringSB;
    }

    public BlendedSteering blendTripleSteering(SteeringBehavior behaviour, SteeringBehavior behaviour2, SteeringBehavior behaviour3, float weight1, float weight2, float weight3) {

        BlendedSteering<Vector2> blendedSteeringSB = new BlendedSteering<Vector2>(enemyAI);
        blendedSteeringSB
                .add(behaviour,weight1)
                .add(avoidObstacle(),weight2)
                .add(behaviour3, weight3);

        return blendedSteeringSB;
    }

    public void update (float delta) {
        /*
        if (!canTurn && turnOff) {
            turnOff = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    canTurn = true;
                }
            }, turnDelay);
        } else {
            if (this.enemyAI.getLinearVelocity().y > 0
                    && (this.enemyAI.getLinearVelocity().y > this.enemyAI.getLinearVelocity().x)) {
                this.facing = "Up";
            }
            else if (this.enemyAI.getLinearVelocity().y < 0
                    && (this.enemyAI.getLinearVelocity().y < this.enemyAI.getLinearVelocity().x)) {
                this.facing = "Down";
            }
            else if (this.enemyAI.getLinearVelocity().x > 0
                    && (this.enemyAI.getLinearVelocity().x > this.enemyAI.getLinearVelocity().y)) {
                this.facing = "Right";
            }
            else if (this.enemyAI.getLinearVelocity().x < 0
                    && (this.enemyAI.getLinearVelocity().x < this.enemyAI.getLinearVelocity().y)) {
                this.facing = "Left";
            }
            canTurn = false;
        }

         */
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
    public StateMachine<EnemyCyclops, EnemyCyclopsState> getStateMachine () {
        return stateMachine;
    }
}
