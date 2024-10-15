package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.utils.EnemyBox2DRaycastCollisionDetector;
import com.mygdx.game.entity.utils.EnemyBox2DSteeringEntity;
import com.mygdx.game.entity.utils.PlayerBox2DSteeringEntity;

public class Player {
    public int PLAYER_HEALTH = 12;

    public static PlayerBox2DSteeringEntity playerB2D;
    public float PLAYER_X = 0f, PLAYER_Y = 0f;

    public static Body playerBody;
    public Shopkeeper shopkeeper;
    public int currentRoom;
    public boolean touchingRoom;
    public boolean midAnimationFrame;
    public boolean hasGreekFire;
    public int greekFireUses;

    public Fixture playerDetectionFixture;
    public boolean buyingStock;

    public Player() {
        PLAYER_HEALTH = 12;
        midAnimationFrame = false;
        hasGreekFire = false;
    }

    public Body createPlayer(World world, float PLAYER_X, float PLAYER_Y){
        BodyFactory bf = new BodyFactory();

        this.playerBody = bf.createPlayerBody(world, PLAYER_X, PLAYER_Y);

        //this.playerDetectionFixture = bf.createEnemyDetectionRadius(playerBody,150f);

        this.playerB2D = new PlayerBox2DSteeringEntity(playerBody,10);

        this.playerBody.setUserData("Player");

        return playerBody;
    }
    /*
    public RaycastObstacleAvoidance sightEnemy(){

        RayConfigurationBase<Vector2>[] localRayConfigurations = new RayConfigurationBase[] {
                new CentralRayWithWhiskersConfiguration<Vector2>(playerAI, 20f,
                        15f, 15 * MathUtils.degreesToRadians)};
        rayConfigurations = localRayConfigurations;

        RaycastCollisionDetector<Vector2> raycastCollisionDetector = new EnemyBox2DRaycastCollisionDetector(DungeonCrawler.world);
        raycastObstacleAvoidanceSB = new RaycastObstacleAvoidance<Vector2>(enemyAI, rayConfigurations[0],
                raycastCollisionDetector, 200);

        return raycastObstacleAvoidanceSB;
    }
     */
}
