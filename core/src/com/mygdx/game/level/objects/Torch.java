package com.mygdx.game.level.objects;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.DungeonCrawler;

import javax.swing.*;

public class Torch extends Light{
    public float torchX, torchY;
    private World world;
    private RayHandler rayHandler;
    public int direction;

    public Torch(RayHandler rayHandler, World world, float x, float y, int direction) {
        this.rayHandler = rayHandler;
        this.world = world;
        this.torchX = x;
        this.torchY = y;
        this.lightType = 1;
        this.direction = direction;
    }

    public ConeLight createTorch(int direction) {

        if (direction == 1){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 80,torchX,torchY, 270,90);
            torch.setXray(true);
            return torch;
        }
        if (direction == 2){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 80,torchX,torchY, 180,90);
            torch.setXray(true);
            return torch;
        }
        if (direction == 3){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 80,torchX,torchY, 90,90);
            torch.setXray(true);
            return torch;
        }
        if (direction == 4){
            ConeLight torch = new ConeLight(rayHandler, 10, new Color(0.25f,0.20f,0,0.70f), 80,torchX,torchY, 0,90);
            torch.setXray(true);
            return torch;
        }
        return null;
    }

    public static void renderTorch(SpriteBatch batch, int direction, float x, float y) {
        CreateAssets tx = CreateAssets.getInstance();
        switch (direction) {
            case 1:
                batch.draw(tx.torchUpTexture,x-4.5f,y-9,0,0,9,12,1,1,0);
                break;
            case 2:
                batch.draw(tx.torchDownTexture,x,y,0,0,9,12,1,1,0);
                break;
            case 3:
                batch.draw(tx.torchLeftTexture,x,y + 16,0,0,12,9,1,1,0);
                break;
            case 4:
                batch.draw(tx.torchRightTexture,x - 14,y,0,0,12,9,1,1,0);
                break;
        }


    }
}
