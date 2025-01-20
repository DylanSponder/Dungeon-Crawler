package com.mygdx.game.entity.behaviours.fsm.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.box2D.Box2DSteeringUtils;
import com.mygdx.game.level.objects.Cobweb;

import static com.mygdx.game.DungeonCrawler.*;

public class Web {
    public Body webBody;
    public Fixture webHitbox;
    public float webX, boneY;
    public boolean webCreated;
    public Body skullBody;
    public Vector2 orientationVector;
    public Vector2 outVector;
    public float vecMulti, orientation, exitAngle;
    public boolean aimed;

    public Web(World world, Body skullBody, float x, float y, boolean multiplied, boolean aimed, Vector2 orientation) {
        this.webX = x;
        this.boneY = y;
        this.skullBody = skullBody;
        this.aimed = aimed;
        if (this.aimed) {
            this.orientationVector = orientation;
        } else {
            if (!multiplied) {
                this.orientationVector = orientation;
                System.out.println(MathUtils.random(-MathUtils.PI, MathUtils.PI));
            }
            else {
                float testAngle = MathUtils.random(-MathUtils.PI, MathUtils.PI);
                this.orientationVector = orientation;
                this.orientation = testAngle;
            }
        }

        this.outVector = new Vector2();
    }

    public Body createWeb(float angle) {
        BodyFactory bodyFactory = new BodyFactory();

        this.webBody = bodyFactory.createWebBody(world, skullBody, webX, boneY, angle);

        this.webBody.setUserData("Web");

        //this.webBody.applyLinearImpulse(0,0,0,0,true);

        if (!aimed) {
            this.vecMulti = MathUtils.random(20, 40);

            this.outVector = Box2DSteeringUtils.angleToVector(this.outVector, this.orientation);

            this.webBody.setLinearVelocity(this.outVector.x*vecMulti,this.outVector.y*vecMulti);
        } else {
            this.vecMulti = MathUtils.random(75, 80);

            this.outVector =  this.orientationVector;



            this.webBody.setLinearVelocity(this.outVector.x*vecMulti,this.outVector.y*vecMulti);
        }

        this.webCreated = true;
        Body temp = this.webBody;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (!webBodiesCollected.contains(temp)) {
                    //destroy web - may need this in future, currently unused
                }
            }
        },3f);
        return this.webBody;
    }

    public static void renderWeb(SpriteBatch batch, Sprite webSprite, float x, float y, float rotation) {

        batch.draw(webSprite, x - 8, y - 8, 8f, 8f, 16, 16, 1f, 1f, rotation * 57.3f);
    }
}