package com.mygdx.game.entity.behaviours.fsm.projectiles;

import box2dLight.ChainLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;
import com.mygdx.game.entity.behaviours.fsm.EnemyCyclops;
import com.mygdx.game.level.objects.*;

import static com.mygdx.game.DungeonCrawler.rayHandler;
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
    public PolygonShape beamShape;
    public ChainLight beamLight;
    public float beamLightDistance, beamLightAlpha;

    public Eyebeam(World world, Body eyeBody, float x, float y, String facing, boolean upDown) {
        this.world = world;
        this.beamX = x;
        this.beamY = y;
        this.eyeBody = eyeBody;
        this.facing = facing;
        this.upDown = upDown;
        this.beamLightAlpha = 0.4f;
        this.beamLightDistance = 10f;
    }

    public Body createEyebeam(Body eyeBody, EnemyCyclops eye, ArrayMap<Body, Eyebeam> eyebeamArrayMap, RayHandler rayHandler) {//
        BodyFactory bodyFactory = new BodyFactory();
        if (facing == "Down"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX, beamY - 32, facing, eyeBody, upDown, this);//3.93923f,0.694593f,-3.93923f,-0.694593f,7.17425f,-63.7223f,15.0527f,-62.3331f,3.93923f,0.694593f
            this.beamLight = new ChainLight(rayHandler, 60, new Color(0.1f,0,1f,0.7f),10,1,new float[]{0.984808f,0.173648f,-0.984808f,-0.173648f,11.5179f,-71.0798f,13.4875f,-70.7325f,0.984808f,0.173648f});//170?
            //this.beamLight.setSoftnessLength(0);
            this.beamLight.setXray(true);
            this.beamLight.attachToBody(beamBody);
        }
        else if (facing == "Up"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX, beamY + 32, facing, eyeBody, upDown,this);//0.422618f,0.906308f,-0.422618f,-0.906308f,64.8315f,-31.3348f,65.6768f,-29.5222f,0.422618f,0.906308f
            this.beamLight = new ChainLight(rayHandler, 60, new Color(0.1f,0,1f,0f),0,1,new float[]{0.469472f,0.882948f,-0.469472f,-0.882948f,63.1028f,-34.6849f,64.0417f,-32.919f,0.469472f,0.882948f});//118?/115
            //this.beamLight.setSoftnessLength(0);
            this.beamLight.setXray(true);
            this.beamLight.attachToBody(beamBody);
        }
        else if (facing == "Left"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX - 32, beamY, facing, eyeBody, upDown,this);//3.27661f,2.29431f,-3.27661f,-2.29431f,38.0209f,-61.2733f,44.5741f,-56.6846f,3.27661f,2.29431f
            this.beamLight = new ChainLight(rayHandler, 60, new Color(0.1f,0,1f,0.7f),10,1,new float[]{0.819152f,0.573576f,-0.819152f,-0.573576f,40.4784f,-59.5525f,42.1167f,-58.4054f,0.819152f,0.573576f});//145
            //this.beamLight.setSoftnessLength(0);
            this.beamLight.setXray(true);
            this.beamLight.attachToBody(beamBody);
        }
        else if (facing == "Right"){
            this.beamBody = bodyFactory.createEyebeam(world, beamX + 32, beamY, facing, eyeBody, upDown,this);//0,4,0,-4,72,-4,72,4,0,4
            this.beamLight = new ChainLight(rayHandler, 60, new Color(0.1f,0,1f,0.7f),10,1,new float[]{0f,1f,0f,-1f,72f,-1f,72f,1f,0f,1f});//90
            //this.beamLight.setSoftnessLength(0);
            this.beamLight.setXray(true);
            this.beamLight.attachToBody(beamBody);
        }

        eye.beamBody = beamBody;
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
