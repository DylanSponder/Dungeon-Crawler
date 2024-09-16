package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;

public class Column {
    //columns must have a base and a top and one or more stems
    //3 different types of tops, one type of base and one undamaged/two alternative cracked stems
    //when the player touches it, they remove top and stem sprites, base sprites appear like obstacles
    //uses lock logic to remove the sprite

    public float columnX, columnY;
    public World world;
    public Body columnBody;
    public boolean visible;
    public int type; //1-9 types
    public ArrayList<Column> localColumn;

    public Column(World world, float x, float y, int type) {
        this.world = world;
        this.columnX = x;
        this.columnY = y;
        this.visible = false;
        this.type = type;
    }

    public void createColumnTop(boolean solid) {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        //this.columnBody = bodyFactory.createWall(world, columnX, columnY);

        DungeonCrawler.columns.add(this);

        this.localColumn = new ArrayList<>();

        //since levels generate top-down so a column top indicates a new column has been started
        this.localColumn.add(this);

        //this.columnBody.setUserData("Column");

        //potArrayMap.put(potBody, this);

        //this.potCreated = true;

        //return this.columnBody;
    }

    public void createColumnStem(boolean solid) {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        DungeonCrawler.columns.add(this);

        //this.columnBody = bodyFactory.createPot(world, potX, potY);

        //ColumnTop col1 = new ColumnTop(world, columnX, columnY, type, solid);

        //DungeonCrawler.columnTops.add(col1);

        //this.columnBody.setUserData("Column");

        //potArrayMap.put(potBody, this);

        //this.potCreated = true;

        //return this.columnBody;
    }

    public void createColumnBase() {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        DungeonCrawler.columns.add(this);



        this.columnBody = bodyFactory.createColumnBase(world, columnX, columnY);

        //ColumnTop col1 = new ColumnTop(world, columnX, columnY, type, solid);

        //DungeonCrawler.columnTops.add(col1);

        this.columnBody.setUserData("Column");

        //potArrayMap.put(potBody, this);

        //this.potCreated = true;

        //return this.columnBody;
    }

    public void createPedestal() {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        DungeonCrawler.columns.add(this);

        this.columnBody = bodyFactory.createColumnBase(world, columnX, columnY);

        //ColumnTop col1 = new ColumnTop(world, columnX, columnY, type, solid);

        //DungeonCrawler.columnTops.add(col1);

        this.columnBody.setUserData("Pedestal");

        //potArrayMap.put(potBody, this);

        //this.potCreated = true;

        //return this.columnBody;
    }
}






