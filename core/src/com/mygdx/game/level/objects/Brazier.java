package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Brazier {

        public float brazierX, brazierY;
        private World world;
        public Body brazierBody;
        public boolean visible, loweredAlpha;
        public int type;
        public float alpha;

        public Brazier(World world, float x, float y, int type) {
            this.world = world;
            this.type = type;
            this.brazierX = x;
            this.brazierY = y;
            this.alpha = 100;
            this.visible = true;
        }

    public Body createBrazier() {
        BodyFactory bodyFactory = new BodyFactory();

        this.brazierBody = bodyFactory.createBrazierBody(world, brazierX, brazierY,1);

        this.brazierBody.setUserData("Brazier");

        return this.brazierBody;
    }

        public static void renderBrazier(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, Brazier b, float alpha) {

            //batch.draw(brazierSprite,x - 8f,y - 8f,0,0,16,16,1,1,0);

            batch.setColor(1, 1, 1, alpha/100);
            batch.draw(tex, x, y, width, height);
        }
}
