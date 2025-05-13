package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.DungeonCrawler;

import java.util.Iterator;

import static com.mygdx.game.DungeonCrawler.*;

public enum BossMinotaurState implements State<BossMinotaur> {

    GO_TO_PLAYER() {
        @Override
        public void enter(BossMinotaur enemy) {
            enemy.enemyAI.setBehaviour(null);
            enemy.alerted = true;

            Arrive seekPlayer = enemy.arriveAtPlayer();

            //  BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 3, 6);
            enemy.enemyAI.setBehaviour(seekPlayer);
        }

        @Override
        public void update(final BossMinotaur enemy) {
            //System.out.println(enemy.enemyAI.getLinearVelocity());
            if (enemy.enemyAI.getLinearVelocity().x < 0.5 && enemy.enemyAI.getLinearVelocity().y < 0.5){

            }
        }

        @Override
        public void exit(BossMinotaur enemy) {
            //System.out.println("No longer attacking the player");
        }

        @Override
        public boolean onMessage(BossMinotaur entity, Telegram telegram) {
            return false;
        }
    },

    STOP() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (BossMinotaur enemy){
            enemy.enemyAI.setMaxLinearSpeed(0);
            enemy.enemyAI.setMaxAngularSpeed(0);
            enemy.enemyAI.setMaxAngularAcceleration(0);
            enemy.enemyAI.setBehaviour(null);
        }
        @Override
        public void update(BossMinotaur enemy) {

        }

        @Override
        public void exit(BossMinotaur enemy) {
            if (!enemy.enraged) {
                enemy.enemyAI.setMaxLinearSpeed(enemy.defaultSpeed);
            } else {
                enemy.enemyAI.setMaxLinearSpeed(enemy.enragedSpeed);
            }
            enemy.enemyAI.setMaxAngularSpeed(10000);
            enemy.enemyAI.setMaxAngularAcceleration(10000);
        }

        @Override
        public boolean onMessage(BossMinotaur enemy, Telegram telegram) {
            return false;
        }
    },

    FACE_PLAYER() {
        @Override
        public void enter(BossMinotaur bossMinotaur) {

        }

        @Override
        public void update(BossMinotaur bossMinotaur) {

        }

        @Override
        public void exit(BossMinotaur bossMinotaur) {

        }

        @Override
        public boolean onMessage(BossMinotaur bossMinotaur, Telegram telegram) {
            return false;
        }
    },

    CHARGE_ATTACK() {
        @Override
        public void enter(BossMinotaur enemy) {
            enemy.enemyAI.setBehaviour(null);
            enemy.locked = true;
            enemy.enemyAI.setMaxLinearSpeed(0);
            System.out.println("SPEED 0");

            if (!enemy.charging) {
                Arrive charge = enemy.chargeAtWall(world);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (enemy.ENEMY_HEALTH < enemy.MAX_HEALTH / 2 ) {
                            enemy.enemyAI.setMaxLinearSpeed(enemy.chargingSpeed + 30);
                        } else {
                            enemy.enemyAI.setMaxLinearSpeed(enemy.chargingSpeed);
                        }
                    }
                }, 1.1f);

                //  BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 3, 6);
                enemy.enemyAI.setBehaviour(charge);
                enemy.charging = true;
                System.out.println("ENTER CHARGE");
            }
        }

        @Override
        public void update(BossMinotaur bossMinotaur) {

        }

        @Override
        public void exit(BossMinotaur enemy) {
            enemy.enemyAI.setMaxLinearSpeed(enemy.defaultSpeed);
            enemy.charging = false;
            System.out.println("EXIT CHARGE");
        }

        @Override
        public boolean onMessage(BossMinotaur bossMinotaur, Telegram telegram) {
            return false;
        }
    },

    DIE() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (BossMinotaur enemy){

            Iterator<BossMinotaur> enemyIt = bossMinotaurs.iterator();

            if (enemyIt.hasNext()) {
                bossMinotaurs.remove(enemy);
            }

        }
        @Override
        public void update(BossMinotaur enemy) {

        }

        @Override
        public void exit(BossMinotaur enemy) {

        }

        @Override
        public boolean onMessage(BossMinotaur enemy, Telegram telegram) {
            return false;
        }
    };
}