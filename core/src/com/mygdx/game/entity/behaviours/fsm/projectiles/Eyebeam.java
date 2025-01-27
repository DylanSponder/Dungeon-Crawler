package com.mygdx.game.entity.behaviours.fsm.projectiles;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.level.objects.Heart;

import static com.mygdx.game.DungeonCrawler.world;

public class Eyebeam {

    public World world;
    public Body beamBody;
    public Fixture beamHitbox;
    public float beamX, beamY;
    public boolean beamCreated, upDown;
    public Body eyeBody;
   // public int direction;
    public float stateTime;
    public String facing;

    public Eyebeam(World world, Body eyeBody, float x, float y, String facing, boolean upDown) {
        this.world = world;
        this.beamX = x;
        this.beamY = y;
        this.eyeBody = eyeBody;
        this.facing = facing;
        this.upDown = upDown;
    }

    public Body createEyebeam(Body eyeBody, ArrayMap<Body, Eyebeam> eyebeamArrayMap, RayHandler rayHandler) {
        BodyFactory bodyFactory = new BodyFactory();
        if (facing == "Down"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX, beamY - 32, facing, eyeBody, upDown);
        }
        else if (facing == "Up"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX, beamY + 32, facing, eyeBody, upDown);
        }
        else if (facing == "Left"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX - 32, beamY, facing, eyeBody, upDown);
        }
        else if (facing == "Right"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX + 32, beamY, facing, eyeBody, upDown);
        }


       // this.beamBody.setUserData("Eyebeam");

       // eyeBody.

        eyebeamArrayMap.put(beamBody, this);

        return this.beamBody;
    }

    public static void renderEyebeam (SpriteBatch batch, TextureRegion beamSprite, String direction, float x, float y){
        if (direction == "Down"){
            batch.draw(beamSprite,x-60,y-72,64,8,64,8,1,1,270);
        }
        else if (direction == "Up"){
            batch.draw(beamSprite,x-68,y+64,64,8,64,8,1,1,90);//24
        }
        else if (direction == "Left"){
            batch.draw(beamSprite,x-130,y-11,64,8,64,8,1,1,180);
        }
        else if (direction == "Right"){
            batch.draw(beamSprite,x+2,y-3,64,8,64,8,1,1,0);
        }
    }
}
