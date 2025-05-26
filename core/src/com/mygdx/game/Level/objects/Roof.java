package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

public class Roof {
    public Body roofBody;
    public boolean upDown, ruined, visible, loweredAlpha;
    public int type, ext;
    public float alpha;
    //public Fixture tutorialHitbox;

    public Roof(World world, float x, float y, boolean upDown, boolean ruined, int type, int ext) {

        this.type = type;

        this.alpha = 100;

        this.ext = ext;

        this.visible = true;

        this.upDown = upDown;

        BodyFactory bodyFactory = new BodyFactory();

        roofBody = bodyFactory.createRoofHitbox(world, x, y, type, ext, upDown);

        this.ruined = ruined;

        this.roofBody.setUserData("Roof");
    }

    public static void renderRoof(SpriteBatch batch, TextureRegion tex, float x, float y, int width, int height, boolean visible, float alpha, Roof r) {// int alpha



        batch.setColor(1, 1, 1, alpha/100);
        batch.draw(tex, x, y, width, height);
    }
}
