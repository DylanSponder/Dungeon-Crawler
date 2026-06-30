package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Drain {

        public float drainX, drainY;
        private World world;
        public Body drainBody;
        public int type;

        public Drain(World world, float x, float y) {
            this.world = world;
            this.drainX = x;
            this.drainY = y;
        }

    public Body createDrain() {
        BodyFactory bodyFactory = new BodyFactory();

        this.drainBody = bodyFactory.createTorchBody(world, drainX, drainY,1);

        this.drainBody.setUserData("Drain");

        return this.drainBody;
    }

        public static void renderDrain(SpriteBatch batch, Sprite brazierSprite, float x, float y) {

            batch.draw(brazierSprite,x - 8f,y - 8f,0,0,24,28,1,1,0);
        }

}
