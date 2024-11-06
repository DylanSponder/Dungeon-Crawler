package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Roof {
    public Body roofBody;
    public boolean upDown, ruined;
    //public Fixture tutorialHitbox;

    public Roof(World world, float x, float y, int width, int height, boolean upDown, boolean ruined) {

        this.upDown = upDown;

        BodyFactory bodyFactory = new BodyFactory();

        roofBody = bodyFactory.createTutorialBody(world, x, y);

        this.ruined = ruined;

        this.roofBody.setUserData("Roof");
    }

    public void createVariableRoof() {
    //TODO: take the values from the room hitbox and translate into roof tiles



    }
}
