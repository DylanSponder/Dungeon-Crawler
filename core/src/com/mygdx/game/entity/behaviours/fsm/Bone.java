package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.box2D.Box2DSteeringUtils;

import static com.mygdx.game.DungeonCrawler.world;

public class Bone {
    public Body boneBody;
    public Fixture boneHitbox;
    public float boneX, boneY;
    public boolean boneCreated;
    public Body skullBody;
    public float orientation;
    public Vector2 outVector;

    public Bone(World world, Body skullBody, float x, float y) {
        this.boneX = x;
        this.boneY = y;
        this.skullBody = skullBody;
        this.orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
        this.outVector = new Vector2();
    }

    public void createBone() {
        //TODO: Create bones that fly out of a skull when destroyed because skulls contain bones OK don't question it
        BodyFactory bodyFactory = new BodyFactory();

        this.boneBody = bodyFactory.createBoneBody(world, skullBody, boneX, boneY);

        //this.boneBody.applyLinearImpulse(0,0,0,0,true);

        this.boneBody.setAngularVelocity(15);

        this.outVector = Box2DSteeringUtils.angleToVector(this.outVector, this.orientation);


        this.boneBody.setLinearVelocity(this.outVector.x*30,this.outVector.y*30);

        //this.boneHitbox = bodyFactory.createBone(world, boneBody, skullBody.getPosition().x, skullBody.getPosition().y);

        this.boneBody.setUserData("Bone");

        this.boneCreated = true;
        }
    }