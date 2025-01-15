package com.mygdx.game.entity.behaviours.fsm.projectiles;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.DungeonCrawler.rayHandler;

public class Arrow {
    public static Body arrowBody;
    public String direction;
    static float arrowX;
    static float arrowY;
    public float stateTime, stateTime2;
    public boolean onFire;
    public boolean flameCreated;
    public PointLight flameLight;

    public Arrow(Body arrow, String direction, float stateTime, boolean onFire){
        this.arrowBody = arrow;
        this.direction = direction;
        this.stateTime = stateTime;
        this.stateTime2 = stateTime;
        this.onFire = onFire;
    }

    public void createArrowFlameLight(Arrow key) {
        if (!flameCreated) {
            this.flameLight = new PointLight(rayHandler,400, new Color(0.25f,0.20f,0,0.7f),40, arrowX + 8, arrowY + 8);
            this.flameLight.attachToBody(key.arrowBody);
            this.flameLight.setSoftnessLength(45f);
            flameCreated = true;
        }
    }

    public void destroyArrowFlameLight(PointLight flameLight) {
        flameLight.remove();
    }

    public static Body createArrowBody(World world, float x, float y) {
        arrowX = x;
        arrowY = y;
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public static Fixture createArrowHitbox(Body arrow, boolean r){
        PolygonShape arrowShape = new PolygonShape();
        if (r){
            arrowShape.setAsBox(2.5f, 6.5f);
        }
        else {
            arrowShape.setAsBox(6.5f, 2.5f);
        }
        Fixture arrowHitbox = arrow.createFixture(arrowShape, 0f);
        arrowShape.dispose();
        arrowHitbox.setSensor(true);
        return arrowHitbox;
    }

    public static void renderArrow (SpriteBatch batch, TextureRegion arrowSprite, String direction, float x, float y){
            if (direction == "Down"){
                batch.draw(arrowSprite,x-5.5f,y-6.5f,8,5,13,5,1,1,270);
            }
            else if (direction == "Up"){
                batch.draw(arrowSprite,x-15.5f,y+1.5f,13,5,13,5,1,1,90);
            }
            else if (direction == "Left"){
                batch.draw(arrowSprite,x-19.5f,y-7.5f,13,5,13,5,1,1,180);
            }
            else if (direction == "Right"){
                batch.draw(arrowSprite,x-6.5f,y-2.5f,13,5,13,5,1,1,0);
        }
    }

    public static void renderFireArrow (SpriteBatch batch, TextureRegion arrowSprite, TextureRegion smallFireSprite, String direction, float x, float y){
        if (direction == "Down"){
            batch.draw(arrowSprite,x-5.5f,y-6.5f,8,5,13,5,1,1,270);
            batch.draw(smallFireSprite, x - 7.5f, y - 11f,0,0,16,16,1,1,0);
        }
        else if (direction == "Up"){
            batch.draw(arrowSprite,x-15.5f,y+1.5f,13,5,13,5,1,1,90);
            batch.draw(smallFireSprite, x - 7.5f, y - 1f,0,0,16,16,1,1,0);
        }
        else if (direction == "Left"){
            batch.draw(arrowSprite,x-19.5f,y-7.5f,13,5,13,5,1,1,180);
            batch.draw(smallFireSprite, x - 11.5f, y - 4.5f,0,0,16,16,1,1,0);
        }
        else if (direction == "Right"){
            batch.draw(arrowSprite,x-6.5f,y-2.5f,13,5,13,5,1,1,0);
            batch.draw(smallFireSprite, x - 3.5f, y - 4.5f,0,0,16,16,1,1,0);
        }
    }
}
