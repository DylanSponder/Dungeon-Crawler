package com.mygdx.game.level.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.box2D.BodyFactory;

import java.util.ArrayList;

public class Column {

    public ArrayList<ColumnPiece> columnPieces;
    public boolean visible;
    public Body stemBody;
    public float alpha;



    public Column() {
        this.visible = true;
        this.alpha = 100;
        this.columnPieces = new ArrayList<>();

    }


    public void addPiece(ColumnPiece piece) {

        columnPieces.add(piece);

    }

    public void createColumnHitbox(int size, boolean fullbase, float columnX, float columnY, World world) {
        BodyFactory bodyFactory = new BodyFactory();

        stemBody = bodyFactory.createColumnHitbox(world, columnX, columnY, size, fullbase);
        stemBody.setUserData("Stem");

    }

    public static void renderPiece(SpriteBatch batch, TextureRegion tex, float x, float y, boolean visible, float alpha, Column c) {// int alpha


        batch.setColor(1, 1, 1, alpha/100);
        batch.draw(tex, x, y);
    }
}
