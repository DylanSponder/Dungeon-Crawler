package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.mygdx.game.entity.behaviours.fsm.Player;

import static com.mygdx.game.DungeonCrawler.*;
import static com.mygdx.game.HUD.*;

public class Compass {

    public static Vector2 doorVec, playerVec;
    public static boolean showing, moving;


    public void Compass() {

    }

    public static void calculateAngle(float x, float y) {
        System.out.println("THIS IS TESTING THE CALCULATE ANGLE FUNCTION");

        playerVec = new Vector2(Player.playerBody.getPosition());
        doorVec = new Vector2(x,y);

        System.out.println("PLAYER POS: " + player.playerBody.getPosition());

        float initialX = MathUtils.atan2(doorVec.y - playerVec.y, doorVec.x - playerVec.x);

        Vector2 finalX = new Vector2((float)Math.cos(initialX),(float)Math.sin(initialX));

        rotateCompassArrowToDoor(finalX);

    }

    public static void rotateCompassArrowToDoor(Vector2 vector) {

        float currentRotation = compassArrowImage.getRotation();

        double degree = Math.atan2(vector.y, vector.x) * MathUtils.radiansToDegrees;

        float x = (float) (degree);

        x -= 90;

        System.out.println("FINAL ANGLE " + x);

        rotateArrow = new RotateByAction();
        rotateArrow.setAmount(1);
        repeatAction = new RepeatAction();
        repeatAction.setCount((int) x);
        compassArrowImage.addAction(rotateArrow);
        compassArrowImage.addAction(repeatAction);
        repeatAction.setAction(rotateArrow);
        moving = true;
    }

    public static void fixRotation() {
        repeatAction.setCount(0);
        float currentRotation = compassArrowImage.getRotation();

        double degree = Math.atan2(doorVec.y, doorVec.x) * MathUtils.radiansToDegrees;

        float x = (float) (degree);

        x -= 90;

        x = - currentRotation;

        rotateArrow = new RotateByAction();
        rotateArrow.setAmount(3);
        repeatAction = new RepeatAction();
        repeatAction.setCount((int) x/3);
        compassArrowImage.addAction(rotateArrow);
     //   compassArrowImage.addAction(repeatAction);
        repeatAction.setAction(rotateArrow);

        Action finishRotatingAction = new Action(){
            @Override
            public boolean act(float delta){
                Compass.moving = false;
                return true;
            }
        };

        compassArrowImage.addAction(Actions.sequence(
                (Action) repeatAction, finishRotatingAction
        ));

        compassMoving = true;
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
