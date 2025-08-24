package com.mygdx.game.box2D;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entity.behaviours.fsm.projectiles.Eyebeam;

import static com.mygdx.game.DungeonCrawler.arrowBody;
import static com.mygdx.game.DungeonCrawler.chiselHitbox;

public class BodyFactory {

    public Body createWall(World world, float x, float y) {
        //needs to acommodate larger walls
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }
    /*
        public Body createSmallWall(World world, float x, float y) {
        //needs to acommodate larger walls
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }
     */

    public Body createFlagHitbox(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 4.5f, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5, 8);
        Fixture temp = body.createFixture(shape, 1.0f);
        temp.setSensor(true);
        temp.setUserData("Flag");
        shape.dispose();
        return body;
    }


        public Body createStatueHitbox(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 4.5f, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7.5f, 9.5f);
        Fixture temp = body.createFixture(shape, 1.0f);
        temp.setSensor(true);
        temp.setUserData("Statue");
        shape.dispose();
        return body;
    }

    public Body createRaisedFloorBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        //body.setUserData("Wall");
        return body;
    }

    public Fixture createRaisedFloorHitbox(World world, Body body, float x, float y) {
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(8, 4.5f);

        Fixture fixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        fixture.setSensor(true);
        return fixture;
    }

    public Body createColumnHitbox(World world, float x, float y, int size, boolean bigbase) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 11 + ((size + 1)*8));
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        int hy = 8 * (size + 1);
        shape.setAsBox(5, hy);
        Fixture temp = body.createFixture(shape, 1.0f);
        temp.setSensor(true);
        temp.setUserData("Stem");
        shape.dispose();
        return body;
    }

    public static Body createFenceTurn(World world, float x, float y, float offsetX, float offsetY, int type) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + offsetX, y + offsetY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        if (type == 1) {
            CircleShape shape = new CircleShape();
            shape.setRadius(8f);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        } else if (type == 2) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(4, 2);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
        return body;
    }

    public Body createRoofHitbox(World world, float x, float y, int type, int ext, boolean upDown) {
        PolygonShape shape = new PolygonShape();
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        switch (type) {
            case 0:
                if (upDown) {
                    shape.setAsBox(26, 40);
                    bodyDef.position.set(x + 32 ,y + 48);
                } else {
                    shape.setAsBox(40, 26);
                    bodyDef.position.set(x - 48 ,y + 32);
                }
                break;
            case 1:
                shape.setAsBox(26, 24 + (ext * 8));
                bodyDef.position.set(x + 40 ,y - (16 + (ext * 8)));
                break;
            case 2:
                shape.setAsBox(43, 32 + (ext * 8));
                bodyDef.position.set(x + 56,y - (24 + (ext * 8)));

                break;
            case 3:
                shape.setAsBox(62, 48 + (ext * 8));
                bodyDef.position.set(x + 72,y - (40 + (ext * 8)));

                break;
            case 4:
                shape.setAsBox(16, 8 + (ext * 8));
                bodyDef.position.set(x,y);

                break;
            case 5:
                shape.setAsBox(16, 8 + (ext * 8));
                bodyDef.position.set(x,y);

                break;
            case 7:
                shape.setAsBox(43, 32 + (ext * 8));
                bodyDef.position.set(x + 56,y - (24 + (ext * 8)));

                break;
        }
        body = world.createBody(bodyDef);
        //shape.setAsBox(8, 8);
        Fixture fix = body.createFixture(shape, 1.0f);
        fix.setSensor(true);
        fix.setUserData("Roof");
        shape.dispose();
        return body;
    }

    public Body createFireBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 10f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4.5f, 6f);
        Fixture fix = body.createFixture(shape, 1.0f);
        shape.dispose();
        fix.setSensor(true);
        return body;
    }

    public Body createTorchBody(World world, float x, float y, int direction) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        if (direction == 1) {
            shape.setAsBox(4.5f, 8f);
            bodyDef.position.set(x + 8, y + 4f);
        } else if (direction == 2) {
            shape.setAsBox(8f, 4.5f);
            bodyDef.position.set(x + 8, y + 4.5f);
        }
            else if (direction == 3) {
            shape.setAsBox(4.5f, 8f);
            bodyDef.position.set(x - 7.5f, y - 6);
        }
            else if (direction == 4) {
            shape.setAsBox(8f, 4.5f);
            bodyDef.position.set(x + 7, y + 4.5f);
        }
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);


        Fixture fix = body.createFixture(shape, 1.0f);
        shape.dispose();
        fix.setSensor(true);
        return body;
    }

    public Body createTrapArea(World world, float x, float y, int direction) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        switch (direction) {
            case 1:
                shape.setAsBox(4f, 12f);
                bodyDef.position.set(x + 8, y - 12);
                break;
            case 2:
                shape.setAsBox(12f, 4f);
                bodyDef.position.set(x - 12, y - 8);
                break;
            case 3:
                shape.setAsBox(4f, 12f);
                bodyDef.position.set(x - 8, y + 28);
                break;
            case 4:
                shape.setAsBox(12f, 4f);
                bodyDef.position.set(x + 28, y + 8);
                break;
        }
        body = world.createBody(bodyDef);
        Fixture fix = body.createFixture(shape, 1.0f);
        fix.setSensor(true);
        shape.dispose();
        return body;
    }

    public Body createEyebeam(World world, float x, float y, String direction, Body eyeBody, boolean upDown, Eyebeam beam) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();

        float eyeBodyX = eyeBody.getPosition().x;
        float eyeBodyY = eyeBody.getPosition().y;

        switch (direction) {
            case "Up":
                Vector2 eyeBodyVecUp = new Vector2(36, -18f);//32
                shape.setAsBox(4f, 32f, eyeBodyVecUp, 1.11f);//45.1
                break;
            case "Left":
                Vector2 eyeBodyVecLeft = new Vector2(20, -28);
                shape.setAsBox(32f, 4f,eyeBodyVecLeft, 2.21f);
                break;
            case "Down":
                Vector2 eyeBodyVecDown = new Vector2(6f, -32);//-32
                shape.setAsBox(4f, 32f, eyeBodyVecDown,85);
                break;
            case "Right":
                Vector2 eyeBodyVecRight = new Vector2(32, 1);
                shape.setAsBox(32f, 4f,eyeBodyVecRight,6.28f);//85//0.24
                break;
        }
      //  eyeBody = world.createBody(bodyDef);
        Fixture fix = eyeBody.createFixture(shape, 1.0f);

        fix.setSensor(true);
        fix.setUserData("Eyebeam");

        shape.dispose();
        return eyeBody;
    }

    public Body createColumnBase(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 2.03f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 1.96f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createColumnBase2(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 5.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 5.5f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createPedestal(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 6.50f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 6.5f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public Body createDoorBody(World world, float x, float y, boolean upDown) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        if (upDown) {
            bodyDef.position.set(x + 8, y);

        } else {
            bodyDef.position.set(x + 16, y + 8);
        }

        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Door");
        return body;
    }

    public Fixture createDoorHitbox(Body body, boolean upDown) {
        PolygonShape shape = new PolygonShape();
        if (upDown) {
            shape.setAsBox(8, 16);
        } else {
            shape.setAsBox(16, 8);
        }
        Fixture fixture = body.createFixture(shape, 1.0f);
        shape.dispose();
        fixture.setSensor(true);
        return fixture;
    }

    public Body createLockBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8, y + 8);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData("Lock");
        return body;
    }

    public static Body createWallTurn(World world, float x, float y, float offsetX, float offsetY) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + offsetX, y + offsetY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(16f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createObstacle(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.4f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createCandle(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8.5f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8.4f);
        Fixture candFix = body.createFixture(shape, 0.8f);
        candFix.setSensor(true);
        shape.dispose();
        return body;
    }

    public static Body createCobweb(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f);
        Fixture cobFixture = body.createFixture(shape, 0.8f);
        shape.dispose();
        cobFixture.setSensor(true);
        return body;
    }

    public static Body createImpassableCobweb(World world, float x, float y) {
        Body body2;
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(x + 8f, y + 8f);
        bodyDef2.fixedRotation = true;
        body2 = world.createBody(bodyDef2);
        CircleShape shape2 = new CircleShape();
        shape2.setRadius(4f);
        Fixture cobFixture2 = body2.createFixture(shape2, 1.0f);
        shape2.dispose();
        return body2;
    }

    public static Body createPot(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + 8f, y + 8f);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(8f);
        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    public static Body createPotion(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(5.5f);

        Fixture potionFixture = body.createFixture(shape, 0.8f);
        potionFixture.setSensor(true);
        shape.dispose();
        return body;
    }

    public static Body createCoin(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(3.5f);

        Fixture coinFixture = body.createFixture(shape, 0.8f);
        coinFixture.setUserData("Coin");
        coinFixture.setSensor(true);
        shape.dispose();
        return body;
    }

    public Body createBoneBody(World world, Body skull, float x, float y) {
        Body boneBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(skull.getPosition().x, skull.getPosition().y);
        bodyDef.fixedRotation = false;
        boneBody = world.createBody(bodyDef);
        PolygonShape boneShape = new PolygonShape();
        boneShape.setAsBox(5f, 2.5f);
        //was 5.5, 3.5
        Fixture boneHitbox = boneBody.createFixture(boneShape, 0.8f);
        boneShape.dispose();
        boneHitbox.setUserData("Bone");
        boneHitbox.setSensor(true);
        return boneBody;
    }

    public Body createWebBody(World world, Body web, float x, float y, float angle) {
        Body webBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(web.getPosition().x, web.getPosition().y);
        bodyDef.angle = angle;
        bodyDef.fixedRotation = true;
        webBody = world.createBody(bodyDef);
        CircleShape webShape = new CircleShape();
        //webShape.setAsBox(8f, 8f);
        webShape.setRadius(8f);
        Fixture webHitbox = webBody.createFixture(webShape, 0.8f);
        webShape.dispose();
        webHitbox.setSensor(true);
        webHitbox.setUserData("Web");
        return webBody;
    }

    public Body createSwordBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createSwordHitbox(Body sword, int d){
        PolygonShape swordShape = new PolygonShape();
        PolygonShape tipShape = new PolygonShape();
        PolygonShape bladeShape = new PolygonShape();
        switch (d) {
            case 1:
                bladeShape.set(new float[]{-2.5f, -5, -2.5f, 5, 0, 7, 2.5f, 5, 2.5f, -5, -2.5f, -5});
                break;
            case 2:
                bladeShape.set(new float[]{-5, 2.5f, 5, 2.5f, 7, 0, 5, -2.5f, -5, -2.5f, -5, 2.5f});
                break;
            case 3:
                bladeShape.set(new float[]{2.5f, 5, 2.5f, -5, 0, -7, -2.5f, -5, -2.5f, 5, 2.5f, 5});
                break;
            case 4:
                bladeShape.set(new float[]{5, -4f, -5,-4f, -7, -1.5f, -5, 1f, 5, 1f, 5, -4f});
                break;
        }


        //PolygonShape minoBottomSlope = new PolygonShape();
       // tipShape.set(new float[]{-9,-6,0,-9,9,-6,-9,-6});
        //tipShape.set(new float[]{-9,-6,0,-9,9,-6,-9,-6});
        //Fixture tipB = sword.createFixture(tipShape,1f);
        //Fixture minoB = body.createFixture(minoBottomSlope, 1.0f);
        //minoB.setUserData("BossMinotaur");

        Fixture swordHitbox = sword.createFixture(bladeShape, 0.8f);

        bladeShape.dispose();
        swordHitbox.setSensor(true);
        swordHitbox.setUserData("Sword");
        return swordHitbox;
    }

    public Body createShieldBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
       // bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createShieldHitbox(Body shield,boolean r){
        PolygonShape shieldShape = new PolygonShape();
        if (r){
            shieldShape.setAsBox(2.5f, 9f);
        }
        else {
            shieldShape.setAsBox(9f, 2.5f);
        }
        Fixture shieldHitbox = shield.createFixture(shieldShape, 1.0f);
        shieldShape.dispose();
        shieldHitbox.setUserData("Shield");
        return shieldHitbox;
    }

    public Body createChiselBody(World world, Body player, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(player.getPosition().x+x, player.getPosition().y+y);
        // bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef);
        body.setUserData("Chisel");
        return body;
    }

    public Fixture createChiselHitbox(Body chisel,boolean r){
        PolygonShape chiselShape = new PolygonShape();
        if (r){
            chiselShape.setAsBox(6f, 2.5f);
        }
        else {
            chiselShape.setAsBox(2.5f, 6f);
        }
        Fixture chiselHitbox = chisel.createFixture(chiselShape, 0.8f);
        chiselShape.dispose();
        chiselHitbox.setSensor(true);
        chiselHitbox.setUserData("Chisel");
        return chiselHitbox;
    }

    public Body createSimpleDynamicBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Body createSimpleStaticBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }


    public Body createSkullBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createSkullHitbox(Body body, float r) {
        CircleShape skullShape = new CircleShape();
        skullShape.setRadius(r);
        Fixture skullHitbox = body.createFixture(skullShape, 0.8f);
        skullShape.dispose();
        skullHitbox.setUserData("SkullHitbox");
        skullHitbox.setSensor(true);
        return skullHitbox;
    }

    public Fixture createMinotaurHitbox(Body body, float r){
       // CircleShape enemyShape = new CircleShape();
       // enemyShape.setRadius(r);
       // Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        //enemyShape.dispose();
        //enemyHitbox.setUserData("EnemyHitbox");

        PolygonShape minoShape1 = new PolygonShape();
        PolygonShape minoShape2 = new PolygonShape();
        PolygonShape minoShape3 = new PolygonShape();
        PolygonShape minoHitboxShape = new PolygonShape();
        CircleShape minoCornerShape1 = new CircleShape();
        CircleShape minoCornerShape2 = new CircleShape();
        CircleShape minoCornerShape3 = new CircleShape();
        CircleShape minoCornerShape4 = new CircleShape();
        minoCornerShape1.setRadius(3);
        minoCornerShape2.setRadius(3);
        minoCornerShape3.setRadius(3);
        minoCornerShape4.setRadius(3);
        Vector2 vec1 = new Vector2(minoCornerShape1.getPosition().x+7,minoCornerShape1.getPosition().y+3);
        Vector2 vec2 = new Vector2(minoCornerShape1.getPosition().x-7,minoCornerShape1.getPosition().y-3);
        Vector2 vec3 = new Vector2(minoCornerShape1.getPosition().x-7,minoCornerShape1.getPosition().y+3);
        Vector2 vec4 = new Vector2(minoCornerShape1.getPosition().x+7,minoCornerShape1.getPosition().y-3);
        minoCornerShape1.setPosition(vec1);
        minoCornerShape2.setPosition(vec2);
        minoCornerShape3.setPosition(vec3);
        minoCornerShape4.setPosition(vec4);
        Fixture minoCornerHitbox1 = body.createFixture(minoCornerShape1,1.0f);
        Fixture minoCornerHitbox2 = body.createFixture(minoCornerShape2,1.0f);
        Fixture minoCornerHitbox3 = body.createFixture(minoCornerShape3,1.0f);
        Fixture minoCornerHitbox4 = body.createFixture(minoCornerShape4,1.0f);

        minoShape1.setAsBox(8f, 5.98f);
        minoShape2.setAsBox(10f, 4f);
        minoShape3.setAsBox(4f, 4f);

        Vector2 vec5 = new Vector2(minoCornerShape1.getPosition().x-7,minoCornerShape1.getPosition().y+2);
        minoHitboxShape.setAsBox(12f, 12f,vec5,0);


        Fixture minoHitbox = body.createFixture(minoShape1, 1.0f);
        Fixture minoHitbox2 = body.createFixture(minoShape2, 1.0f);
        Fixture minoBoundHitbox = body.createFixture(minoShape3, 1.0f);

        Fixture minoDamageHitbox = body.createFixture(minoHitboxShape, 1.0f);

        minoDamageHitbox.setSensor(true);

        //minoBoundHitbox.setSensor(true);

        //PolygonShape minoBottomSlope = new PolygonShape();
        //minoBottomSlope.set(new float[]{-9,-6,0,-9,9,-6,-9,-6});
        //Fixture minoB = body.createFixture(minoBottomSlope, 1.0f);
        //minoB.setUserData("BossMinotaur");

        body.setUserData("Enemy");
        minoDamageHitbox.setUserData("BossMinotaur");
        minoCornerHitbox1.setUserData("BossMinotaur");
        minoCornerHitbox2.setUserData("BossMinotaur");
        minoCornerHitbox3.setUserData("BossMinotaur");
        minoCornerHitbox4.setUserData("BossMinotaur");
        minoHitbox.setUserData("BossMinotaur");
        minoHitbox2.setUserData("BossMinotaur");
        minoBoundHitbox.setUserData("Enemy");
        minoHitboxShape.dispose();
        minoCornerShape1.dispose();
        minoCornerShape2.dispose();
        minoCornerShape3.dispose();
        minoCornerShape4.dispose();
        minoShape1.dispose();
        minoShape2.dispose();
        minoShape3.dispose();

        return minoBoundHitbox;
    }

    public Fixture createEnemyHitbox(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyHitbox.setUserData("EnemyHitbox");
        return enemyHitbox;
    }

    public Fixture createEnemyDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyDetectionHitbox = body.createFixture(enemyShape, 0.8f);
        enemyShape.dispose();
        enemyDetectionHitbox.setUserData("Proximity");
        enemyDetectionHitbox.setSensor(true);
        return enemyDetectionHitbox;
    }

    public Fixture createSpawnerDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture spawner = body.createFixture(enemyShape, 0.8f);
        enemyShape.dispose();
        spawner.setUserData("Spawner");
        spawner.setSensor(true);
        return spawner;
    }

    public Body createShopBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Body createTutorialBody(World world, float x, float y) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);
        //bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        return body;
    }

    public Fixture createShopHitbox(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        // PolygonShape enemyShape = new PolygonShape();
        // enemyShape.setAsBox(x, y);
        Fixture enemyHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyHitbox.setUserData("ShopHitbox");
        return enemyHitbox;
    }

    public Fixture createShopDetectionRadius(Body body, float r){
        CircleShape enemyShape = new CircleShape();
        enemyShape.setRadius(r);
        Fixture enemyDetectionHitbox = body.createFixture(enemyShape, 1.0f);
        enemyShape.dispose();
        enemyDetectionHitbox.setUserData("ShopSell");
        enemyDetectionHitbox.isSensor();
        return enemyDetectionHitbox;
    }

    public static Fixture createRoom(int roomIndex, World world, int roomX, int roomY, int h, int w){
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(roomX, roomY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape roomShape = new PolygonShape();
        roomShape.setAsBox(w, h);
        Fixture roomHitbox = body.createFixture(roomShape, 1.0f);
        roomShape.dispose();
        body.setUserData("Room-"+roomIndex);
        roomHitbox.setUserData("Room-"+roomIndex);
        //roomHitbox.isSensor();
        roomHitbox.setSensor(true);
        return roomHitbox;
    }


    public Body createPlayerBody(World world, float playerX, float playerY) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerX, playerY);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        PolygonShape playerShape1 = new PolygonShape();
        PolygonShape playerShape2 = new PolygonShape();
        PolygonShape playerShape3 = new PolygonShape();
        CircleShape playerCornerShape1 = new CircleShape();
        CircleShape playerCornerShape2 = new CircleShape();
        CircleShape playerCornerShape3 = new CircleShape();
        CircleShape playerCornerShape4 = new CircleShape();
        playerCornerShape1.setRadius(3);
        playerCornerShape2.setRadius(3);
        playerCornerShape3.setRadius(3);
        playerCornerShape4.setRadius(3);
        Vector2 vec1 = new Vector2(playerCornerShape1.getPosition().x+3,playerCornerShape1.getPosition().y+3);
        Vector2 vec2 = new Vector2(playerCornerShape1.getPosition().x-3,playerCornerShape1.getPosition().y-3);
        Vector2 vec3 = new Vector2(playerCornerShape1.getPosition().x-3,playerCornerShape1.getPosition().y+3);
        Vector2 vec4 = new Vector2(playerCornerShape1.getPosition().x+3,playerCornerShape1.getPosition().y-3);
        playerCornerShape1.setPosition(vec1);
        playerCornerShape2.setPosition(vec2);
        playerCornerShape3.setPosition(vec3);
        playerCornerShape4.setPosition(vec4);

        playerShape1.setAsBox(3f, 5.98f);
        playerShape2.setAsBox(5.98f, 3f);

        //player bounds for room door locking
        playerShape3.setAsBox(6.2f, 6.2f);

        Fixture playerHitbox = body.createFixture(playerShape1, 1.0f);
        Fixture playerHitbox2 = body.createFixture(playerShape2, 1.0f);

        Fixture playerBoundHitbox = body.createFixture(playerShape3, 1.0f);
        playerBoundHitbox.setSensor(true);

        Fixture playerCornerHitbox1 = body.createFixture(playerCornerShape1,1.0f);
        Fixture playerCornerHitbox2 = body.createFixture(playerCornerShape2,1.0f);
        Fixture playerCornerHitbox3 = body.createFixture(playerCornerShape3,1.0f);
        Fixture playerCornerHitbox4 = body.createFixture(playerCornerShape4,1.0f);

        body.setUserData("Player");
        playerCornerHitbox1.setUserData("Player");
        playerCornerHitbox2.setUserData("Player");
        playerCornerHitbox3.setUserData("Player");
        playerCornerHitbox4.setUserData("Player");
        playerHitbox.setUserData("Player");
        playerHitbox2.setUserData("Player");
        playerBoundHitbox.setUserData("PlayerBound");
        playerCornerShape1.dispose();
        playerCornerShape2.dispose();
        playerCornerShape3.dispose();
        playerCornerShape4.dispose();
        playerShape1.dispose();
        playerShape2.dispose();
        playerShape3.dispose();
        return body;
    }
}
