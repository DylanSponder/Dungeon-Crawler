package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.mygdx.game.box2D.BodyFactory;

import static com.mygdx.game.DungeonCrawler.*;

public class RaisedFloor {
    public World world;
    public float rafX;
    public float rafY;
    public float topY;
    public float time;
    public float raiseTime;
    public float offset;
    public Body rafBody, maskBody, rafTop, rafBottom, rafTopBody;
    public Fixture rafHitbox;
    public static Rectangle scissors1;
    public Rectangle scissors2;
    public boolean raising, lowering, entityColliding, lowered;
    public Rectangle clipBounds;
    public boolean active;

    public RaisedFloor(World world, float x, float y) {
        this.world = world;
        this.rafX = x;
        this.rafY = y;
        this.time = 0;
        this.raiseTime = 5;
        this.raising = false;
        this.lowering = true;
        scissors1 = new Rectangle();
    }

    public void raiseFloorAfterEntityMoves() {
        this.lowering = false;
        this.raising = true;
    }

    public void createRaisedFloor() {
        BodyFactory bodyFactory = new BodyFactory();

        this.rafBody = bodyFactory.createRaisedFloorBody(world, rafX, rafY + 4);
        this.rafTop = bodyFactory.createRaisedFloorTopLimit(world, rafX + 8, rafY + 17);
        this.rafBottom = bodyFactory.createRaisedFloorBottomLimit(world, rafX + 8, rafY - 1f);
        //this.rafTopBody = bodyFactory.createRaisedFloorBody(world, rafX, rafY + 4);

        this.rafHitbox = bodyFactory.createRaisedFloorHitbox(world, rafBody);

       // maskBody = bodyFactory.createRaisedFloorBody(world, rafX, rafY);
        this.topY = rafBody.getPosition().y;
        //rafBody.setUserData("Wall");
    }

    public static void renderRaisedFloor(SpriteBatch batch, Rectangle clipBounds, TextureRegion tex, float x, float y) {
        /*
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(300, 300, 800, 800);

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        */


        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors1);

        boolean pop1 = ScissorStack.pushScissors(scissors1);

        if (pop1) {
            batch.draw(tex, x, y, 16, 16);
            batch.flush();
            ScissorStack.popScissors();
        }

    }

    public static void renderMask(SpriteBatch batch, TextureRegion tex, float x, float y) {

       // batch.draw(tex, x, y, 16, 8);
    }
}
