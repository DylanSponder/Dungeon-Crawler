package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class FirePit {

    public float firepitX, firepitY;
    private World world;
    public Body firepitBody;
    public int type;

    public FirePit(World world, float x, float y) {
        this.world = world;
        this.firepitX = x;
        this.firepitY = y;
    }

    public Body createFirepit() {
        BodyFactory bodyFactory = new BodyFactory();

        this.firepitBody = bodyFactory.createTorchBody(world, firepitX, firepitY,1);

        this.firepitBody.setUserData("Firepit");

        return this.firepitBody;
    }

    public static void renderFirepit(SpriteBatch batch, Sprite firepitSprite, float x, float y) {

        batch.draw(firepitSprite,x - 8f,y - 8f,0,0,16,16,1,1,0);
    }

}
