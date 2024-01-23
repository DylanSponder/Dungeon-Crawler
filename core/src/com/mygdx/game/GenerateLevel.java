package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entity.CreateBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateLevel {
    public List generateLevel(World world,float PLAYER_X,float PLAYER_Y) {

        //load level
        LevelParser lp = new LevelParser();
        RenderRules r = new RenderRules();
        final CreateCell stc = new CreateCell();
        final CreateBody cr = new CreateBody();
        final CreateTexture tx = new CreateTexture();
        tx.textureRegionBuilder();

        stc.middleFloorTile.setTile(new StaticTiledMapTile(tx.roomMiddleFloorTexture));
        stc.topLeftWallTile.setTile(new StaticTiledMapTile(tx.roomTopLeftWallTexture));
        stc.topWallTile.setTile(new StaticTiledMapTile(tx.roomTopWallTexture));
        stc.topRightWallTile.setTile(new StaticTiledMapTile(tx.roomTopRightWallTexture));
        stc.leftWallTile.setTile(new StaticTiledMapTile(tx.roomLeftWallTexture));
        stc.rightWallTile.setTile(new StaticTiledMapTile(tx.roomRightWallTexture));
        stc.bottomLeftWallTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftWallTexture));
        stc.bottomWallTile.setTile(new StaticTiledMapTile(tx.roomBottomWallTexture));
        stc.bottomRightWallTile.setTile(new StaticTiledMapTile(tx.roomBottomRightWallTexture));
        stc.topLeftTurnTile.setTile(new StaticTiledMapTile(tx.roomTopLeftTurnTexture));
        stc.topRightTurnTile.setTile(new StaticTiledMapTile(tx.roomTopRightTurnTexture));
        stc.bottomLeftTurnTile.setTile(new StaticTiledMapTile(tx.roomBottomLeftTurnTexture));
        stc.bottomRightTurnTile.setTile(new StaticTiledMapTile(tx.roomBottomRightTurnTexture));

        //initialize map
        TiledMap map = new TiledMap();

        //set map layer dimensions
        //set to 1000 tile layers wide and high but can be changed if required
        TiledMapTileLayer layer = new TiledMapTileLayer(1000, 1000, 16, 16);

        try {
            List<List<String>> level = lp.read("room.csv");

            //levelY is what determines the size of the level.
            //When levelY is either 1000 or 0 the map will be outside the TiledMapTileLayer and thus will not render
            int levelY = 999;
            int levelSize = level.size();
            for (int i = 0; i < levelSize; i++) {
                List<String> levelTextures = r.translateSymbols(level, i);
                int layerSize = levelTextures.size();
                for (int i2 = 0; i2 < layerSize; i2++) {
                    TiledMapTileLayer.Cell currentCell = new TiledMapTileLayer.Cell();
                    switch (levelTextures.get(i2)) {
                        case "middleFloorTile":
                            currentCell = stc.middleFloorTile;
                            break;
                        case "topLeftWallTile":
                            currentCell = stc.topLeftWallTile;
                            Body newTopLeftWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "topWallTile":
                            currentCell = stc.topWallTile;
                            Body newTopWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "topRightWallTile":
                            currentCell = stc.topRightWallTile;
                            Body newTopRightWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "leftWallTile":
                            currentCell = stc.leftWallTile;
                            Body newLeftWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "rightWallTile":
                            currentCell = stc.rightWallTile;
                            Body newRightWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "bottomLeftWallTile":
                            currentCell = stc.bottomLeftWallTile;
                            Body newBottomLeftWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "bottomWallTile":
                            currentCell = stc.bottomWallTile;
                            Body newBottomWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "bottomRightWallTile":
                            currentCell = stc.bottomRightWallTile;
                            Body newBottomRightWall = cr.createWall(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16);
                            break;
                        case "topLeftTurnTile":
                            currentCell = stc.topLeftTurnTile;
                            Body newTopLeftTurn = cr.createWallTurn(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 0.1f);
                            break;
                        case "topRightTurnTile":
                            currentCell = stc.topRightTurnTile;
                            Body newTopRightTurn = cr.createWallTurn(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 0.1f);
                            break;
                        case "bottomLeftTurnTile":
                            currentCell = stc.bottomLeftTurnTile;
                            Body newBottomLeftTurn = cr.createWallTurn(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 15.9f, 15.9f);
                            break;
                        case "bottomRightTurnTile":
                            currentCell = stc.bottomRightTurnTile;
                            Body newBottomRightTurn = cr.createWallTurn(world,(i2 * 16) + 16 * 16, levelY * 16 + Gdx.graphics.getHeight() / 30 - 16, 0.1f, 15.9f);
                            break;
                    }
                    layer.setCell(i2 + 16, levelY, currentCell);
                }
                levelY--;
            }
            //set player starting coordinates according to the position of the level
            //TODO change levelY to something like room[0].levelY when multiple room support is added so the player spawns in the first generated room of the level.
            PLAYER_X = Gdx.graphics.getWidth() / 30 * 16;
            PLAYER_Y = levelY * 16 + levelSize * 16 - 16;

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        //add current layers to the TileMap and assign it a renderer
        List list = new ArrayList();
        list.add(layer);
        list.add(PLAYER_X);
        list.add(PLAYER_Y);
        return list;
    }
}
