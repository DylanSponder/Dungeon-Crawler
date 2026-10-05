package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.Random;

import java.util.Iterator;

import static com.mygdx.game.DungeonCrawler.*;

public enum EnemyCrabState implements State<EnemyCrab> {

    WANDER() {
        @Override
        public void enter(EnemyCrab enemy) {
            //set a random orientation for the enemy
            float orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
            //enemy.alerted = false;
            enemy.enemyAI.setBehaviour(null);


           if (!enemy.choseADirection) {
               int dir = Random.randomInt(4, 1);

               if (enemy.currentDirection != 0) {
                   if (enemy.currentDirection == 1) {
                       int dir1 = Random.randomInt(4, 2);

                       switch (dir) {
                           case 2:
                               enemy.enemyBody.setLinearVelocity(12,0);
                               enemy.currentDirection = 2;
                               break;
                           case 3:
                               enemy.enemyBody.setLinearVelocity(0,-12);
                               enemy.currentDirection = 3;
                               break;
                           case 4:
                               enemy.enemyBody.setLinearVelocity(-12,0);
                               enemy.currentDirection = 4;
                               break;
                       }
                   }
                   if (enemy.currentDirection == 2) {

                       int dir2 = Random.randomInt(4, 1);
                       while (dir2 == 2) {
                           dir2 = Random.randomInt(4, 1);
                       }

                       switch (dir) {
                           case 1:
                               enemy.enemyBody.setLinearVelocity(0,12);
                               enemy.currentDirection = 1;
                               break;
                           case 3:
                               enemy.enemyBody.setLinearVelocity(0,-12);
                               enemy.currentDirection = 3;
                               break;
                           case 4:
                               enemy.enemyBody.setLinearVelocity(-12,0);
                               enemy.currentDirection = 4;
                               break;
                       }
                   }
                   if (enemy.currentDirection == 3) {
                       int dir3 = Random.randomInt(4, 1);
                       while (dir3 == 3) {
                           dir3 = Random.randomInt(4, 1);
                       }

                       switch (dir) {
                           case 1:
                               enemy.enemyBody.setLinearVelocity(0,12);
                               enemy.currentDirection = 1;
                               break;
                           case 2:
                               enemy.enemyBody.setLinearVelocity(12,0);
                               enemy.currentDirection = 2;
                               break;
                           case 4:
                               enemy.enemyBody.setLinearVelocity(-12,0);
                               enemy.currentDirection = 4;
                               break;
                       }
                   }
                   if (enemy.currentDirection == 4) {
                       int dir1 = Random.randomInt(3, 1);

                       switch (dir) {
                           case 1:
                               enemy.enemyBody.setLinearVelocity(0,12);
                               enemy.currentDirection = 1;
                               break;
                           case 2:
                               enemy.enemyBody.setLinearVelocity(12,0);
                               enemy.currentDirection = 2;
                               break;
                           case 3:
                               enemy.enemyBody.setLinearVelocity(0,-12);
                               enemy.currentDirection = 3;
                               break;
                       }
                   }
               } else {
                   switch (dir) {
                       case 1:
                           enemy.enemyBody.setLinearVelocity(0,12);
                           enemy.currentDirection = 1;
                           break;
                       case 2:
                           enemy.enemyBody.setLinearVelocity(12,0);
                           enemy.currentDirection = 2;
                           break;
                       case 3:
                           enemy.enemyBody.setLinearVelocity(0,-12);
                           enemy.currentDirection = 3;
                           break;
                       case 4:
                           enemy.enemyBody.setLinearVelocity(-12,0);
                           enemy.currentDirection = 4;
                           break;
                   }
               }




           }

            enemy.choseADirection = true;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (enemy.enemyBody.getInertia() == 0f) {
                       // enemy.choseADirection = false;
                    }
                }
            }, 0.1f);


            //Wander wander = enemy.wander(enemy.enemyAI, orientation);
            //BlendedSteering blendedWanderSteering = enemy.blendSteering(wander, enemy.avoidObstacle(), 2.5f, 2);
            //enemy.enemyAI.setBehaviour(blendedWanderSteering);
            //BlendedSteering blendedWanderSteering = enemy.blendTripleSteering(wander, enemy.avoidObstacle(), enemy.detectPlayer(), 2.5f, 2.5f, 0.5f);
            //enemy.enemyAI.setBehaviour(blendedWanderSteering);
            //BlendedSteering blendedSightSteering = enemy.blendSteering(enemy.detectPlayer(), 2.5f, 2);
            //enemy.playerDetectionRay.setBehaviour(enemy.detectPlayer());
        }

        @Override
        public void update(EnemyCrab enemy) {
            enemy.alerted = false;
        }

        @Override
        public void exit(EnemyCrab enemy) {

        }

        @Override
        public boolean onMessage(EnemyCrab enemy, Telegram telegram) {
            return false;
        }
    },

    DETECT() {
        @Override
        public void enter(EnemyCrab enemy) {
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
        public void update(EnemyCrab enemy) {

        }

        @Override
        public void exit(EnemyCrab enemy) {

        }

        @Override
        public boolean onMessage(EnemyCrab enemy, Telegram telegram) {
            return false;
        }
    },

    GO_TO_PLAYER() {
        @Override
        public void enter(EnemyCrab enemy) {
            enemy.enemyAI.setBehaviour(null);
            enemy.alerted = true;
            enemy.enemyAI.setMaxLinearAcceleration(200);

            Arrive seekPlayer = enemy.arriveAtPlayer();

          //  BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 3, 6);
            enemy.enemyAI.setBehaviour(seekPlayer);
        }

        @Override
        public void update(final EnemyCrab enemy) {
            //System.out.println(enemy.enemyAI.getLinearVelocity());
            if (enemy.enemyAI.getLinearVelocity().x < 0.5 && enemy.enemyAI.getLinearVelocity().y < 0.5){
                //System.out.println("Ack! I'm stuck!");
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
        public void exit(EnemyCrab enemy) {
            //System.out.println("No longer attacking the player");
            enemy.enemyAI.setMaxLinearAcceleration(60);
        }

        @Override
        public boolean onMessage(EnemyCrab entity, Telegram telegram) {
            return false;
        }
    },

    STOP() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (EnemyCrab enemy){
            enemy.enemyAI.setMaxLinearSpeed(0);
            enemy.enemyAI.setMaxAngularSpeed(0);
            enemy.enemyAI.setMaxAngularAcceleration(0);
            enemy.enemyAI.setBehaviour(null);
        }
        @Override
        public void update(EnemyCrab enemy) {

        }

        @Override
        public void exit(EnemyCrab enemy) {

        }

        @Override
        public boolean onMessage(EnemyCrab enemy, Telegram telegram) {
            return false;
        }
    },

    DIE() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (EnemyCrab enemy){
            //Crab Crab = new Crab(world, enemy.enemyBody.getPosition().x, enemy.enemyBody.getPosition().y);

            Iterator<EnemyCrab> enemyIt = enemyCrabs.iterator();

            if (enemyIt.hasNext()) {
                //enemy.die(enemy.enemyBody.getPosition().x, enemy.enemyBody.getPosition().y);
                enemy.scuttleBodyUp.destroyFixture(enemy.upScuttleHitbox);
                enemy.scuttleBodyDown.destroyFixture(enemy.downScuttleHitbox);
                enemy.scuttleBodyLeft.destroyFixture(enemy.leftScuttleHitbox);
                enemy.scuttleBodyRight.destroyFixture(enemy.rightScuttleHitbox);
                enemyCrabs.remove(enemy);

                //Crab.createCrab();
            }

        }
        @Override
        public void update(EnemyCrab enemy) {

        }

        @Override
        public void exit(EnemyCrab enemy) {

        }

        @Override
        public boolean onMessage(EnemyCrab enemy, Telegram telegram) {
            return false;
        }
    };
}