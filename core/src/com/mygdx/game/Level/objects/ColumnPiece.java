package com.mygdx.game.level.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DungeonCrawler;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;

public class ColumnPiece {
    //columnPieces must have a base and a top and one or more stems
    //3 different types of tops, one type of base and one undamaged/two alternative cracked stems
    //when the player touches it, they remove top and stem sprites, base sprites appear like obstacles
    //uses lock logic to remove the sprite

    public float columnX, columnY, alpha;
    public World world;
    public Body columnBody, stemBody;
    public boolean visible, lowerCreated;
    public int type; //1-X types
    public ArrayList<ColumnPiece> localColumn;
    public boolean loweredAlpha, base, stem, top;

    public ColumnPiece(World world, float x, float y, int type) {
        this.world = world;
        this.columnX = x;
        this.columnY = y;
        this.visible = true;
        this.type = type;
        this.alpha = 100;
    }

    public void createColumnTop(boolean solid) {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        //this.columnBody = bodyFactory.createWall(world, columnX, columnY);

        DungeonCrawler.columnPieces.add(this);


    }

    public void createColumnStem(boolean solid) {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        DungeonCrawler.columnPieces.add(this);


    }

    public void createColumnHitbox(int size, boolean fullbase) {
        BodyFactory bodyFactory = new BodyFactory();

        stemBody = bodyFactory.createColumnHitbox(this.world, this.columnX, this.columnY, size, fullbase);
        stemBody.setUserData("Stem");

    }

    public void createColumnBase() {

        DungeonCrawler.columnPieces.add(this);
        //this.columnBody.setUserData("Wall");

    }

    public void createColumnBaseLower(int type) {

        BodyFactory bodyFactory = new BodyFactory();

        DungeonCrawler.columnPieces.add(this);
        if (type == 1) {
            this.columnBody = bodyFactory.createColumnBase(world, columnX, columnY);
        } else if (type == 2) {
            this.columnBody = bodyFactory.createColumnBase2(world, columnX, columnY);
        }
        else if (type == 3) {
            this.columnBody = bodyFactory.createColumnBase2(world, columnX, columnY);

        }
        else if (type == 4) {
            this.columnBody = bodyFactory.createColumnBase(world, columnX, columnY);
        }

        this.columnBody.setUserData("Wall");

    }

    public void createPedestal() {
        //creates and activates the pots hitbox for collisions
        BodyFactory bodyFactory = new BodyFactory();

        DungeonCrawler.columnPieces.add(this);

        this.columnBody = bodyFactory.createPedestal(world, columnX, columnY+2);

        this.columnBody.setUserData("Pedestal");

    }
}






