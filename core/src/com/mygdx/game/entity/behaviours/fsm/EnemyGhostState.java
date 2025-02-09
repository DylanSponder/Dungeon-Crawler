package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.CreateAssets;

import java.util.Iterator;

import static com.mygdx.game.DungeonCrawler.enemyGhosts;
import static com.mygdx.game.DungeonCrawler.enemySpiders;

public enum EnemyGhostState implements State<EnemyGhost> {

    WANDER() {
        @Override
        public void enter(EnemyGhost enemy) {
            //set a random orientation for the enemy
            float orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
            //enemy.alerted = false;
            enemy.enemyAI.setBehaviour(null);
            Wander wander = enemy.wander(enemy.enemyAI, orientation);
            BlendedSteering blendedWanderSteering = enemy.blendSteering(wander, enemy.avoidObstacle(), 2.5f, 2);
            enemy.enemyAI.setBehaviour(blendedWanderSteering);
            //BlendedSteering blendedWanderSteering = enemy.blendTripleSteering(wander, enemy.avoidObstacle(), enemy.detectPlayer(), 2.5f, 2.5f, 0.5f);
            //enemy.enemyAI.setBehaviour(blendedWanderSteering);
            //BlendedSteering blendedSightSteering = enemy.blendSteering(enemy.detectPlayer(), 2.5f, 2);
            //enemy.playerDetectionRay.setBehaviour(enemy.detectPlayer());
        }

        @Override
        public void update(EnemyGhost enemy) {
            enemy.alerted = false;
        }

        @Override
        public void exit(EnemyGhost enemy) {

        }

        @Override
        public boolean onMessage(EnemyGhost enemy, Telegram telegram) {
            return false;
        }
    },

    DETECT() {
        @Override
        public void enter(EnemyGhost enemy) {
            //set a random orientation for the enemy
            float orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
            //enemy.playerDetectionRay.setBehaviour(null);
            Wander wander = enemy.wander(enemy.enemyAI, orientation);
            //BlendedSteering blendedWanderSteering = enemy.blendSteering(wander, enemy.avoidObstacle(), 2.5f, 2);
            //enemy.enemyAI.setBehaviour(blendedWanderSteering);
            //BlendedSteering blendedWanderSteering = enemy.blendTripleSteering(wander, enemy.avoidObstacle(), enemy.detectPlayer(), 2.5f, 2.5f, 0.5f);
            //enemy.enemyAI.setBehaviour(blendedWanderSteering);
            //BlendedSteering blendedSightSteering = enemy.blendSteering(wander, enemy.detectPlayer(), 2.5f, 2);
            //enemy.playerDetectionRay.setBehaviour(blendedSightSteering);
        }

        @Override
        public void update(EnemyGhost enemy) {

        }

        @Override
        public void exit(EnemyGhost enemy) {

        }

        @Override
        public boolean onMessage(EnemyGhost enemy, Telegram telegram) {
            return false;
        }
    },

    GO_TO_PLAYER() {
        @Override
        public void enter(EnemyGhost enemy) {
            enemy.enemyAI.setBehaviour(null);
            enemy.alerted = true;

            Arrive seekPlayer = enemy.arriveAtPlayer();

            //  BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 3, 6);
            enemy.enemyAI.setBehaviour(seekPlayer);
        }

        @Override
        public void update(final EnemyGhost enemy) {
            if (enemy.enemyAI.getLinearVelocity().x < 0.5 && enemy.enemyAI.getLinearVelocity().y < 0.5){
            }
        }

        @Override
        public void exit(EnemyGhost enemy) {
        }

        @Override
        public boolean onMessage(EnemyGhost entity, Telegram telegram) {
            return false;
        }
    },

    STOP() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (EnemyGhost enemy){
            enemy.enemyAI.setMaxLinearSpeed(0);
            enemy.enemyAI.setMaxAngularSpeed(0);
            enemy.enemyAI.setMaxAngularAcceleration(0);
            enemy.enemyAI.setBehaviour(null);
        }
        @Override
        public void update(EnemyGhost enemy) {

        }

        @Override
        public void exit(EnemyGhost enemy) {

        }

        @Override
        public boolean onMessage(EnemyGhost enemy, Telegram telegram) {
            return false;
        }
    },

    DIE() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (EnemyGhost enemy){
            //Skull skull = new Skull(world, enemy.enemyBody.getPosition().x, enemy.enemyBody.getPosition().y);

            Iterator<EnemyGhost> enemyIt = enemyGhosts.iterator();

            if (enemyIt.hasNext()) {
                //enemy.die(enemy.enemyBody.getPosition().x, enemy.enemyBody.getPosition().y);
                //enemy.alive = false;
                enemyGhosts.remove(enemy);
                //skull.createSkull();
            }

        }
        @Override
        public void update(EnemyGhost enemy) {

        }

        @Override
        public void exit(EnemyGhost enemy) {

        }

        @Override
        public boolean onMessage(EnemyGhost enemy, Telegram telegram) {
            return false;
        }
    };
}