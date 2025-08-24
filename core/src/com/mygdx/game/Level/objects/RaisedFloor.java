package com.mygdx.game.level.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.mygdx.game.box2D.BodyFactory;
import org.w3c.dom.css.Rect;

import static com.mygdx.game.DungeonCrawler.*;

public class RaisedFloor {
    public World world;
    public float rafX;
    public float rafY;
    public float topY;
    public float time;
    public float raiseTime;
    public float offset;
    public Body rafBody, maskBody;
    public Fixture rafHitbox;
    public static Rectangle scissors1;
    public Rectangle scissors2;
    public boolean raising, lowering;
    public Rectangle clipBounds;

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

    public void createRaisedFloor() {
        BodyFactory bodyFactory = new BodyFactory();

        rafBody = bodyFactory.createRaisedFloorBody(world, rafX, rafY);
        maskBody = bodyFactory.createRaisedFloorBody(world, rafX, rafY);
        topY = rafBody.getPosition().y;
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
