package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
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
    public float vecMulti;

    public Bone(World world, Body skullBody, float x, float y, boolean multiplied, float initialAngle) {
        this.boneX = x;
        this.boneY = y;
        this.skullBody = skullBody;
        if (!multiplied) {
            this.orientation = MathUtils.random(-MathUtils.PI, MathUtils.PI);
        }
        else {
            float testAngle = MathUtils.random(-MathUtils.PI, MathUtils.PI);
            this.orientation = initialAngle + testAngle;
        }

        this.outVector = new Vector2();
    }

    public void createBone() {
        BodyFactory bodyFactory = new BodyFactory();

        this.boneBody = bodyFactory.createBoneBody(world, skullBody, boneX, boneY);

        //this.boneBody.applyLinearImpulse(0,0,0,0,true);

        this.boneBody.setAngularVelocity(8f);

        this.outVector = Box2DSteeringUtils.angleToVector(this.outVector, this.orientation);

        this.vecMulti = MathUtils.random(35, 45);

        this.boneBody.setLinearVelocity(this.outVector.x*vecMulti,this.outVector.y*vecMulti);

        //this.boneHitbox = bodyFactory.createBone(world, boneBody, skullBody.getPosition().x, skullBody.getPosition().y);

        this.boneBody.setUserData("Bone");

        this.boneCreated = true;
        }

        public static void renderBone(SpriteBatch batch, Sprite boneSprite, float x, float y, float rotation) {
        //57.3f
            batch.draw(boneSprite, x - 8f, y - 7.5f, 8f, 7.5f, 16, 16, 1f, 1f, rotation * 57.3f);
        }
    }