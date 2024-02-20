package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.ai.steer.utils.RayConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.Box2dRaycastCollisionDetector;

public enum EnemyState implements State<Enemy> {

    WANDER() {
        private float orientation;
        @Override
        public void enter(Enemy enemy) {
            //set a random orientation for the enemy
            float orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
            enemy.enemyAI.setBehaviour(null);
            Wander wander = enemy.wander(enemy.enemyAI, orientation);
            BlendedSteering blendedWanderSteering = enemy.blendSteering(wander, 2.5f, 2);
            enemy.enemyAI.setBehaviour(blendedWanderSteering);
            System.out.println("Wandering aimlessly");
        }

        @Override
        public void update(Enemy enemy) {
            /*
            //enemy sometimes stops moving/gets stuck - hack fix to resume normal wandering
            if (enemy.enemyAI.getLinearVelocity().x > 0.5 && enemy.enemyAI.getLinearVelocity().y >0.5){
                enemy.enemyAI.setOrientation(orientation);
                System.out.println("Get a move on!");
            }

             */
        }

        @Override
        public void exit(Enemy enemy) {

        }

        @Override
        public boolean onMessage(Enemy enemy, Telegram telegram) {
            return false;
        }
    },

    ATTACK() {
        BlendedSteering blendedAttackSteering;
        Flee fleeSB;
        @Override
        public void enter(Enemy enemy) {
            enemy.enemyAI.setBehaviour(null);
            Arrive attack = enemy.attack();
            BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 1, 4);
            enemy.enemyAI.setBehaviour(blendedAttackSteering);
            System.out.println("Time to strike!");

        }

        @Override
        public void update(final Enemy enemy) {
            System.out.println("I'm attacking the player");
            System.out.println(enemy.enemyAI.getLinearVelocity());
            if (enemy.enemyAI.getLinearVelocity().x < 0.5 && enemy.enemyAI.getLinearVelocity().y < 0.5){
                System.out.println("Ack! I'm stuck!");
                /*
                final float stuckTimer = 2f;
                float ori = enemy.enemyAI.getOrientation();

                fleeSB = new Flee<Vector2>(enemy.enemyAI).setTarget(DungeonCrawler.player.playerB2D);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (enemy.enemyAI.getLinearVelocity().x < 0.5 && enemy.enemyAI.getLinearVelocity().y < 0.5){
                            System.out.println("Darn, I'm still stuck!");
                            enemy.enemyAI.setBehaviour(null);
                            enemy.enemyAI.setBehaviour(fleeSB);
                        }
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                            enemy.enemyAI.setBehaviour(null);
                            enemy.enemyAI.setBehaviour(blendedAttackSteering);
                           }
                       }, stuckTimer);
                    }
                }, stuckTimer);
                 */
            }
        }

        @Override
        public void exit(Enemy enemy) {
            System.out.println("No longer attacking the player");
        }

        @Override
        public boolean onMessage(Enemy entity, Telegram telegram) {
            return false;
        }
    };
}