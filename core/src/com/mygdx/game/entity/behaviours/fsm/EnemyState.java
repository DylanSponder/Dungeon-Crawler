package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.math.MathUtils;

public enum EnemyState implements State<Enemy> {

    WANDER() {
        @Override
        public void enter(Enemy enemy) {
            //set a random orientation for the enemy
            float orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
            enemy.enemyAI.setBehaviour(null);
            Wander wander = enemy.wander(enemy.enemyAI, orientation);
            BlendedSteering blendedWanderSteering = enemy.blendSteering(wander, 2.5f, 2);
            enemy.enemyAI.setBehaviour(blendedWanderSteering);
        }

        @Override
        public void update(Enemy enemy) {

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
        @Override
        public void enter(Enemy enemy) {
            enemy.enemyAI.setBehaviour(null);
            Arrive attack = enemy.attack();
            BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 1, 4);
            enemy.enemyAI.setBehaviour(blendedAttackSteering);
        }

        @Override
        public void update(final Enemy enemy) {
            //System.out.println(enemy.enemyAI.getLinearVelocity());
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