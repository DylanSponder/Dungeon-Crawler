package com.mygdx.game.level.objects;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.CreateAssets;
import com.mygdx.game.box2D.BodyFactory;

import static com.mygdx.game.DungeonCrawler.world;

public class Bridge {

    public float bridgeX, bridgeY;
    public Body bridgeBody;
    public int type;
    public boolean upDown;

    public Bridge(World world, float x, float y, int type, boolean upDown) {

        this.bridgeX = x;
        this.bridgeY = y;
        this.type = type;
        this.upDown = upDown;

    }

    public void createBridge() {
        BodyFactory bodyFactory = new BodyFactory();

        this.bridgeBody = bodyFactory.createBridgeHitbox(world, bridgeX, bridgeY);
        this.bridgeBody.setUserData("Bridge");
    }

    public static void renderBridge(SpriteBatch batch, float x, float y, int type, boolean upDown) {
        final CreateAssets tx = CreateAssets.getInstance();
        if (!upDown) {
            switch (type) {
                case 1:
                    batch.draw(tx.horBridge1,x,y,0,0,16,16,1,1,0);
                    break;
                case 2:
                    batch.draw(tx.horBridge2,x,y,0,0,16,16,1,1,0);
                    break;
                case 3:
                    batch.draw(tx.horBridge3,x,y,0,0,16,16,1,1,0);
                    break;
                case 4:
                    batch.draw(tx.horBridge4,x,y,0,0,16,16,1,1,0);
                    break;
                case 5:
                    batch.draw(tx.horBridge5,x,y,0,0,16,16,1,1,0);
                    break;
                case 6:
                    batch.draw(tx.horBridge6,x,y,0,0,16,16,1,1,0);
                    break;
                case 7:
                    batch.draw(tx.horBridge7,x,y,0,0,16,16,1,1,0);
                    break;
                case 8:
                    batch.draw(tx.horBridge8,x,y,0,0,16,16,1,1,0);
                    break;
                case 9:
                    batch.draw(tx.horBridge9,x,y,0,0,16,16,1,1,0);
                    break;
                case 10:
                    batch.draw(tx.horBridge10,x,y,0,0,16,16,1,1,0);
                    break;
                case 11:
                    batch.draw(tx.horBridge11,x,y,0,0,16,16,1,1,0);
                    break;
                case 12:
                    batch.draw(tx.horBridge12,x,y,0,0,16,16,1,1,0);
                    break;
                case 13:
                    batch.draw(tx.horBridge13,x,y,0,0,16,16,1,1,0);
                    break;
                case 14:
                    batch.draw(tx.horBridgeLeftEnd,x,y,0,0,16,16,1,1,0);
                    break;
                case 15:
                    batch.draw(tx.horBridgeRightEnd,x,y,0,0,16,16,1,1,0);
                    break;
                case 16:
                    batch.draw(tx.horBridge16,x,y,0,0,16,16,1,1,0);
                    break;
                case 17:
                    batch.draw(tx.horBridge17,x,y,0,0,16,16,1,1,0);
                    break;
                case 18:
                    batch.draw(tx.horBridge18,x,y,0,0,16,16,1,1,0);
                    break;
                case 19:
                    batch.draw(tx.horBridge19,x,y,0,0,16,16,1,1,0);
                    break;
                case 20:
                    batch.draw(tx.horBridge20,x,y,0,0,16,16,1,1,0);
                    break;
                case 21:
                    batch.draw(tx.horBridge21,x,y,0,0,16,16,1,1,0);
                    break;
                case 22:
                    batch.draw(tx.horBridge22,x,y,0,0,16,16,1,1,0);
                    break;
                case 23:
                    batch.draw(tx.horBridge23,x,y,0,0,16,16,1,1,0);
                    break;
                case 24:
                    batch.draw(tx.horBridge24,x,y,0,0,16,16,1,1,0);
                    break;

            }
        } else {
            switch (type) {
                case 1:
                    batch.draw(tx.verBridge1,x,y,0,0,16,16,1,1,0);
                    break;
                case 2:
                    batch.draw(tx.verBridge2,x,y,0,0,16,16,1,1,0);
                    break;
                case 3:
                    batch.draw(tx.verBridge3,x,y,0,0,16,16,1,1,0);
                    break;
                case 4:
                    batch.draw(tx.verBridge4,x,y,0,0,16,16,1,1,0);
                    break;
                case 5:
                    batch.draw(tx.verBridge5,x,y,0,0,16,16,1,1,0);
                    break;
                case 6:
                    batch.draw(tx.verBridge6,x,y,0,0,16,16,1,1,0);
                    break;
                case 7:
                    batch.draw(tx.verBridge7,x,y,0,0,16,16,1,1,0);
                    break;
                case 8:
                    batch.draw(tx.verBridge8,x,y,0,0,16,16,1,1,0);
                    break;
                case 9:
                    batch.draw(tx.verBridge9,x,y,0,0,16,16,1,1,0);
                    break;
                case 10:
                    batch.draw(tx.verBridge10,x,y,0,0,16,16,1,1,0);
                    break;
                case 11:
                    batch.draw(tx.verBridge11,x,y,0,0,16,16,1,1,0);
                    break;
                case 12:
                    batch.draw(tx.verBridge12,x,y,0,0,16,16,1,1,0);
                    break;
                case 13:
                    batch.draw(tx.verBridge13,x,y,0,0,16,16,1,1,0);
                    break;
                case 14:
                    batch.draw(tx.verBridge14,x,y,0,0,16,16,1,1,0);
                    break;
                case 15:
                    batch.draw(tx.verBridge15,x,y,0,0,16,16,1,1,0);
                    break;
                case 16:
                    batch.draw(tx.verBridge16,x,y,0,0,16,16,1,1,0);
                    break;
                case 17:
                    batch.draw(tx.verBridge17,x,y,0,0,16,16,1,1,0);
                    break;
                case 18:
                    batch.draw(tx.verBridge18,x,y,0,0,16,16,1,1,0);
                    break;

            }
        }




    }
}
