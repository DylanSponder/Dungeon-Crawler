package com.mygdx.game.entity.behaviours.fsm;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Tutorial {
    public Body tutorialBody;
    //public Fixture tutorialHitbox;

    public Tutorial(World world, float x, float y) {

        BodyFactory bodyFactory = new BodyFactory();

        tutorialBody = bodyFactory.createTutorialBody(world, x, y);
       // shopDetectionBody = bodyFactory.createEnemyBody(world, x, y);
        //tutorialHitbox = bodyFactory.createTutorialHitbox(tutorialBody, 7.5f);

        //shopDetectionRadius = bodyFactory.createShopDetectionRadius(shopBody, 100);
        //shopDetectionRadius.setSensor(true);

        this.tutorialBody.setUserData("Shopkeeper");
    }
}
