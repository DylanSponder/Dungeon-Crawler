package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
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
import com.mygdx.game.entity.Skull;
import com.mygdx.game.entity.utils.EnemyBox2DSteeringEntity;
import com.mygdx.game.entity.utils.EnemyBox2DRaycastCollisionDetector;
import com.mygdx.game.HUD;
import com.mygdx.game.level.objects.Text;
// import jdk.internal.jshell.tool.StopDetectingInputStream;

import static com.mygdx.game.DungeonCrawler.*;

public class EnemySkull {
    private StateMachine<EnemySkull, EnemySkullState> stateMachine;
    public Body enemyBody, enemyDetectionBody, enemyPlayerDetectionBody;
    public Fixture enemyHitbox;
    public Fixture enemyDetectionRadius;
    public EnemyBox2DSteeringEntity enemyAI;
    //public PlayerBox2DRaycastCollisionDetector playerDetectionRay;
    public ShapeRenderer shapeRenderer;
    public Vector2 tmp = new Vector2();
    public Vector2 tmp2 = new Vector2();
    public Vector2 tmp3 = new Vector2();
    public Vector2 tmp4 = new Vector2();
    public RayConfigurationBase<Vector2>[] rayConfigurations, rayConfigurations2;
    public RaycastObstacleAvoidance<Vector2> raycastObstacleAvoidanceSB, raycastPlayerDetectionSB;
    public Seek seekSB;
    public Arrive arriveSB;
    public BlendedSteering blendedSteeringSB;
    public Vector2 wanderCenter;
    public int ENEMY_HEALTH;
    public boolean debug;
    public Wander<Vector2> wanderSB;
    public IndexedGraph worldMap;
    public HUD hud;
    public Skull skull;
    public int room;
    public boolean playerSighted, alerted, playerInRange, rayCastable;
    public Ray playerDetectionRay;
    public int sightCounter;
    public Text alertMessage;
    public float timeSinceAlerted;

    public EnemySkull(World world, float x, float y) {
        BodyFactory bodyFactory = new BodyFactory();
        shapeRenderer = new ShapeRenderer();

        alertMessage = new Text(DungeonCrawler.defaultFont,"!", Color.WHITE,true,1f,0.0045f,false);

        rayCastable = false;

        sightCounter = 0;

        alerted = false;

        Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);

        ENEMY_HEALTH = 3;

        playerInRange = false;

        //creates an enemy with a body, hitbox and steering entity
        enemyBody = bodyFactory.createSimpleBody(world, x, y);
        enemyDetectionBody = bodyFactory.createSimpleBody(world, x, y);

        enemyHitbox = bodyFactory.createEnemyHitbox(enemyBody, 7f);

        enemyDetectionRadius = bodyFactory.createEnemyDetectionRadius(enemyBody, 120f);

        enemyDetectionRadius.setSensor(true);

        enemyAI = new EnemyBox2DSteeringEntity(enemyBody, 10);
        //playerDetectionRay = new EnemyBox2DSteeringEntity(enemyBody,10);

        stateMachine = new DefaultStateMachine<EnemySkull, EnemySkullState>(this, EnemySkullState.WANDER);
        stateMachine.changeState(EnemySkullState.WANDER);
        this.enemyBody.setUserData("Enemy");

        debug = false;
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

    /*
    public void showAlertMessage() {
        alertMessage.textX = this.enemyAI.getBody().getPosition().x;
        alertMessage.textY = this.enemyAI.getBody().getPosition().y;
    }
    */

    public void throwBoneAtPlayer() {



       // angle =

       // Bone bone = new Bone(world, skull.skullBody, skull.skullBody.getPosition().x, skull.skullBody.getPosition().y, false, 0, true, angle);


    }

    public Wander<Vector2> wander(EnemyBox2DSteeringEntity owner, float wanderOrientation) {
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

                        if (fixture.getBody().getType() == BodyDef.BodyType.StaticBody && fixture.getBody().getUserData() != "Skull") {
                            //sighted = true;
                            //System.out.println(fixture.getBody().getUserData());
                            sightCounter = 0;
                            playerSighted = false;
                            return 0;
                        } else if (fixture.getBody().getType() == BodyDef.BodyType.DynamicBody) {
                            playerSighted = false;
                            this.getStateMachine().changeState(EnemySkullState.WANDER);
                            //this.enemyAI.setBehaviour();
                            if (fixture.getBody().getUserData() != "Player"
                                    && fixture.getUserData() != "Proximity"
                                    && fixture.getUserData() != "EnemyHitbox"
                                    && fixture.getUserData() != "Bone"
                            ) {
                                System.out.println("FIXTURE USER DATA " + fixture.getUserData());
                                System.out.println("BODY USER DATA " + fixture.getUserData());
                                //System.out.println("NOT A PLAYER BUT DYNAMIC");
                                return 0;

                            }
                            else if (fixture.getBody().getUserData() == "Bone"
                            ||        fixture.getUserData() == "Proximity"
                            ||        fixture.getUserData() == "UpArrow"
                            ||        fixture.getUserData() == "DownArrow"
                            ||        fixture.getUserData() == "LeftArrow"
                            ||        fixture.getUserData() == "RightArrow"
                            ||        fixture.getUserData() == "UpSword"
                            ||        fixture.getUserData() == "DownSword"
                            ||        fixture.getUserData() == "LeftSword"
                            ||        fixture.getUserData() == "RightSword"
                            ) {
                                return 1;
                            }

                            else if (fixture.getBody().getUserData() == "Player"

                            ) {
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

        stateMachine.update();
    }
    public StateMachine<EnemySkull, EnemySkullState> getStateMachine () {
        return stateMachine;
    }
}
