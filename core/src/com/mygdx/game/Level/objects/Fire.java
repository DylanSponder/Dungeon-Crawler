package com.mygdx.game.level.objects;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;

public class Fire {
    public float fireX, fireY;
    public World world;
    public ArrayList<Fire> fires;
    public Body fireBody;
    public RayHandler rayHandler;
    public boolean extinguish, smoking, active;
    public PointLight fireLight;
    public float stateTime;
    public int type;

    public Fire(World world, RayHandler rayHandler, float x, float y, boolean extinguish, float stateTime, int type) {
        this.world = world;
        this.rayHandler = rayHandler;
        this.fireX = x;
        this.fireY = y;
        this.extinguish = extinguish;
        this.stateTime = stateTime;
        this.type = type;
    }

    public void createFire(Color color, int distance) {
        this.fireLight = new PointLight(rayHandler,400, color,60, fireX + 8, fireY + 8);
        this.fireLight.setXray(true);

        this.smoking = false;
        this.active = true;

        if (this.extinguish) {

            BodyFactory bodyFactory = new BodyFactory();

            fireBody = bodyFactory.createFireBody(world, fireX, fireY);

            fireBody.setUserData("Fire");
        }
        //ConeLight fireLight2 = new ConeLight(rayHandler, 400, new Color(0.25f,0.20f,0,0.85f),70,fireX+8,fireY+16,270,70);
    }

    public static void renderFire(SpriteBatch batch, TextureRegion currentFrame, float x, float y, boolean smoking) {

            batch.draw(currentFrame, x, y);
    }
}
