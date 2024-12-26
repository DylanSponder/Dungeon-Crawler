package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Roof {
    public Body roofBody;
    public boolean upDown, ruined, visible;
    public int type, ext;
    //public Fixture tutorialHitbox;

    public Roof(World world, float x, float y, boolean upDown, boolean ruined, int type, int ext) {

        this.type = type;

        this.ext = ext;

        this.visible = true;

        this.upDown = upDown;

        BodyFactory bodyFactory = new BodyFactory();

        roofBody = bodyFactory.createRoofHitbox(world, x, y, type, ext);

        this.ruined = ruined;

        this.roofBody.setUserData("Roof");
    }

    public void createVariableRoof() {




    }
}
