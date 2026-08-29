package com.mygdx.game.level.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;

import static com.mygdx.game.DungeonCrawler.potParticleEffect;

public class Pot {
        public float potX, potY;
        private World world;
        public Body potBody;
        public Fixture potHitbox;
        public boolean potCreated, damaged;
        public float POT_HEALTH;
        public int type;
        public float particleTime;
        public ParticleEffect particleEffect;
        public ArrayList<RaisedFloor> linkedFloorList;
        public RaisedFloor linkedFloor;
        public boolean onRaisedFloor;

        public Pot(World world, float x, float y, int type) {
            this.type = type;
            this.world = world;
            this.potX = x;
            this.potY = y;
            this.potCreated = false;
            this.damaged = false;
            this.POT_HEALTH = 2;
            this.particleTime = Gdx.graphics.getDeltaTime();
        }

        public Body createPot(ArrayMap<Body, Pot> potArrayMap, RaisedFloor raf) {
            //this.particleEffect = new ParticleEffect();
            this.particleEffect = potParticleEffect;
            //this.particleEffect.load(Gdx.files.internal("HellasDungeon/Particles/Pot/Pot.p"),Gdx.files.internal("HellasDungeon/Particles/Pot/"));
            //this.particleEffect.scaleEffect(0.16f);
            //this.particleEffect.start();

            //creates and activates the pots hitbox for collisions
            BodyFactory bodyFactory = new BodyFactory();

            this.potBody = bodyFactory.createPot(world, potX, potY);

            this.potBody.setUserData("Pot");

            potArrayMap.put(potBody, this);

            this.potCreated = true;

            return this.potBody;
        }

        public static void renderPot(SpriteBatch batch, Sprite potSprite, float x, float y) {

            batch.draw(potSprite,x - 8f,y - 8f,0,0,16,16,1,1,0);
        }

        public static void renderParticles(SpriteBatch batch, float x, float y, ParticleEffect particleEffect, float particleTime) {

            particleEffect.draw(batch, particleTime);
        }
    }
