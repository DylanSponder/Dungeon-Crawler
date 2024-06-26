package com.mygdx.game.entity;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class EnemyBox2DRaycastCollisionDetector implements RaycastCollisionDetector<Vector2> {

    World world;
    Box2dRaycastCallback callback;

    public EnemyBox2DRaycastCollisionDetector(World world) {

        this(world, new Box2dRaycastCallback());
    }

    public EnemyBox2DRaycastCollisionDetector(World world, Box2dRaycastCallback callback) {
        this.world = world;
        this.callback = callback;
    }

    @Override
    public boolean collides (Ray<Vector2> ray) {

        return findCollision(null, ray);
    }

    @Override
    public boolean findCollision (Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        callback.collided = false;
        if (!inputRay.start.epsilonEquals(inputRay.end, MathUtils.FLOAT_ROUNDING_ERROR)) {
            callback.outputCollision = outputCollision;
            world.rayCast(callback, inputRay.start, inputRay.end);
        }
        return callback.collided;
    }

    public static class Box2dRaycastCallback implements RayCastCallback {
        public Collision<Vector2> outputCollision;
        public boolean collided;

        public Box2dRaycastCallback () {
        }

        @Override
        public float reportRayFixture (Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

            //checks to see if the body this ray collided with was a sensor e.g. a detectionRadius
            if (!fixture.isSensor()){
                if (outputCollision != null) outputCollision.set(point, normal);
                collided = true;
                if (fixture.getBody().getType() == BodyDef.BodyType.DynamicBody){
                    //System.out.println("I'm colliding with a dynamic object!");
                    //System.out.println(fixture.getBody().getUserData());
                    if (fixture.getBody().getUserData() == "Player") {

                    }
                }
                else if (fixture.getBody().getType() == BodyDef.BodyType.StaticBody){
                    //System.out.println("I'm colliding with a static object!");
                }
            }
            return fraction;
        }
    }
}
