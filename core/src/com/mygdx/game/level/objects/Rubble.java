package com.mygdx.game.level.objects;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

import static com.mygdx.game.DungeonCrawler.world;

public class Rubble {

    public float rubX, rubY;
    public Body rubBody;
    public int type;

    public Rubble(World world, float x, float y, int type) {

        this.rubX = x;
        this.rubY = y;
        this.type = type;

    }

    public void createRubble() {
        BodyFactory bodyFactory = new BodyFactory();

        this.rubBody = bodyFactory.createRubbleHitbox(world, rubX, rubY);
        this.rubBody.setUserData("Rubble");
    }


    public static void renderRubble(SpriteBatch batch, Sprite rubbleSprite, float x, float y, int type) {

        //generate a random number for x and y offsets

        if (type == 1) {
            batch.draw(rubbleSprite,x + 6,y - 12,0,0,11,11,1,1,0);
        }
        if (type == 2) {
            batch.draw(rubbleSprite,x + 5f,y - 14,0,0,13,16,1,1,0);
        }
        if (type == 3) {
            batch.draw(rubbleSprite,x + 6,y - 12,0,0,11,11,1,1,0);
        }


    }
}
