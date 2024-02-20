package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.Timepiece;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.Box2DSteeringEntity;
import com.mygdx.game.entity.Box2dRadiusProximity;
import com.mygdx.game.entity.Box2dRaycastCollisionDetector;
import com.mygdx.game.entity.Player;

import static com.mygdx.game.DungeonCrawler.camera;

public class Enemy {

    private StateMachine<Enemy, EnemyState> stateMachine;

    public Body enemyBody, enemyDetectionBody;
    public Stage stage;
    public Fixture enemyHitbox;
    public Fixture enemyDetectionRadius;
    public Box2DSteeringEntity enemyAI;

    public ShapeRenderer shapeRenderer;
    protected Matrix4 transform = new Matrix4();
    public Vector2 tmp = new Vector2();
    public Vector2 tmp2 = new Vector2();

    public RayConfigurationBase<Vector2>[] rayConfigurations;
    public RaycastObstacleAvoidance<Vector2> raycastObstacleAvoidanceSB;
    //public Wander wanderSB;
    public Arrive arriveSB;
    //public Wander wanderSB;
    public BlendedSteering blendedSteeringSB;
    public Vector2 wanderCenter;

    float ENEMY_HEALTH = 3;
    boolean wandering;
    boolean attacking;
    public static float detectionRadius;
    public boolean debug;
    public Wander<Vector2> wanderSB;

    public Enemy(World world, float x, float y) {
        BodyFactory bodyFactory = new BodyFactory();
        shapeRenderer = new ShapeRenderer();
       // wanderSB = new Wander(enemyAI);

        Viewport vp = new ExtendViewport(camera.viewportWidth, camera.viewportHeight);
        stage = new Stage(vp);

        ENEMY_HEALTH = 3;
        detectionRadius = 40;

        //creates an enemy with a body, hitbox and steering entity
        enemyBody = bodyFactory.createEnemyBody(world, x, y);
        enemyDetectionBody = bodyFactory.createEnemyBody(world, x, y);
        enemyHitbox = bodyFactory.createEnemyHitbox(enemyBody, 7.5f);


        enemyDetectionRadius = bodyFactory.createEnemyDetectionRadius(enemyBody,100);
        enemyDetectionRadius.setSensor(true);
        enemyAI = new Box2DSteeringEntity(enemyBody, 10);

        stateMachine = new DefaultStateMachine<Enemy, EnemyState>(this, EnemyState.WANDER);
        stateMachine.changeState(EnemyState.WANDER);
        this.enemyBody.setUserData("Enemy");

        debug = false;

    }

    public Wander<Vector2> wander(Box2DSteeringEntity owner, float wanderOrientation) {
        wanderSB = new Wander<Vector2>(owner) //
                .setFaceEnabled(false) // We want to use Face internally (independent facing is on)
                //.setAlignTolerance(0.001f) // Used by Face
               // .setDecelerationRadius(1) // Used by Face
                .setTimeToTarget(0.1f) // Used by Face
                .setWanderOffset(3) //
                .setWanderOrientation(wanderOrientation)
                .setWanderRadius(1.5f) //
                .setWanderRate(MathUtils.PI2 * 4)
                .setLimiter(new LinearAccelerationLimiter(80));
        debug = true;

      //

        wanderCenter = wanderSB.getWanderCenter();
        System.out.println("HELLO "+ wanderCenter);

        BlendedSteering blendedSteering = blendSteering(wanderSB,1,4);
       // BlendedSteering blendedSteering = enemy.blendSteering(wander, 1, 4);
        enemyAI.setBehaviour(blendedSteering);


        return wanderSB;
    }

    public Arrive<Vector2> attack() {
         arriveSB = new Arrive<Vector2>(enemyAI, DungeonCrawler.player.playerB2D)
                .setTimeToTarget(0.010f)
                .setArrivalTolerance(1f)
                .setDecelerationRadius(0);
        return arriveSB;
    }

    public RaycastObstacleAvoidance avoidObstacle(){
        RayConfigurationBase<Vector2>[] localRayConfigurations = new RayConfigurationBase[] {
                new CentralRayWithWhiskersConfiguration<Vector2>(enemyAI, 17.5f,
                        15f, 15 * MathUtils.degreesToRadians)};
        rayConfigurations = localRayConfigurations;

        RaycastCollisionDetector<Vector2> raycastCollisionDetector = new Box2dRaycastCollisionDetector(DungeonCrawler.world);
        raycastObstacleAvoidanceSB = new RaycastObstacleAvoidance<Vector2>(enemyAI, rayConfigurations[0],
                raycastCollisionDetector, 200);

        return raycastObstacleAvoidanceSB;
    }

    public BlendedSteering blendSteering(SteeringBehavior behaviour, float weight1, float weight2) {


        BlendedSteering<Vector2> blendedSteeringSB = new BlendedSteering<Vector2>(enemyAI);
        blendedSteeringSB
                .add(behaviour,weight1)
                .add(avoidObstacle(),weight2);

        return blendedSteeringSB;
    }

    public void update (float delta) {

        stateMachine.update();
    }
    public StateMachine<Enemy, EnemyState> getStateMachine () {
        return stateMachine;
    }
}