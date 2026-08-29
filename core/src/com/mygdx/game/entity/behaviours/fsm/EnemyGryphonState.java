package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CreateAssets;

import java.util.Iterator;

import static com.mygdx.game.DungeonCrawler.enemyGryphons;

public enum EnemyGryphonState implements State<EnemyGryphon> {

    WANDER() {
        @Override
        public void enter(EnemyGryphon enemy) {
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
        public void update(EnemyGryphon enemy) {
            enemy.alerted = false;
        }

        @Override
        public void exit(EnemyGryphon enemy) {

        }

        @Override
        public boolean onMessage(EnemyGryphon enemy, Telegram telegram) {
            return false;
        }
    },

    DETECT() {
        @Override
        public void enter(EnemyGryphon enemy) {
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
        public void update(EnemyGryphon enemy) {

        }

        @Override
        public void exit(EnemyGryphon enemy) {

        }

        @Override
        public boolean onMessage(EnemyGryphon enemy, Telegram telegram) {
            return false;
        }
    },

    GO_TO_PLAYER() {
        @Override
        public void enter(EnemyGryphon enemy) {
            enemy.enemyAI.setBehaviour(null);
            enemy.alerted = true;
            enemy.enemyAI.setMaxLinearAcceleration(200);

            Arrive seekPlayer = enemy.arriveAtPlayer();

          //  BlendedSteering blendedAttackSteering = enemy.blendSteering(attack, 3, 6);
            enemy.enemyAI.setBehaviour(seekPlayer);
        }

        @Override
        public void update(final EnemyGryphon enemy) {
            //System.out.println(enemy.enemyAI.getLinearVelocity());

            if (enemy.flyingUp && enemy.swapFlyingDirection) {
                enemy.flyingUp = false;
                enemy.swapFlyingDirection = false;

                for (float i = 0; i < 20; i++) {
                    float finalI = i;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (enemy.enemyBody.getLinearVelocity().x > 0) {
                                enemy.enemyBody.applyLinearImpulse(10000,25000,0,0,true);
                                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x + 2,enemy.enemyBody.getLinearVelocity().y + finalI);
                            } else if (enemy.enemyBody.getLinearVelocity().x < 0) {
                                enemy.enemyBody.applyLinearImpulse(-10000,25000,0,0,true);
                                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x - 2,enemy.enemyBody.getLinearVelocity().y + finalI);
                            } else {
                                enemy.enemyBody.applyLinearImpulse(0,25000,0,0,true);
                                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,enemy.enemyBody.getLinearVelocity().y + finalI);
                            }
                            /*
                            if (enemy.enemyBody.getLinearVelocity().x > 0) {
                                enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x + 2,enemy.enemyBody.getLinearVelocity().y + finalI);
                            } else if (enemy.enemyBody.getLinearVelocity().x < 0) {
                                enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x - 2,enemy.enemyBody.getLinearVelocity().y + finalI);
                            } else {
                                enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,enemy.enemyBody.getLinearVelocity().y + finalI);
                            }

                             */
                        }
                    }, finalI / 15);
                }

                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,25);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,12);
                    }
                }, 1f);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        enemy.swapFlyingDirection = true;
                    }
                }, 2f);

            } else if (!enemy.flyingUp && enemy.swapFlyingDirection) {
                enemy.flyingUp = true;
                enemy.swapFlyingDirection = false;

                for (float i = 0; i < 20; i++) {
                    float finalI = i;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if (enemy.enemyBody.getLinearVelocity().x > 0) {
                                enemy.enemyBody.applyLinearImpulse(10000,-25000,0,0,true);
                                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x + 2,enemy.enemyBody.getLinearVelocity().y + finalI);
                            } else if (enemy.enemyBody.getLinearVelocity().x < 0) {
                                enemy.enemyBody.applyLinearImpulse(-10000,-25000,0,0,true);
                                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x - 2,enemy.enemyBody.getLinearVelocity().y + finalI);
                            } else {
                                enemy.enemyBody.applyLinearImpulse(0,-25000,0,0,true);
                                //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,enemy.enemyBody.getLinearVelocity().y + finalI);
                            }
                            /*
                            if (enemy.enemyBody.getLinearVelocity().x > 0) {
                                enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x + 2,enemy.enemyBody.getLinearVelocity().y - finalI);
                            } else if (enemy.enemyBody.getLinearVelocity().x < 0) {
                                enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x - 2,enemy.enemyBody.getLinearVelocity().y - finalI);
                            } else {
                                enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,enemy.enemyBody.getLinearVelocity().y - finalI);
                            }

                             */
                        }
                    }, finalI / 15);
                }
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        //enemy.enemyBody.setLinearVelocity(enemy.enemyBody.getLinearVelocity().x,-12);
                    }
                }, 1f);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        enemy.swapFlyingDirection = true;
                    }
                }, 2f);
            }

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
        public void exit(EnemyGryphon enemy) {
            //System.out.println("No longer attacking the player");
            enemy.enemyAI.setMaxLinearAcceleration(60);
            //enemy.enemyBody.setLinearVelocity(0,0);
        }

        @Override
        public boolean onMessage(EnemyGryphon entity, Telegram telegram) {
            return false;
        }
    },

    STOP() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (EnemyGryphon enemy){
            enemy.enemyAI.setMaxLinearSpeed(0);
            enemy.enemyAI.setMaxAngularSpeed(0);
            enemy.enemyAI.setMaxAngularAcceleration(0);
            enemy.enemyAI.setBehaviour(null);
        }
        @Override
        public void update(EnemyGryphon enemy) {

        }

        @Override
        public void exit(EnemyGryphon enemy) {

        }

        @Override
        public boolean onMessage(EnemyGryphon enemy, Telegram telegram) {
            return false;
        }
    },

    DIE() {
        final CreateAssets tx = CreateAssets.getInstance();
        @Override
        public void enter (EnemyGryphon enemy){
            //Gryphon Gryphon = new Gryphon(world, enemy.enemyBody.getPosition().x, enemy.enemyBody.getPosition().y);

            Iterator<EnemyGryphon> enemyIt = enemyGryphons.iterator();

            if (enemyIt.hasNext()) {
                //enemy.die(enemy.enemyBody.getPosition().x, enemy.enemyBody.getPosition().y);
                enemyGryphons.remove(enemy);
                //Gryphon.createGryphon();
            }

        }
        @Override
        public void update(EnemyGryphon enemy) {

        }

        @Override
        public void exit(EnemyGryphon enemy) {

        }

        @Override
        public boolean onMessage(EnemyGryphon enemy, Telegram telegram) {
            return false;
        }
    };
}