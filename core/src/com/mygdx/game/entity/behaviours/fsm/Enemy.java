package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.HUD;
import com.mygdx.game.entity.projectiles.Skull;
import com.mygdx.game.entity.utils.EnemySkullBox2DSteeringEntity;
import com.mygdx.game.level.objects.Text;

public class Enemy {

    private StateMachine<EnemySkull, EnemySkullState> skullStateMachine;
    private StateMachine<EnemySpider, EnemySpiderState> spiderStateMachine;

    public int enemyID;
    public Body enemyBody, enemyDetectionBody, enemyPlayerDetectionBody;
    public Fixture enemyHitbox;
    public Fixture enemyDetectionRadius;
    public EnemySkullBox2DSteeringEntity enemyAI;
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
    public boolean playerSighted, alerted, playerInRange, rayCastable, inRespawnRange;
    public Ray playerDetectionRay;
    public int sightCounter;
    public Text alertMessage;
    public float timeSinceAlerted;


    public void createEnemy(int enemyID) {
        this.enemyID = enemyID;
    }

    public StateMachine<EnemySkull, EnemySkullState> getSkullStateMachine(int id) {
        return skullStateMachine;
    }

    public StateMachine<EnemySpider, EnemySpiderState> getSpiderStateMachine(int id) {
            return spiderStateMachine;
    }
}
