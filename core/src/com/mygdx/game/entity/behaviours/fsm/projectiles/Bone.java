package com.mygdx.game.entity.behaviours.fsm.projectiles;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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

import static com.mygdx.game.DungeonCrawler.*;

public class Bone {
    public Body boneBody;
    public Fixture boneHitbox;
    public float boneX, boneY;
    public boolean boneCreated;
    public Body skullBody;
    public Vector2 orientationVector;
    public Vector2 outVector;
    public float vecMulti, orientation;
    public boolean aimed;
    public float particleTime;
    public ParticleEffect particleEffect;



    public Bone(World world, Body skullBody, float x, float y, boolean multiplied, boolean aimed, Vector2 orientation) {
        this.boneX = x;
        this.boneY = y;
        this.skullBody = skullBody;
        this.aimed = aimed;
        if (this.aimed) {
            this.orientationVector = orientation;
        } else {
            if (!multiplied) {
                this.orientationVector = orientation;
            }
            else {
                float testAngle = MathUtils.random(-MathUtils.PI, MathUtils.PI);
                this.orientationVector = orientation;
                this.orientation = testAngle;
            }
        }


        this.outVector = new Vector2();
    }

    public Body createBone() {
        this.particleEffect = boneParticleEffect;

        BodyFactory bodyFactory = new BodyFactory();

        this.boneBody = bodyFactory.createBoneBody(world, skullBody, boneX, boneY);

        this.boneBody.setUserData("Bone");

        this.boneBody.setAngularVelocity(4f);

        if (!aimed) {
            this.vecMulti = MathUtils.random(17.5f, 22.5f);
            //35,45

            this.outVector = Box2DSteeringUtils.angleToVector(this.outVector, this.orientation);

            this.boneBody.setLinearVelocity(this.outVector.x*vecMulti,this.outVector.y*vecMulti);
        } else {
            this.vecMulti = 16.25f;
            //32.5

            this.outVector =  this.orientationVector;

            this.boneBody.setLinearVelocity(this.outVector.x*vecMulti,this.outVector.y*vecMulti);
        }

        this.boneCreated = true;
        Body temp = this.boneBody;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (!boneBodiesCollided.contains(temp)) {
                    //destroy bone - may need this in future, currently unused
                }
                }
            },5);
        return this.boneBody;
        }

        public static void renderBone(SpriteBatch batch, Sprite boneSprite, float x, float y, float rotation) {

            batch.draw(boneSprite, x - 8f, y - 7.5f, 8f, 7.5f, 16, 16, 1f, 1f, rotation * 57.3f);
        }
    }