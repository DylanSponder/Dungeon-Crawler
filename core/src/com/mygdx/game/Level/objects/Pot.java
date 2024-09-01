package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.CreateTexture;
import com.mygdx.game.box2D.BodyFactory;

public class Pot {
        public float potX, potY;
        private World world;
        public Body potBody;
        public Fixture potHitbox;
        public boolean potCreated, damaged;
        public float POT_HEALTH;

        public Pot(World world, float x, float y) {
            this.world = world;
            this.potX = x;
            this.potY = y;
            this.potCreated = false;
            this.damaged = false;
            this.POT_HEALTH = 2;
        }

        public Body createPot(ArrayMap<Body, Pot> potArrayMap) {
            //creates and activates the pots hitbox for collisions
            BodyFactory bodyFactory = new BodyFactory();

            this.potBody = bodyFactory.createPot(world, potX, potY);

            this.potBody.setUserData("Pot");

            potArrayMap.put(potBody, this);

            this.potCreated = true;

            return this.potBody;
        }

        public static void renderPot(SpriteBatch batch, float x, float y) {

            final CreateTexture tx = CreateTexture.getInstance();

          //  batch.draw(tx.amphoraSprite,x,y,0,0,16,16,1,1,0);
        }
    }
