package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entity.behaviours.fsm.Player;

import static com.mygdx.game.DungeonCrawler.*;
import static com.mygdx.game.HUD.*;

public class Compass {

    public static Vector2 doorVec, playerVec;
    public static boolean showing, moving;
    public static float initialX, doorX, doorY;

    public static void calculateAngle(float x, float y) {
        playerVec = new Vector2(Player.playerBody.getPosition());
        doorX = x;
        doorY = y;
        Vector2 vec = new Vector2(doorX,doorY);

        initialX = MathUtils.atan2(vec.y - playerVec.y, vec.x - playerVec.x);

        doorVec = new Vector2((float)Math.cos(initialX),(float)Math.sin(initialX));

        rotateCompassArrowToDoor(doorVec);
    }

    public static void rotateCompassArrowToDoor(Vector2 vector) {

        double degree = Math.atan2(vector.y, vector.x) * MathUtils.radiansToDegrees;

        float x = (float) (degree);

        x -= 90;

        moving = true;
        compassArrowImage.setRotation(x);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                //to allow the compass arrow to act long enough to move into position on the HUD
                moving = false;
            }
        }, 0.1f);
    }

    public static void fixRotation() {

        playerVec = new Vector2(Player.playerBody.getPosition());

        //System.out.println("---------------------------------");
        //System.out.println("PLAYER POS FOR NEW ROTATION: " + player.playerBody.getPosition());
        //System.out.println("DOOR X AND Y: " + doorX + " " + doorY);

        float currentRotation = compassArrowImage.getRotation();

        //System.out.println("CURRENT ROTATION " + currentRotation);

        currentRotation = Math.abs(currentRotation);

        //System.out.println("CURRENT ROTATION ADJUSTED " + currentRotation);

        Vector2 vec = new Vector2(doorX,doorY);

        initialX = MathUtils.atan2(vec.y - playerVec.y, vec.x - playerVec.x);

        doorVec = new Vector2((float)Math.cos(initialX),(float)Math.sin(initialX));

        double degree = Math.atan2(doorVec.y, doorVec.x) * MathUtils.radiansToDegrees;

        float x = (float) (degree);

        //System.out.println("DEGREES TO TARGET " + x);

        rotateArrow = new RotateByAction();

        x = x - 90;

        x = Math.abs(x);

        //System.out.println("DEGREES TO TARGET ADJUSTED " + x);

        x = x - currentRotation;

        //System.out.println("DEGREES MINUS CURRENT ROTATION " + x);

        if (x < 0) {
            rotateArrow.setAmount(0.2f);
            //System.out.println("ANTI-CLOCKWISE");
        } else {
            rotateArrow.setAmount(-0.2f);
            //System.out.println("CLOCKWISE");
        }
        x = Math.abs(x);

        //System.out.println("DEGREES MINUS CURRENT ROTATION ADJUSTED " + x);

        //System.out.println("---------------------------------");

        repeatAction = new RepeatAction();
        repeatAction.setCount((int) x * 5);
        compassArrowImage.addAction(rotateArrow);
        repeatAction.setAction(rotateArrow);
        moving = true;
        Action finishRotatingAction = new Action(){
            @Override
            public boolean act(float delta){
                moving = false;
                return true;
            }
        };

        compassArrowImage.addAction(Actions.sequence(
                (Action) repeatAction, finishRotatingAction
        ));
    }

    public static void hideCompass() {
        compassImage.setColor(1,1,1,0);
        compassArrowImage.setColor(1,1,1,0);
        showing = false;
    }

    public static void showCompass() {
        compassImage.setColor(1,1,1,1);
        compassArrowImage.setColor(1,1,1,1);
        showing = true;
    }

    public static void resetCompass() {
        compassArrowImage.setRotation(0);
    }
}
