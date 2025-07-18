package com.mygdx.game.level.objects;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;

public class Fire extends Light{
    public float fireX, fireY;
    public World world;
    public ArrayList<Fire> fires;
    public Body fireBody;
    public Fixture fireSpawnerBody;
    public RayHandler rayHandler;
    public boolean extinguish, smoking, active, blue;
    public PointLight fireLight;
    public float stateTime;
    public int type;
    public boolean upDown;
    public Sound fireAmbient;
    public ConeLight torchLight;
    public int direction;
    public Color fireColor;

    public Fire(World world, RayHandler rayHandler, float x, float y, boolean extinguish, float stateTime, int type, boolean upDown, int direction) {
        this.world = world;
        this.rayHandler = rayHandler;
        this.fireX = x;
        this.fireY = y;
        this.extinguish = extinguish;
        this.stateTime = stateTime;
        this.type = type;
        this.upDown = upDown;
        final CreateAssets tx = CreateAssets.getInstance();
        this.fireAmbient = tx.fireAmbient;
        this.lightType = 2;
        this.direction = direction;
        if (type == 1) {
            this.fireColor = new Color(0.30f,0.12f,0,0.70f);
            //0.25f,0.20f,0,0.7f
            //0.30f,0.12f,0,0.70f
        }
        else if (type == 2) {
            this.fireColor = new Color(0f,0,1f,0.7f);
        }
        else {
            this.fireColor = new Color(0.30f,0.12f,0,0.70f);
        }
    }

    public void createFire(Color color, int distance, ConeLight torchLight) {

        if (torchLight != null) {
            this.torchLight = torchLight;
        }

        this.light = new PointLight(rayHandler,400, this.fireColor,65, fireX + 8, fireY + 8);
        this.light.setXray(true);



        this.smoking = false;
        this.active = true;

       // this.fireAmbient.play();
        this.fireAmbient.setVolume(1,0.3f);

        if (this.extinguish) {

            BodyFactory bodyFactory = new BodyFactory();

            if (this.type == 1) {
                fireBody = bodyFactory.createFireBody(world, fireX, fireY);
                fireBody.setUserData("Fire");
            } else if (this.type == 2){
                fireSpawnerBody = bodyFactory.createSpawnerDetectionRadius(fireBody, 70f);
                fireSpawnerBody.setUserData("Spawner");
            } else if (this.type == 3){
                fireBody = bodyFactory.createTorchBody(world, fireX, fireY, direction);
                fireBody.setUserData("Fire");
            }
            else if (this.type == 4){
                fireBody = bodyFactory.createFireBody(world, fireX, fireY+2);
                fireBody.setUserData("Fire");
            }
        }
        //ConeLight fireLight2 = new ConeLight(rayHandler, 400, new Color(0.25f,0.20f,0,0.85f),70,fireX+8,fireY+16,270,70);
    }

    public static void renderFire(SpriteBatch batch, TextureRegion currentFrame, float x, float y, boolean smoking, boolean upDown) {

        if (upDown) {
            batch.draw(currentFrame, x, y,0,0,16,16,1,1,180);

        } else {
            batch.draw(currentFrame, x, y);
        }
    }

    public void respawnEnemy(float time, int enemyType) {
        //respawn an enemy at the nearest Skull, then remove the Skull

    }
}
