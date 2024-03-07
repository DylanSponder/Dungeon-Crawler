package com.mygdx.game.entity;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DRadiusProximity extends Box2DSquareAABBProximity {

    public Box2DRadiusProximity(Steerable<Vector2> owner, World world, float detectionRadius) {
        super(owner, world, detectionRadius);
    }

    @SuppressWarnings("unchecked")
    protected Steerable<Vector2> getSteerable (Fixture fixture) {
        return (Steerable<Vector2>)fixture.getBody().getUserData();
    }

    @Override
    protected boolean accept (Steerable<Vector2> steerable) {
        // The bounding radius of the current body is taken into account
        // by adding it to the radius proximity
        float range = detectionRadius + steerable.getBoundingRadius();

        // Make sure the current body is within the range.
        // Notice we're working in distance-squared space to avoid square root.
        float distanceSquare = steerable.getPosition().dst2(owner.getPosition());

        return distanceSquare <= range * range;
    }

}