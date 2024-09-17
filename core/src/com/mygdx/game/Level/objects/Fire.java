package com.mygdx.game.level.objects;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Fire {
    public float fireX, fireY;
    public World world;
    public ArrayList<Fire> fires;
    public Body fireBody;
    public RayHandler rayHandler;

    public Fire(World world, RayHandler rayHandler, float x, float y) {
        this.world = world;
        this.rayHandler = rayHandler;
        this.fireX = x;
        this.fireY = y;
    }

    public void createFire() {
        PointLight fireLight = new PointLight(rayHandler,400, new Color(0.25f,0.20f,0,0.90f),90, fireX + 8, fireY + 8);
        fireLight.setXray(true);
        //ConeLight fireLight2 = new ConeLight(rayHandler, 400, new Color(0.25f,0.20f,0,0.85f),70,fireX+8,fireY+16,270,70);
    }
}
