package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.utils.EnemyGhostBox2DSteeringEntity;
import com.mygdx.game.entity.utils.EnemyBox2DRaycastCollisionDetector;
import com.mygdx.game.level.objects.DisplayText;

import static com.mygdx.game.DungeonCrawler.*;

public class EnemyGhost extends Enemy {
    public StateMachine<EnemyGhost, EnemyGhostState> stateMachine;
    public int enemyID;
    public String facing;
    public boolean active;
    public EnemyGhostBox2DSteeringEntity enemyAI;

    public EnemyGhost(World world, float x, float y) {
        BodyFactory bodyFactory = new BodyFactory();
        this.shapeRenderer = new ShapeRenderer();

        this.alertMessage = new DisplayText(DungeonCrawler.defaultFont3,"!", Color.RED,true,1f,0.0045f,false, false, null, 0);

        this.lostSightMessage = new DisplayText(DungeonCrawler.defaultFont4,"?", Color.YELLOW,true,1f,0.0045f,false, false, null, 0);

        this.rayCastable = false;

        this.sightCounter = 0;

        this.alerted = false;

        Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);

        this.ENEMY_HEALTH = 1;

        this.playerInRange = false;

        //creates an enemy with a body, hitbox and steering entity
        this.enemyBody = bodyFactory.createEnemyBody(world, x, y);
        this.enemyDetectionBody = bodyFactory.createEnemyBody(world, x, y);

        this.enemyHitbox = bodyFactory.createEnemyHitbox(enemyBody, 5.95f);

        this.enemyDetectionRadius = bodyFactory.createEnemyDetectionRadius(enemyBody, 100f);

        //enemyDetectionRadius.setSensor(true);

        this.enemyAI = new EnemyGhostBox2DSteeringEntity(enemyBody, 10);
        //playerDetectionRay = new EnemyBox2DSteeringEntity(enemyBody,10);

        this.stateMachine = new DefaultStateMachine<EnemyGhost, EnemyGhostState>(this, EnemyGhostState.WANDER);
        stateMachine.changeState(EnemyGhostState.STOP);
        this.enemyBody.setUserData("Enemy");

        this.enemyHitbox.setUserData("EnemyGhost");

       // this.enemyHitbox.setSensor(true);

        this.debug = false;
/*
        IndexedAStarPathFinder pathFinder;

        FlatTiledNode startNode = worldMap.getNode(enemyAI.getBody().getPosition().x, enemyAI.getBody().getPosition().y);
        FlatTiledNode endNode = worldMap.getNode(DungeonCrawler.player.playerBody.getPosition().x, DungeonCrawler.player.playerBody.getPosition().y);

        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        worldMap = new IndexedGraph() {
            @Override
            public Array<Connection> getConnections(Object fromNode) {
                return null;
            }

            @Override
            public int getIndex(Object node) {
                return 0;
            }

            @Override
            public int getNodeCount() {
                return 0;
            }
        };
        IndexedGraph indexedGraph =  new IndexedGraph() {
            @Override
            public int getIndex(Object node) {
                return 0;
            }

            @Override
            public int getNodeCount() {
                return 0;
            }

            @Override
            public Array<Connection> getConnections(Object fromNode) {
                return null;
            }
        };
 */
    }

    public Wander<Vector2> wander(Steerable<Vector2> owner, float wanderOrientation) {
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
                                && fixture.getBody().getUserData() != "TrapArea") {
                            sightCounter = 0;
                            playerSighted = false;
                            return 0;
                        } else if (fixture.getBody().getType() == BodyDef.BodyType.DynamicBody && !fixture.isSensor() && fixture.getBody().getUserData() != "Enemy") {
                            playerSighted = false;
                            this.getStateMachine().changeState(EnemyGhostState.WANDER);
                            //this.enemyAI.setBehaviour();
                            if (fixture.getBody().getUserData() != "Player"
                                    && fixture.getUserData() != "Proximity"
                                    && fixture.getUserData() != "EnemyHitbox"
                                    && fixture.getUserData() != "Bone"
                                    && fixture.getUserData() != "Candle"
                                    && fixture.getUserData() != "Roof"
                                    && fixture.getUserData() != "TrapArea"
                                //&& !fixture.isSensor()
                            ) {
                                //System.out.println("NOT A PLAYER BUT DYNAMIC");
                                return 0;

                            }

                            else if (fixture.getBody().getUserData() == "Player") {

                                sightCounter++;
                                //number of successful ray hits on the player - more for slower detection
                                if (sightCounter > 10) {
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
        if (this.enemyAI.getLinearVelocity().y > 0
                && this.enemyAI.getLinearVelocity().y > this.enemyAI.getLinearVelocity().x) {
            this.facing = "Up";
        }
        else if (this.enemyAI.getLinearVelocity().y < 0
                && this.enemyAI.getLinearVelocity().y < this.enemyAI.getLinearVelocity().x) {
            this.facing = "Down";
        }
        else if (this.enemyAI.getLinearVelocity().x > 0
                && this.enemyAI.getLinearVelocity().x > this.enemyAI.getLinearVelocity().y) {
            this.facing = "Right";
        }
        else if (this.enemyAI.getLinearVelocity().x < 0
                && this.enemyAI.getLinearVelocity().x < this.enemyAI.getLinearVelocity().y) {
            this.facing = "Left";
        }
        stateMachine.update();
    }
    public StateMachine<EnemyGhost, EnemyGhostState> getStateMachine () {
        return stateMachine;
    }
}