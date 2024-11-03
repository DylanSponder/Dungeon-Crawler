package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Roof {
    public Body roofBody;
    public boolean upDown;
    //public Fixture tutorialHitbox;

    public Roof(World world, float x, float y, boolean upDown) {

        this.upDown = upDown;

        BodyFactory bodyFactory = new BodyFactory();

        roofBody = bodyFactory.createTutorialBody(world, x, y);

        this.roofBody.setUserData("Roof");
    }
}
