package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;


import static com.mygdx.game.DungeonCrawler.*;


public class Trap {

    //There are 3 types of Traps
    // Spinning, pole spike trap (animation is by rotation only, not sprite work)
    // Classic floor spike trap (animation by sprite movement up and then down)
    // Arrow trap - appears like a Greek theatre tragedy mask
    //Standing arrow trap - appears like a Greek automata/ballista

    public int type;
    public Body trapBody, trapArea;
    public float trapX, trapY;
    public World world;
    public int direction;
    public boolean active;
    public float trapDelay, trapSoundDelay, trapResetDelay;

    public Trap(World world, float x, float y, int type, int direction) {
        this.world = world;
        this.type = type;
        this.trapX = x;
        this.trapY = y;
        this.direction = direction;
    }

    public void createTrap() {

        BodyFactory bodyFactory = new BodyFactory();

        this.trapBody = bodyFactory.createSimpleStaticBody(world, trapX, trapY);

        this.trapArea = bodyFactory.createTrapArea(world, trapX, trapY, direction);
        this.trapBody.setUserData("Trap");
        this.trapArea.setUserData("TrapArea");
    }

    public static void renderTrap(SpriteBatch batch, int direction, float x, float y) {

        CreateAssets tx = CreateAssets.getInstance();
        switch (direction) {
            case 1:
                batch.draw(tx.arrowTrap,x,y,0,0,16,16,1,1,0);
                break;
            case 2:
                batch.draw(tx.arrowTrap,x,y,0,0,16,16,1,1,270);
                break;
            case 3:
                batch.draw(tx.arrowTrap,x,y + 16,0,0,16,16,1,1,180);
                break;
            case 4:
                batch.draw(tx.arrowTrap,x + 16,y,0,0,16,16,1,1,90);
                break;
        }
    }

    public static void renderTrapActive(SpriteBatch batch, int direction, float x, float y) {

        CreateAssets tx = CreateAssets.getInstance();
        switch (direction) {
            case 1:
                batch.draw(tx.arrowTrapActivated,x,y,0,0,16,16,1,1,0);
                break;
            case 2:
                batch.draw(tx.arrowTrapActivated,x,y,0,0,16,16,1,1,270);
                break;
            case 3:
                batch.draw(tx.arrowTrapActivated,x,y + 16,0,0,16,16,1,1,180);
                break;
            case 4:
                batch.draw(tx.arrowTrapActivated,x + 16,y,0,0,16,16,1,1,90);
                break;
        }
    }

    public void fireArrow(float x, float y) {
        trapSoundDelay = 1f;
        trapDelay = 1.3f;
        trapResetDelay = 3f;
        soundController.playSound("TrapOpens", 9, 9,0.1f);
        switch (this.direction) {

            case 1:
                active = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        soundController.playSound("Whoosh", 7, 6,0.1f);
                    }
                }, trapSoundDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        arrowsToBeFired.put(Trap.this, 1);
                    }
                }, trapDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        active = false;
                        soundController.playSound("TrapCloses", 8, 8,0.1f);
                    }
                }, trapResetDelay);
                break;
            case 2:
                active = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        soundController.playSound("Whoosh", 7, 6,0.1f);
                    }
                }, trapSoundDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        arrowsToBeFired.put(Trap.this, 2);
                    }
                }, trapDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        active = false;
                        soundController.playSound("TrapCloses", 8, 8,0.1f);
                    }
                }, trapResetDelay);
                break;
            case 3:
                active = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        soundController.playSound("Whoosh", 7, 6,0.1f);
                    }
                }, trapSoundDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        arrowsToBeFired.put(Trap.this, 3);
                    }
                }, trapDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        active = false;
                        soundController.playSound("TrapCloses", 8, 8,0.1f);
                    }
                }, trapResetDelay);
                break;
            case 4:
                active = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        soundController.playSound("Whoosh", 7, 6,0.1f);
                    }
                }, trapSoundDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        arrowsToBeFired.put(Trap.this, 4);
                    }
                }, trapDelay);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        active = false;
                        soundController.playSound("TrapCloses", 8, 8,0.1f);
                    }
                }, trapResetDelay);
                break;
        }
    }
}
