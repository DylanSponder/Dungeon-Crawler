package com.mygdx.game.entity;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.box2D.Box2DSteeringUtils;

public class Box2DSteeringEntity implements Steerable<Vector2> {
    public Vector3 halfExtents;
    Body body;
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;
    SteeringBehavior<Vector2> behaviour;
    SteeringAcceleration<Vector2> steeringOutput;

    public Box2DSteeringEntity(Body body, float boundingRadius){
        this.body = body;
        this.boundingRadius = boundingRadius;

        this.maxLinearSpeed = 175;
        this.maxLinearAcceleration = 1000;
        this.maxAngularSpeed = 1;
        this.maxAngularAcceleration = 1000;


        this.tagged = false;

        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    public void update(float delta){
        if(behaviour!= null){
            behaviour.calculateSteering(steeringOutput);
            applySteering(delta);
        }
    }

    private void applySteering(float delta) {
        boolean anyAccelerations = false;

        if(!steeringOutput.linear.isZero()){
            Vector2 force = steeringOutput.linear;
            force.scl(1000,1000);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        if (steeringOutput.angular != 0) {
            // this method internally scales the torque by deltaTime
            body.applyTorque(steeringOutput.angular, true);
            anyAccelerations = true;
        }
        else {
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * delta); // this is superfluous if independentFacing is always true
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if (anyAccelerations){
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();

            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed){
                  body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
        }
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Box2DSteeringUtils.vectorToAngle(vector);
        //return this.vectorToAngle(vector); - do not use - returns StackOverflowError
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Box2DSteeringUtils.angleToVector(outVector,angle);
        //return this.angleToVector(outVector,angle); - do not use - returns StackOverflowError
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public Body getBody(){
        return body;
    }

    public void setBehaviour(SteeringBehavior<Vector2> behaviour){
        this.behaviour = behaviour;
    }

    public SteeringBehavior<Vector2> getBehaviour(){
        return behaviour;
    }
}
