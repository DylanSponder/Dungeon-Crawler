package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.box2D.BodyFactory;

public class Column {
    //columns must have a base and a top and one or more stems
    //3 different types of tops, one type of base and one undamaged/two alternative cracked stems
    //when the player touches it, they remove top and stem sprites, base sprites appear like obstacles
    //uses lock logic to remove the sprite

    public float columnX, columnY;
    private World world;
    public Body columnBody;
    public boolean visible;
    public int type; //1-9 types

    public Column(World world, float x, float y, int type) {
        this.world = world;
        this.columnX = x;
        this.columnY = y;
        this.visible = false;
        this.type = type;
    }

    public Body createColumn() {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        //this.columnBody = bodyFactory.createPot(world, potX, potY);

        this.columnBody.setUserData("Column");

        //potArrayMap.put(potBody, this);

        //this.potCreated = true;

        return this.columnBody;
    }
}

class ColumnBase extends Column {

    private Fixture baseHitbox;
    public ColumnBase(World world, float x, float y, int type) {
        super(world, x, y, type);

    }
    public Body createColumnBase(Body body){
        return body;
    }
}

class ColumnStem extends Column {

    public ColumnStem(World world, float x, float y, int type) {
        super(world, x, y, type);
    }
}

class ColumnTop extends Column {

    public ColumnTop(World world, float x, float y, int type) {
        super(world, x, y, type);
    }
}
