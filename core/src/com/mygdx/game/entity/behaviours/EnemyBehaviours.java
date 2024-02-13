package com.mygdx.game.entity.behaviours;

import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entity.Box2DSteeringEntity;

public class EnemyBehaviours {
    boolean wandering;
    boolean attacking;
    double randDirection;
    Wander wanderBehaviour;

    float wanderDelay;

    public void EnemyBehaviours() {
        wandering = true;
        attacking = false;
    }

    public Wander wander(Box2DSteeringEntity enemyAI){

        int delayMin = 1, delayMax = 8;
        int dirMin = 1, dirMax = 4;

        wanderDelay = (int)(Math.random() * (delayMax - delayMin + 1)) + delayMin;
        randDirection = (int)(Math.random() * (dirMax - delayMin + 1)) + delayMin;
        final int randDir = (int)randDirection;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                switch (randDir) {
                    case 1:
                        break;
            }
            }
        }, wanderDelay);

        final Wander<Vector2> wanderB = new Wander<>(enemyAI)
                .setWanderRadius(10f)
                .setWanderOrientation(1f);

        return wanderBehaviour;
    }





}
