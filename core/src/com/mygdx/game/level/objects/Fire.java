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
    public boolean extinguish, smoking, active, blue, visible, loweredAlpha;
    public PointLight fireLight;
    public float stateTime;
    public int type;
    public boolean upDown, left, right;
    public Sound fireAmbient;
    public ConeLight torchLight;
    public int direction;
    public Color fireColor;
    public float time, alpha;

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
        this.time = 1;
        this.alpha = 100;
        this.visible = true;
        if (type == 1) {
            this.fireColor = new Color(0.30f,0.12f,0,0.70f);
            //0.25f,0.20f,0,0.7f
            //0.30f,0.12f,0,0.70f
        }
        else if (type == 2) {
            this.fireColor = new Color(0f,0,1f,0.7f);
        }
        else if (type == 3 || type == 6){
            this.fireColor = new Color(0.30f,0.12f,0,0.70f);
        } else if (type == 5){

        }
    }

    public void createFire(Color color, int distance, ConeLight torchLight) {

        if (torchLight != null) {
            this.torchLight = torchLight;
        }

        this.light = new PointLight(rayHandler,400, color,80, fireX + 8, fireY + 8);//distance was 65
        this.light.setXray(true);

        this.smoking = false;
        this.active = true;

       // this.fireAmbient.play();
        this.fireAmbient.setVolume(1,0.3f);

        if (this.extinguish) {

            BodyFactory bodyFactory = new BodyFactory();

            if (this.type == 1) {
                fireBody = bodyFactory.createFireBody(world, fireX, fireY, this.extinguish);
                fireBody.setUserData("Fire");
            } else if (this.type == 2){
                fireBody = bodyFactory.createFireBody(world, fireX, fireY,this.extinguish);
                fireBody.setUserData("Fire");
                fireSpawnerBody = bodyFactory.createSpawnerDetectionRadius(fireBody, 70f);
                fireSpawnerBody.setUserData("Spawner");
            } else if (this.type == 3){
                fireBody = bodyFactory.createTorchBody(world, fireX, fireY, direction);
                fireBody.setUserData("Fire");
            }
            else if (this.type == 4){
                fireBody = bodyFactory.createFireBody(world, fireX, fireY-1f,this.extinguish);
                fireBody.setUserData("Fire");
            }
            else if (this.type == 5){
                fireBody.setUserData("Fire");
            }
            else if (this.type == 6){
                fireBody = bodyFactory.createCandleFlameBody(world, fireX + 5, fireY + 10.5f);
                fireBody.setUserData("Fire");
            }


        } else {
            BodyFactory bodyFactory = new BodyFactory();

            fireBody = bodyFactory.createFireBody(world, fireX, fireY-1f,this.extinguish);
            fireBody.setUserData("FireCannotExtinguish");

        }
        //ConeLight fireLight2 = new ConeLight(rayHandler, 400, new Color(0.25f,0.20f,0,0.85f),70,fireX+8,fireY+16,270,70);
    }

    public static void renderFire(SpriteBatch batch, TextureRegion currentFrame, float x, float y, int width, int height, boolean smoking, int direction, boolean visible, Fire f, float alpha) {
        batch.setColor(1, 1, 1, alpha/100);
        switch (direction) {
            case 1:
                batch.draw(currentFrame, x, y,0,0,16,16,1,1,0);
                break;
            case 2:
                batch.draw(currentFrame, x, y,0,0,16,16,1,1,270);
                break;
            case 3:
                batch.draw(currentFrame, x, y,0,0,16,16,1,1,180);
                break;
            case 4:
                batch.draw(currentFrame, x, y,0,0,16,16,1,1,90);
                break;
            default:
                batch.draw(currentFrame, x, y);
        }

    }
}
