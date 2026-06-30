package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;

public class Lock {
    public float lockX, lockY;
    private World world;
    public Body lockBody;
    public boolean visible;
    public int direction;

    public Lock(World world, float x, float y, int direction) {
        this.world = world;
        this.lockX = x;
        this.lockY = y;
        this.visible = false;
        this.direction = direction;
    }

    public void createLock() {
        BodyFactory bodyFactory = new BodyFactory();

        this.lockBody = bodyFactory.createLockBody(world, lockX, lockY);
    }

    public static void renderLock(SpriteBatch batch, int direction, float x, float y) {

        final CreateAssets tx = CreateAssets.getInstance();

            if (direction == 3){
                batch.draw(tx.lockDownTexture,x - 8,y-8,0,0,32,19,1,1,0);
            }
            else if (direction == 1){
                batch.draw(tx.lockUpTexture,x - 8,y-8,0,0,32,19,1,1,0);
            }
            else if (direction == 4){
                batch.draw(tx.lockLeftTexture,x + 2,y-24,0,0,19,32,1,1,0);
            }
            else if (direction == 2){
                batch.draw(tx.lockRightTexture,x-8,y-24,0,0,19,32,1,1,0);
        }
    }
}
