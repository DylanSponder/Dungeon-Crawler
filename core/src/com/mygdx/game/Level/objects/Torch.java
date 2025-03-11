package com.mygdx.game.level.objects;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;

public class Torch extends Light{
    public float torchX, torchY;
    private World world;
    private RayHandler rayHandler;

    public Torch(RayHandler rayHandler, World world, float x, float y) {
        this.rayHandler = rayHandler;
        this.world = world;
        this.torchX = x;
        this.torchY = y;
        this.lightType = 1;
    }

    public ConeLight createTorch(int direction) {

        if (direction == 1){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 70,torchX,torchY, 270,90);
            torch.setXray(true);
            return torch;
        }
        if (direction == 2){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 70,torchX,torchY, 180,90);
            torch.setXray(true);
            return torch;
        }
        if (direction == 3){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 70,torchX,torchY, 90,90);
            torch.setXray(true);
            return torch;
        }
        if (direction == 4){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 70,torchX,torchY, 0,90);
            torch.setXray(true);
            return torch;
        }
        return null;
    }
}
