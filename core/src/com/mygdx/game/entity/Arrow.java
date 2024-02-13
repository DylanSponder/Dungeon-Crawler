package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Arrow {
    public static Body arrowBody;
    public String direction;
    static float arrowX;
    static float arrowY;

    public Arrow(Body arrow, String direction){
        arrowBody = arrow;
        this.direction = direction;
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
        Fixture arrowHitbox = arrow.createFixture(arrowShape, 1.0f);
        arrowShape.dispose();
        return arrowHitbox;
    }

    public static void renderArrow (SpriteBatch batch, Sprite arrowSprite, String direction, float x, float y){
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
}
